package com.github.wesleyegberto.programmingblock.controller;

import com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component.Block;
import com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component.CommandBlock;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class MainController {
	Log logger = LogFactory.getLog(MainController.class.getSimpleName());

	private Scene scene;

	@FXML
	private Pane paneToolbox;
	@FXML
	private VBox commandToolbox;
	@FXML
	private Pane paneCode;
	@FXML
	private VBox boxCode;

	private Block lastCreatedBlock;

	public void initialize() {
		logger.debug("init");

		initializeDragEventsTarget();
		Block[] defaultCommands = {
			new CommandBlock("/images/comando_avancar_fundo.png", "viraEsquerda()", 200f, 100f, true),
			new CommandBlock("/images/comando_recuar_fundo.png", "viraEsquerda()", 200f, 100f, true)
		};
		for(Block command : defaultCommands) {
			// Cria o evento para duplicar o Block
			command.setOnMouseDragged(evt -> {
				//logger.debug("Mouse dragged event from toolbox");
				// Dispara o evento para o bloco recém criado
				MouseEvent mouseEvent = evt.copyFor(lastCreatedBlock, lastCreatedBlock.getBackground());
				lastCreatedBlock.fireEvent(mouseEvent);
				evt.consume();
			});
			command.setOnMousePressed(evt -> {
				logger.debug("Mouse pressed detected event from toolbox");

				Block sourceBlock = (Block) evt.getSource();
				lastCreatedBlock = sourceBlock.cloneBlock();

				// Dispara o evento para o bloco recém criado
				MouseEvent mouseEvent = evt.copyFor(lastCreatedBlock.getBackground(), lastCreatedBlock.getBackground());
				lastCreatedBlock.fireEvent(mouseEvent);

				// Seta a localização atual
				lastCreatedBlock.setLayoutX(sourceBlock.getLayoutX());
				lastCreatedBlock.setLayoutY(sourceBlock.getLayoutY());
				lastCreatedBlock.setDragAnchor(evt.getSceneX(), evt.getSceneY());

				((BorderPane) scene.getRoot()).getChildren().add(lastCreatedBlock);
				evt.setDragDetect(false);
				evt.consume();

			});
			commandToolbox.getChildren().add(command);
		}
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	private void initializeDragEventsTarget() {
		// Target
		paneCode.setOnDragEntered(event -> {
			if (event.getGestureSource() != paneCode && event.getDragboard().hasString()) {
				// destacar algo
				logger.debug("Drag entered");
			}
			event.consume();
		});
		paneCode.setOnDragOver(evt -> {
			if (evt.getGestureSource() != paneCode && evt.getDragboard().hasString()) {
				evt.acceptTransferModes(TransferMode.MOVE);
			}
			evt.consume();
		});
		paneCode.setOnDragExited(evt -> {
			logger.debug("Drag exited");
			// reverter borda
			evt.consume();
		});
		paneCode.setOnDragDropped(evt -> {
			logger.debug("Drag dropper");
			boolean success = false;
			if(evt.getGestureSource() != null && evt.getGestureSource() == lastCreatedBlock && !boxCode.getChildren().contains(lastCreatedBlock)) {
				logger.debug("Gesture source: " + evt.getGestureSource());
				boxCode.getChildren().add(lastCreatedBlock);
				success = true;
			}
			evt.setDropCompleted(success);
			evt.consume();
			lastCreatedBlock = null;
		});
		paneCode.setOnDragDone(evt -> {
			if (evt.getTransferMode() == TransferMode.MOVE) {
				logger.debug("[OnDragDone] Drag done!");
			}
			evt.consume();
		});
	}
}
