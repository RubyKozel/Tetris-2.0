import javafx.scene.paint.Color;

public enum ShapeStyle {

	I_SHAPE(0, Color.CYAN), //
	I_ROT(1, Color.CYAN), //
	S_SHAPE(2, Color.YELLOW), //
	S_ROT(3, Color.YELLOW), //
	Z_SHAPE(4, Color.ORANGE), //
	Z_ROT(5, Color.ORANGE), //
	T_SHAPE(6, Color.RED), //
	T_ROT1(7, Color.RED), //
	T_ROT2(8, Color.RED), //
	T_ROT3(9, Color.RED), //
	L_SHAPE(10, Color.GREEN), //
	L_ROT1(11, Color.GREEN), //
	L_ROT2(12, Color.GREEN), //
	L_ROT3(13, Color.GREEN), //
	J_SHAPE(14, Color.BLUE), //
	J_ROT1(15, Color.BLUE), //
	J_ROT2(16, Color.BLUE), //
	J_ROT3(17, Color.BLUE), //
	O_SHAPE(18, Color.MAGENTA);

	private int shape_id;
	private Color c;

	ShapeStyle(int shape_id, Color c) {
		this.shape_id = shape_id;
		this.c = c;
	}

	public int getID() {
		return shape_id;
	}

	public Color getColor() {
		return c;
	}

	public void setID(int shape_id) {
		this.shape_id = shape_id;
	}
}
