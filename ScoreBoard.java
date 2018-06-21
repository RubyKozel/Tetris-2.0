import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class ScoreBoard extends FlowPane {

	private Label scoreLabel = new Label("Score: ");
	private Label theScore = new Label("0");

	public ScoreBoard() {
		super();
		this.setWidth(Constants.SCORE_BOARD_X);
		this.setHeight(Constants.BOARD_Y);
		this.setStyle("-fx-background-color: #708090;");
		this.setBorder(new Border(
				new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		this.setPrefHeight(this.getHeight());
		this.setPrefWidth(this.getWidth());
		this.setMaxHeight(this.getHeight());
		this.setMaxWidth(this.getWidth());
		this.getChildren().addAll(scoreLabel, theScore);

	}

	public void setScore(String score) {
		this.theScore.setText(score);
	}
}
