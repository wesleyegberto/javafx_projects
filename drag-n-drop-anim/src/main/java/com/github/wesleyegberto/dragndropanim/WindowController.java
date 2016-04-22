package com.github.wesleyegberto.dragndropanim;

import com.github.wesleyegberto.dragndropanim.components.AnimatedGif;
import com.github.wesleyegberto.dragndropanim.components.AnimatedGifBuilder;
import com.github.wesleyegberto.dragndropanim.components.listener.AnimatedGifMouseListener;
import com.github.wesleyegberto.dragndropanim.components.listener.ImageViewMouseListener;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;


public class WindowController {
	@FXML
	private VBox boxCommands;
	@FXML
	private ScrollPane sclPaneTarget;
	@FXML
	private VBox boxTarget;

	private AnimatedGif[] gifsActions;
	private ImageView imgViewOnDragging;

	public void initialize() {
		gifsActions = new AnimatedGif[]{
			AnimatedGifBuilder.fromGifAtResources("/images/acao_avancar.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_recuar.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_esquerda.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_direita.gif").withDuration(1000).withCycleCount(10).build()
		};

		initializeDragEventsTarge();

		ImageView imageView = null;
		for (AnimatedGif animGif : gifsActions) {
			imageView = animGif.getView();

			AnimatedGifMouseListener listenerGif = new AnimatedGifMouseListener(animGif);
			imageView.setOnMouseEntered(listenerGif);
			imageView.setOnMouseExited(listenerGif);

			ImageViewMouseListener mouseListener = new ImageViewMouseListener(animGif);
			imageView.setOnMousePressed(mouseListener);
			imageView.setOnDragDetected(mouseListener);
			imageView.setOnMouseReleased(mouseListener);

			boxCommands.getChildren().add(imageView);
		}
	}

	private void initializeDragEventsTarge() {
		// Target
		sclPaneTarget.setOnDragEntered(event -> {
			if (event.getGestureSource() != sclPaneTarget && event.getDragboard().hasString()) {
				// destacar algo
			}
			event.consume();
		});
		sclPaneTarget.setOnDragOver(evt -> {
			if (evt.getGestureSource() != sclPaneTarget && evt.getDragboard().hasString()) {
				evt.acceptTransferModes(TransferMode.MOVE);
			}
			evt.consume();
		});
		sclPaneTarget.setOnDragExited(evt -> {
			// reverter borda
			evt.consume();
		});
		sclPaneTarget.setOnDragDropped(evt -> {
			Dragboard db = evt.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				System.out.println("[OnDragDropped] Dropped: " + db.getString());
				success = true;
			}
			if(evt.getGestureSource() != null && evt.getGestureSource() instanceof ImageView) {
				System.out.println("[OnDragDropped] Gesture source: " + evt.getGestureSource());
				ImageView imgViewSource = (ImageView) evt.getGestureSource();
				boxTarget.getChildren().add(imgViewSource);
				success = true;
			}
			evt.setDropCompleted(success);
			evt.consume();
		});
		sclPaneTarget.setOnDragDone(evt -> {
			/* the drag and drop gesture ended */
			/* if the data was successfully moved, clear it */
			if (evt.getTransferMode() == TransferMode.MOVE) {
				System.out.println("[OnDragDone] Drag done!");
			}
			evt.consume();
		});
	}


	private void initializeDragEvents(ImageView imgView, AnimatedGif animGif) {
		imgView.setOnDragDetected(event -> {
			((ImageView) event.getSource()).setCursor(Cursor.CLOSED_HAND);
			Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);

			ClipboardContent content = new ClipboardContent();
			content.putString("ImageView dragging");
			imgViewOnDragging = imgView;
			db.setDragView(new Image(getClass().getResourceAsStream("/images/dragdrop.png"), 64.0, 64.0, true, true));
			db.setContent(content);
			event.consume();
		});
		imgView.setOnMouseReleased(evt -> {
			((ImageView) evt.getSource()).setCursor(Cursor.DEFAULT);
			evt.consume();
		});

		// Target
		sclPaneTarget.setOnDragEntered(event -> {
			if (event.getGestureSource() != sclPaneTarget && event.getDragboard().hasString()) {
				// destacar algo
			}
			event.consume();
		});
		sclPaneTarget.setOnDragOver(evt -> {
			if (evt.getGestureSource() != sclPaneTarget && evt.getDragboard().hasString()) {
				evt.acceptTransferModes(TransferMode.MOVE);
			}
			evt.consume();
		});
		sclPaneTarget.setOnDragExited(evt -> {
			// reverter borda
			evt.consume();
		});
		sclPaneTarget.setOnDragDropped(evt -> {
			Dragboard db = evt.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				System.out.println("[OnDragDropped] Dropped: " + db.getString());
				ImageView imgViewDropped = imgViewOnDragging;
				boxTarget.getChildren().add(imgViewDropped);
				success = true;
			}
			evt.setDropCompleted(success);
			evt.consume();
		});
		sclPaneTarget.setOnDragDone(evt -> {
			if (evt.getTransferMode() == TransferMode.MOVE) {
				System.out.println("[OnDragDone] Drag done!");
			}
			evt.consume();
		});
	}
}
