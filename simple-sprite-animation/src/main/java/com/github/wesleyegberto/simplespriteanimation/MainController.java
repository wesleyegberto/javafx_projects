package com.github.wesleyegberto.simplespriteanimation;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class MainController {
	@FXML
	private Button startButton;

	@FXML
	private Canvas spriteCanvas;
	private Screen screen;

	private SpriteMario spriteMario;

	public void initialize() {
		GraphicsContext gc = spriteCanvas.getGraphicsContext2D();
		screen = new Screen(spriteCanvas, gc);

		String spriteSheetMario = "/sprite_sheets/mario_sprite.png";
		spriteMario = new SpriteMario(new Image(getClass().getResourceAsStream(spriteSheetMario)), screen);

	}

	public void startAnimation() {
		spriteMario.animateForwardWalking();
	}
}
