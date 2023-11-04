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

	boolean multicolor = false;
	boolean interactable = false;

	static final Color DEFAULT_COLOR = Color.WHITESMOKE;

	static final int DEFAULT_GRID_WIDTH = 69;
	static final int DEFAULT_GRID_HEIGHT = 35;
	static final double DEFAULT_CELL_SIZE = 10;

	int gridWidth = DEFAULT_GRID_WIDTH;
	int gridHeight = DEFAULT_GRID_HEIGHT;
	double cellSize = DEFAULT_CELL_SIZE;

	GridPane grid;
	Scene scene;
	GeneralController<Project> controller;

	void setRequiredData(Scene scene, GeneralController<Project> controller) {
		this.scene = scene;
		this.controller = controller;
	}

	/**
	 * Creates a grid for the cellular automaton and adds it to the specified scene.
	 *
	 * @param scene The scene to which the grid will be added.
	 */
	void generateGrid() {
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
		grid.setGridLinesVisible(true);
		((BorderPane) scene.getRoot()).setCenter(grid);
	}

	void initializeGrid(char defaultState) {
		for (int row = 0; row < gridHeight; row++)
			for (int col = 0; col < gridWidth; col++)
				addCell(row, col, defaultState);
	}

	void addCell(int row, int col, char state) {
		Rectangle cell = new Rectangle(cellSize, cellSize, state == '0' ? DEFAULT_COLOR : Color.BLACK);
		cell.setStroke(Color.DARKGREY);
		GridPane.setConstraints(cell, col, row, 1, 1, HPos.CENTER, VPos.CENTER);

		cell.setOnMouseClicked(event -> {
			if (interactable && event.getButton() == MouseButton.PRIMARY) {
				cell.setFill(cell.getFill().equals(DEFAULT_COLOR) ? Color.BLACK : DEFAULT_COLOR);
				updateColors();
			}
		});

		grid.add(cell, col, row);
	}

	Color getCellColor(int row, int col) {
		try {
			return (Color) getCell(row, col).getFill();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	void updateColors() {
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				if (!getCellColor(row, cell).equals(DEFAULT_COLOR))
					getCell(row, cell).setFill(getAliveColor(row, cell));
	}

	abstract Color getAliveColor(int row, int col);

	Rectangle getCell(int row, int col) {
		if (row > -1 && row < gridHeight && col > -1 && col < gridWidth)
			return (Rectangle) grid.getChildren().get(row * gridWidth + col);
		throw new IndexOutOfBoundsException("Invalid row or column index in getCell");
	}

	/**
	 * Toggles a cell in the grid based on its state (0 or 1).
	 *
	 * @param row   The row of the cell.
	 * @param col   The column of the cell.
	 * @param state The state of the cell ('0' or '1').
	 */
	void toggleCell(int row, int col, char state) {
		getCell(row, col).setFill(state == '0' ? DEFAULT_COLOR : getAliveColor(row, col));
	}

	void clearGrid() {
		for (int row = 0; row < gridHeight; row++)
			for (int col = 0; col < gridWidth; col++)
				toggleCell(row, col, '0');
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

}
