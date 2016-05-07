package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class IfBlock extends FluxControlBlock {

	private String headerBackgroundImage;
	private String leftBarBackgroundImage;
	private String footerBackgroundImage;
	private String textIfImage;
	private String textThenImage;
	private String operandImage;

	public IfBlock(String headerBackground, String leftBarBackground, String footerBackground,
				   String textIfImage, String textThenImage, String operandImage,
				   double width, double height, boolean isTemplate) {
		super(headerBackground, null, width, height, isTemplate);

		this.headerBackgroundImage = headerBackground;
		this.leftBarBackgroundImage = leftBarBackground;
		this.footerBackgroundImage = footerBackground;
		this.textIfImage = textIfImage;
		this.textThenImage = textThenImage;
		this.operandImage = operandImage;

		createBlock();
	}
	
	@Override
	public IfBlock cloneBlock() {
		return new IfBlock(headerBackgroundImage, leftBarBackgroundImage, footerBackgroundImage, textIfImage, textThenImage,
			operandImage, getWidth(), getHeight(), false);
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		setCursor(Cursor.DEFAULT);
		// Layout para os componentes interno
		layout = new BorderPane();
		layout.setMinWidth(getWidth());
		getChildren().add(layout);

		// Header
		Shape shapeToClip = createHeaderShape();
		background.setClip(shapeToClip);
		background.setFitWidth(Constants.FLUX_CONTORL_BLOCK_WIDTH);
		background.setFitHeight(Constants.BLOCK_HEIGHT + 16d);

		StackPane headerBackgroundPane = new StackPane();
		headerBackgroundPane.setMinSize(0, 0);
		headerBackgroundPane.setAlignment(Pos.CENTER_LEFT);
		headerBackgroundPane.setMaxHeight(Constants.BLOCK_HEIGHT + 16d);
		headerBackgroundPane.getChildren().add(background);

		HBox headerLayout = new HBox();
		headerBackgroundPane.getChildren().add(headerLayout);

		// If
		ImageView textImageView = new ImageView(new Image(getClass().getResourceAsStream(textIfImage)));
		headerLayout.getChildren().add(textImageView);

		ImageView firstOperand = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		firstOperand.setFitHeight(Constants.BLOCK_HEIGHT);
		firstOperand.setOnMouseDragReleased(evt -> {
			Clipboard clipboard = Clipboard.getInstance();
			System.out.println("Operand dragged at first: " + clipboard.getValue());
		});
		headerLayout.getChildren().add(firstOperand);

		ImageView operator = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		operator.setFitHeight(Constants.BLOCK_HEIGHT);
		operator.setOnMouseDragReleased(evt -> {
			Clipboard clipboard = Clipboard.getInstance();
			System.out.println("Operand dragged at operator: " + clipboard.getValue());
		});
		headerLayout.getChildren().add(operator);


		ImageView secondOperand = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		secondOperand.setFitHeight(Constants.BLOCK_HEIGHT);
		secondOperand.setOnMouseDragReleased(evt -> {
			Clipboard clipboard = Clipboard.getInstance();
			System.out.println("Operand dragged at second: " + clipboard.getValue());
		});
		headerLayout.getChildren().add(secondOperand);

		// Then
		textImageView = new ImageView(new Image(getClass().getResourceAsStream(textThenImage)));
		headerLayout.getChildren().add(textImageView);

		layout.setTop(headerBackgroundPane);

		// Center
		boxCode = new VBox(0.0);
		boxCode.setMinSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		layout.setCenter(boxCode);

		// Left
		layout.setLeft(createFooterImageViewPaneFromResource(leftBarBackgroundImage));

		// Footer
		layout.setBottom(createFooterFromResource(footerBackgroundImage));

	}

	public static IfBlockBuilder createBuilder() {
		return new IfBlockBuilder();
	}

	public static class IfBlockBuilder {
		private String headerBackground;
		private String leftBarBackground;
		private String footerBackground;
		private String textIfImage;
		private String textThenImage;
		private String operandImage;

		private double width = Constants.FLUX_CONTORL_BLOCK_WIDTH;
		private double height = 240;
		private boolean isTemplate;

		public IfBlockBuilder setHeaderBackground(String headerBackground) {
			this.headerBackground = headerBackground;
			return this;
		}

		public IfBlockBuilder setLeftBarBackground(String leftBarBackground) {
			this.leftBarBackground = leftBarBackground;
			return this;
		}

		public IfBlockBuilder setFooterBackground(String footerBackground) {
			this.footerBackground = footerBackground;
			return this;
		}

		public IfBlockBuilder setTextIfImage(String textIfImage) {
			this.textIfImage = textIfImage;
			return this;
		}

		public IfBlockBuilder setTextThenImage(String textThenImage) {
			this.textThenImage = textThenImage;
			return this;
		}

		public IfBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public IfBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public IfBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public IfBlockBuilder setOperandImage(String operandImage) {
			this.operandImage = operandImage;
			return this;
		}

		public IfBlock build() {
			return new IfBlock(headerBackground, leftBarBackground, footerBackground, textIfImage, textThenImage,
							  operandImage, width, height, isTemplate);
		}
	}
}
