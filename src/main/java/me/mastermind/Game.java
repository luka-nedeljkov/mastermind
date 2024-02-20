package me.mastermind;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Optional;
import java.util.Random;

public class Game {

	private final Color DEFAULT_GUESS_COLOR = Color.rgb(198, 198, 198);
	private final Color DEFAULT_RESULT_COLOR = Color.rgb(70, 70, 70);
	private final Color GUESSED_IN_PLACE = Color.rgb(150, 50, 175);
	private final Color GUESSED_NOT_IN_PLACE = Color.rgb(0, 200, 200);
	private Circle[] guessCircles;
	private Circle[] resultCircles;
	private Circle[] finalCircles;
	private Color[] winningCombination;

	private int row;
	private int column;

	public Game(Circle[] guessCircles, Circle[] resultCircles, Circle[] finalCircles) {
		this.guessCircles = guessCircles;
		this.resultCircles = resultCircles;
		this.finalCircles = finalCircles;
		row = 0;
		column = 0;
		generateCombination();
	}

	public void click(MouseEvent e) {
		Color color = (Color) ((Circle) e.getSource()).getFill();
		if(column > 3) {
			return;
		}
		guessCircles[row * 4 + column].setFill(color);
		column++;
	}

	public void submit() {
		if(column < 4) {
			return;
		}
		column = 0;
		if(!checkGuess() && row == 5) {
			showCombination();
			newGame("You lost :(");
		}
		row++;
	}

	public void clear() {
		column = 0;
		for(int i = 0; i < 4; i++) {
			guessCircles[row * 4 + i].setFill(DEFAULT_GUESS_COLOR);
		}
	}

	private boolean checkGuess() {
		Color[] guess = new Color[4];
		for(int i = 0; i < 4; i++) {
			guess[i] = (Color) guessCircles[row * 4 + i].getFill();
		}
		Color[] guessClone = guess.clone();
		Color[] win = winningCombination.clone();
		int inPlace = matchingInPlace(guessClone, win);
		int notInPlace = matchingNotInPlace(guessClone, win);
		setResultCircles(inPlace, notInPlace);
		if(inPlace == 4) {
			showCombination();
			newGame("You won!");
			return true;
		}
		return false;
	}

	private void showCombination() {
		for(int i = 0; i < 4; i++) {
			finalCircles[i].setFill(winningCombination[i]);
		}
	}

	private int matchingInPlace(Color[] guess, Color[] win) {
		int counter = 0;
		for(int i = 0; i < 4; i++) {
			if(guess[i].equals(win[i])) {
				counter++;
				guess[i] = Color.BLACK;
				win[i] = Color.WHITE;
			}
		}
		return counter;
	}

	private int matchingNotInPlace(Color[] guess, Color[] win) {
		int counter = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(guess[i].equals(win[j])) {
					counter++;
					guess[i] = Color.BLACK;
					win[j] = Color.WHITE;
				}
			}
		}
		return counter;
	}

	private void setResultCircles(int inPlace, int notInPlace) {
		for(int i = 0; i < inPlace; i++) {
			resultCircles[row * 4 + i].setFill(GUESSED_IN_PLACE);
		}
		for(int i = inPlace; i < notInPlace + inPlace; i++) {
			resultCircles[row * 4 + i].setFill(GUESSED_NOT_IN_PLACE);
		}
	}

	private void generateCombination() {
		winningCombination = new Color[4];
		Random random = new Random();
		for(int i = 0; i < 4; i++) {
			switch(random.nextInt(6)) {
				case 0 -> winningCombination[i] = Color.RED;
				case 1 -> winningCombination[i] = Color.YELLOW;
				case 2 -> winningCombination[i] = Color.rgb(0, 150, 0);
				case 3 -> winningCombination[i] = Color.DODGERBLUE;
				case 4 -> winningCombination[i] = Color.rgb(150, 0, 150);
				case 5 -> winningCombination[i] = Color.rgb(255, 167, 0);
			}
		}
	}

	private void newGame(String header) {
		gameEndAlert(header);
		reset();
	}

	private void gameEndAlert(String header) {
		ButtonType yes = new ButtonType("Yes");
		ButtonType no = new ButtonType("No");

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("New Game?");
		alert.setHeaderText(header);
		alert.setContentText("Do you wish to continue?");
		alert.getButtonTypes().setAll(yes, no);
		Optional<ButtonType> newGame = alert.showAndWait();

		if(newGame.get().equals(no)) {
			Platform.exit();
		}
	}

	private void reset() {
		row = -1;
		column = 0;
		for(int i = 0; i < 24; i++) {
			guessCircles[i].setFill(DEFAULT_GUESS_COLOR);
			resultCircles[i].setFill(DEFAULT_RESULT_COLOR);
		}
		for(int i = 0; i < 4; i++) {
			finalCircles[i].setFill(DEFAULT_GUESS_COLOR);
		}
		generateCombination();
	}

}
