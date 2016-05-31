package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Animation;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * @author Wesley Egberto on 21/05/16.
 */
public class CommandBlock extends Block {
	private String textImagePath;
	private String[] animImagesSrc;

	private Image[] animImages;
	private Animation animation;

	private HBox layout;

	public CommandBlock(String backgroundImage, String textImagePath, String[] animImages, String code, boolean isTemplate) {
		super(backgroundImage, code, isTemplate, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		this.textImagePath = textImagePath;
		this.animImagesSrc = animImages;

		setWidth(applyFactor(Constants.BLOCK_WIDTH));
		setHeight(applyFactor(Constants.BLOCK_HEIGHT));
		setMinSize(applyFactor(Constants.BLOCK_WIDTH), applyFactor(Constants.BLOCK_HEIGHT));
		setPrefSize(applyFactor(Constants.BLOCK_WIDTH), applyFactor(Constants.BLOCK_HEIGHT));

		createBlock();
	}

	@Override
	public CommandBlock cloneBlock() {
		CommandBlock block = new CommandBlockBuilder().setBackgroundImage(backgroundPath).setTextImage(textImagePath)
									.setAnimImages(animImagesSrc).setCode(code).setTemplate(false).build();
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		return block;
	}

	@Override
	public String generateCode() {
		return code + ";";
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
		blockClip = Shape.subtract(blockClip, createTriangleToRemove(connectionLeftPad));
		blockClip = Shape.union(blockClip, createTriangleToAdd(connectionLeftPad, getHeight()));

		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight() + connectionBottomPad);
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);

		// Layout para os componentes interno
		layout = new HBox();
		layout.setPadding(StyleConstants.BLOCK_INSETS);
		getChildren().add(layout);

		// Texto do comando
		ImageView imgTexto = new ImageView(new Image(getClass().getResourceAsStream(textImagePath)));
		imgTexto.setFitHeight(getHeight());
		imgTexto.setCursor(Cursor.HAND);
		if(isTemplate()) {
			imgTexto.setFitWidth(applyFactor(imgTexto.getImage().getWidth()));
		}
		layout.getChildren().add(imgTexto);

		this.animImages = new Image[animImagesSrc.length];
		for(int i = 0; i < animImagesSrc.length; i++) {
			animImages[i] = new Image(getClass().getResourceAsStream(animImagesSrc[i]));
		}
		animation = new Animation(animImages, 1000);
		//animation.setAutoReverse(true);
		animation.setCycleCount(10);

		ImageView commandImg = animation.getImageView();
		commandImg.setCursor(Cursor.HAND);
		if(isTemplate()) {
			commandImg.setFitHeight(getHeight());
			commandImg.setFitWidth(applyFactor(animImages[0].getWidth()));
		}
		layout.getChildren().add(commandImg);

		setOnMouseEntered(evt -> animation.play());
		setOnMouseExited(evt -> {
			animation.jumpTo(Duration.ZERO);
			animation.stop();
		});

	}

	public static CommandBlockBuilder createBuilder() {
		return new CommandBlockBuilder();
	}


	public static class CommandBlockBuilder {
		private String backgroundImage = Constants.BLOCK_BACKGROUND_IMAGE;
		private String textImage;
		private String[] animImages;
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

		public CommandBlockBuilder setAnimImages(String[] animImages) {
			this.animImages = animImages;
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
			return new CommandBlock(backgroundImage, textImage, animImages, code, isTemplate);
		}
	}
}
