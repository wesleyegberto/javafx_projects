package com.github.wesleyegberto.simplespriteanimation;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

/**
 * Class based on MichaelJW's class (https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development)
 */
public class Sprite {
	// Sprite sheet
	private Image spriteSheet;
	private double spriteWidth;
	private double spriteHeight;
	private double frameOffset;
	private double frameWidth;
	private double frameHeight;

	/** Time of the transition of each frame */
	private int frameTransitionTime = 500;
	/** Frames per row */
	private int framesPerRow;
	/** Frames per col */
	private int framesPerCol;

	// Info about current frame, position in frame and velocity
	private int currentFrame;
	private double currentPositionX;
	private double currentPositionY;
	private double velocityX;
	private double velocityY;

	private double scaleFactor = 1;

	public Sprite(Image i, int frameOffset, int framesPerRow, int framesPerCol, double frameWidth, double frameHeight) {
		spriteSheet = i;
		spriteWidth = i.getWidth();
		spriteHeight = i.getHeight();
		this.frameOffset = frameOffset;
		this.framesPerRow = framesPerRow;
		this.framesPerCol = framesPerCol;
		// info about each frame
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}

	protected  void setFrameTransitionTime(int frameTransitionTime) {
		this.frameTransitionTime = frameTransitionTime;
	}

	protected void setPosition(double x, double y) {
		currentPositionX = x;
		currentPositionY = y;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public double getCurrentPositionX() {
		return currentPositionX;
	}

	public double getCurrentPositionY() {
		return currentPositionY;
	}

	public void setVelocity(double x, double y) {
		velocityX = x;
		velocityY = y;
	}

	public void addVelocity(double x, double y) {
		velocityX += x;
		velocityY += y;
	}

	public void update(double time) {
		currentPositionX += velocityX * time;
		currentPositionY += velocityY * time;
	}

	/**
	 * Start an animation from a Frame to another.
	 * TODO Should animate frame by frame, who should control is the specialized sprite where received command from user.
	 * @param startFrame index of frame to start
	 * @param endFrame index of frame to stop
	 * @param taxAcceleration tax of acceleration until get top velocity
	 * @param velocity velocity of animation
	 * @param screen screen to draw
	 */
	protected void animateFromPosition(int startFrame, int endFrame, double taxAcceleration, double velocity, Screen screen) {
		if(currentFrame >= endFrame)
			currentFrame = startFrame;
		for(; currentFrame < endFrame; currentFrame++) {
			screen.clearScreen();
			drawFrame(screen, currentFrame);
		}
	}

	/**
	 * Draw a frame from the sprite sheet.
	 */
	protected void drawFrame(Screen screen, int frameToDraw) {
		// Calculate the frame to draw
		int rowToDraw = frameToDraw / framesPerRow;
		int colToDraw = frameToDraw % framesPerRow;
		// Calculate the position of the frame in the sprite sheet
		double offsetX = colToDraw * frameOffset;
		double offsetY = rowToDraw * frameHeight;
//		System.out.printf("%d %f %f\n", frameToDraw, offsetX, offsetX);
		drawImage(screen, offsetX, offsetY, frameWidth, frameHeight);
	}

	/**
	 * Draw a peace of the sprite sheet.
	 */
	private void drawImage(Screen screen, double offsetX, double offsetY, double width, double height) {
		screen.drawImage(spriteSheet, offsetX, offsetY, width, height, currentPositionX, currentPositionY, width * scaleFactor, height * scaleFactor);
	}

	public Rectangle2D getCurrentBoundary() {
		return new Rectangle2D(currentPositionX, currentPositionY, spriteWidth, spriteHeight);
	}

	public boolean intersects(Sprite s) {
		return s.getCurrentBoundary().intersects(this.getCurrentBoundary());
	}
}