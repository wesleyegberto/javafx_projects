package com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component;

import javafx.scene.Cursor;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 21/04/16.
 */

public class CommandBlock extends Block {
	private boolean hasParameter;

	public CommandBlock(String backgroundImage, String code, float width, float height, boolean isTemplate) {
		this(backgroundImage, code, width, height, isTemplate, false);
	}

	public CommandBlock(String backgroundImage, String code, float width, float height, boolean isTemplate, boolean hasParameter) {
		super(backgroundImage, code, width, height, isTemplate);
		this.hasParameter = hasParameter;

		createBlock();
	}

	@Override
	public CommandBlock cloneBlock() {
		CommandBlock command = new CommandBlock(backgroundPath, code, width, height, false, hasParameter);
		command.startDragX = super.startDragX;
		command.startDragY = super.startDragY;
		command.dragAnchor = super.dragAnchor;
		return command;
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, width, height);
		blockClip = Shape.subtract(blockClip, createTriangleToRemove());
		blockClip = Shape.union(blockClip, createTriangleToAdd());

		background.setClip(blockClip);
		background.setFitWidth(width);
		background.setFitHeight(height + 16f);
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);
	}
}
