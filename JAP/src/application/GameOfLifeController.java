package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class GameOfLifeController extends GeneralController<GameOfLife> {

	Timeline timeline = new Timeline();
	boolean animationIsRunning = false;
	char[][] snapshot;

	@FXML
	public void initialize() {
		setInputLimit(3, iterationsTextField);
		setInputLimit(18, ruleTextField);
		ruleTextField.setPromptText("000100000001100000");
	}

	@Override
	void setLanguage() {
		iterationsLabel.setText(translate("Iterations") + ":");
		startButton.setText(translate("Start"));
		stopButton.setText(translate("Stop"));
		stage.setTitle(translate(project.getTitle()));
		backButton.setText(translate("Back"));
		resetButton.setText(translate("Reset"));
		clearButton.setText(translate("Clear"));
		ruleLabel.setText(translate("Rule") + ":");
		alertHeaderText = translate("Invalid input");
		alertContentText = translate("Please enter a positive integer.");
		randomButton.setText(translate("Random"));
		multicolorRadioButton.setText(translate("Multicolor"));
	}

	void continueAnimation() {
		timeline.play();
		startButton.setDisable(true);
	}

	void startAnimation() {
		try {
			int total = Integer.parseInt(iterationsTextField.getText());
			if (total < 0)
				throw new NumberFormatException();

			startButton.setDisable(true);
			timeline.getKeyFrames().clear();
			animationIsRunning = true;
			snapshot = project.getGridState(true, null);

			for (int i = 0; i <= total; i++) {
				int exce = i;
				KeyFrame keyFrame = new KeyFrame(Duration.millis(GameOfLife.ANIMATION_DELAY_MS * i), event -> {
					project.modifyStates(false, project.getGridState(false, ruleTextField.getText()));
					project.updateColors();
					infoLabel.setText("Exce: " + exce);
					if (exce == total) {
						startButton.setDisable(false);
						animationIsRunning = false;
					}
				});
				timeline.getKeyFrames().add(keyFrame);
			}

			timeline.play();
		} catch (NumberFormatException e) {
			Project.showAlert(alertHeaderText, alertContentText);
		}
	}

	@Override
	public void start(ActionEvent event) {

		if (animationIsRunning)
			continueAnimation();
		else
			startAnimation();

	}

	@FXML
	void reset() {
		animationIsRunning = false;
		timeline.stop();
		infoLabel.setText("Exce: 0");
		startButton.setDisable(false);
		if (snapshot != null) {
			project.modifyStates(false, snapshot);
			project.updateColors();
		}

	}

	@FXML
	void changeColor() {
		project.updateColors();
	}

	@FXML
	void clear() {
		project.clearGrid();
	}

	@FXML
	void stop() {
		timeline.pause();
		startButton.setDisable(false);
	}

	@FXML
	void randomize() {
		project.modifyStates(true, null);
		project.updateColors();
	}

	@FXML
	void multicolor() {
		project.useAlternativeColor = !project.useAlternativeColor;
		project.updateColors();
	}

	@FXML
	void interactable() {
		project.interactable = !project.interactable;
	}

}
