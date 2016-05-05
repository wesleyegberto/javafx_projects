package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class CommandBlock extends Block {
	private String textImagePath;
	private String commandName;
	private boolean hasParameter;
	private ParamBlock param;

	private HBox layout;
	private ImageView imgTank;

	private double startTranslation;
	private double endTranslation;

	public CommandBlock(String backgroundImage, String textImagePath, String commandName, String code, double width, double height, boolean isTemplate) {
		this(backgroundImage, textImagePath, commandName, code, width, height, isTemplate, false);
	}

	public CommandBlock(String backgroundImage, String textImagePath, String commandName, String code, double width, double height, boolean isTemplate, boolean hasParameter) {
		super(backgroundImage, code, width, height, isTemplate);
		this.commandName = commandName;
		this.textImagePath = textImagePath;
		this.hasParameter = hasParameter;

		setWidth(width);
		setHeight(height);
		setMinSize(width, height);
		setPrefSize(width, height);

		createBlock();
	}

	public void setStartTranslation(double startTranslation) {
		this.startTranslation = startTranslation;
	}

	public void setEndTranslation(double endTranslation) {
		this.endTranslation = endTranslation;
	}

	@Override
	public CommandBlock cloneBlock() {
		CommandBlock block = new MovementCommandBlockBuilder().setBackgroundImage(backgroundPath).setTextImage(textImagePath)
									.setCommandName(commandName).setCode(code)
									.setWidth(getWidth()).setHeight(getHeight())
									.setIsTemplate(false).setHasParameter(hasParameter)
									.setTranslationX(startTranslation, endTranslation)
									.build();
		block.startDragX = super.startDragX;
		block.startDragY = super.startDragY;
		block.dragAnchor = super.dragAnchor;
		return block;
	}

	@Override
	public String generateCode() {
		return code;
	}

	@Override
	protected void createBlock() {
		Shape blockClip = createRectangle(0, 0, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		blockClip = Shape.subtract(blockClip, createTriangleToRemove(connectionLeftPad));
		blockClip = Shape.union(blockClip, createTriangleToAdd(connectionLeftPad, getHeight()));

		background.setClip(blockClip);
		background.setFitWidth(getWidth());
		background.setFitHeight(getHeight() + 16d);
		background.setCursor(Cursor.CLOSED_HAND);

		getChildren().add(background);

		// Layout para os componentes interno
		layout = new HBox();
		layout.setPadding(StyleConstants.BLOCK_INSETS);
		getChildren().add(layout);

		// Texto do comando
		ImageView imgTexto = new ImageView(new Image(getClass().getResourceAsStream(textImagePath)));
		imgTexto.setFitHeight(getHeight());
		layout.getChildren().add(imgTexto);

		// Espaço para o parâmetro
		if(hasParameter) {
			ImageView imgParam = new ImageView(new Image(getClass().getResourceAsStream(Constants.PARAM_IMAGE)));
			imgParam.setOnMouseDragReleased(evt -> {
				Clipboard clipboard = Clipboard.getInstance();
				//System.out.println("Mouse drag released: " + clipboard.getValue());
				if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
					param = clipboard.getValue().cloneBlock();
					param.setDragAnchor(evt.getSceneX(), evt.getSceneY());

					clipboard.clear();
					// Seta o bloco recebido na segunda posição
					ImageView paramSlot = (ImageView) layout.getChildren().set(1, param);
					double newWidth = getWidth() - paramSlot.getImage().getWidth() + param.getWidth();
					setMinWidth(newWidth);
					setWidth(newWidth);
					setPrefWidth(newWidth);
					setMaxWidth(newWidth);
					updateBlock();

					evt.consume();
				}
			});
			layout.getChildren().add(imgParam);
		}

		// Tank
		this.imgTank = new ImageView(new Image(getClass().getResourceAsStream(Constants.TANK_IMAGE)));
		layout.getChildren().add(imgTank);

	}

	private void updateBlock() {
		if(param == null)
			return;
		Shape blockNewClip = createRectangle(0, 0, getWidth(), getHeight());
		blockNewClip = Shape.subtract(blockNewClip, createTriangleToRemove(50d));
		blockNewClip = Shape.union(blockNewClip, createTriangleToAdd(50d, getHeight()));
		background.setClip(blockNewClip);
		background.setFitWidth(getWidth());
	}

	public void createHorizontalAnimation() {
		imgTank.setTranslateX(startTranslation);
		// Prepara a animação
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		final KeyValue kv = new KeyValue(imgTank.translateXProperty(), endTranslation);
		final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);
		timeline.getKeyFrames().add(kf);
		setOnMouseEntered(evt -> timeline.play());
		setOnMouseExited(evt -> {
			timeline.stop();
			imgTank.setTranslateX(startTranslation);
		});
	}
}
