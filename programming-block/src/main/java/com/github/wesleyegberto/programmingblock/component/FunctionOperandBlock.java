package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class FunctionOperandBlock extends ParamBlock {
	public FunctionOperandBlock(String backgroundImage, double width, double height, boolean isTemplate, String functionName) {
		super(backgroundImage, functionName, width, height, isTemplate);

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setPrefSize(width, height);
		setMaxSize(width, height);

		createBlock();
	}

	@Override
	public FunctionOperandBlock cloneBlock() {
		FunctionOperandBlock block = new FunctionOperandBlock(backgroundPath, getWidth(), getHeight(), false, code);
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		return block;
	}

	@Override
	public String generateCode() {
		return code + "()";
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight());
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);

	}

	@Override
	public String toString() {
		return "FunctionOperandBlock [id=" + id + "]";
	}

	public static FunctionParamBlockBuilder createBuilder() {
		return new FunctionParamBlockBuilder();
	}

	public static class FunctionParamBlockBuilder {
		private String backgroundImage;
		private double width = 130d;
		private double height = 80d;
		private boolean isTemplate;
		private String functionName;

		public FunctionParamBlockBuilder setBackgroundImage(String backgroundImage) {
			this.backgroundImage = backgroundImage;
			return this;
		}

		public FunctionParamBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public FunctionParamBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public FunctionParamBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public FunctionParamBlockBuilder setFunctionName(String functionName) {
			this.functionName = functionName;
			return this;
		}

		public FunctionOperandBlock build() {
			return new FunctionOperandBlock(backgroundImage, width, height, isTemplate, functionName);
		}
	}

}
