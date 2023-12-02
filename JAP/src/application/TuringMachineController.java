package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TuringMachineController extends GeneralController<TuringMachine> {

	@FXML
	TextArea tmTextArea;

	@FXML
	public void initialize() {
		tmTextArea.setEditable(false);
	}

	@Override
	public void initiateAction(ActionEvent event) throws IOException {
		project.tape = tapeTextField.getText().toCharArray();
		if (project.tape.length < 1) {
			Project.showAlert("Tape too short", "Tape length should be at least 1");
			return;
		}
		project.model = ruleTextField.getText();
		if (!project.isValidModel(project.model))
			return;
		project.currentState = '1';
		project.headPosition = project.tape.length / 2;
		tmTextArea.appendText("Card: " + project.model + "\n");
		tmTextArea.appendText("Initial tape (head position between brackets):\n");
		project.displayTape(tmTextArea);
		tmTextArea.appendText("TM started\n");
		String[] rules = project.model.split(" ");
		for (int i = 0; i < rules.length && project.currentState != '0'; i++) {
			project.executeTransition(rules[i]);
			tmTextArea.appendText("Step=" + i + ", Tapepos=" + project.headPosition + ", Rule=" + rules[i] + ", State="
					+ project.currentState + "\n");
			project.displayTape(tmTextArea);
		}
		tmTextArea.appendText("Final tape config is:\n");
		project.displayTape(tmTextArea);
	}

	@Override
	void setLanguage() {

	}

	@Override
	void setAdditionalData() {
		setLanguage();
		ruleTextField.setText(project.model);
		tapeTextField.setText("00000000000000000000000");
	}

}
