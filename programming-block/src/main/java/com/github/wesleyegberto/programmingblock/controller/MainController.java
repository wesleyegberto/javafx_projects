package com.github.wesleyegberto.programmingblock.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.wesleyegberto.programmingblock.component.*;
import javafx.scene.Parent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @author Wesley Egberto on 21/04/16.
 * 
 * Thanks to http://stackoverflow.com/users/1862915/badisi for helping with a drag-n-drop
 * more smooth (http://stackoverflow.com/questions/13624491/implement-drag-and-drop-like-in-scene-builder)
 */
public class MainController implements Initializable {
	Log logger = LogFactory.getLog(MainController.class.getSimpleName());

	@FXML
	private VBox commandToolbox;
	@FXML
	private ScrollPane paneCode;
	@FXML
	private VBox boxCode;

	private Clipboard clipboard = Clipboard.getInstance();

	private ImageView dragImageView = new ImageView();

	private BorderPane sceneRoot;

	public void setScene(Scene scene) {
		sceneRoot = ((BorderPane) scene.getRoot());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.debug("init");

		paneCode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		VBox.setVgrow(paneCode, Priority.ALWAYS);

		// Criação do bloco principal
		ProgramBlock programBlock = ProgramBlock.createBuilder()
				.setHeaderBackgroundImage("/images/programa/header_programa.png")
				.setLeftBackgroundImage("/images/programa/left_bar_programa.png")
				.setFooterBackgroundImage("/images/programa/footer_programa.png")
				.setTextImagePath("/images/texto_programa.png")
				.setIsTemplate(true).build();
		boxCode.getChildren().add(programBlock);

		initializeDragEventsTarget(programBlock);
		
		Block[] defaultCommands = {
			// Comandos
			new MovementCommandBlockBuilder().setTextImage("/images/texto_avancar.png")
				.setCommandName("AVANÇAR").setCode("avancar(:param)").setTranslationX(0, 100)
				.setHasParameter(true).setIsTemplate(true).build(),
			new MovementCommandBlockBuilder().setTextImage("/images/texto_recuar.png")
				.setCommandName("RECUAR").setCode("recuar(:param)").setTranslationX(100, 0)
				.setHasParameter(true).setIsTemplate(true).build(),

			// Parâmetros
			ValueParamBlock.createBuilder().setBackgroundImage("/images/param_value.png").setTemplate(true).build(),

			// Operandos de If, For e While
			FunctionParamBlock.createBuilder().setBackgroundImage("/images/funcao_medir_distancia.png").setTemplate(true).build(),

			// Estrutura de controle
			IfBlock.createBuilder()
				.setHeaderBackground("/images/controle_fluxo/header.png")
				.setLeftBarBackground("/images/controle_fluxo/left_bar.png")
				.setFooterBackground("/images/controle_fluxo/footer.png")
				.setTextIfImage("/images/controle_fluxo/se.png")
				.setTextThenImage("/images/controle_fluxo/entao.png")
				.setOperandImage("/images/controle_fluxo/operand.png")
				.setTemplate(true)
				.build()
		};
		
		
		for (Block command : defaultCommands) {
			addEventsForDraggableBlock(command);
			System.out.println(command);
			commandToolbox.getChildren().add(command);
		}
	}

	private void initializeDragEventsTarget(final FluxControlBlock block) {
		//final Region droppablePane = block.getPaneCode();
		final VBox droppableArea = block.getBoxCode();

		droppableArea.setOnMouseDragEntered(evt -> {
			//logger.debug("Program block entered");
			droppableArea.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;");
			evt.consume();
		});
		droppableArea.setOnMouseDragExited(evt -> {
			//logger.debug("Program block exited");
			droppableArea.setStyle("-fx-border-style:none;");
			evt.consume();
		});
		droppableArea.setOnMouseDragReleased(evt -> {
			if(!clipboard.hasValue() || !clipboard.getValue().isTemplate()) {
				return;
			}
			if(clipboard.getValue() instanceof CommandBlock || clipboard.getValue() instanceof FluxControlBlock) {
				logger.debug(clipboard.getValue() + " was released at " + block);
				Block newBlock = cloneBlockFromToolbox(evt);
				block.addBlock(newBlock);
				clipboard.clear();
				evt.consume();
			}
		});
	}

	private void addEventsForDraggableBlock(final Block block) {
		block.setOnDragDetected(evt -> {
			logger.debug(block + " started been dragged");
			clipboard.setValue(block);
			SnapshotParameters snapParams = new SnapshotParameters();
			snapParams.setFill(Color.TRANSPARENT);
			dragImageView.setImage(block.snapshot(snapParams, null));
			if(!sceneRoot.getChildren().contains(dragImageView)) {
				sceneRoot.getChildren().add(dragImageView);
			}
			dragImageView.startFullDrag();
			evt.consume();
		});
		block.setOnMouseDragged(evt -> {
			Point2D localPoint = sceneRoot.sceneToLocal(new Point2D(evt.getSceneX(), evt.getSceneY()));
			dragImageView.relocate((int) (localPoint.getX() - dragImageView.getBoundsInLocal().getWidth() / 2),
							 	   (int) (localPoint.getY() - dragImageView.getBoundsInLocal().getHeight() / 2));
			evt.consume();
		});
		block.setOnMousePressed(evt -> {
			//logger.debug("Mouse Released from " + dragItem);
			//clipboard.setValue(block);
			dragImageView.setMouseTransparent(true);
			block.setMouseTransparent(true);
			block.setCursor(Cursor.CLOSED_HAND);
		});
		block.setOnMouseReleased(evt -> {
			//logger.debug("Mouse Released from " + dragItem);
			dragImageView.setMouseTransparent(false);
			block.setMouseTransparent(false);
			block.setCursor(Cursor.DEFAULT);
			sceneRoot.getChildren().remove(dragImageView);
			clipboard.clear();
		});
	}
	
	private void addEventsForTargetBlock(final Block block) {
		// Events as a target
		block.setOnMouseDragEntered(evt -> {
			//logger.debug("Mouse entered: " + block);
			block.setStyle("-fx-border-color:red;-fx-border-width:1;-fx-border-style:solid;");
			evt.consume();
		});
		block.setOnMouseDragExited(evt -> {
			block.setStyle("-fx-border-style:none;");
			evt.consume();
		});
		block.setOnMouseDragReleased(evt -> {
			if(!clipboard.hasValue())
				return;
			if(clipboard.getValue() instanceof CommandBlock || clipboard.getValue() instanceof FluxControlBlock) {
				logger.debug("Item " + clipboard.getValue() + " released at " + block);
				Block newBlock = clipboard.getValue();

				if(newBlock.isTemplate()) { // Cria bloco a partir do template
					newBlock = cloneBlockFromToolbox(evt);
				} else { // Já está criado, apenas move
					// Retira e coloca o item após o item em que foi droppado
					FluxControlBlock sourceParent = getParenteBlock(clipboard.getValue().getParent());
					if(sourceParent != null) {
						sourceParent.removeBlock(newBlock);
					} else {
						VBox parentSource = (VBox) newBlock.getParent();
						parentSource.getChildren().remove(newBlock);
					}
				}
				// Adiciona no target
				FluxControlBlock parentBlock = getParenteBlock(block.getParent());
				if(parentBlock == null) {
					VBox parentTarget = (VBox) block.getParent();
					int index = parentTarget.getChildren().indexOf(block);
					System.out.println("\tDropped in VBox at: " + index);
					parentTarget.getChildren().add(index + 1, newBlock);
				} else {
					parentBlock.addBlockAfter(newBlock, block);
				}
				// faz com que o bloco pare de ignorar MouseEvents
				newBlock.setMouseTransparent(false);
				clipboard.clear();
				sceneRoot.getChildren().remove(dragImageView);
				evt.consume();
			}
		});
	}

	private FluxControlBlock getParenteBlock(Parent parent) {
		if(parent instanceof FluxControlBlock)
			return (FluxControlBlock) parent;
		else if(parent != null && parent.getParent() != null)
			return getParenteBlock(parent.getParent());
		return null;
	}

	private Block cloneBlockFromToolbox(MouseDragEvent evt) {
		Block newBlock = clipboard.getValue().cloneBlock();
		// Seta a localização atual
		newBlock.setDragAnchor(evt.getSceneX(), evt.getSceneY());
		addEventsForDraggableBlock(newBlock);
		addEventsForTargetBlock(newBlock);
		if (newBlock instanceof FluxControlBlock) {
			initializeDragEventsTarget((FluxControlBlock) newBlock);
		} else {
		}
		return newBlock;
	}
}
