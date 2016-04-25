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

/**
 * @author Wesley
 */
public abstract class Block extends Region {
	protected Parent root;

	private static int commonId = 1;
	protected int id;
	
	protected String backgroundPath;
	protected ImageView background;
	protected Image[] backgroundAnimated;
	protected String code;

	protected double connectionLeftPad = 50d;
	protected double connectionWidth = 20;
	protected double connectionHeight = 10;
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

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setPrefSize(width, height);
		setMaxSize(width, height);
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
	protected Polygon createTriangleToAdd(double startX) {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
			startX, getHeight(),
			startX + connectionWidth, getHeight(),
			startX + connectionWidth / 2d, getHeight() + connectionHeight
		);
		triangle.setFill(Color.BLACK);
		return triangle;
	}

}
