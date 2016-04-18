package com.github.wesleyegberto.simplespriteanimation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represent a screen where the sprites are drawn.
 */
public class Screen {
	private Canvas spriteCanvas;
	private final GraphicsContext gc;
	private final double gcWidth;
	private final double gcHeight;

	public Screen(Canvas canvas, GraphicsContext gc) {
		this.gc = gc;
		this.gcWidth = canvas.getWidth();
		this.gcHeight = canvas.getHeight();
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public double getGcWidth() {
		return gcWidth;
	}

	public double getGcHeight() {
		return gcHeight;
	}

	public void clearScreen() {
		gc.clearRect(0, 0, gcWidth, gcHeight);
	}

	public void drawImage(Image spriteSheet, double offsetX, double offsetY, double imgWidth, double imgHeight,
						  double currentPositionX, double currentPositionY, double widthInTheScreen, double heightInTheScreen) {
		gc.drawImage(spriteSheet, offsetX, offsetY, imgWidth, imgHeight, currentPositionX, currentPositionY, widthInTheScreen, heightInTheScreen);
	}
}
