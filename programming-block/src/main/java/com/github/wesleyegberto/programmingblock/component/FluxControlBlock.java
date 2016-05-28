package com.github.wesleyegberto.programmingblock.component;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public abstract class FluxControlBlock extends Block {
	protected static final double LEFT_BAR_WIDTH = 20d;

	protected BorderPane layout;
	protected HBox headerLayout;
	protected VBox boxCode;

	// Lista de comandos internos (quando for um controle de fluxo)
	protected List<Block> listInternalCommands = new ArrayList<>();
	
	public FluxControlBlock(String backgroundImage, String code, double width, double height, boolean isTemplate) {
		super(backgroundImage, code, isTemplate, width, height);

		setMinSize(applyFactor(width), applyFactor(height));
		setWidth(applyFactor(width));
	}

	public VBox getBoxCode() {
		return boxCode;
	}

	public void addBlock(Block newBlock) {
		boxCode.getChildren().add(newBlock);
		listInternalCommands.add(newBlock);
	}

	public void addBlockAfter(Block newBlock, Block block) {
		if(this == block) {
			addBlock(newBlock);
		} else {
			int index = boxCode.getChildren().indexOf(block);
			int listIndex = listInternalCommands.indexOf(block);
			if (index >= 0) {
				boxCode.getChildren().add(index + 1, newBlock);
				listInternalCommands.add(listIndex + 1, newBlock);
			} else {
				boxCode.getChildren().add(newBlock);
				listInternalCommands.add(newBlock);
			}
		}
	}

	public void removeBlock(Block block) {
		boxCode.getChildren().remove(block);
		listInternalCommands.remove(block);
	}

	public void cleanBlocks() {
		boxCode.getChildren().clear();
		listInternalCommands.forEach(block -> {
			if(block instanceof FluxControlBlock)
				((FluxControlBlock) block).cleanBlocks();
		});
		listInternalCommands.clear();
	}

	protected Shape createHeaderShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, applyFactor(Constants.BLOCK_HEIGHT) - connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(createRectangle(0, 0, getWidth(), applyFactor(Constants.BLOCK_HEIGHT)), rectConnection);
		shapeToClip = Shape.subtract(shapeToClip, createTriangleToRemove(connectionLeftPad));
		//shapeToClip = Shape.union(shapeToClip, createTriangleToAdd(connectionLeftPad + LEFT_BAR_WIDTH, Constants.BLOCK_HEIGHT));
		return shapeToClip;
	}

	protected Shape createFooterShape() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, -connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape shapeToClip = Shape.union(rectConnection, createRectangle(0, 0, getWidth(), applyFactor(Constants.BLOCK_HEIGHT)));
		shapeToClip = Shape.subtract(shapeToClip, createTriangleToRemove(connectionLeftPad + LEFT_BAR_WIDTH));
		//shapeToClip = Shape.union(shapeToClip, createTriangleToAdd(connectionLeftPad, Constants.BLOCK_HEIGHT));
		return shapeToClip;
	}

	protected ImageViewPane createFooterImageViewPaneFromResource(String imageFromResource) {
		ImageView leftBackground = new ImageView(new Image(getClass().getResourceAsStream(imageFromResource)));
		leftBackground.setFitWidth(LEFT_BAR_WIDTH);
		leftBackground.setFitHeight(applyFactor(Constants.BLOCK_HEIGHT));
		return new ImageViewPane(leftBackground);
	}

	protected void setupHeaderBackground(ImageView background) {
		Shape shapeToClip = createHeaderShape();
		background.setClip(shapeToClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(applyFactor(Constants.BLOCK_HEIGHT)/* + 16d*/);
	}

	protected ImageView createFooterFromResource(String imageFromResource) {
		Shape shapeToClip = createFooterShape();
		ImageView footerBackground = new ImageView(new Image(getClass().getResourceAsStream(imageFromResource)));
		footerBackground.setClip(shapeToClip);
		footerBackground.setFitWidth(getWidth());
		footerBackground.setFitHeight(applyFactor(Constants.BLOCK_HEIGHT)/* + 16d*/);
		return footerBackground;
	}

	protected void updateBlock() {
		// Retângulo com a barra esquerda
		Rectangle rectConnection = createRectangle(0, applyFactor(Constants.BLOCK_HEIGHT) - connectionHeight, LEFT_BAR_WIDTH, connectionHeight * 3);
		rectConnection.setArcHeight(0d);
		rectConnection.setArcWidth(0d);
		Shape blockNewClip = Shape.union(createRectangle(0, 0, getWidth(), applyFactor(Constants.BLOCK_HEIGHT)), rectConnection);
		blockNewClip = Shape.subtract(blockNewClip, createTriangleToRemove(50d));
		blockNewClip = Shape.union(blockNewClip, createTriangleToAdd(50d, applyFactor(Constants.BLOCK_HEIGHT)));
		background.setClip(blockNewClip);
		background.setFitWidth(getWidth());
	}

	protected void updateOperand(ImageView operandImgVw, MouseDragEvent evt,
							   int index, ParamBlock firstOperand) {
		firstOperand.setDragAnchor(evt.getSceneX(), evt.getSceneY());
		firstOperand.setOnMouseDragReleased(operandImgVw.getOnMouseDragReleased());

		// Seta o bloco recebido e atualiza o tamanho do bloco
		Node prevParam = headerLayout.getChildren().set(index, firstOperand);
		double newWidth = background.getFitWidth();
		if (prevParam instanceof ImageView) {
			newWidth = newWidth - operandImgVw.getImage().getWidth() + firstOperand.getWidth();
		} else {
			newWidth = newWidth - ((ParamBlock) prevParam).getWidth() + firstOperand.getWidth();
		}
		setWidth(newWidth);
		updateBlock();
	}
}
