package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Project {

	/** The size of each cell in the grid. */
	static final double CELL_SIZE = 3;
	
	/** The width of the grid. */
	static final int GRID_WIDTH = 203;

	/** The height of the grid. */
	static final int GRID_HEIGHT = 100;
	
	/** The grid for displaying the cellular automaton. */
	GridPane grid;
	
	/**
	 * Creates a grid for the cellular automaton and adds it to the specified scene.
	 *
	 * @param scene The scene to which the grid will be added.
	 */
	void makeGrid(Scene scene) {
		grid = new GridPane();
		initializeGrid('0');
		addGridToScene(scene);
	}
	
	String getFxml() {
		return null;
	}
	String getTitle() {
		return null;
	}
	
	/**
	 * Adds the grid to the specified scene.
	 *
	 * @param scene The scene to which the grid will be added.
	 */
	void addGridToScene(Scene scene) {
		grid.setAlignment(Pos.CENTER);
		((BorderPane) scene.getRoot()).setCenter(grid);
	}
	
	void initializeGrid(char defaultState) {
		for (int row = 0; row < GRID_HEIGHT; row++)
			for (int col = 0; col < GRID_WIDTH; col++)
				toggleCell(row, col, defaultState);
	}
	
	/**
	 * Toggles a cell in the grid based on its state (0 or 1).
	 *
	 * @param row   The row of the cell.
	 * @param col   The column of the cell.
	 * @param state The state of the cell ('0' or '1').
	 */
	void toggleCell(int row, int col, char state) {
		Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE, state == '0' ? Color.WHITESMOKE : Color.BLACK);
		cell.setStroke(Color.GREY);
		grid.add(cell, col, row);
	}
	
	/**
	 * Displays an alert dialog with the given header and content text.
	 *
	 * @param headerText  The header text for the alert.
	 * @param contentText The content text for the alert.
	 */
	static void showAlert(String headerText, String contentText) {
		new Alert(AlertType.WARNING) {
			{
				setHeaderText(headerText);
				setContentText(contentText);
				show();
			}
		};
	}
	
}
