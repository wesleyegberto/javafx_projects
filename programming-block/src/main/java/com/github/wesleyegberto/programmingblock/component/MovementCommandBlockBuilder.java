package com.github.wesleyegberto.programmingblock.component;

public class MovementCommandBlockBuilder {
	private String backgroundImage = Constants.BLOCK_BACKGROUND_IMAGE;
	private String textImage;
	private String commandName;
	private String code;
	private boolean isTemplate;
	private boolean hasParameter = false;
	private double fromX;
	private double toX;

	public MovementCommandBlockBuilder setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
		return this;
	}

	public MovementCommandBlockBuilder setTextImage(String textImage) {
		this.textImage = textImage;
		return this;
	}

	public MovementCommandBlockBuilder setCommandName(String commandName) {
		this.commandName = commandName;
		return this;
	}

	public MovementCommandBlockBuilder setCode(String code) {
		this.code = code;
		return this;
	}

	public MovementCommandBlockBuilder setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
		return this;
	}

	public MovementCommandBlockBuilder setHasParameter(boolean hasParameter) {
		this.hasParameter = hasParameter;
		return this;
	}

	public MovementCommandBlockBuilder setTranslationX(double fromX, double toX) {
		this.fromX = fromX;
		this.toX = toX;
		return this;
	}

	public CommandBlock build() {
		CommandBlock block = new CommandBlock(backgroundImage, textImage, commandName, code, isTemplate, hasParameter);
		block.setStartTranslation(fromX);
		block.setEndTranslation(toX);
		block.createHorizontalAnimation();
		return block;
	}
}