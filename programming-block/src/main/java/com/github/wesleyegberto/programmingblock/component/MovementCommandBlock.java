package com.github.wesleyegberto.programmingblock.component;

import com.github.wesleyegberto.programmingblock.component.util.Clipboard;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * @author Wesley Egberto on 21/04/16.
 */
public class MovementCommandBlock extends Block {
	private String textImagePath;
	private String commandName;
	private String commandImage;
	private boolean hasParameter;
	private ParamBlock param;

	private HBox layout;
	private ImageView imgTank;

	private double startTranslation;
	private double endTranslation;
	private double startAngle;
	private double endAngle;

	public MovementCommandBlock(String backgroundImage, String textImagePath, String commandName, String commandImage,
						String code, boolean isTemplate) {
		this(backgroundImage, textImagePath, commandName, commandImage, code, isTemplate, false);
	}

	public MovementCommandBlock(String backgroundImage, String textImagePath, String commandName, String commandImage,
						String code, boolean isTemplate, boolean hasParameter) {
		super(backgroundImage, code, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT, isTemplate);
		this.commandName = commandName;
		this.textImagePath = textImagePath;
		this.commandImage = commandImage;
		this.hasParameter = hasParameter;

		setWidth(Constants.BLOCK_WIDTH);
		setHeight(Constants.BLOCK_HEIGHT);
		setMinSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
		setPrefSize(Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);

		createBlock();
	}

	public void setStartTranslation(double startTranslation) {
		this.startTranslation = startTranslation;
	}

	public void setEndTranslation(double endTranslation) {
		this.endTranslation = endTranslation;
	}

	public void setStartAngle(double startAngle) {
		this.startAngle = startAngle;
	}

	public void setEndAngle(double endAngle) {
		this.endAngle = endAngle;
	}

	@Override
	public MovementCommandBlock cloneBlock() {
		MovementCommandBlock block = new MovementCommandBlockBuilder().setBackgroundImage(backgroundPath).setTextImage(textImagePath)
									.setCommandName(commandName).setCommandImage(commandImage).setCode(code)
									.setTemplate(false).setHasParameter(hasParameter)
									.setTranslationX(startTranslation, endTranslation, startAngle, endAngle)
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
		Shape blockClip = createRectangle(0, 0, getWidth(), getHeight());
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
			if(!isTemplate()) {
				imgParam.setOnMouseDragReleased(evt -> {
					Clipboard clipboard = Clipboard.getInstance();
					//System.out.println("Mouse drag released: " + clipboard.getValue());
					if (clipboard.hasValue() && clipboard.getValue() instanceof ParamBlock) {
						param = clipboard.getValue().cloneBlock();
						param.setDragAnchor(evt.getSceneX(), evt.getSceneY());
						param.setOnMouseDragReleased(imgParam.getOnMouseDragReleased());

						// Seta o bloco recebido e atualiza o tamanho do bloco
						Node prevParam = layout.getChildren().set(1, param);
						double newWidth = background.getFitWidth();
						if(prevParam instanceof ImageView) {
							newWidth = background.getFitWidth() - imgParam.getImage().getWidth() + param.getWidth();
						} else {
							newWidth = background.getFitWidth() - ((ParamBlock) prevParam).getWidth() + param.getWidth();
						}
						setWidth(newWidth);
						updateBlock();
					}
					clipboard.clear();
					evt.consume();
				});
			}
			layout.getChildren().add(imgParam);
		}

		// Tank
		this.imgTank = new ImageView(new Image(getClass().getResourceAsStream(commandImage)));
		layout.getChildren().add(imgTank);

	}

	public void createHorizontalAnimation() {
		imgTank.setTranslateX(startTranslation);
		// Prepara a animação horizontal
		if(startTranslation != endTranslation) {
			final Timeline timeline = new Timeline();
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.setAutoReverse(false);
			final KeyValue kv = new KeyValue(imgTank.translateXProperty(), endTranslation);
			final KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
			timeline.getKeyFrames().add(kf);
			setOnMouseEntered(evt -> timeline.play());
			setOnMouseExited(evt -> {
				timeline.stop();
				imgTank.setTranslateX(startTranslation);
			});
		} else if(startAngle != endAngle) {
			RotateTransition rt = new RotateTransition(Duration.seconds(1), imgTank);
			rt.setFromAngle(startAngle);
			rt.setToAngle(endAngle);
			rt.setCycleCount(4);
			rt.setAutoReverse(false);
			setOnMouseEntered(evt -> rt.play() );
			setOnMouseExited(evt -> {
				rt.stop();
				imgTank.setRotate(0);
			});
		}
	}

	public static MovementCommandBlockBuilder createBuilder() {
		return new MovementCommandBlockBuilder();
	}

	public static class MovementCommandBlockBuilder {
		private String backgroundImage = Constants.BLOCK_BACKGROUND_IMAGE;
		private String textImage;
		private String commandName;
		private String commandImage;
		private String code;
		private boolean isTemplate;
		private boolean hasParameter = false;
		private double fromX;
		private double toX;
		private double fromAng;
		private double toAng;

		public MovementCommandBlockBuilder setBackgroundImage(String backgroundImage) {
			this.backgroundImage = backgroundImage;
			return this;
		}

		public MovementCommandBlockBuilder setTextImage(String textImage) {
			this.textImage = textImage;
			return this;
		}

		public MovementCommandBlockBuilder setCommandName(String commandName) {
			this.commandName = commandName;
			return this;
		}

		public MovementCommandBlockBuilder setCommandImage(String commandImage) {
			this.commandImage = commandImage;
			return this;
		}

		public MovementCommandBlockBuilder setCode(String code) {
			this.code = code;
			return this;
		}

		public MovementCommandBlockBuilder setTemplate(boolean isTemplate) {
			this.isTemplate = isTemplate;
			return this;
		}

		public MovementCommandBlockBuilder setHasParameter(boolean hasParameter) {
			this.hasParameter = hasParameter;
			return this;
		}

		public MovementCommandBlockBuilder setTranslationX(double fromX, double toX, double fromAng, double toAng) {
			this.fromX = fromX;
			this.toX = toX;
			this.fromAng = fromAng;
			this.toAng = toAng;
			return this;
		}

		public MovementCommandBlock build() {
			MovementCommandBlock block = new MovementCommandBlock(backgroundImage, textImage, commandName, commandImage,
													code, isTemplate, hasParameter);
			block.setStartTranslation(fromX);
			block.setEndTranslation(toX);
			block.setStartAngle(fromAng);
			block.setEndAngle(toAng);
			block.createHorizontalAnimation();
			return block;
		}
	}
}