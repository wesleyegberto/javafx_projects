package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
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

	private ParamBlock firstOperand;
	private RelationalOperatorBlock operator;
	private ParamBlock secondOperand;

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
		setupHeaderBackground(background);

		StackPane headerBackgroundPane = new StackPane();
		headerBackgroundPane.setMinSize(0, 0);
		headerBackgroundPane.setAlignment(Pos.CENTER_LEFT);
		headerBackgroundPane.setMaxHeight(Constants.BLOCK_HEIGHT + 16d);
		headerBackgroundPane.getChildren().add(background);

		headerLayout = new HBox();
		headerBackgroundPane.getChildren().add(headerLayout);

		// If
		ImageView textImageView = new ImageView(new Image(getClass().getResourceAsStream(textIfImage)));
		headerLayout.getChildren().add(textImageView);

		ImageView firstOperandImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		firstOperandImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			firstOperandImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at first: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					firstOperand = clipboard.getValue().cloneBlock();
					updateOperand(firstOperandImgVw, evt, 1, firstOperand);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(firstOperandImgVw);

		ImageView operatorImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		operatorImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			operatorImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operation dragged at operator: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof RelationalOperatorBlock) {
					operator = clipboard.getValue().cloneBlock();
					operator.setDragAnchor(evt.getSceneX(), evt.getSceneY());
					operator.setOnMouseDragReleased(operatorImgVw.getOnMouseDragReleased());
					headerLayout.getChildren().set(2, operator);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(operatorImgVw);


		ImageView secondOperandImgVw = new ImageView(new Image(getClass().getResourceAsStream(operandImage)));
		secondOperandImgVw.setFitHeight(Constants.BLOCK_HEIGHT);
		if(!isTemplate()) {
			secondOperandImgVw.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Operand dragged at second: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					secondOperand = clipboard.getValue().cloneBlock();
					updateOperand(secondOperandImgVw, evt, 3, secondOperand);
				}
				clipboard.clear();
				evt.consume();
			});
		}
		headerLayout.getChildren().add(secondOperandImgVw);

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

		private double width = Constants.BLOCK_WIDTH;
		private double height = Constants.CONTROL_FLUX_BLOCK_HEIGHT;
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
