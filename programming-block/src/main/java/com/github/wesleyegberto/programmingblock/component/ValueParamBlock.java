package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class ValueParamBlock extends ParamBlock {
	private Object value;
	private ComboBox<Number> combobox;

	public ValueParamBlock(String backgroundImage, double width, double height, boolean isTemplate) {
		super(backgroundImage, null, width, height, isTemplate);

		createBlock();
	}

	@Override
	public ValueParamBlock cloneBlock() {
		ValueParamBlock block = new ValueParamBlock(backgroundPath, getWidth(), getHeight(), isTemplate());
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

		// Lista de valores
		combobox = new ComboBox<>();
		combobox.setPrefWidth(80);
		combobox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		combobox.setEditable(true);
		layout.getChildren().add(combobox);

	}

	@Override
	public String toString() {
		return "ValueParamBlock [id=" + id + "]";
	}

	public static ValueParamBlockBuilder createBuilder() {
		return new ValueParamBlockBuilder();
	}

	public static class ValueParamBlockBuilder {
		private String backgroundImage;
		private double width = 100d;
		private double height = 80d;
		private boolean isTemplate;

		public ValueParamBlockBuilder setBackgroundImage(String backgroundImage) {
			this.backgroundImage = backgroundImage;
			return this;
		}

		public ValueParamBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public ValueParamBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public ValueParamBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public ValueParamBlock build() {
			return new ValueParamBlock(backgroundImage, width, height, isTemplate);
		}
	}

}
