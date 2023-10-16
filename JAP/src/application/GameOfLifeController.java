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
		setInputLimit(3);
	}

	@Override
	void setLanguage() {
		inputLabel.setText(translate("Iterations", inSpanish) + ":");
		startButton.setText(translate("Start", inSpanish));
		stopButton.setText(translate("Stop", inSpanish));
		stage.setTitle(translate(project.getTitle(), inSpanish));
		backButton.setText(translate("Back", inSpanish));
		resetButton.setText(translate("Reset", inSpanish));
		clearButton.setText(translate("Clear", inSpanish));
		alertHeaderText = translate("Invalid input", inSpanish);
		alertContentText = translate("Please enter a positive integer.", inSpanish);
	}

	void saveGridState() {
		snapshot = new char[project.gridHeight][project.gridWidth];
		for (int row = 0; row < project.gridHeight; row++)
			for (int col = 0; col < project.gridWidth; col++)
				snapshot[row][col] = project.getColor(row, col).equals(Color.BLACK) ? '1' : '0';
	}

	@Override
	public void start(ActionEvent event) {

		if (running) {
			timeline.play();
			startButton.setDisable(true);
		} else
			try {
				int iterations = Integer.parseInt(inputTextField.getText());
				if (iterations < 0)
					throw new NumberFormatException();

				startButton.setDisable(true);
				timeline.getKeyFrames().clear();
				running = true;
				saveGridState();
				
				for (int exec = 0; exec <= iterations; exec++) {
					int iteration = exec;
					KeyFrame keyFrame = new KeyFrame(Duration.millis(GameOfLife.ANIMATION_DELAY_MS * exec), e -> {
						project.updateGrid(project.getNewGen());
						infoLabel.setText("Exce: " + iteration);
						if (iteration == iterations) {
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

}
