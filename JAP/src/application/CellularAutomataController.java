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
		setInputLimit(8, ruleTextField);
	}

	@Override
	void setLanguage() {
		modelLabel = translate("Model");
		infoLabel.setText(modelLabel + ": " + modelValue);
		ruleLabel.setText(modelLabel + ":");
		startButton.setText(translate("Set"));
		stage.setTitle(translate(project.getTitle()));
		alertHeaderText = translate("Invalid input");
		alertContentText = translate("Please enter a binary number (8 digits).");
		backButton.setText(translate("Back"));
	}

	@Override
	public void start(ActionEvent event) {
		String model = ruleTextField.getText();
		if (model.matches("^[01]{8}$")) {
			modelValue = project.toDec(model);
			infoLabel.setText(modelLabel + ": " + modelValue);
			project.evolve(model);
		} else
			Project.showAlert(alertHeaderText, alertContentText);
	}

}
