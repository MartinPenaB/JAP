package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameOfLifeController extends GeneralController<GameOfLife> {

	Timeline timeline = new Timeline();
	boolean running = false;
	char[][] snapshot;

	@FXML
	public void initialize() {
		setInputLimit(3, iterationsTextField);
		setInputLimit(18, ruleTextField);
		ruleTextField.setPromptText("000100000001100000");
	}

	@Override
	void setLanguage() {
		iterationsLabel.setText(translate("Iterations", inSpanish) + ":");
		startButton.setText(translate("Start", inSpanish));
		stopButton.setText(translate("Stop", inSpanish));
		stage.setTitle(translate(project.getTitle(), inSpanish));
		backButton.setText(translate("Back", inSpanish));
		resetButton.setText(translate("Reset", inSpanish));
		clearButton.setText(translate("Clear", inSpanish));
		ruleLabel.setText(translate("Rule", inSpanish) + ":");
		alertHeaderText = translate("Invalid input", inSpanish);
		alertContentText = translate("Please enter a positive integer.", inSpanish);
		randomButton.setText(translate("Random", inSpanish));
	}

	void saveGridState() {
		snapshot = new char[project.gridHeight][project.gridWidth];
		for (int row = 0; row < project.gridHeight; row++)
			for (int col = 0; col < project.gridWidth; col++)
				snapshot[row][col] = project.getColor(row, col).equals(Color.WHITESMOKE) ? '0' : '1';
	}

	@Override
	public void start(ActionEvent event) {

		if (running) {
			timeline.play();
			startButton.setDisable(true);
		} else
			try {
				int total = Integer.parseInt(iterationsTextField.getText());
				if (total < 0)
					throw new NumberFormatException();

				startButton.setDisable(true);
				timeline.getKeyFrames().clear();
				running = true;
				saveGridState();

				for (int exec = 0; exec <= total; exec++) {
					int iteration = exec;
					KeyFrame keyFrame = new KeyFrame(Duration.millis(GameOfLife.ANIMATION_DELAY_MS * exec), e -> {
						project.updateGrid(project.getNewGen(ruleTextField.getText()));
						infoLabel.setText("Exce: " + iteration);
						if (iteration == total) {
							startButton.setDisable(false);
							running = false;
						}
					});

					timeline.getKeyFrames().add(keyFrame);
					
				}

				timeline.play();
			} catch (NumberFormatException e) {
				Project.showAlert(alertHeaderText, alertContentText);
			}

	}

	@FXML
	void reset() {
		running = false;
		timeline.stop();
		infoLabel.setText("Exce: 0");
		startButton.setDisable(false);
		if (snapshot != null)
			project.updateGrid(snapshot);
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
		project.randomizeGrid();
	}

	@FXML
	void multicolor() {
		project.colorsAreRandomized = !project.colorsAreRandomized;
	}

}
