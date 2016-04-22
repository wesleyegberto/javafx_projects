package com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wesley
 */
public abstract class Block extends Parent {
	protected String backgroundPath;
	protected ImageView background;
	protected String code;

	protected float width;
	protected float height;
	protected float connectionWidth = 10f;
	protected float connectionHeight = 10f;
	// Atributos para controle do drag-n-drop
	protected double minX = 0;
	protected double minY = 0;
	protected double maxX = 0;
	protected double maxY = 0;
	protected double startDragX;
	protected double startDragY;
	protected Point2D dragAnchor;

	private final boolean isTemplate;

	// Lista de comandos internos (quando for um controle de fluxo)
	protected List<Block> listInternalCommands = new ArrayList<>();

	public Block(String backgroundPath, String code, float width, float height, boolean isTemplate) {
		this.backgroundPath = backgroundPath;
		this.code = code;
		this.width = width;
		this.height = height;
		this.isTemplate = isTemplate;

		// carrega o fundo do bloco
		Image backgroundImage = new Image(getClass().getResourceAsStream(backgroundPath));
		this.background = new ImageView(backgroundImage);

		if(isTemplate) {
			setOnMousePressed(evt -> {
				// Salva as posições iniciais
				startDragX = getTranslateX();
				startDragY = getTranslateY();
				// Salva o ponto em que ocorreu o click
				dragAnchor = new Point2D(evt.getSceneX(), evt.getSceneY());
			});
		} else {
			setOnMousePressed(evt -> {
				// Salva as posições iniciais
				startDragX = getTranslateX();
				startDragY = getTranslateY();
				// Salva o ponto em que ocorreu o click
				dragAnchor = new Point2D(evt.getSceneX(), evt.getSceneY());
			});
			setOnMouseDragged(evt -> {
				double newTranslateX = startDragX + evt.getSceneX() - dragAnchor.getX();
				double newTranslateY = startDragY + evt.getSceneY() - dragAnchor.getY();
				setTranslateX(newTranslateX);
				setTranslateY(newTranslateY);
				/*
				// Calcula a distância das bordas em X a partir da posição atual da peça
				double minTranslateX = minX;
				double maxTranslateX = maxX - width;
				// Calcula a distância das bordas em Y a partir da posição atual da peça
				double minTranslateY = minY - height;
				double maxTranslateY = maxY - height;
				// Se não ultrapassou os limites efetua o dragging
				System.out.printf("%f %f\t%f %f\t%f %f\n", minTranslateX, maxTranslateX, minTranslateY, maxTranslateY, newTranslateY, newTranslateX);
				if ((newTranslateX > minTranslateX) && (newTranslateX < maxTranslateX) &&
					(newTranslateY > minTranslateY) && (newTranslateY < maxTranslateY)) {
					setTranslateX(newTranslateX);
					setTranslateY(newTranslateY);
				}*/
			});
			setOnDragDetected(event -> {
				setCursor(Cursor.CLOSED_HAND);
			});
			// ativa o cache para renderizar mais rápido
			setCache(true);
		}
	}

	public ImageView getBackground() {
		return background;
	}

	public void setBackground(ImageView background) {
		this.background = background;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getConnectionWidth() {
		return connectionWidth;
	}

	public void setConnectionWidth(float connectionWidth) {
		this.connectionWidth = connectionWidth;
	}

	public float getConnectionHeight() {
		return connectionHeight;
	}

	public void setConnectionHeight(float connectionHeight) {
		this.connectionHeight = connectionHeight;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public void setDragAnchor(double sceneX, double sceneY) {
		this.dragAnchor = new Point2D(sceneX, sceneY);
	}

	public void addBlock(Block block) {
		this.listInternalCommands.add(block);
	}

	public abstract <T extends Block> T cloneBlock();

	/**
	 * Retorna o código que é gerado a partir dos blocos.
	 */
	public abstract String generateCode();

	protected abstract void createBlock();

	protected Rectangle createRectangle(float x, float y, float w, float h) {
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
	protected Polygon createTriangleToRemove() {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
			50d, 0d,
			70d, 0d,
			60d, 15d
		);
		triangle.setFill(Color.BLACK);
		return triangle;
	}

	/**
	 * Cria o triângulo que será adicionado no fim do bloco.
	 */
	protected Polygon createTriangleToAdd() {
		Polygon triangle = new Polygon();
		triangle.getPoints().setAll(
			50d, (double) height,
			70d, (double) height,
			60d, (double) height + 15d
		);
		triangle.setFill(Color.BLACK);
		return triangle;
	}

	public double getMinX() {
		return minX;
	}

}
