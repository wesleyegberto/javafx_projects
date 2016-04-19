package com.github.wesleyegberto.dragndropanim;

import com.github.wesleyegberto.dragndropanim.gif.AnimatedGif;
import com.github.wesleyegberto.dragndropanim.gif.Animation;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URISyntaxException;

/**
 * Author http://stackoverflow.com/users/1844265/roland
 * Post from http://stackoverflow.com/questions/28183667/how-i-can-stop-an-animated-gif-in-javafx
 * Requires GifDecoder from here: http://www.java2s.com/Code/Java/2D-Graphics-GUI/DecodesaGIFfileintooneormoreframes.htm
 */
public class AnimatedGifDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws URISyntaxException {
		HBox root = new HBox();

		Animation ani = new AnimatedGif(getClass().getResource("/images/acao_avancar.gif").toExternalForm(), 1000);
		ani.setCycleCount(10);
		ani.play();

		Button btPause = new Button( "Pause");
		btPause.setOnAction( e -> ani.pause());

		Button btResume = new Button( "Resume");
		btResume.setOnAction( e -> ani.play());

		root.getChildren().addAll( ani.getView(), btPause, btResume);

		Scene scene = new Scene(root, 1600, 900);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
