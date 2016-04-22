/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * 
 * Node that represents the playing area/ desktop where the puzzle pices sit
 * 
 */
public class Desk extends Pane {
	Desk(int numOfColumns, int numOfRows) {
		setStyle("-fx-background-color: #cccccc; -fx-border-color: #464646; "
				+ "-fx-effect: innershadow( two-pass-box , rgba(0,0,0,0.8) , 15, 0.0 , 0 , 4 );");
		double DESK_WIDTH = Piece.WIDTH * numOfColumns;
		double DESK_HEIGHT = Piece.HEIGHT * numOfRows;
		setPrefSize(DESK_WIDTH, DESK_HEIGHT);
		setMaxSize(DESK_WIDTH, DESK_HEIGHT);
		autosize();
		
		// create path for lines
		Path grid = new Path();
		grid.setStroke(Color.rgb(70, 70, 70));
		getChildren().add(grid);
		
		// create vertical lines
		for (int col = 0; col < numOfColumns - 1; col++) {
			grid.getElements().addAll(
				new MoveTo(Piece.WIDTH + Piece.WIDTH * col, 3),
				new LineTo(Piece.WIDTH + Piece.WIDTH * col, Piece.HEIGHT * numOfRows - 3)
			);
		}

		// create horizontal lines
		for (int row = 0; row < numOfRows - 1; row++) {
			grid.getElements().addAll(
				new MoveTo(5, Piece.HEIGHT + Piece.HEIGHT * row),
				new LineTo(Piece.WIDTH * numOfColumns - 5, Piece.HEIGHT + Piece.HEIGHT * row)
			);
		}
	}

	@Override
	protected void layoutChildren() {
	}

}
