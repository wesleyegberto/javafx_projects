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
	public static final double TEMPLATE_FACTOR_SIZE = 0.6;
	private static int commonId = 1;
	protected int id;
	
	protected String backgroundPath;
	protected ImageView background;
	protected String code;

	protected double connectionLeftPad = 50d;
	protected double connectionWidth = 20d;
	protected double connectionHeight = 10d;
	protected double connectionBottomPad = 16d;

	// Atributos para controle do drag-n-drop
	protected double startDragX;
	protected double startDragY;
	protected Point2D dragAnchor;

	private final boolean isTemplate;

	// Atributos para os tamanhos originais
	protected double originalWidth;
	protected double originalHeight;

	public Block(String backgroundPath, String code, boolean isTemplate, double originalWidth, double originalHeight) {
		id = commonId++;
		this.backgroundPath = backgroundPath;
		this.code = code;
		this.isTemplate = isTemplate;
		this.originalWidth = originalWidth;
		this.originalHeight = originalHeight;

		// Se for Template então aplica os fatores para diminuir a imagem
		connectionLeftPad = applyFactor(connectionLeftPad);
		connectionWidth = applyFactor(connectionWidth);
		connectionHeight = applyFactor(connectionHeight);
		connectionBottomPad = applyFactor(connectionBottomPad);

		// carrega o fundo do bloco
		Image backgroundImage = new Image(getClass().getResourceAsStream(backgroundPath));
		this.background = new ImageView(backgroundImage);

		setCursor(Cursor.HAND);
	}
	
	public boolean isTemplate() {
		return isTemplate;
	}

	public void setDragAnchor(double sceneX, double sceneY) {
		this.dragAnchor = new Point2D(sceneX, sceneY);
	}

	/**
	 * Aplica um fator na medida recebida.
	 * @param x medida para aplicar fator
	 * @return medida redimensionada
	 */
	public double applyFactor(double x) {
		return x * (isTemplate() ? TEMPLATE_FACTOR_SIZE : 1);
	}

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

	public abstract <T extends Block> T cloneBlock();

	/**
	 * Retorna o código que é gerado a partir dos blocos.
	 */
	public abstract String generateCode();

	protected abstract void createBlock();
}
