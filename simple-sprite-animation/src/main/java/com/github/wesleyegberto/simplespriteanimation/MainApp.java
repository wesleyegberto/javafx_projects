package com.github.wesleyegberto.simplespriteanimation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		String fxmlFile = "/fxml/principal_layout.fxml";
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		Scene scene = new Scene(rootNode);
		scene.getStylesheets().add("/styles/styles.css");

		primaryStage.setTitle("Hello JavaFX and Maven");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
