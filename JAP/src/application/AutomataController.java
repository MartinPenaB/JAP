package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The controller class for the Cellular Automata.
 */
public class AutomataController extends Controller{

	@FXML
	private Label modelLabel;
	@FXML
	private Button setButton;
	@FXML
	private MenuButton languageMenuButton;

	private int modelValue = 0;
	private boolean isEnglish = true;

	CellularAutomata automata;
	Stage stage;

	/**
	 * Changes the language of the application.
	 *
	 * @param modelLabelText     The text for the model label.
	 * @param setButtonText      The text for the set button.
	 * @param languageButtonText The text for the language button.
	 * @param windowTitle        The title of the main window.
	 * @param isEnglish          True if the language is set to English, false for
	 *                           Spanish.
	 */
	public void changeLanguage(String modelLabelText, String setButtonText, String languageButtonText,
			String windowTitle, boolean isEnglish) {
		modelLabel.setText(modelLabelText + modelValue);
		inputLabel.setText(modelLabelText);
		setButton.setText(setButtonText);
		languageMenuButton.setText(languageButtonText);
		stage.setTitle(windowTitle);
		this.isEnglish = isEnglish;
	}

	void init(CellularAutomata automata, Stage stage) {
		this.automata = automata;
		this.stage = stage;
		inputTextField.setTextFormatter(new TextFormatter<>(change -> {
			if (change.isAdded() && change.getControlNewText().length() > 8)
				return null;
			return change;
		}));
	}

	/**
	 * Switches the application language to English.
	 */
	@FXML
	public void switchToEnglish() {
		changeLanguage("Model: ", "Set", "Language", "Cellular Automata", true);
	}

	/**
	 * Switches the application language to Spanish.
	 */
	@FXML
	public void switchToSpanish() {
		changeLanguage("Modelo: ", "Generar", "Lenguaje", "Automata Celular", false);
	}

	/**
	 * Updates the model and displays the result.
	 */
	@FXML
	public void updateModel() {
		String model = inputTextField.getText();
		if (model.matches("^[01]+$") && model.length() == 8 && (modelValue = automata.toDec(model)) <= 255) {
			modelLabel.setText(isEnglish ? "Model: " + modelValue : "Modelo: " + modelValue);
			automata.evolve(model);
		} else {
			Project.showAlert(isEnglish ? "Invalid input" : "Entrada invalida",
					isEnglish ? "Please enter a binary number (8 digits)."
							: "Por favor entre un numero en binario (8 digitos).");
		}
	}

}
