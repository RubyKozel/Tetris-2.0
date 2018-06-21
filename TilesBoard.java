import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class TilesBoard extends Pane {
	private ScoreBoard theScoreBoard;
	private boolean newShape = true;
	private Timeline timer = null;
	private int x_offset = 0;
	private int y_offset = Constants.START_Y;
	private LinkedList<Rectangle> shapeToDraw = null;
	private List<Rectangle> drawenShapes = new LinkedList<>();
	private Shape theShape = null;
	private int[] fullRows = new int[Constants.ROWS];
	private boolean isPlaying = false;
	private int score = 0;
	private CollisionDetector colDet;

	public TilesBoard(int rows, int cols, int width, int height, ScoreBoard sb) {
		super();
		theScoreBoard = sb;
		setStyle("-fx-background-color: #696969");
		colDet = new CollisionDetector(width, height);
		IntStream.range(0, cols).forEach(i -> {
			Line l = new Line(i * (width / cols), 0, i * (width / cols), height);
			l.setStyle("-fx-stroke: white;");
			getChildren().add(l);
		});
		IntStream.range(0, rows).forEach(i -> {
			Line l = new Line(0, i * (height / rows), width, i * (height / rows));
			l.setStyle("-fx-stroke: white;");
			getChildren().add(l);
		});

		this.setFocusTraversable(true);
		this.setFocused(true);
		this.setOnKeyPressed(e -> setKeys(e));
	}

	private void setKeys(KeyEvent e) {
		if (e.getCode() == KeyCode.P) {
			if (!isPlaying) {
				play();
				isPlaying = true;
			} else if (isPlaying) {
				pause();
				isPlaying = false;
			}
		} else if (e.getCode() == KeyCode.RIGHT && isPlaying) {
			if (!shapeToDraw.stream()
					.anyMatch(rect -> colDet.isColliding(drawenShapes, rect, CollisionDetector.CollisionType.RIGHT))) {
				shapeToDraw.forEach(rectangle -> rectangle.setX(rectangle.getX() + rectangle.getWidth()));
				x_offset += Constants.SHAPE_WIDTH;
			}
		} else if (e.getCode() == KeyCode.LEFT && isPlaying) {
			if (!shapeToDraw.stream()
					.anyMatch(rect -> colDet.isColliding(drawenShapes, rect, CollisionDetector.CollisionType.LEFT))) {
				shapeToDraw.forEach(rectangle -> rectangle.setX(rectangle.getX() - rectangle.getWidth()));
				x_offset -= Constants.SHAPE_WIDTH;
			}

		} else if (e.getCode() == KeyCode.DOWN && isPlaying) {
			if (!shapeToDraw.stream()
					.anyMatch(rect -> colDet.isColliding(drawenShapes, rect, CollisionDetector.CollisionType.DOWN))) {
				shapeToDraw.forEach(rectangle -> rectangle.setY(rectangle.getY() + rectangle.getWidth()));
				y_offset += Constants.SHAPE_WIDTH;
			}
		} else if (e.getCode() == KeyCode.SPACE && isPlaying) {
			rotate();
		} else if (e.getCode() == KeyCode.ENTER) {
			stop();
			startOver();
			play();
		}
	}

	private void startOver() {
		getChildren().removeAll(drawenShapes);
		getChildren().removeAll(shapeToDraw);
		drawenShapes.clear();
		newShape = true;
		x_offset = 0;
		y_offset = Constants.START_Y;
		shapeToDraw = null;
		theShape = null;
		isPlaying = true;
		score = 0;
	}

	public void rotate() {
		if (theShape.getStyle() == ShapeStyle.O_SHAPE)
			return;
		LinkedList<Rectangle> potentialShapeToDraw = shapeToDrawAfterCollision();
		if (potentialShapeToDraw != null) {
			getChildren().removeAll(shapeToDraw);
			shapeToDraw = potentialShapeToDraw;
			getChildren().addAll(shapeToDraw);
		}
	}

	private LinkedList<Rectangle> shapeToDrawAfterCollision() {
		LinkedList<Rectangle> potentialShapeToDraw = Shape.Rotator.getRotate(theShape);
		potentialShapeToDraw.forEach(shape -> {
			shape.setX(shape.getX() + x_offset);
			shape.setY(shape.getY() + y_offset);
		});
		if (potentialShapeToDraw.stream()
				.anyMatch(r -> colDet.isColliding(drawenShapes, r, CollisionDetector.CollisionType.DOWN)))
			potentialShapeToDraw = colDet.tryToKick(CollisionDetector.KickType.UP, potentialShapeToDraw, drawenShapes);
		else if (potentialShapeToDraw.stream()
				.anyMatch(r -> colDet.isColliding(drawenShapes, r, CollisionDetector.CollisionType.LEFT)))
			potentialShapeToDraw = colDet.tryToKick(CollisionDetector.KickType.RIGHT, potentialShapeToDraw,
					drawenShapes);
		else if (potentialShapeToDraw.stream()
				.anyMatch(r -> colDet.isColliding(drawenShapes, r, CollisionDetector.CollisionType.RIGHT)))
			potentialShapeToDraw = colDet.tryToKick(CollisionDetector.KickType.LEFT, potentialShapeToDraw,
					drawenShapes);
		return potentialShapeToDraw;
	}

	public void draw() {
		if (newShape) {
			theShape = new Shape(Shape.RandomShape.random());
			shapeToDraw = theShape.getShapeToDraw();
			getChildren().addAll(shapeToDraw);
			newShape = false;
		}
		if (!shapeToDraw.stream()
				.anyMatch(r -> colDet.isColliding(drawenShapes, r, CollisionDetector.CollisionType.DOWN))) {
			shapeToDraw.forEach(rectangle -> rectangle.setY(rectangle.getY() + rectangle.getHeight()));
			y_offset += Constants.SHAPE_WIDTH;
		} else {
			drawenShapes.addAll(shapeToDraw);
			shapeToDraw.forEach(shape -> fullRows[(int) (shape.getY() / Constants.SHAPE_WIDTH)]++);
			clearFullRows();
			if (fullRows[Constants.GAME_OVER_ROW] > 0) {
				stop();
				score = 0;
				return;
			}
			newShape = true;
			x_offset = 0;
			y_offset = Constants.START_Y;
		}

	}

	public void clearFullRows() {
		boolean f_continue = true;
		int rowsCleared = 1;
		while (f_continue) {
			for (int i = fullRows.length - 1; i >= Constants.GAME_OVER_ROW; i--) {
				if (fullRows[i] == Constants.COLS) {
					final int row_to_del = i;
					this.getChildren().removeAll(drawenShapes);
					drawenShapes = drawenShapes.stream()
							.filter(shape -> shape.getY() != row_to_del * Constants.SHAPE_WIDTH)
							.collect(Collectors.toList());
					drawenShapes.stream().filter(shape -> shape.getY() < row_to_del * Constants.SHAPE_WIDTH)
							.forEach(shape -> shape.setY(shape.getY() + Constants.SHAPE_WIDTH));
					this.getChildren().addAll(drawenShapes);
					for (int j = i; j >= Constants.GAME_OVER_ROW; j--)
						fullRows[j] = fullRows[j - 1];
					f_continue = true;
					score += rowsCleared++ * 10;
					break;
				} else
					f_continue = false;
			}
		}
		rowsCleared = 0;
		this.theScoreBoard.setScore(score + "");
		decreaseDelay();
	}

	private void decreaseDelay() {
		if (score > 100)
			this.timer.setDelay(Duration.seconds(Constants.START_DELAY - Constants.DELTA));
		else if (score > 200)
			this.timer.setDelay(Duration.seconds(Constants.START_DELAY - 2 * Constants.DELTA));
		else if (score > 400)
			this.timer.setDelay(Duration.seconds(Constants.START_DELAY / 2));
		else if (score > 800)
			this.timer.setDelay(Duration.seconds(Constants.START_DELAY / 2 - Constants.DELTA));

	}

	public int getScore() {
		return this.score;
	}

	public void play() {
		timer = new Timeline(new KeyFrame(Duration.seconds(Constants.START_DELAY), e -> draw()));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();
	}

	public void pause() {
		this.timer.pause();
	}

	public void stop() {
		this.timer.stop();
	}
}
