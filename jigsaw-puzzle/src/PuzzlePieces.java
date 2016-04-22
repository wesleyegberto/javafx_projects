/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A sample in which an image is broken into pieces to create a jigsaw puzzle.
 * 
 * @see javafx.scene.shape.Path
 * @see javafx.scene.image.Image
 * @see javafx.scene.image.ImageView
 * @see javafx.scene.control.Button
 * @see javafx.scene.layout.Pane
 * @see javafx.scene.input.MouseEvent
 * @see javafx.scene.effect.DropShadow
 * @resource PuzzlePieces-picture.jpg
 */
public class PuzzlePieces extends Application {
	private Timeline timeline;

	private void init(Stage primaryStage) {
		Group root = new Group();
		primaryStage.setScene(new Scene(root));

		// load puzzle image
		Image image = new Image(getClass().getResourceAsStream("/Penguins.jpg"));
		int numOfColumns = (int) (image.getWidth() / Piece.WIDTH);
		int numOfRows = (int) (image.getHeight() / Piece.HEIGHT);

		// create desk
		final Desk desk = new Desk(numOfColumns, numOfRows);

		// create puzzle pieces
		final List<Piece> pieces = new ArrayList<Piece>();
		for (int col = 0; col < numOfColumns; col++) {
			for (int row = 0; row < numOfRows; row++) {
				int x = col * Piece.WIDTH;
				int y = row * Piece.HEIGHT;
				final Piece piece = new Piece(image, x, y, row > 0, col > 0, row < numOfRows - 1,
						col < numOfColumns - 1, desk.getWidth(), desk.getHeight());
				pieces.add(piece);
			}
		}
		desk.getChildren().addAll(pieces);

		// create button box
		Button shuffleButton = new Button("Shuffle");
		shuffleButton.setStyle("-fx-font-size: 2em;");
		shuffleButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				if (timeline != null)
					timeline.stop();
				timeline = new Timeline();
				for (final Piece piece : pieces) {
					piece.setActive();
					double shuffleX = Math.random() * (desk.getWidth() - Piece.WIDTH + 48f) - 24f - piece.getCorrectX();
					double shuffleY = Math.random() * (desk.getHeight() - Piece.HEIGHT + 30f) - 15f - piece.getCorrectY();
					timeline.getKeyFrames()
							.add(new KeyFrame(Duration.seconds(1), new KeyValue(piece.translateXProperty(), shuffleX),
									new KeyValue(piece.translateYProperty(), shuffleY)));
				}
				timeline.playFromStart();
			}
		});

		Button solveButton = new Button("Solve");
		solveButton.setStyle("-fx-font-size: 2em;");
		solveButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				if (timeline != null)
					timeline.stop();
				timeline = new Timeline();
				for (final Piece piece : pieces) {
					piece.setInactive();
					timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
							new KeyValue(piece.translateXProperty(), 0), new KeyValue(piece.translateYProperty(), 0)));
				}
				timeline.playFromStart();
			}
		});

		HBox buttonBox = new HBox(8);
		buttonBox.getChildren().addAll(shuffleButton, solveButton);

		// create vbox for desk and buttons
		VBox vb = new VBox(10);
		vb.getChildren().addAll(desk, buttonBox);
		root.getChildren().addAll(vb);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
