package com.github.wesleyegberto.dragndropanim.gif;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class AnimatedGifMouseListener implements EventHandler<MouseEvent> {
	private AnimatedGif animatedGif;

	public AnimatedGifMouseListener(AnimatedGif animatedGif) {
		this.animatedGif = animatedGif;
	}

	@Override
	public void handle(MouseEvent event) {
		System.out.println("[MouseEvent] " + event.getEventType());
		if(event.getEventType() == MouseEvent.MOUSE_ENTERED) {
			animatedGif.play();
		} else if(event.getEventType() == MouseEvent.MOUSE_EXITED) {
			animatedGif.stop();
		}
	}


	private void initializeDragEvents() {
		// Source
		inputToAnimate.setOnMousePressed(evt -> {
			log.debug("inputToAnimate - Mouse pressed");
			orgSceneX = evt.getSceneX();
			orgSceneY = evt.getSceneY();
			orgTranslateX = ((TextField) (evt.getSource())).getTranslateX();
			orgTranslateY = ((TextField) (evt.getSource())).getTranslateY();
		});
		inputToAnimate.setOnDragDetected(event -> {
			/* drag was detected, start a drag-and-drop gesture */
			log.debug("inputToAnimate - Drag detected");
			((TextField) event.getSource()).setCursor(Cursor.CLOSED_HAND);
			/* allow any transfer mode */
			Dragboard db = inputToAnimate.startDragAndDrop(TransferMode.ANY);

			/* Put a string on a dragboard */
			ClipboardContent content = new ClipboardContent();
			content.putString(inputToAnimate.getText());
			db.setDragView(new Image(getClass().getResourceAsStream("/images/dragdrop.png"), 64.0, 64.0, true, true));
			db.setContent(content);
			event.consume();
		});
		/* Was used a Image instead of the drag animation
		 * because the Dragboard.setContent is canceling mouse dragged event
		inputToAnimate.setOnMouseDragged(evt -> {
			//log.debug("inputToAnimate - Mouse dragged");

			double offsetX = evt.getSceneX() - orgSceneX;
            double offsetY = evt.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            inputToAnimate.setTranslateX(newTranslateX);
            inputToAnimate.setTranslateY(newTranslateY);
			evt.consume();
		});
		*/
		inputToAnimate.setOnMouseReleased(evt -> {
			log.debug("inputToAnimate - Mouse drag exited");
			((TextField) evt.getSource()).setCursor(Cursor.DEFAULT);
			evt.consume();
		});

		// Target
		helloButton.setOnDragEntered(event -> {
			/* the drag-and-drop gesture entered the target */
			log.debug("helloButton - Drag entered");
			/* show to the user that it is an actual gesture target */
			if (event.getGestureSource() != helloButton && event.getDragboard().hasString()) {
				helloButton.setScaleX(1.1);
				helloButton.setScaleY(1.1);
			}
			event.consume();
		});
		helloButton.setOnDragOver(evt -> {
			/* data is dragged over the target */
			log.debug("helloButton - Drag over");
			/*
			 * accept it only if it is not dragged from the same node and if it
			 * has a string data
			 */
			if (evt.getGestureSource() != helloButton && evt.getDragboard().hasString()) {
				/* allow for moving (mouse pointer changed) */
				evt.acceptTransferModes(TransferMode.MOVE);
			}
			evt.consume();
		});
		helloButton.setOnDragExited(evt -> {
			/* mouse moved away, remove the graphical cues */
			log.debug("helloButton - Drag exited");
			helloButton.setScaleX(1);
			helloButton.setScaleY(1);
			evt.consume();
		});
		helloButton.setOnDragDropped(evt -> {
			/* data dropped */
			log.debug("helloButton - Drag dropped");
			/* if there is a string data on dragboard, read it and use it */
			Dragboard db = evt.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				log.debug("helloButton - Dropped value: " + db.getString());
				helloButton.setText(db.getString());
				success = true;
			}
			/*
			 * let the source know whether the string was successfully
			 * transferred and used
			 */
			evt.setDropCompleted(success);
			evt.consume();
		});
		helloButton.setOnDragDone(evt -> {
			/* the drag and drop gesture ended */
			/* if the data was successfully moved, clear it */
			if (evt.getTransferMode() == TransferMode.MOVE) {
				inputToAnimate.setText("");
			}
			evt.consume();
		});
	}
}