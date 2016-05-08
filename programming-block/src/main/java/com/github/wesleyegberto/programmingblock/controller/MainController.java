package com.github.wesleyegberto.programmingblock.controller;

import com.github.wesleyegberto.programmingblock.component.*;
import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Wesley Egberto on 21/04/16.
 * 
 * Thanks to http://stackoverflow.com/users/1862915/badisi for helping with a drag-n-drop
 * more smooth (http://stackoverflow.com/questions/13624491/implement-drag-and-drop-like-in-scene-builder)
 */
public class MainController implements Initializable {
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
		paneCode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		VBox.setVgrow(paneCode, Priority.ALWAYS);

		// Criação do bloco principal
		ProgramBlock programBlock = ProgramBlock.createBuilder()
				.setHeaderBackgroundImage("/images/programa/header_programa.png")
				.setLeftBackgroundImage("/images/programa/left_bar_programa.png")
				.setFooterBackgroundImage("/images/programa/footer_programa.png")
				.setTextImagePath("/images/programa/texto_programa.png")
				.setIsTemplate(true).build();
		boxCode.getChildren().add(programBlock);

		initializeDragEventsTarget(programBlock);
		
		Block[] defaultCommands = {
			// Comandos
			new MovementCommandBlockBuilder().setTextImage("/images/texto_avancar.png")
				.setCommandName("AVANÇAR").setCode("avancar(:param)").setTranslationX(0, 100)
				.setHasParameter(true).setTemplate(true).build(),
			new MovementCommandBlockBuilder().setTextImage("/images/texto_recuar.png")
				.setCommandName("RECUAR").setCode("recuar(:param)").setTranslationX(100, 0)
				.setHasParameter(true).setTemplate(true).build(),

			// Parâmetros e Operandos
			ValueParamBlock.createBuilder().setBackgroundImage("/images/param_value.png").setTemplate(true).build(),
			FunctionOperandBlock.createBuilder()
				.setBackgroundImage("/images/funcao_medir_distancia.png").setWidth(130d)
				.setTemplate(true).setFunctionName("medirDistancia").build(),

			// Operadores
			/*
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operacao_adicao.png")
				.setTemplate(true).setType(RelationalOperatorBlock.OperatorType.ADDITION).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operacao_subtracao.png")
				.setTemplate(true).setType(RelationalOperatorBlock.OperatorType.SUBTRACTION).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operacao_multiplicacao.png")
				.setTemplate(true).setType(RelationalOperatorBlock.OperatorType.MULTIPLICATION).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operacao_divisao.png")
				.setTemplate(true).setType(RelationalOperatorBlock.OperatorType.DIVISION).build(),
			*/
			// Operadores relacionais
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_maior.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.GREATER).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_maior_igual.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.GREATER_EQUALS).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_menor.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.LOWER).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_menor_igual.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.LOWER_EQUALS).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_igual.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.EQUALS).build(),
			RelationalOperatorBlock.createBuilder()
				.setBackgroundImage("/images/param_comando.png").setOperationImage("/images/operador_diferente.png")
				.setTemplate(true).setType(RelationalOperatorBlock.RelationalOperatorType.NOT_EQUALS).build(),

			// Estrutura de controle
			IfBlock.createBuilder()
				.setHeaderBackground("/images/se/header.png")
				.setLeftBarBackground("/images/se/left_bar.png")
				.setFooterBackground("/images/se/footer.png")
				.setTextIfImage("/images/se/texto_se.png")
				.setTextThenImage("/images/se/texto_entao.png")
				.setOperandImage("/images/param_comando.png")
				.setTemplate(true)
				.build()
		};
		
		
		for (Block command : defaultCommands) {
			addEventsForDraggableBlock(command);
			commandToolbox.getChildren().add(command);
		}
	}

	private void initializeDragEventsTarget(final FluxControlBlock block) {
		//final Region droppablePane = block.getPaneCode();
		final VBox droppableArea = block.getBoxCode();

		droppableArea.setOnMouseDragEntered(evt -> {
			//System.out.println("Mouse drag entered at inner " + block);
			droppableArea.setStyle("-fx-border-color:red;-fx-border-width:2;-fx-border-style:solid;");
			evt.consume();
		});
		droppableArea.setOnMouseDragExited(evt -> {
			//System.out.println("Mouse drag exited from inner " + block);
			droppableArea.setStyle("-fx-border-style:none;");
			evt.consume();
		});
		droppableArea.setOnMouseDragReleased(evt -> {
			//System.out.println("Mouse drag released inner: " + block);
			if(!clipboard.hasValue()) {
				evt.consume();
				return;
			}
			if(clipboard.getValue() instanceof CommandBlock || clipboard.getValue() instanceof FluxControlBlock) {
				//System.out.println(clipboard.getValue() + " was released at " + block);
				if(block instanceof ProgramBlock && clipboard.getValue().isTemplate()) {
					Block newBlock = cloneBlockFromToolbox(evt, clipboard.getValue());
					block.addBlock(newBlock);
				} else {
					setDraggedBlockToTarget(evt, block, true, clipboard.getValue());
				}
			}
			clipboard.clear();
			evt.consume();
		});
	}

	private void addEventsForDraggableBlock(final Block block) {
		block.setOnDragDetected(evt -> {
			//System.out.println(block + " started been dragged");
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
			//System.out.println("Mouse Released from " + dragItem);
			//clipboard.setValue(block);
			dragImageView.setMouseTransparent(true);
			block.setMouseTransparent(true);
			block.setCursor(Cursor.CLOSED_HAND);
			evt.consume();
		});
		block.setOnMouseReleased(evt -> {
			//System.out.println("Mouse Released from " + dragItem);
			dragImageView.setMouseTransparent(false);
			block.setMouseTransparent(false);
			block.setCursor(Cursor.DEFAULT);
			sceneRoot.getChildren().remove(dragImageView);
			clipboard.clear();
			evt.consume();
		});
	}
	
	private void addEventsForTargetBlock(final Block block) {
		// Events as a target
		block.setOnMouseDragEntered(evt -> {
			//System.out.println("Mouse entered: " + block);
			block.setStyle("-fx-border-color:red;-fx-border-width:1;-fx-border-style:solid;");
			evt.consume();
		});
		block.setOnMouseDragExited(evt -> {
			//System.out.println("Mouse exited: " + block);
			block.setStyle("-fx-border-style:none;");
			evt.consume();
		});
		block.setOnMouseDragReleased(evt -> {
			//System.out.println("Mouse drag released: " + block);
			if(!clipboard.hasValue()) {
				evt.consume();
				return;
			}
			if(clipboard.getValue() instanceof CommandBlock || clipboard.getValue() instanceof FluxControlBlock) {
				//System.out.println("Item " + clipboard.getValue() + " released at " + block);
				setDraggedBlockToTarget(evt, block, false, clipboard.getValue());
				clipboard.clear();
				sceneRoot.getChildren().remove(dragImageView);
				evt.consume();
			}
		});
	}

	private void setDraggedBlockToTarget(MouseDragEvent evt, Block targetBlock, boolean draggedToInner, Block draggedBlock) {
		if(draggedBlock.isTemplate()) { // Cria bloco a partir do template
			draggedBlock = cloneBlockFromToolbox(evt, draggedBlock);
		} else { // Já está criado, apenas move
			// Retira e coloca o item após o item em que foi droppado
			FluxControlBlock sourceParent = getParenteBlock(draggedBlock.getParent());
			if(sourceParent != null) {
				sourceParent.removeBlock(draggedBlock);
			} else {
				VBox parentSource = (VBox) draggedBlock.getParent();
				parentSource.getChildren().remove(draggedBlock);
			}
		}
		// Adiciona no target
		FluxControlBlock parentBlock;
		if(draggedToInner && targetBlock instanceof FluxControlBlock) {
			parentBlock = (FluxControlBlock) targetBlock;
		} else {
			parentBlock = getParenteBlock(targetBlock.getParent());
		}
		if(parentBlock == null) {
			VBox parentTarget = (VBox) targetBlock.getParent();
			int index = parentTarget.getChildren().indexOf(targetBlock);
			//System.out.println("\tDropped in VBox at: " + index);
			parentTarget.getChildren().add(index + 1, draggedBlock);
		} else {
			parentBlock.addBlockAfter(draggedBlock, targetBlock);
		}
		// faz com que o bloco pare de ignorar MouseEvents
		draggedBlock.setMouseTransparent(false);
	}

	private FluxControlBlock getParenteBlock(Parent parent) {
		if(parent instanceof FluxControlBlock)
			return (FluxControlBlock) parent;
		else if(parent != null && parent.getParent() != null)
			return getParenteBlock(parent.getParent());
		return null;
	}

	private Block cloneBlockFromToolbox(MouseDragEvent evt, Block draggedBlock) {
		Block newBlock = draggedBlock.cloneBlock();
		// Seta a localização atual
		newBlock.setDragAnchor(evt.getSceneX(), evt.getSceneY());
		addEventsForDraggableBlock(newBlock);
		if (newBlock instanceof FluxControlBlock) {
			initializeDragEventsTarget((FluxControlBlock) newBlock);
		} else {
			addEventsForTargetBlock(newBlock);
		}
		return newBlock;
	}
}
