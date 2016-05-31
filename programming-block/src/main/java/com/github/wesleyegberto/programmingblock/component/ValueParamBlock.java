package com.github.wesleyegberto.programmingblock.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class ValueParamBlock extends ParamBlock {
	private Object value;
	private ComboBox<String> combobox;

	public ValueParamBlock(String backgroundImage, double width, double height, boolean isTemplate) {
		super(backgroundImage, null, width, height, isTemplate);

		createBlock();
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight());
		background.setCursor(Cursor.CLOSED_HAND);


		// Layout para os componentes interno
		StackPane layout = new StackPane();
		layout.setAlignment(Pos.CENTER);
		getChildren().add(layout);

		layout.getChildren().add(background);

		// Lista de valores
		combobox = new ComboBox<>();
		combobox.setTranslateY(5);
		combobox.setPrefHeight(applyFactor(40d));
		combobox.setPrefWidth(applyFactor(100d));
		if(isTemplate()) {
			combobox.setDisable(true);
			combobox.setEditable(false);
		} else {
			for (int i = 1; i <= 10; i++) {
				combobox.getItems().add(String.valueOf(i));
			}
			combobox.setEditable(true);
		}
		layout.getChildren().add(combobox);

	}

	@Override
	public ValueParamBlock cloneBlock() {
		ValueParamBlock block = new ValueParamBlock(backgroundPath, originalWidth, originalHeight, false);
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		block.value = value;
		return block;
	}

	@Override
	public double applyFactor(double x) {
		return x * (isTemplate() ? 0.8 : 1);
	}

	@Override
	public String generateCode() {
		if(!"".equals(combobox.getValue()) && !combobox.getValue().isEmpty()) {
			return combobox.getValue();
		} else {
			return "";
		}
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
		private double width = 130d;
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
