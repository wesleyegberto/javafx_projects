package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class IfBlock extends FluxControlBlock {

	protected ImageView backgroundIf;
	protected ImageView backgroundBlock;
	protected ImageView backgroundThen;
	
	public IfBlock(String backgroundImageIf, String backgroundBlock, String backgroundImageThen,
				  String code, double width, double height, boolean isTemplate) {
		super(backgroundImageIf, code, width, height, isTemplate);

		// carrega o fundo do bloco
		this.backgroundIf = new ImageView(new Image(getClass().getResourceAsStream(backgroundImageIf)));
		this.backgroundBlock = new ImageView(new Image(getClass().getResourceAsStream(backgroundBlock)));
		this.backgroundThen = new ImageView(new Image(getClass().getResourceAsStream(backgroundImageThen)));
		
	}
	
	@Override
	public IfBlock cloneBlock() {
		
		return null;
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		// Criação do header
		Shape blockClip = createRectangle(0f, 0f, 20f, getHeight());
		blockClip = Shape.subtract(blockClip, createTriangleToRemove(50d));
		blockClip = Shape.union(blockClip, createTriangleToAdd(50d));
		
		backgroundIf.setClip(blockClip);
		backgroundIf.setFitWidth(20f);
		backgroundIf.setFitHeight(getHeight() + 16f);
		backgroundIf.setCursor(Cursor.CLOSED_HAND);
		
		
		getChildren().add(backgroundIf);
	}

	@Override
	public String toString() {
		return "IfBlock [id=" + id + ", hashCode=" + hashCode() + "]";
	}
}
