package com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component;

/**
 * @author Wesley Egberto on 21/04/16.
 */

public class OperatorBlock extends Block {

	public OperatorBlock(String backgroundImage, String code, float width, float height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);
	}

	@Override
	public OperatorBlock cloneBlock() {
		return null;
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {

	}
}
