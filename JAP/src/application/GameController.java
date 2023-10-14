package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class GameController extends GeneralController<GameOfLife> {

	@FXML
	public void initialize() {
		setInputLimit(18);
	}

	@Override
	void setLanguage() {
		inputLabel.setText(translate("Model", inSpanish) + ":");
		startButton.setText(translate("Start", inSpanish));
		stage.setTitle(translate(project.getTitle(), inSpanish));
		backButton.setText(translate("Back", inSpanish));
	}

	@Override
	public void start(ActionEvent event) {
		
		
	}
	

}
