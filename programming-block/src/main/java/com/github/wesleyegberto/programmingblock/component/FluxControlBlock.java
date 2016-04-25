package com.github.wesleyegberto.programmingblock.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class FluxControlBlock extends Block {

	// Lista de comandos internos (quando for um controle de fluxo)
	protected List<Block> listInternalCommands = new ArrayList<>();
	
	public FluxControlBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);
	}

	public void addBlock(Block block) {
		this.listInternalCommands.add(block);
	}
}
