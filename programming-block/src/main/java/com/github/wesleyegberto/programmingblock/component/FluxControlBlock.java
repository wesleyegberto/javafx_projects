package com.github.wesleyegberto.programmingblock.component;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class FluxControlBlock extends Block {

	protected static final double LEFT_BAR_MIN_HEIGHT = 80d;
	protected static final double LEFT_BAR_WIDTH = 20d;

	protected ReadOnlyDoubleProperty realHeightProperty;
	protected BorderPane layout;
	protected ScrollPane paneCode;
	protected VBox boxCode;

	// Lista de comandos internos (quando for um controle de fluxo)
	protected List<Block> listInternalCommands = new ArrayList<>();
	
	public FluxControlBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);
	}

	public ScrollPane getPaneCode() {
		return paneCode;
	}

	public VBox getBoxCode() {
		return boxCode;
	}

	public void addBlock(Block block) {
		this.listInternalCommands.add(block);
	}

	protected Shape createHeaderShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, getHeight() - connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(createRectangle(0, 0, getWidth(), getHeight()), rectConnection);
		shapeToClip = Shape.union(shapeToClip, createTriangleToAdd(connectionLeftPad));
		return shapeToClip;
	}

	protected Shape createFooterShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, 0d - connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(rectConnection, createRectangle(0, 0, getWidth(), getHeight()));
		shapeToClip = Shape.subtract(shapeToClip, createTriangleToRemove(connectionLeftPad));
		return shapeToClip;
	}
}
