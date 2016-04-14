package com.github.wesleyegberto.twoscenes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage window;
    private Scene mainScene, aboutScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

        // Scene 1
        Label lbl = new Label("Prepare to be surprised");
        Button btn = new Button("Click me");
		btn.setOnAction(evt -> window.setScene(aboutScene));
        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(lbl, btn);

        mainScene = new Scene(mainLayout, 400, 300);

        // Scene 2
        Label lblMessage = new Label("Surprise modafoca!");
		lblMessage.setOnMouseClicked(evt -> window.setScene(mainScene));
        StackPane aboutLayout = new StackPane();
        aboutLayout.getChildren().add(lblMessage);
        aboutScene = new Scene(aboutLayout, 300, 200);

		window.setTitle("Two Scenes");
		window.setScene(mainScene);
		window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
