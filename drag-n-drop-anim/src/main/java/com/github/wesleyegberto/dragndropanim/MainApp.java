package com.github.wesleyegberto.dragndropanim;

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
		String fxmlFile = "/fxml/layout.fxml";
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

		Scene scene = new Scene(rootNode);

		primaryStage.setTitle("Drag N' Drop with Actions");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
