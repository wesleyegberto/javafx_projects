package com.github.wesleyegberto.firstjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

		Label lbl = new Label("First label lol");
		Button btn = new Button("First button lol");
		btn.setOnAction((evt) -> System.out.println("The button was clicked!"));

		StackPane layout = new StackPane();
		layout.getChildren().addAll(lbl, btn);

		// Scene is the internal window (panel)
		Scene internalWindow = new Scene(layout, 400, 300);

		// Stage is the window
		primaryStage.setScene(internalWindow);
		primaryStage.setTitle("My First JavaFx Application");
		primaryStage.show();
    }

}
