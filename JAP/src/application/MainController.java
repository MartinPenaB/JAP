package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main controller class for the application.
 */
public class MainController {

	@FXML
	public ComboBox<Project> projectComboBox;
	@FXML
	private AnchorPane mainAnchorPane;

	/**
	 * Navigates to the main window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 */
	@FXML
	public void goToMain(ActionEvent event) {
		new Main().start((Stage) ((Node) event.getSource()).getScene().getWindow());
	}

	/**
	 * Navigates to a project window.
	 *
	 * @param event The ActionEvent triggered by the button click.
	 * @throws IOException If an I/O error occurs.
	 */
	@FXML
	public void goToProject(ActionEvent event) throws IOException {
		if (projectComboBox.getValue() instanceof CellularAutomata ca) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CA.fxml"));
			Parent root = loader.load();

			AutomataController controller = loader.getController();
			controller.automata = ca;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.mainStage = stage;

			Scene scene = new Scene(root);

			String css = getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);

			ca.makeGrid(scene);

			stage.setResizable(false);
			stage.getIcons().add(new Image("/javaicon.png"));
			stage.setTitle("Cellular Automata");
			stage.setScene(scene);
			stage.show();
		} else {
			showAlert("No project selected", "Please select a project.");
		}
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
		String css = getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
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

	/**
	 * Displays an alert dialog with the given header and content text.
	 *
	 * @param headerText  The header text for the alert.
	 * @param contentText The content text for the alert.
	 */
	private void showAlert(String headerText, String contentText) {
		new Alert(AlertType.WARNING) {
			{
				setHeaderText(headerText);
				setContentText(contentText);
				show();
			}
		};
	}
}
