import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage theStage) throws Exception {
		theStage.setAlwaysOnTop(true);
		ScoreBoard scoreBoard = new ScoreBoard();
		BorderPane gamePane = new BorderPane(
				new TilesBoard(Constants.ROWS, Constants.COLS, Constants.BOARD_X, Constants.BOARD_Y, scoreBoard));
		gamePane.setRight(scoreBoard);
		theStage.setScene(new Scene(gamePane));
		theStage.setResizable(false);
		theStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
