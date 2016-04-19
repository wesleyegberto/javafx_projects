package com.github.wesleyegberto.dragndropanim;

import com.github.wesleyegberto.dragndropanim.gif.AnimatedGif;
import com.github.wesleyegberto.dragndropanim.gif.AnimatedGifBuilder;
import com.github.wesleyegberto.dragndropanim.gif.AnimatedGifMouseListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class WindowController {
	@FXML
	private VBox boxCommands;
	@FXML
	private Pane paneTarget;
	/*@FXML
	private ImageView imvAvancar;
	@FXML
	private ImageView imvRecuar;
	@FXML
	private ImageView imvVirarEsquerda;
	@FXML
	private ImageView imvVirarDireta;*/

	private AnimatedGif[] gifsActions;

	public void initialize() {
		gifsActions = new AnimatedGif[]{
			AnimatedGifBuilder.fromGifAtResources("/images/acao_avancar.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_recuar.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_esquerda.gif").withDuration(1000).withCycleCount(10).build(),
			AnimatedGifBuilder.fromGifAtResources("/images/acao_direita.gif").withDuration(1000).withCycleCount(10).build()
		};

		ImageView imageView = null;
		for (AnimatedGif animGif : gifsActions) {
			imageView = animGif.getView();
			AnimatedGifMouseListener listener = new AnimatedGifMouseListener(animGif);
			imageView.setOnMouseEntered(listener);
			imageView.setOnMouseExited(listener);
			boxCommands.getChildren().add(imageView);
		}
	}
}
