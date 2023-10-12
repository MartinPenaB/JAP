package application;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Project {

	static final double DEFAULT_CELL_SIZE = 3;
	static final int DEFAULT_GRID_WIDTH = 203;
	static final int DEFAULT_GRID_HEIGHT = 100;

	double cellSize = DEFAULT_CELL_SIZE;
	int gridWidth = DEFAULT_GRID_WIDTH;
	int gridHeight = DEFAULT_GRID_HEIGHT;

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
		for (int row = 0; row < gridHeight; row++)
			for (int col = 0; col < gridWidth; col++)
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
		Rectangle cell = new Rectangle(cellSize, cellSize, state == '0' ? Color.WHITESMOKE : Color.BLACK);
		cell.setStroke(Color.DARKGREY);
		GridPane.setConstraints(cell, col, row, 1, 1, HPos.CENTER, VPos.CENTER);
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
	
	abstract String getFxml();

	abstract String getTitle();
	
	abstract void evolve(String rule);

}
