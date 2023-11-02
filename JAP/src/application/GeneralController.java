package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class GeneralController<T> {

	@FXML
	protected AnchorPane headerAnchorPane;
	@FXML
	protected AnchorPane footerAnchorPane;
	@FXML
	protected TextField iterationsTextField;
	@FXML
	protected TextField ruleTextField;
	@FXML
	protected Label iterationsLabel;
	@FXML
	protected Button startButton;
	@FXML
	protected ColorPicker colorPicker;
	@FXML
	protected Button randomButton;
	@FXML
	protected RadioButton multicolorRadioButton;
	@FXML
	protected Button closeButton;
	@FXML
	protected Button helpButton;
	@FXML
	protected Button backButton;
	@FXML
	protected Button stopButton;
	@FXML
	protected Button clearButton;
	@FXML
	protected Button resetButton;
	@FXML
	protected Label infoLabel;
	@FXML
	protected Label ruleLabel;
	@FXML
	protected MenuBar languageMenuBar;
	@FXML
	protected ComboBox<Project> projectComboBox;
	@FXML
	protected AnchorPane mainAnchorPane;

	String alertHeaderText;
	String alertContentText;

	T project;
	Stage stage;

	Map<String, String> dictionary = new HashMap<>();
	static boolean inSpanish = false;

	void setRequiredData(T project, Stage stage) {
		this.project = project;
		this.stage = stage;
		setDictionary();
		setLanguage();
	}

	void setDictionary() {
		dictionary.put("Select a project", "Elija un projecto");
		dictionary.put("Select one of the projects using the drop down list and click OK to confirm selection.", "Elija uno the los projectos usando el menu y presione OK para confirmar la seleccion.");
		dictionary.put("English", "Ingles");
		dictionary.put("Random", "Aleatorio");
		dictionary.put("Rule", "Regla");
		dictionary.put("Back", "Atras");
		dictionary.put("Stop", "Parar");
		dictionary.put("Reset", "Resetear");
		dictionary.put("Clear", "Borrar");
		dictionary.put("Iterations", "Iteraciones");
		dictionary.put("Spanish", "Espanol");
		dictionary.put("Close", "Cerrar");
		dictionary.put("Help", "Ayuda");
		dictionary.put("Model", "Modelo");
		dictionary.put("Set", "Generar");
		dictionary.put("Start", "Generar");
		dictionary.put("Language", "Lenguaje");
		dictionary.put("Cellular Automata", "Automata Celular");
		dictionary.put("Game of Life", "Juego de la Vida");
		dictionary.put("Invalid input", "Entrada invalida");
		dictionary.put("Please enter a binary number (8 digits).", "Por favor entre un numero en binario (8 digitos).");
		dictionary.put("Please enter a positive integer.", "Por favor entre un numero entero positivo.");
	}

	@FXML
	public void switchToEnglish() {
		inSpanish = false;
		setLanguage();
	}

	@FXML
	public void switchToSpanish() {
		inSpanish = true;
		setLanguage();
	}

	String translate(String str, boolean inSpanish) {
		return inSpanish ?  dictionary.getOrDefault(str, str) : getKeyFromValue(str);
	}
	
	String getKeyFromValue(String value) {
        for (Map.Entry<String, String> entry : dictionary.entrySet()) 
        	 if (value.equals(entry.getValue())) return entry.getKey();
        return value;
    }


	void setInputLimit(int inputLimit, TextField textField) {
		textField.setTextFormatter(new TextFormatter<>(
				change -> change.isAdded() && change.getControlNewText().length() > inputLimit ? null : change));
	}

	/**
	 * Navigates to the main window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 */
	@FXML
	public void goToMain() {
		stage.setScene(Main.mainScene);
	}

	@FXML
	public abstract void start(ActionEvent event) throws IOException;

	abstract void setLanguage();

}
