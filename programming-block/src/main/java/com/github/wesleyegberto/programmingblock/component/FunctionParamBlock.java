package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class FunctionParamBlock extends OperandBlock {
	private Object value;

	public FunctionParamBlock(String backgroundImage, double width, double height, boolean isTemplate) {
		super(backgroundImage, null, width, height, isTemplate);

		createBlock();
	}

	@Override
	public FunctionParamBlock cloneBlock() {
		FunctionParamBlock block = new FunctionParamBlock(backgroundPath, getWidth(), getHeight(), isTemplate());
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		block.value = value;
		return block;
	}

	@Override
	public String generateCode() {
		return null;
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight() + 16f);
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);

		// Layout para os componentes interno
		HBox layout = new HBox();
		layout.setPadding(StyleConstants.PARAM_INSETS);
		getChildren().add(layout);

	}

	@Override
	public String toString() {
		return "FunctionParamBlock [id=" + id + "]";
	}

	public static FunctionParamBlockBuilder createBuilder() {
		return new FunctionParamBlockBuilder();
	}

	public static class FunctionParamBlockBuilder {
		private String backgroundImage;
		private double width = 200d;
		private double height = 80d;
		private boolean isTemplate;

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

		public FunctionParamBlock build() {
			return new FunctionParamBlock(backgroundImage, width, height, isTemplate);
		}
	}

}
