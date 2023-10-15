package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * The controller class for the Cellular Automata.
 */
public class CellularAutomataController extends GeneralController<CellularAutomata> {

	private int modelValue = 0;

	String modelLabel;

	@FXML
	public void initialize() {
		setInputLimit(8);
	}

	@Override
	void setLanguage() {
		modelLabel = translate("Model", inSpanish);
		infoLabel.setText(modelLabel + ": " + modelValue);
		inputLabel.setText(modelLabel + ":");
		startButton.setText(translate("Set", inSpanish));
		stage.setTitle(translate(project.getTitle(), inSpanish));
		alertHeaderText = translate("Invalid input", inSpanish);
		alertContentText = translate("Please enter a binary number (8 digits).", inSpanish);
		backButton.setText(translate("Back", inSpanish));
	}

	@Override
	public void start(ActionEvent event) {
		String model = inputTextField.getText();
		if (model.matches("^[01]+$") && model.length() == 8 && (modelValue = project.toDec(model)) <= 255) {
			infoLabel.setText(modelLabel + ": " + modelValue);
			project.evolve(model);
		} else
			Project.showAlert(alertHeaderText, alertContentText);
	}

}
