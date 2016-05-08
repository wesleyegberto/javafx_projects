package com.github.wesleyegberto.programmingblock.component;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * @author Wesley
 */
public abstract class Block extends Region {
	private static int commonId = 1;
	protected int id;
	
	protected String backgroundPath;
	protected ImageView background;
	protected String code;

	protected double connectionLeftPad = 50d;
	protected double connectionWidth = 20d;
	protected double connectionHeight = 10d;

	// Atributos para controle do drag-n-drop
	protected double startDragX;
	protected double startDragY;
	protected Point2D dragAnchor;

	private final boolean isTemplate;

	public Block(String backgroundPath, String code, double width, double height, boolean isTemplate) {
		id = commonId++;
		this.backgroundPath = backgroundPath;
		this.code = code;
		this.isTemplate = isTemplate;

		setCursor(Cursor.HAND);

		// carrega o fundo do bloco
		Image backgroundImage = new Image(getClass().getResourceAsStream(backgroundPath));
		this.background = new ImageView(backgroundImage);
	}
	
	public boolean isTemplate() {
		return isTemplate;
	}

	public void setDragAnchor(double sceneX, double sceneY) {
		this.dragAnchor = new Point2D(sceneX, sceneY);
	}

	public abstract <T extends Block> T cloneBlock();

	/**
	 * Retorna o código que é gerado a partir dos blocos.
	 */
	public abstract String generateCode();

	protected abstract void createBlock();

	protected void updateBlock() {
		Shape blockNewClip = createRectangle(0, 0, getWidth(), getHeight());
		blockNewClip = Shape.subtract(blockNewClip, createTriangleToRemove(50d));
		blockNewClip = Shape.union(blockNewClip, createTriangleToAdd(50d, getHeight()));
		background.setClip(blockNewClip);
		background.setFitWidth(getWidth());
	}

	protected Rectangle createRectangle(double x, double y, double w, double h) {
		Rectangle rec = new Rectangle();
		rec.setX(x);
		rec.setY(y);
		rec.setWidth(w);
		rec.setHeight(h);
		rec.setArcWidth(20);
		rec.setArcHeight(20);
		return rec;
	}

	/**
	 * Cria o triângulo que será removido do topo do bloco.
	 */
	protected Polygon createTriangleToRemove(double startX) {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
			startX, 0d,
			startX + connectionWidth, 0d,
			startX + connectionWidth / 2d, connectionHeight
		);
		triangle.setFill(Color.BLACK);
		return triangle;
	}

	/**
	 * Cria o triângulo que será adicionado no fim do bloco.
	 */
	protected Polygon createTriangleToAdd(double startX, double startY) {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
			startX, startY,
			startX + connectionWidth, startY,
			startX + connectionWidth / 2d, startY + connectionHeight
		);
		triangle.setFill(Color.BLACK);
		return triangle;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + "{id:" + id + "}" + "]";
	}
}
