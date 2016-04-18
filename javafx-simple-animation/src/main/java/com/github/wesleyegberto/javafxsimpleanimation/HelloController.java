package com.github.wesleyegberto.javafxsimpleanimation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class HelloController {
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	private Scene scene;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private Button helloButton;
	@FXML
	private Label messageLabel;
	@FXML
	private TextField inputToAnimate;
	@FXML
	private Button btnForward;
	@FXML
	private Canvas animCanvas;
	private GraphicsContext gc;
	private Sprite sprite;

	// Data of dragging
	double orgSceneX, orgSceneY;
	double orgTranslateX, orgTranslateY;

	public void initialize() {
		initializeDragEvents();
		
		Image imgUfo = new Image(getClass().getResourceAsStream("/images/ufo.png"), 40.0, 40.0, true, true);

		sprite = new Sprite();
		sprite.setImage(imgUfo);
		
		gc = animCanvas.getGraphicsContext2D();
		sprite.render(gc);
	}

	private void initializeDragEvents() {
		// Source
		inputToAnimate.setOnMousePressed(evt -> {
			log.debug("inputToAnimate - Mouse pressed");
			orgSceneX = evt.getSceneX();
			orgSceneY = evt.getSceneY();
			orgTranslateX = ((TextField) (evt.getSource())).getTranslateX();
			orgTranslateY = ((TextField) (evt.getSource())).getTranslateY();
		});
		inputToAnimate.setOnDragDetected(event -> {
			/* drag was detected, start a drag-and-drop gesture */
			log.debug("inputToAnimate - Drag detected");
			((TextField) event.getSource()).setCursor(Cursor.CLOSED_HAND);
			/* allow any transfer mode */
			Dragboard db = inputToAnimate.startDragAndDrop(TransferMode.ANY);

			/* Put a string on a dragboard */
			ClipboardContent content = new ClipboardContent();
			content.putString(inputToAnimate.getText());
			db.setDragView(new Image(getClass().getResourceAsStream("/images/dragdrop.png"), 64.0, 64.0, true, true));
			db.setContent(content);
			event.consume();
		});
		/* Was used a Image instead of the drag animation
		 * because the Dragboard.setContent is canceling mouse dragged event
		inputToAnimate.setOnMouseDragged(evt -> {
			//log.debug("inputToAnimate - Mouse dragged");
			
			double offsetX = evt.getSceneX() - orgSceneX;
            double offsetY = evt.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            inputToAnimate.setTranslateX(newTranslateX);
            inputToAnimate.setTranslateY(newTranslateY);
			evt.consume();
		});
		*/
		inputToAnimate.setOnMouseReleased(evt -> {
			log.debug("inputToAnimate - Mouse drag exited");
			((TextField) evt.getSource()).setCursor(Cursor.DEFAULT);
			evt.consume();
		});

		// Target
		helloButton.setOnDragEntered(event -> {
			/* the drag-and-drop gesture entered the target */
			log.debug("helloButton - Drag entered");
			/* show to the user that it is an actual gesture target */
			if (event.getGestureSource() != helloButton && event.getDragboard().hasString()) {
				helloButton.setScaleX(1.1);
				helloButton.setScaleY(1.1);
			}
			event.consume();
		});
		helloButton.setOnDragOver(evt -> {
			/* data is dragged over the target */
			log.debug("helloButton - Drag over");
			/*
			 * accept it only if it is not dragged from the same node and if it
			 * has a string data
			 */
			if (evt.getGestureSource() != helloButton && evt.getDragboard().hasString()) {
				/* allow for moving (mouse pointer changed) */
				evt.acceptTransferModes(TransferMode.MOVE);
			}
			evt.consume();
		});
		helloButton.setOnDragExited(evt -> {
			/* mouse moved away, remove the graphical cues */
			log.debug("helloButton - Drag exited");
			helloButton.setScaleX(1);
			helloButton.setScaleY(1);
			evt.consume();
		});
		helloButton.setOnDragDropped(evt -> {
			/* data dropped */
			log.debug("helloButton - Drag dropped");
			/* if there is a string data on dragboard, read it and use it */
			Dragboard db = evt.getDragboard();
			boolean success = false;
			if (db.hasString()) {
				log.debug("helloButton - Dropped value: " + db.getString());
				helloButton.setText(db.getString());
				success = true;
			}
			/*
			 * let the source know whether the string was successfully
			 * transferred and used
			 */
			evt.setDropCompleted(success);
			evt.consume();
		});
		helloButton.setOnDragDone(evt -> {
			/* the drag and drop gesture ended */
			/* if the data was successfully moved, clear it */
			if (evt.getTransferMode() == TransferMode.MOVE) {
				inputToAnimate.setText("");
			}
			evt.consume();
		});
	}

	private void clearCanvas() {
		gc.clearRect(0, 0, animCanvas.getWidth(), animCanvas.getHeight());
	}

	private void updateCanvas() {
		sprite.update(1);
		clearCanvas();
		sprite.render(gc);
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		scene.addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
			switch (evt.getCode()) {
				case UP:
					goUp();
					break;
				case LEFT:
					goBackward();
					break;
				case RIGHT:
					goForward();
					break;
				case DOWN:
					goDown();
					break;
				default:
					break;
			}
		});
	}

	public void goUp() {
		sprite.setVelocity(0, -10);
		updateCanvas();
	}
	
	public void goBackward() {
		sprite.setVelocity(-10, 0);
		updateCanvas();
	}
	
	public void goForward() {
		sprite.setVelocity(10, 0);
		updateCanvas();
	}
	
	public void goDown() {
		sprite.setVelocity(0, 10);
		updateCanvas();
	}
	
	public void sayHello() {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();

		StringBuilder builder = new StringBuilder();

		if (!StringUtils.isEmpty(firstName)) {
			builder.append(firstName);
		}

		if (!StringUtils.isEmpty(lastName)) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(lastName);
		}

		if (builder.length() > 0) {
			String name = builder.toString();
			log.debug("Saying hello to " + name);
			messageLabel.setText("Hello " + name);
		} else {
			log.debug("Neither first name nor last name was set, saying hello to anonymous person");
			messageLabel.setText("Hello mysterious person");
		}
	}

	

}
