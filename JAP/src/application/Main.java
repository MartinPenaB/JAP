package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = loader.load();

			MainController controller = loader.getController();
			controller.projectComboBox.getItems().addAll(new CellularAutomata());

			Scene scene = new Scene(root);

			String css = getClass().getResource("application.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			stage.setResizable(false);
			stage.getIcons().add(new Image("/javaicon.png"));
			stage.setTitle("[JAP - Computer Sciene]");
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
