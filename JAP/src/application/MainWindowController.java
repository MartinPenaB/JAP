package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main controller class for the application.
 */
public class MainWindowController extends GeneralController<Project> {
	
	String helpHeaderText = "Help";
	String helpContentText = "Select one of the projects using the drop down list and click OK to confirm selection.";

	@FXML
	public void initialize() {
		setDictionary();
	}

	/**
	 * Navigates to a project window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 * @throws IOException If an I/O error occurs.
	 */
	@Override
	public void start(ActionEvent event) throws IOException {

		Project project = projectComboBox.getValue();

		if (project == null) {
			Project.showAlert("No project selected", "Please select a project.");
			return;
		}

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = project.scene;
		if (scene != null) {
			stage.setScene(scene);
			project.controller.setLanguage();
			return;
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource(project.getFxml()));
		scene = new Scene(loader.load());
		GeneralController<Project> controller = loader.getController();
		controller.setRequiredData(project, stage);
		project.setRequiredData(scene, controller);
		project.generateGrid(scene, project);
		addStyle(scene, "application.css");
		projectStageSetup(stage, scene);

	}

	void addStyle(Scene scene, String cssFile) {
		String css = getClass().getResource(cssFile).toExternalForm();
		scene.getStylesheets().add(css);
	}

	void projectStageSetup(Stage stage, Scene scene) {
		stage.setResizable(false);
		stage.getIcons().add(new Image("/javaicon.png"));
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Opens the help window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 * @throws IOException If an I/O error occurs.
	 */
	@FXML
	public void openHelp() {
		new Alert(AlertType.INFORMATION) {
			{
				setHeaderText(helpHeaderText);
				setContentText(helpContentText);
				show();
			}
		};
	}

	/**
	 * Quits the application.
	 */
	@FXML
	public void quitApplication() {
		((Stage) mainAnchorPane.getScene().getWindow()).close();
	}

	@Override
	void setLanguage() {
		projectComboBox.setPromptText(translate("Select a project", inSpanish));
		languageMenuBar.getMenus().getFirst().setText(translate("Language", inSpanish));
		languageMenuBar.getMenus().getFirst().getItems().forEach(item -> item.setText(translate(item.getText(), inSpanish)));	
		closeButton.setText(translate("Close", inSpanish));
		helpButton.setText(translate("Help", inSpanish));
		helpHeaderText = translate("Help", inSpanish);
		helpContentText = translate("Select one of the projects using the drop down list and click OK to confirm selection.", inSpanish);
	}

}
