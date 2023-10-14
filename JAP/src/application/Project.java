package application;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Project {
	
	static final int DEFAULT_GRID_WIDTH = 69;
	static final int DEFAULT_GRID_HEIGHT = 35;
	static final double DEFAULT_CELL_SIZE = 10;
	
	int gridWidth = DEFAULT_GRID_WIDTH;
	int gridHeight = DEFAULT_GRID_HEIGHT;
	double cellSize = DEFAULT_CELL_SIZE;

	GridPane grid;

	/**
	 * Creates a grid for the cellular automaton and adds it to the specified scene.
	 *
	 * @param scene The scene to which the grid will be added.
	 */
	void makeGrid(Scene scene, Project project) {
		grid = new GridPane();
		initializeGrid('0', project instanceof GameOfLife);
		addGridToScene(scene);
	}

	/**
	 * Adds the grid to the specified scene.
	 *
	 * @param scene The scene to which the grid will be added.
	 */
	void addGridToScene(Scene scene) {
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		((BorderPane) scene.getRoot()).setCenter(grid);
	}

	void initializeGrid(char defaultState, boolean allowInteraction) {
		for (int row = 0; row < gridHeight; row++)
			for (int col = 0; col < gridWidth; col++)
				addCell(row, col, defaultState, allowInteraction);
	}

	
	void addCell(int row, int col, char state, boolean allowInteraction) {
		Rectangle cell = new Rectangle(cellSize, cellSize, state == '0' ? Color.WHITESMOKE : Color.BLACK);
		cell.setStroke(Color.DARKGREY);
		GridPane.setConstraints(cell, col, row, 1, 1, HPos.CENTER, VPos.CENTER);
		
		if(allowInteraction)
			cell.setOnMouseClicked(event -> {
			    if (event.getButton() == MouseButton.PRIMARY)
			    	cell.setFill(cell.getFill().equals(Color.WHITESMOKE) ? Color.BLACK : Color.WHITESMOKE);
			});

		grid.add(cell, col, row);
	}
	
	/**
	 * Toggles a cell in the grid based on its state (0 or 1).
	 *
	 * @param row   The row of the cell.
	 * @param col   The column of the cell.
	 * @param state The state of the cell ('0' or '1').
	 */
	void toggleCell(int row, int col, char state) {
		Rectangle cell = (Rectangle) grid.getChildren().get(row * gridWidth + col);
		cell.setFill(state == '0' ? Color.WHITESMOKE : Color.BLACK);
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
