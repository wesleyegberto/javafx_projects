package com.github.wesleyegberto.programmingblock.component;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 25/04/16.
 */
public class ProgramBlock extends FluxControlBlock {
	private String textImagePath;
	private String leftBackgroundImage;
	private String footerBackgroundImage;

	public ProgramBlock(String backgroundHeaderImage, String leftBackgroundImage, String footerBackgroundImage,
						String textImagePath, double width, double height, boolean isTemplate) {
		super(backgroundHeaderImage, null, width, height, isTemplate);
		this.leftBackgroundImage = leftBackgroundImage;
		this.footerBackgroundImage = footerBackgroundImage;
		this.textImagePath = textImagePath;

		connectionLeftPad = connectionLeftPad + LEFT_BAR_WIDTH;

		createBlock();
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
		background.setFitHeight(getHeight() + 16d);

		StackPane header = new StackPane();
		header.setAlignment(Pos.CENTER_LEFT);
		setPrefSize(getWidth(), getHeight());
		setMaxSize(getWidth(), getHeight());
		header.getChildren().add(background);
		ImageView imgTexto = new ImageView(new Image(getClass().getResourceAsStream(textImagePath)));
		header.getChildren().add(imgTexto);

		layout.setTop(header);

		// Center
		boxCode = new VBox(0.0);
		paneCode = new ScrollPane(boxCode);
		//paneCode.setStyle("-fx-background: #AAAAAA;");
		paneCode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		VBox.setVgrow(paneCode, Priority.ALWAYS);
		
		layout.setCenter(paneCode);
		
		// Left
		Image leftBackgroundImage = new Image(getClass().getResourceAsStream(this.leftBackgroundImage));
		ImageView leftBackground = new ImageView(leftBackgroundImage);
		leftBackground.fitHeightProperty().bind(paneCode.heightProperty()); 
		leftBackground.setFitWidth(LEFT_BAR_WIDTH);
		layout.setLeft(leftBackground);

		// Footer
		// Forma do footer
		shapeToClip = createFooterShape();
		// Componentes do footer
		Image backgroundImage = new Image(getClass().getResourceAsStream(footerBackgroundImage));
		ImageView footerBackground = new ImageView(backgroundImage);
		footerBackground.setClip(shapeToClip);
		footerBackground.setFitWidth(getWidth());
		footerBackground.setFitHeight(getHeight());
		layout.setBottom(footerBackground);
	}

	@Override
	public ProgramBlock cloneBlock() {
		throw null;
	}

	@Override
	public String generateCode() {
		StringBuilder code = new StringBuilder();
		code.append("program{");
		code.append("}");
		return code.toString();
	}

	public static ProgramBlockBuilder createBuilder() {
		return new ProgramBlockBuilder();
	}

	public static class ProgramBlockBuilder {
		private String headerBackgroundImage;
		private String leftBackgroundImage;
		private String footerBackgroundImage;
		private String textImagePath;
		private double width = Constants.BLOCK_WIDTH;
		private double height = Constants.BLOCK_HEIGHT;
		private boolean isTemplate;

		public ProgramBlockBuilder setHeaderBackgroundImage(String headerBackgroundImage) {
			this.headerBackgroundImage = headerBackgroundImage;
			return this;
		}

		public ProgramBlockBuilder setLeftBackgroundImage(String leftBackgroundImage) {
			this.leftBackgroundImage = leftBackgroundImage;
			return this;
		}

		public ProgramBlockBuilder setFooterBackgroundImage(String footerBackgroundImage) {
			this.footerBackgroundImage = footerBackgroundImage;
			return this;
		}

		public ProgramBlockBuilder setTextImagePath(String textImagePath) {
			this.textImagePath = textImagePath;
			return this;
		}

		public ProgramBlockBuilder setWidth(double width) {
			this.width = width;
			return this;
		}

		public ProgramBlockBuilder setHeight(double height) {
			this.height = height;
			return this;
		}

		public ProgramBlockBuilder setIsTemplate(boolean isTemplate) {
			this.isTemplate = isTemplate;
			return this;
		}

		public ProgramBlock build() {
			ProgramBlock block = new ProgramBlock(headerBackgroundImage, leftBackgroundImage, footerBackgroundImage, textImagePath, width, height, isTemplate);

			return block;
		}
	}
}
