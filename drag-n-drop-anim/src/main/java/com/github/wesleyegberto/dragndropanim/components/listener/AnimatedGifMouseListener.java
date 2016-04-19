package com.github.wesleyegberto.dragndropanim.components.listener;

import com.github.wesleyegberto.dragndropanim.components.AnimatedGif;
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
}