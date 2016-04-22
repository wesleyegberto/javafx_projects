/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * 
 * Node that represents a puzzle piece
 * 
 */
public class Piece extends Parent {
	public static final int HEIGHT = 75;
	public static final int WIDTH = 150;
	
	private final double correctX;
	private final double correctY;
	
	private final boolean hasTopTab;
	private final boolean hasLeftTab;
	private final boolean hasBottomTab;
	private final boolean hasRightTab;
	
	private double startDragX;
	private double startDragY;
	
	private Shape pieceStroke;
	private Shape pieceClip;
	private ImageView imageView = new ImageView();
	private Point2D dragAnchor;

	public Piece(Image image, final double correctX, final double correctY, boolean topTab, boolean leftTab,
			boolean bottomTab, boolean rightTab, final double deskWidth, final double deskHeight) {

		this.correctX = correctX;
		this.correctY = correctY;
		this.hasTopTab = topTab;
		this.hasLeftTab = leftTab;
		this.hasBottomTab = bottomTab;
		this.hasRightTab = rightTab;

		// configure clip
		pieceClip = createPiece();
		pieceClip.setFill(Color.WHITE);
		pieceClip.setStroke(null);

		// add a stroke
		pieceStroke = createPiece();
		pieceStroke.setFill(null); // para não preencher internamente (apenas bordas)
		pieceStroke.setStroke(Color.BLACK);

		// create image view
		imageView.setImage(image);
		imageView.setClip(pieceClip); // Seta o recorte da imagem
		setFocusTraversable(true);
		getChildren().addAll(imageView, pieceStroke);

		// turn on caching so the jigsaw piece is fasr to draw when dragging
		setCache(true);

		// start in inactive state
		setInactive();

		// add listeners to support dragging
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				toFront(); // traz a peça para frente das outras (do grupo)
				// Salva as posições iniciais
				startDragX = getTranslateX();
				startDragY = getTranslateY();
				
				dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				// Se soltar a peça no lugar com uma pequena margem de diferen�a
				// então considera como no lugar certo
				// (translate é a posição ajustada dinâmicamente)
				if (getTranslateX() > (-10) && getTranslateX() < (10) &&
						getTranslateY() > (-10) && getTranslateY() < (10)) {
					// Reseta no lugar
					setTranslateX(0);
					setTranslateY(0);
					// Inativa para não arrastar mais
					setInactive();
				}
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				double newTranslateX = startDragX + me.getSceneX() - dragAnchor.getX();
				double newTranslateY = startDragY + me.getSceneY() - dragAnchor.getY();
				// Calcula a distância das bordas em X a partir da posição atual da peça
				double minTranslateX = -45f - correctX;
				double maxTranslateX = (deskWidth - Piece.HEIGHT + 50f) - correctX;
				// Calcula a distância das bordas em Y a partir da posição atual da peça
				double minTranslateY = -30f - correctY;
				double maxTranslateY = (deskHeight - Piece.HEIGHT + 70f) - correctY;
				// Se não ultrapassou os limites efetua o dragging
				if ((newTranslateX > minTranslateX) && (newTranslateX < maxTranslateX) &&
					(newTranslateY > minTranslateY) && (newTranslateY < maxTranslateY)) {
					setTranslateX(newTranslateX);
					setTranslateY(newTranslateY);
				}
			}
		});
	}

	private Shape createPiece() {
		float ELIPSE_HEIGHT = 10f;
		float ELIPSE_WIDTH = 17.5f;
		float RECT_WIDTH = 25f;
		float RECT_HEIGHT = 12.5f;
		float RECT_ARC = 6.25f;

		// Cria a estrutura externa
		Shape shape = createPieceRectangle();
		
		// Cria as pontas de conexão se houver peças para conectar no lado
		if (hasRightTab) {
			shape = Shape.union(shape, createPieceTab((RECT_HEIGHT + ELIPSE_HEIGHT / 2f), (-HEIGHT / 2f), ELIPSE_HEIGHT, ELIPSE_WIDTH, // Elipse
													0f, (-HEIGHT / 2f - RECT_HEIGHT), RECT_HEIGHT, RECT_WIDTH, // Retângulo
													4f, (-HEIGHT / 2f - RECT_HEIGHT), RECT_ARC,
													4f, (-HEIGHT / 2f + RECT_HEIGHT), RECT_ARC));
		}

		if (hasBottomTab) {
			shape = Shape.union(shape, createPieceTab((-WIDTH / 2f), (RECT_HEIGHT + (ELIPSE_HEIGHT / 2f)), ELIPSE_WIDTH, ELIPSE_HEIGHT, // Elipse
													(-WIDTH / 2f - RECT_HEIGHT), 0f, RECT_WIDTH, RECT_HEIGHT, // Retângulo
													(-WIDTH / 2f - RECT_HEIGHT), 4f, RECT_ARC,
													(-WIDTH / 2f + RECT_HEIGHT), 4f, RECT_ARC));
		}

		if (hasLeftTab) {
			shape = Shape.subtract(shape, createPieceTab((-WIDTH + RECT_HEIGHT + ELIPSE_HEIGHT / 2f), (-HEIGHT / 2f), ELIPSE_HEIGHT, ELIPSE_WIDTH, // Elipse
														-WIDTH, (-HEIGHT / 2f - RECT_HEIGHT), RECT_HEIGHT, RECT_WIDTH, // Retângulo
														-WIDTH + 4f, (-HEIGHT / 2f - RECT_HEIGHT), RECT_ARC,
														-WIDTH + 4f, (-HEIGHT / 2f + RECT_HEIGHT), RECT_ARC));
		}
		if (hasTopTab) {
			shape = Shape.subtract(shape, createPieceTab((-WIDTH / 2f), (-HEIGHT + RECT_HEIGHT + ELIPSE_HEIGHT / 2f), ELIPSE_WIDTH, ELIPSE_HEIGHT, // Elipse
														(-WIDTH / 2f - RECT_HEIGHT), -HEIGHT, RECT_WIDTH, RECT_HEIGHT, // Retângulo
														(-WIDTH / 2f - RECT_HEIGHT), -HEIGHT + 4f, RECT_ARC,
														(-WIDTH / 2f + RECT_HEIGHT), -HEIGHT + 4f, RECT_ARC));
		}
		shape.setTranslateX(correctX);
		shape.setTranslateY(correctY);
		shape.setLayoutX(WIDTH);
		shape.setLayoutY(HEIGHT);
		return shape;

	}

	private Rectangle createPieceRectangle() {
		Rectangle rec = new Rectangle();
		rec.setX(-WIDTH);
		rec.setY(-HEIGHT);
		rec.setWidth(WIDTH);
		rec.setHeight(HEIGHT);
		return rec;
	}

	/**
	 * Cria a ponta de conexão entra duas peças ("cabeça" e "pescoço")
	 */
	private Shape createPieceTab(double eclipseCenterX, double eclipseCenterY, double eclipseRadiusX,
			double eclipseRadiusY, double rectangleX, double rectangleY, double rectangleWidth, double rectangleHeight,
			double circle1CenterX, double circle1CenterY, double circle1Radius, double circle2CenterX,
			double circle2CenterY, double circle2Radius) {
		Ellipse e = new Ellipse(eclipseCenterX, eclipseCenterY, eclipseRadiusX, eclipseRadiusY);
		Rectangle r = new Rectangle(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
		// Une a "cabeça" com o "pescoço"
		Shape tab = Shape.union(e, r);
		// Curva do lado esquerdo "pescoço" da peça"
		Circle c1 = new Circle(circle1CenterX, circle1CenterY, circle1Radius);
		tab = Shape.subtract(tab, c1);
		// Curva do lado direito "pescoço" da peça"
		Circle c2 = new Circle(circle2CenterX, circle2CenterY, circle2Radius);
		tab = Shape.subtract(tab, c2);
		return tab;
	}

	public void setActive() {
		setDisable(false); // habilita os listener
		setEffect(new DropShadow()); // coloca a borda
		toFront(); // traz a pe�a para frente das outras

	}

	public void setInactive() {
		setEffect(null); // retira a borda
		setDisable(true); // desabilita os listener
		toBack(); // envia a peça para o fundo
	}

	public double getCorrectX() {
		return correctX;
	}

	public double getCorrectY() {
		return correctY;
	}
}
