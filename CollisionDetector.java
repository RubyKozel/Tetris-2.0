import java.util.LinkedList;
import java.util.List;

import javafx.scene.shape.Rectangle;

public class CollisionDetector {
	private int width;
	private int height;

	public CollisionDetector(int width, int height) {
		this.width = width;
		this.height = height;
	}

	enum CollisionType {
		RIGHT, LEFT, DOWN
	}	

	enum KickType {
		LEFT, RIGHT, UP
	}

	public boolean isColliding(List<Rectangle> drawenShapes, Rectangle r, CollisionType colType) {
		switch (colType) {
		case RIGHT:
			return isCollidingRight(drawenShapes, r);
		case LEFT:
			return isCollidingLeft(drawenShapes, r);
		case DOWN:
			return isCollidingDown(drawenShapes, r);
		}
		return true;
	}

	public boolean isCollidingRight(List<Rectangle> drawenShapes, Rectangle r) {
		return drawenShapes.stream()
				.anyMatch(rect -> r.getX() < rect.getX() + rect.getWidth() && r.getX() + r.getWidth() >= rect.getX()
						&& r.getY() < rect.getY() + rect.getHeight() && r.getY() + r.getHeight() > rect.getY())
				|| r.getX() + r.getWidth() >= width;
	}

	public boolean isCollidingLeft(List<Rectangle> drawenShapes, Rectangle r) {
		return drawenShapes.stream()
				.anyMatch(rect -> r.getX() <= rect.getX() + rect.getWidth() && r.getX() + r.getWidth() > rect.getX()
						&& r.getY() < rect.getY() + rect.getHeight() && r.getY() + r.getHeight() > rect.getY())
				|| r.getX() - r.getWidth() < 0;
	}

	public boolean isCollidingDown(List<Rectangle> drawenShapes, Rectangle r) {
		return drawenShapes.stream()
				.anyMatch(rect -> (r.getY() + r.getHeight() >= rect.getY() && rect.getY() + rect.getHeight() > r.getY())
						&& r.getX() == rect.getX())
				|| r.getY() + r.getHeight() >= height;
	}

	public LinkedList<Rectangle> tryToKick(KickType kick, LinkedList<Rectangle> potentialShapeToDraw,
			List<Rectangle> drawenShapes) {
		switch (kick) {
		case LEFT:
			while (potentialShapeToDraw.stream().anyMatch(r -> isCollidingRight(drawenShapes, r))) {
				potentialShapeToDraw.forEach(rect -> rect.setX(rect.getX() - Constants.SHAPE_WIDTH));
				if (potentialShapeToDraw.stream().anyMatch(r -> isCollidingLeft(drawenShapes, r)))
					return null;
			}
			return potentialShapeToDraw;
		case RIGHT:
			while (potentialShapeToDraw.stream().anyMatch(r -> isCollidingLeft(drawenShapes, r))) {
				potentialShapeToDraw.forEach(rect -> rect.setX(rect.getX() + Constants.SHAPE_WIDTH));
				if (potentialShapeToDraw.stream().anyMatch(r -> isCollidingRight(drawenShapes, r)))
					return null;
			}
			return potentialShapeToDraw;
		case UP:
			while (potentialShapeToDraw.stream().anyMatch(r -> isCollidingDown(drawenShapes, r))) {
				potentialShapeToDraw.forEach(rect -> rect.setY(rect.getY() - Constants.SHAPE_WIDTH));
				if (potentialShapeToDraw.stream()
						.anyMatch(r -> isCollidingRight(drawenShapes, r) || isCollidingLeft(drawenShapes, r)))
					return null;
			}
			return potentialShapeToDraw;
		}
		return null;
	}
}