package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;

/**
 * @author Wesley Egberto on 21/05/16.
 */
public class CommandBlock extends Block {
	private String textImagePath;
	private String defaultImage;
	private String nextImage;

	private HBox layout;
	private ImageView commandImg;

	public CommandBlock(String backgroundImage, String textImagePath, String defaultImage, String nextImage,
						String code, boolean isTemplate) {
		super(backgroundImage, code, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT, isTemplate);
		this.textImagePath = textImagePath;
		this.defaultImage = defaultImage;
		this.nextImage = nextImage;

		setWidth(Constants.BLOCK_WIDTH);
		setHeight(Constants.BLOCK_HEIGHT);
		setMinSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		setPrefSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);

		createBlock();
	}

	@Override
	public CommandBlock cloneBlock() {
		CommandBlock block = new CommandBlockBuilder().setBackgroundImage(backgroundPath).setTextImage(textImagePath)
									.setDefaultImage(defaultImage).setNextImage(nextImage).setCode(code)
									.setTemplate(false).build();
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		return block;
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		blockClip = Shape.subtract(blockClip, createTriangleToRemove(connectionLeftPad));
		blockClip = Shape.union(blockClip, createTriangleToAdd(connectionLeftPad, getHeight()));

		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight() + 16d);
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);

		// Layout para os componentes interno
		layout = new HBox();
		layout.setPadding(StyleConstants.BLOCK_INSETS);
		getChildren().add(layout);

		// Texto do comando
		ImageView imgTexto = new ImageView(new Image(getClass().getResourceAsStream(textImagePath)));
		imgTexto.setFitHeight(getHeight());
		layout.getChildren().add(imgTexto);

		// Tank
		this.commandImg = new ImageView(new Image(getClass().getResourceAsStream(defaultImage)));
		layout.getChildren().add(commandImg);
	}

	public static CommandBlockBuilder createBuilder() {
		return new CommandBlockBuilder();
	}


	public static class CommandBlockBuilder {
		private String backgroundImage = Constants.BLOCK_BACKGROUND_IMAGE;
		private String textImage;
		private String defaultImage;
		private String nextImage;
		private String code;
		private boolean isTemplate;

		public CommandBlockBuilder setBackgroundImage(String backgroundImage) {
			this.backgroundImage = backgroundImage;
			return this;
		}

		public CommandBlockBuilder setTextImage(String textImage) {
			this.textImage = textImage;
			return this;
		}

		public CommandBlockBuilder setDefaultImage(String defaultImage) {
			this.defaultImage = defaultImage;
			return this;
		}

		public CommandBlockBuilder setNextImage(String nextImage) {
			this.nextImage = nextImage;
			return this;
		}

		public CommandBlockBuilder setCode(String code) {
			this.code = code;
			return this;
		}

		public CommandBlockBuilder setTemplate(boolean isTemplate) {
			this.isTemplate = isTemplate;
			return this;
		}

		public CommandBlock build() {
			return new CommandBlock(backgroundImage, textImage, defaultImage, nextImage, code, isTemplate);
		}
	}
}
