package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
	protected TextField inputTextField;
	@FXML
	protected Label inputLabel;
	@FXML
	protected Button startButton;
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
	protected MenuButton languageMenuButton;
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
		dictionary.put("English", "Ingles");
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
		return inSpanish ? dictionary.get(str) : str;
	}

	void setInputLimit(int inputLimit) {
		inputTextField.setTextFormatter(new TextFormatter<>(
				change -> change.isAdded() && change.getControlNewText().length() > inputLimit ? null : change));
	}

	/**
	 * Navigates to the main window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 */
	@FXML
	public void goToMain(ActionEvent event) {
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		mainStage.setTitle("[JAP - Computer Sciene]");
		mainStage.setScene(Main.mainScene);
	}

	@FXML
	public abstract void start(ActionEvent event) throws IOException;

	abstract void setLanguage();

}
