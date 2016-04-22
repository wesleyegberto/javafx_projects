package com.github.wesleyegberto.programmingblock.br.com.github.wesleyegberto.component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wesley
 */
public abstract class Block extends Parent {
	protected Parent root;

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
				evt.consume();
			});
		} else {
			setOnMousePressed(evt -> {
				System.out.println("Mouse pressed");
				// Salva as posições iniciais
				startDragX = getTranslateX();
				startDragY = getTranslateY();
				// Salva o ponto em que ocorreu o click
				dragAnchor = new Point2D(evt.getSceneX(), evt.getSceneY());
				toFront();
				evt.consume();
			});
			setOnMouseDragged(evt -> {
				double newTranslateX = startDragX + evt.getSceneX() - dragAnchor.getX();
				double newTranslateY = startDragY + evt.getSceneY() - dragAnchor.getY();
				setTranslateX(newTranslateX);
				setTranslateY(newTranslateY);
				evt.consume();
			});
			setOnDragDetected(event -> {
				System.out.println("Drag detected");
				setCursor(Cursor.CLOSED_HAND);
				toFront();

				Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
				ClipboardContent clipboard = new ClipboardContent();
				clipboard.putString("Foi");

				dragboard.setContent(clipboard);
				event.consume();
			});


			// Target
			setOnDragEntered(event -> {
				System.out.println("Drag entered");
					// destacar algo
				event.consume();
			});
			setOnDragOver(evt -> {
				System.out.println("Drag over");
				if(evt.getSource() != Block.this) {
					evt.acceptTransferModes(TransferMode.ANY);
					DropShadow dropShadow = new DropShadow();
					dropShadow.setRadius(5.0);
					dropShadow.setOffsetX(3.0);
					dropShadow.setOffsetY(3.0);
					dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
					setEffect(dropShadow);
				}
				//evt.consume();
			});
			setOnDragExited(evt -> {
				System.out.println("Drag exited");
				if(evt.getSource() != Block.this) {
					// reverter borda
					setEffect(null);
				}
				evt.consume();
			});
			setOnDragDropped(evt -> {
				System.out.println("Drag dropped");
				evt.setDropCompleted(true);
				evt.consume();
			});
			setOnDragDone(evt -> {
				System.out.println("Drag done");
				evt.consume();
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

	public double getMinX() {
		return minX;
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

	private ChangeListener changeListener = new ChangeListener<Parent>() {
		@Override
		public void changed(ObservableValue<? extends Parent> ov, Parent oldP, Parent newP) {
			root = newP;
		}
	};


}
