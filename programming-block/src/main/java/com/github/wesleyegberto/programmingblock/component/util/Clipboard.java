package com.github.wesleyegberto.programmingblock.component.util;

import com.github.wesleyegberto.programmingblock.component.Block;

/**
 * @author Wesley Egberto on 24/04/16.
 */
public class Clipboard {
	private static Clipboard clipboard;

	private Block value;

	private Clipboard() {}

	public static Clipboard getInstance() {
		if(clipboard == null) {
			synchronized (Clipboard.class) {
				if(clipboard == null) {
					clipboard = new Clipboard();
				}
			}
		}
		return clipboard;
	}

	public void setValue(Block value) {
		this.value = value;
	}

	public Block getValue() {
		return value;
	}

	public boolean hasValue() {
		return value != null;
	}

	public void clear() {
		value = null;
	}
}
