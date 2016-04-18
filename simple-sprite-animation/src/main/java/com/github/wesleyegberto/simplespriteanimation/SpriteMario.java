package com.github.wesleyegberto.simplespriteanimation;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteMario extends Sprite {
	private static int FRAMES_PER_ROW = 3;
	private static int FRAMES_PER_COL = 1;
	private static int FRAME_OFFSET = 17; // px
	private static int FRAME_WIDTH = 16; // px
	private static int FRAME_HEIGHT = 28; // px

	// Frame positions by action/animation
	private static int FRAME_POSITION_WALKING_FORWARD_START = 0;
	private static int FRAME_POSITION_WALKING_FORWARD_END = 3;
	private static int FRAME_POSITION_WALKING_BACkWARD_START = 3;
	private static int FRAME_POSITION_WALKING_BACkWARD_END = 6;

	private Screen screen;

	public SpriteMario(Image sprintSheet, Screen screen) {
		super(sprintSheet, FRAME_OFFSET, FRAMES_PER_ROW, FRAMES_PER_COL, FRAME_WIDTH, FRAME_HEIGHT);
		this.screen = screen;
		setVelocity(10, 0);
		setPosition(20, 20);
	}

	public void animateForwardWalking() {
		screen.clearScreen();
		setScaleFactor(3);
		animateFromPosition(FRAME_POSITION_WALKING_FORWARD_START, FRAME_POSITION_WALKING_FORWARD_END,
							0.5, // takes 0.5 seconds to reach top speed
							1, // top speed
							screen);
	}

	public void animateBackwardWalking() {

	}
}
