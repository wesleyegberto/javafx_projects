package com.github.wesleyegberto.dragndropanim.components.listener;

import com.github.wesleyegberto.dragndropanim.components.AnimatedGif;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class ImageViewMouseListener implements EventHandler<MouseEvent> {
	AnimatedGif animGif;

	public ImageViewMouseListener(AnimatedGif animGif) {
		this.animGif = animGif;
	}

	@Override
	public void handle(MouseEvent evt) {
		EventType<? extends MouseEvent> eventType = evt.getEventType();
		System.out.println("[MouseEvent] " + eventType);
		if(eventType == MouseDragEvent.MOUSE_PRESSED) {
			/*orgSceneX = evt.getSceneX();
			orgSceneY = evt.getSceneY();
			orgTranslateX = ((ImageView) (evt.getSource())).getTranslateX();
			orgTranslateY = ((ImageView) (evt.getSource())).getTranslateY();*/
		} else if(eventType == MouseDragEvent.MOUSE_RELEASED) {
			((ImageView) evt.getSource()).setCursor(Cursor.DEFAULT);
			evt.consume();
		} else if(eventType == MouseDragEvent.DRAG_DETECTED) {
			ImageView imgView = (ImageView) evt.getSource();
			imgView.setCursor(Cursor.CLOSED_HAND);
			Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putString("ImageView dragging");
			//imgViewOnDragging = imgView;
			db.setDragView(new Image(getClass().getResourceAsStream("/images/dragdrop.png"), 64.0, 64.0, true, true));
			db.setContent(content);
			evt.consume();
		}
	}
}