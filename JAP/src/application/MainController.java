package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main controller class for the application.
 */
public class MainController extends GeneralController<Project> {

	@FXML
	public void initialize() {
		initDictionary();
	}

	/**
	 * Navigates to a project window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 * @throws IOException If an I/O error occurs.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(ActionEvent event) throws IOException {

		Project project = projectComboBox.getValue();

		if (project == null) {
			Project.showAlert("No project selected", "Please select a project.");
			return;
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource(project.getFxml()));
		Scene scene = new Scene(loader.load());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		((GeneralController<Project>) loader.getController()).setRequiredData(project, stage);
		project.makeGrid(scene, project);
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
	public void openHelp(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Help.fxml"));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		addStyle(scene, "application.css");
		stage.setScene(scene);
		stage.show();
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
		languageMenuButton.setText(translate("Language", inSpanish));
		languageMenuButton.getItems().forEach((item) -> {
			item.setText(translate(item.getText(), inSpanish));
		});
		closeButton.setText(translate("Close", inSpanish));
		helpButton.setText(translate("Help", inSpanish));
	}

}
