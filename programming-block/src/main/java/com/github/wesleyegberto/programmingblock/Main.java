package com.github.wesleyegberto.programmingblock;

import com.github.wesleyegberto.programmingblock.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
		launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
		String fxmlFile = "/fxml/main_layout.fxml";
		FXMLLoader loader = new FXMLLoader();
		Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
		MainController controller = loader.getController();
		Scene scene = new Scene(rootNode);

		controller.setScene(scene);

		primaryStage.setTitle("Controlando o Savior");
		primaryStage.setScene(scene);
		primaryStage.show();
    }

}