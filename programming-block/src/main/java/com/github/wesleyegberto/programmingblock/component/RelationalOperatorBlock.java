package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class RelationalOperatorBlock extends Block {
	private String operationImage;
	private final RelationalOperatorType type;

	public RelationalOperatorBlock(String backgroundImage, String operationImage, double width, double height, boolean isTemplate, RelationalOperatorType type) {
		super(backgroundImage, null, width, height, isTemplate);
		this.operationImage = operationImage;
		this.type = type;

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setPrefSize(width, height);
		setMaxSize(width, height);

		createBlock();
	}

	@Override
	public RelationalOperatorBlock cloneBlock() {
		RelationalOperatorBlock block = new RelationalOperatorBlock(backgroundPath, operationImage, getWidth(), getHeight(), isTemplate(), type);
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		return block;
	}

	@Override
	public String generateCode() {
		return type.getType();
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight());
		background.setCursor(Cursor.CLOSED_HAND);

		// Texto da operação
		ImageView imgTexto = new ImageView(new Image(getClass().getResourceAsStream(operationImage)));
		imgTexto.setFitHeight(getHeight());

		StackPane pane = new StackPane();
		pane.getChildren().add(background);
		pane.getChildren().add(imgTexto);

		getChildren().add(pane);
	}

	@Override
	public String toString() {
		return "RelationalOperatorBlock [id=" + id + "]";
	}

	public static OperatorBlockBuilder createBuilder() {
		return new OperatorBlockBuilder();
	}

	public static class OperatorBlockBuilder {
		private String backgroundImage;
		private String operationImage;
		private double width = 70d;
		private double height = 80d;
		private boolean isTemplate;
		private RelationalOperatorType type;

		public OperatorBlockBuilder setBackgroundImage(String backgroundImage) {
			this.backgroundImage = backgroundImage;
			return this;
		}

		public OperatorBlockBuilder setOperationImage(String operationImage) {
			this.operationImage = operationImage;
			return this;
		}

		public OperatorBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public OperatorBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public OperatorBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public OperatorBlockBuilder setType(RelationalOperatorType type) {
			this.type = type;
			return this;
		}

		public RelationalOperatorBlock build() {
			return new RelationalOperatorBlock(backgroundImage, operationImage, width, height, isTemplate, type);
		}
	}

	public enum RelationalOperatorType {
		GREATER(">"),
		GREATER_EQUALS(">="),
		LOWER("<"),
		LOWER_EQUALS("<="),
		EQUALS("=="),
		NOT_EQUALS("!=");

		String type;

		RelationalOperatorType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
	/*
	public enum ArithmeticOperatorType {
		ADDITION("+"),
		SUBTRACTION("-"),
		MULTIPLICATION("*"),
		DIVISION("/");

		String type;

		ArithmeticOperatorType(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
	*/
}
