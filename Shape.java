import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shape implements ShapeInterface {
	private ShapeStyle style;
	private Point[] patt = pattern(Constants.START_X, Constants.START_Y, Constants.SHAPE_WIDTH);
	private LinkedList<Rectangle> shapeToDraw;
	private static HashMap<ShapeStyle, ShapeStyle> rotateMap = new HashMap<>();

	public Shape(ShapeStyle style) {
		this.style = style;
		setmap();
		drawStyle();
	}

	private void setmap() {
		rotateMap.put(ShapeStyle.L_SHAPE, ShapeStyle.L_ROT1);
		rotateMap.put(ShapeStyle.L_ROT1, ShapeStyle.L_ROT2);
		rotateMap.put(ShapeStyle.L_ROT2, ShapeStyle.L_ROT3);
		rotateMap.put(ShapeStyle.L_ROT3, ShapeStyle.L_SHAPE);

		rotateMap.put(ShapeStyle.J_SHAPE, ShapeStyle.J_ROT1);
		rotateMap.put(ShapeStyle.J_ROT1, ShapeStyle.J_ROT2);
		rotateMap.put(ShapeStyle.J_ROT2, ShapeStyle.J_ROT3);
		rotateMap.put(ShapeStyle.J_ROT3, ShapeStyle.J_SHAPE);

		rotateMap.put(ShapeStyle.T_SHAPE, ShapeStyle.T_ROT1);
		rotateMap.put(ShapeStyle.T_ROT1, ShapeStyle.T_ROT2);
		rotateMap.put(ShapeStyle.T_ROT2, ShapeStyle.T_ROT3);
		rotateMap.put(ShapeStyle.T_ROT3, ShapeStyle.T_SHAPE);

		rotateMap.put(ShapeStyle.Z_SHAPE, ShapeStyle.Z_ROT);
		rotateMap.put(ShapeStyle.Z_ROT, ShapeStyle.Z_SHAPE);

		rotateMap.put(ShapeStyle.S_SHAPE, ShapeStyle.S_ROT);
		rotateMap.put(ShapeStyle.S_ROT, ShapeStyle.S_SHAPE);

		rotateMap.put(ShapeStyle.I_SHAPE, ShapeStyle.I_ROT);
		rotateMap.put(ShapeStyle.I_ROT, ShapeStyle.I_SHAPE);
	}

	@Override
	public void drawStyle() {
		shapeToDraw = draw(style);
	}

	public LinkedList<Rectangle> getShapeToDraw() {
		return shapeToDraw;
	}

	public ShapeStyle getStyle() {
		return style;
	}

	public void setStyle(ShapeStyle style) {
		this.style = style;
	}

	private LinkedList<Rectangle> draw(ShapeStyle style) {
		LinkedList<Rectangle> lst = new LinkedList<>();
		for (int i : shapes[style.getID()]) {
			Rectangle r = new Rectangle(patt[i].x, patt[i].y, Constants.SHAPE_WIDTH, Constants.SHAPE_WIDTH);
			r.setFill(style.getColor());
			r.setStroke(Color.BLACK);
			lst.add(r);
		}
		return lst;
	}

	static class RandomShape {
		public static ShapeStyle random() {
			return ShapeStyle.values()[new Random().nextInt(ShapeStyle.values().length)];
		}
	}

	static class Rotator {

		public static LinkedList<Rectangle> getRotate(Shape theShape) {
			theShape.setStyle(rotateMap.get(theShape.style));
			theShape.drawStyle();
			return theShape.getShapeToDraw();
		}
	}
}
