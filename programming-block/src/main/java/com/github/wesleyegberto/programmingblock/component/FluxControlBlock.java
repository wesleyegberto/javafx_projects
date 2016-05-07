package com.github.wesleyegberto.programmingblock.component;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	//protected ScrollPane paneCode;
	protected VBox boxCode;

	// Lista de comandos internos (quando for um controle de fluxo)
	protected List<Block> listInternalCommands = new ArrayList<>();
	
	public FluxControlBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, width, height, isTemplate);
		setMinSize(0, 0);
		setMinSize(width, height);
	}

	public VBox getBoxCode() {
		return boxCode;
	}

	public void addBlock(Block newBlock) {
		System.out.println("\tDropped at FluxControlBlock");
		this.listInternalCommands.add(newBlock);
		boxCode.getChildren().add(newBlock);
	}

	public void addBlockAfter(Block newBlock, Block block) {
		if(this == block) {
			addBlock(newBlock);
		} else {
			int index = boxCode.getChildren().indexOf(block);
			//System.out.println("\tDropped in FluxControlBlock at: " + index);
			if (index >= 0) {
				boxCode.getChildren().add(index + 1, newBlock);
			} else {
				boxCode.getChildren().add(newBlock);
			}
		}
	}

	public void removeBlock(Block block) {
		System.out.print("Removing " + block + " from " + this + ": ");
		listInternalCommands.remove(block);
		System.out.println(boxCode.getChildren().remove(block));
	}

	protected Shape createHeaderShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, Constants.BLOCK_HEIGHT - connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(createRectangle(0, 0, Constants.FLUX_CONTORL_BLOCK_WIDTH, Constants.BLOCK_HEIGHT), rectConnection);
		shapeToClip = Shape.subtract(shapeToClip, createTriangleToRemove(connectionLeftPad));
		shapeToClip = Shape.union(shapeToClip, createTriangleToAdd(connectionLeftPad + LEFT_BAR_WIDTH, Constants.BLOCK_HEIGHT));
		return shapeToClip;
	}

	protected Shape createFooterShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, -connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(rectConnection, createRectangle(0, 0, Constants.FLUX_CONTORL_BLOCK_WIDTH, Constants.BLOCK_HEIGHT));
		shapeToClip = Shape.subtract(shapeToClip, createTriangleToRemove(connectionLeftPad + LEFT_BAR_WIDTH));
		shapeToClip = Shape.union(shapeToClip, createTriangleToAdd(connectionLeftPad, Constants.BLOCK_HEIGHT));
		return shapeToClip;
	}

	protected ImageViewPane createFooterImageViewPaneFromResource(String imageFromResource) {
		ImageView leftBackground = new ImageView(new Image(getClass().getResourceAsStream(imageFromResource)));
		leftBackground.setFitWidth(LEFT_BAR_WIDTH);
		leftBackground.setFitHeight(LEFT_BAR_MIN_HEIGHT);
		return new ImageViewPane(leftBackground);
	}

	protected ImageView createFooterFromResource(String imageFromResource) {
		Shape shapeToClip = createFooterShape();
		ImageView footerBackground = new ImageView(new Image(getClass().getResourceAsStream(imageFromResource)));
		footerBackground.setClip(shapeToClip);
		footerBackground.setFitWidth(Constants.FLUX_CONTORL_BLOCK_WIDTH);
		footerBackground.setFitHeight(Constants.BLOCK_HEIGHT + 16d);
		return footerBackground;
	}
}
