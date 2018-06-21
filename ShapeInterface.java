import java.awt.Point;

public interface ShapeInterface {
	final int[][] shapes = { { 0, 4, 8, 12 }, // I // n=0
			{ 12, 13, 14, 15 }, // Rotated I //n=1
			{ 1, 2, 4, 5 }, // S //n=2
			{ 1, 5, 6, 10 }, // Rotated S //n=3
			{ 0, 1, 5, 6 }, // Z // n=4
			{ 1, 4, 5, 8 }, // Rotated Z // n=5
			{ 0, 1, 2, 5 }, // T // n=6
			{ 1, 5, 6, 9 }, // T Rot 1 // n=7
			{ 1, 4, 5, 6 }, // T Rot 2 // n=8
			{ 1, 4, 5, 9 }, // T Rot 3 // n=9
			{ 1, 5, 9, 10 }, // L // n=10
			{ 2, 4, 5, 6 }, // L Rot 1 // n=11
			{ 1, 2, 6, 10 }, // L Rot 2 // n=12
			{ 0, 1, 2, 4 }, // L Rot 3 // n=13
			{ 1, 5, 8, 9 }, // J // n=14
			{ 0, 1, 2, 6 }, // J Rot 1 // n=15
			{ 1, 2, 5, 9 }, // J Rot 2 // n=16
			{ 0, 4, 5, 6 }, // J Rot 3 // n=17
			{ 1, 2, 5, 6 }, // Square // // n=18
	};
	
	void drawStyle();

	default Point[] pattern(int startx, int starty, int shapeWidth) {
		return new Point[] { new Point(startx, starty), // 0
				new Point(startx + shapeWidth, starty), // 1
				new Point(startx + shapeWidth * 2, starty), // 2
				new Point(startx + shapeWidth * 3, starty), // 3
				new Point(startx, starty + shapeWidth), // 4
				new Point(startx + shapeWidth, starty + shapeWidth), // 5
				new Point(startx + shapeWidth * 2, starty + shapeWidth), // 6
				new Point(startx + shapeWidth * 3, starty + shapeWidth), // 7
				new Point(startx, starty + shapeWidth * 2), // 8
				new Point(startx + shapeWidth, starty + shapeWidth * 2), // 9
				new Point(startx + shapeWidth * 2, starty + shapeWidth * 2), // 10
				new Point(startx + shapeWidth * 3, starty + shapeWidth * 2), // 11
				new Point(startx, starty + shapeWidth * 3), // 12
				new Point(startx + shapeWidth, starty + shapeWidth * 3), // 13
				new Point(startx + shapeWidth * 2, starty + shapeWidth * 3), // 14
				new Point(startx + shapeWidth * 3, starty + shapeWidth * 3)// 15
		};
	}
}
