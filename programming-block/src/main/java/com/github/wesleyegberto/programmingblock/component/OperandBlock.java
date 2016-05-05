package com.github.wesleyegberto.programmingblock.component;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class OperandBlock extends Block {

	private Object value;

	public OperandBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setPrefSize(width, height);
		setMaxSize(width, height);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
