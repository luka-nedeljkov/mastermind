module me.mastermind {
	requires javafx.controls;
	requires javafx.fxml;


	opens me.mastermind to javafx.fxml;
	exports me.mastermind;
}