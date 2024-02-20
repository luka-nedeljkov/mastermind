package me.mastermind;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	@FXML
	private VBox guessingBox;
	@FXML
	private VBox resultBox;
	@FXML
	private HBox finalBox;

	private Game game;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Circle[] guessCircles = new Circle[24];
		Circle[] resultCircles = new Circle[24];
		Circle[] finalCircles = new Circle[4];
		for(int i = 0; i < 6; i++) {
			guessCircles[i * 4] = (Circle) ((HBox) guessingBox.getChildren().get(i)).getChildren().get(0);
			guessCircles[i * 4 + 1] = (Circle) ((HBox) guessingBox.getChildren().get(i)).getChildren().get(1);
			guessCircles[i * 4 + 2] = (Circle) ((HBox) guessingBox.getChildren().get(i)).getChildren().get(2);
			guessCircles[i * 4 + 3] = (Circle) ((HBox) guessingBox.getChildren().get(i)).getChildren().get(3);
		}
		for(int i = 0; i < 6; i++) {
			resultCircles[i * 4] = (Circle) ((HBox) resultBox.getChildren().get(i)).getChildren().get(0);
			resultCircles[i * 4 + 1] = (Circle) ((HBox) resultBox.getChildren().get(i)).getChildren().get(1);
			resultCircles[i * 4 + 2] = (Circle) ((HBox) resultBox.getChildren().get(i)).getChildren().get(2);
			resultCircles[i * 4 + 3] = (Circle) ((HBox) resultBox.getChildren().get(i)).getChildren().get(3);
		}
		for(int i = 0; i < 4; i++) {
			finalCircles[i] = (Circle) finalBox.getChildren().get(i);
		}
		game = new Game(guessCircles, resultCircles, finalCircles);
	}

	@FXML
	private void click(MouseEvent e) {
		game.click(e);
	}

	@FXML
	private void submit() {
		game.submit();
	}

	@FXML
	private void clear() {
		game.clear();
	}

}