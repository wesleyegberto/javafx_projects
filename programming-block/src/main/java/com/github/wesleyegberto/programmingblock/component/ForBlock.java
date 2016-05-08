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
 *
 * Produção: FOR → for (i = EXP ; i OP_REL EXP ; i = i OP_ARIT EXP ) { STMT_LIST }
 * Ex. possível: for (i = 0; i < 10; i = i + 1;) { }
 */
public class ForBlock extends FluxControlBlock {

	private String headerBackgroundImage;
	private String leftBarBackgroundImage;
	private String footerBackgroundImage;
	private String textForImage;
	private String textToImage;
	private String textDoImage;
	private String operandImage;

	private ParamBlock initialValue;
	private ParamBlock finalValue;
	private ParamBlock stepValue;

	public ForBlock(String headerBackground, String leftBarBackground, String footerBackground,
					String textForImage, String textToImage, String textDoImage, String operandImage,
					double width, double height, boolean isTemplate) {
		super(headerBackground, null, width, height, isTemplate);

		this.headerBackgroundImage = headerBackground;
		this.leftBarBackgroundImage = leftBarBackground;
		this.footerBackgroundImage = footerBackground;
		this.textForImage = textForImage;
		this.textToImage = textToImage;
		this.textDoImage = textDoImage;
		this.operandImage = operandImage;

		createBlock();
	}
	
	@Override
	public ForBlock cloneBlock() {
		return new ForBlock(headerBackgroundImage, leftBarBackgroundImage, footerBackgroundImage, textForImage,
			textToImage, textDoImage, operandImage, getWidth(), getHeight(), false);
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
		background.setFitWidth(getWidth());
		background.setFitHeight(Constants.BLOCK_HEIGHT + 16d);

		StackPane headerBackgroundPane = new StackPane();
		headerBackgroundPane.setMinSize(0, 0);
		headerBackgroundPane.setAlignment(Pos.CENTER_LEFT);
		headerBackgroundPane.setMaxHeight(Constants.BLOCK_HEIGHT + 16d);
		headerBackgroundPane.getChildren().add(background);

		headerLayout = new HBox();
		headerBackgroundPane.getChildren().add(headerLayout);

		// For
		ImageView textForImgVw = new ImageView(new Image(getClass().getResourceAsStream(textForImage)));
		headerLayout.getChildren().add(textForImgVw);

		ImageView initialValueImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		initialValueImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			initialValueImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at first: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					initialValue = clipboard.getValue().cloneBlock();
					updateOperand(initialValueImgVw, evt, 1, initialValue);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(initialValueImgVw);

		// To
		ImageView textToImgVw = new ImageView(new Image(getClass().getResourceAsStream(textToImage)));
		headerLayout.getChildren().add(textToImgVw);

		ImageView finalValueImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		finalValueImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			finalValueImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at second: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					finalValue = clipboard.getValue().cloneBlock();
					updateOperand(finalValueImgVw, evt, 3, finalValue);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(finalValueImgVw);

		// Do
		textForImgVw = new ImageView(new Image(getClass().getResourceAsStream(textDoImage)));
		headerLayout.getChildren().add(textForImgVw);

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

	public static ForBlockBuilder createBuilder() {
		return new ForBlockBuilder();
	}

	public static class ForBlockBuilder {
		private String headerBackground;
		private String leftBarBackground;
		private String footerBackground;
		private String textForImage;
		private String textToImage;
		private String textDoImage;
		private String operandImage;

		private double width = Constants.FOR_BLOCK_WIDTH;
		private double height = Constants.CONTROL_FLUX_BLOCK_HEIGHT;
		private boolean isTemplate;

		public ForBlockBuilder setHeaderBackground(String headerBackground) {
			this.headerBackground = headerBackground;
			return this;
		}

		public ForBlockBuilder setLeftBarBackground(String leftBarBackground) {
			this.leftBarBackground = leftBarBackground;
			return this;
		}

		public ForBlockBuilder setFooterBackground(String footerBackground) {
			this.footerBackground = footerBackground;
			return this;
		}

		public ForBlockBuilder setTextForImage(String textForImage) {
			this.textForImage = textForImage;
			return this;
		}

		public ForBlockBuilder setTextToImage(String textToImage) {
			this.textToImage = textToImage;
			return this;
		}

		public ForBlockBuilder setTextDoImage(String textDoImage) {
			this.textDoImage = textDoImage;
			return this;
		}

		public ForBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public ForBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public ForBlockBuilder setTemplate(boolean template) {
			isTemplate = template;
			return this;
		}

		public ForBlockBuilder setOperandImage(String operandImage) {
			this.operandImage = operandImage;
			return this;
		}

		public ForBlock build() {
			return new ForBlock(headerBackground, leftBarBackground, footerBackground, textForImage,
							textToImage, textDoImage, operandImage, width, height, isTemplate);
		}
	}
}
