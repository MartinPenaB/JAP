package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Represents a cellular automaton project.
 */
public class CellularAutomata extends Project {

	/** The size of each cell in the grid. */
	static final double CELL_SIZE = 3;

	/** The width of the grid. */
	static final int GRID_WIDTH = 203;

	/** The height of the grid. */
	static final int GRID_HEIGHT = 100;

	/** Delay between adding cells in milliseconds */
	static final int ANIMATION_DELAY_MS = 30;

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

	/**
	 * Evolves the cellular automaton based on the given model.
	 *
	 * @param rule The model of the cellular automaton.
	 */
	void evolve(String rule) {

		String generation = pad("1");
		Timeline timeline = new Timeline();

		for (int gen = 0; gen < GRID_HEIGHT; gen++) {

			String currGen = generation;
			int genIdx = gen;

			KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY_MS * gen), event -> {
				for (int cell = 0; cell < GRID_WIDTH; cell++)
					toggleCell(genIdx, cell, currGen.charAt(cell));
			});

			timeline.getKeyFrames().add(keyFrame);
			generation = pad(nextGen(generation, rule));

		}

		timeline.play();

	}

	char checkCells(int start, String currentGen, String model) {
		StringBuilder rev = new StringBuilder();
		for (int i = 0; i < model.length(); i++)
			rev.insert(0, model.charAt(i));
		return rev.charAt(toDec(currentGen.substring(start, start + 3)));
	}

	/**
	 * Generates the next generation of the cellular automaton based on the current
	 * generation.
	 *
	 * @param rule       the model of the cellular automata
	 * @param currentGen The current generation of the cellular automaton.
	 * @return The next generation of the cellular automaton.
	 */
	String nextGen(String currentGen, String rule) {
		StringBuilder nextGen = new StringBuilder();
		for (int i = 0; i < currentGen.length() - 2; i++)
			nextGen.append(checkCells(i, currentGen, rule));
		return nextGen.toString();
	}

	/**
	 * Trims leading '0' characters from the model.
	 *
	 * @param model The model to be trimmed.
	 * @return The trimmed model.
	 */
	String trimModel(String model) {
		return model.replaceFirst("^0+", "");
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

	/**
	 * Pads the model with '0' characters to match the GRID_WIDTH.
	 *
	 * @param model The model to be padded.
	 * @return The padded model.
	 */
	String pad(String model) {
		StringBuilder padded = new StringBuilder(model);
		while (padded.length() != GRID_WIDTH) {
			if (padded.length() % 2 == 0)
				padded.insert(0, '0');
			else
				padded.append('0');
		}
		return padded.toString();
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
		cell.setStroke(Color.WHITESMOKE);
		grid.add(cell, col, row);
	}

	/**
	 * Converts a binary model to its decimal equivalent.
	 *
	 * @param model The binary model to be converted.
	 * @return The decimal equivalent of the model.
	 */
	int toDec(String model) {
		int dec = 0;
		for (int pow = model.length() - 1, idx = 0; pow >= 0; pow--, idx++)
			dec += model.charAt(idx) == '1' ? Math.pow(2, pow) : 0;
		return dec;
	}

	/**
	 * Returns a string representation of the cellular automaton project.
	 *
	 * @return A string describing the project.
	 */
	public String toString() {
		return "[A12] CA - Cellular Automata";
	}

	private void initializeGrid(char defaultState) {
		for (int row = 0; row < GRID_HEIGHT; row++)
			for (int col = 0; col < GRID_WIDTH; col++)
				toggleCell(row, col, defaultState);
	}
}
