package com.github.wesleyegberto.programmingblock.component;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class ParamBlock extends Block {

	public ParamBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, isTemplate, width, height);
		setMinSize(applyFactor(width), applyFactor(height));
		setPrefSize(applyFactor(width), applyFactor(height));
		setMaxSize(applyFactor(width), applyFactor(height));
		setWidth(applyFactor(width));
		setHeight(applyFactor(height));
	}

	@Override
	public double applyFactor(double x) {
		return x * (isTemplate() ? 0.8 : 1);
	}
}
