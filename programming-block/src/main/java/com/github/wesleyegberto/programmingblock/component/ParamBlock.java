package com.github.wesleyegberto.programmingblock.component;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class ParamBlock extends Block {

	public ParamBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);
		setMinSize(width, height);
		setPrefSize(width, height);
		setMaxSize(width, height);
		setWidth(width);
		setHeight(height);
	}
}
