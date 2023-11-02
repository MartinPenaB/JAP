package application;

import java.util.Random;

import javafx.scene.paint.Color;

public class GameOfLife extends Project {

	Random rand = new Random();
	
	Color aliveColor = Color.BLACK;

	static final String DEAD_RULE = "000100000";
	static final String ALIVE_RULE = "001100000";

	static final float ANIMATION_DELAY_MS = 100f;

	@Override
	String getFxml() {
		return "GL.fxml";
	}

	@Override
	String getTitle() {
		return "Game of Life";
	}

	@Override
	public String toString() {
		return "[A22] GL - Game of Life";
	}

	int getCellValue(int row, int col) {
		Color color = getCellColor(row, col);
		return color != null && !color.equals(DEFAULT_COLOR) ? 1 : 0;
	}

	int getRowNeighbors(int row, int col, boolean containsSelf) {
		return getCellValue(row, col - 1) + getCellValue(row, containsSelf ? -1 : col) + getCellValue(row, col + 1);
	}

	int getTotalNeighbors(int row, int col) {
		return getRowNeighbors(row - 1, col, false) + getRowNeighbors(row, col, true)
				+ getRowNeighbors(row + 1, col, false);
	}

	char getCellFate(int row, int col, String rules) {
		Color state = getCellColor(row, col);
		int neighbors = getTotalNeighbors(row, col);
		String deadRule = DEAD_RULE;
		String aliveRule = ALIVE_RULE;
		if (rules.matches("^[01]{18}$")) {
			deadRule = rules.substring(0, 9);
			aliveRule = rules.substring(9);
		} else
			controller.ruleTextField.clear();
		return state.equals(DEFAULT_COLOR) ? deadRule.charAt(neighbors) : aliveRule.charAt(neighbors);
	}

	char[][] getNewGen(String rules) {
		char[][] snapshot = new char[gridHeight][gridWidth];
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				snapshot[row][cell] = getCellFate(row, cell, rules);
		return snapshot;
	}

	void randomizeGridStates() {
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				toggleCell(row, cell, rand.nextBoolean() ? '1' : '0');
	}

	void updateGridStates(char[][] snapshot) {
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				toggleCell(row, cell, snapshot[row][cell]);
	}

	@Override
	Color getAliveColor(int row, int col) {

		Color newAliveColor;

		switch (getTotalNeighbors(row, col)) {
		case 0:
			newAliveColor = Color.RED;
			break;
		case 1:
			newAliveColor = Color.GREEN;
			break;
		case 2:
			newAliveColor = Color.BLUE;
			break;
		case 3:
			newAliveColor = Color.YELLOW;
			break;
		case 4:
			newAliveColor = Color.MAGENTA;
			break;
		case 5:
			newAliveColor = Color.CYAN;
			break;
		case 6:
			newAliveColor = Color.LIGHTBLUE;
			break;
		case 7:
			newAliveColor = Color.LIGHTSALMON;
			break;
		case 8:
			newAliveColor = Color.VIOLET;
			break;
		default:
			newAliveColor = Color.BLACK;
		}

		return multicolor ? newAliveColor : aliveColor;

	}

}
