package application;

import javafx.scene.paint.Color;


public class GameOfLife extends Project {
	
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

	Color getColor(int row, int col) {
		try {
			return (Color) getCell(row, col).getFill();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	int getNum(int row, int col) {
		Color color = getColor(row, col);
		return (color != null && color.equals(Color.BLACK)) ? 1 : 0;
	}

	int getRowNeighbors(int row, int col, boolean containsSelf) {
		return getNum(row, col - 1) + getNum(row, containsSelf ? -1 : col) + getNum(row, col + 1);
	}

	int getTotalNeighbors(int row, int col) {
		return getRowNeighbors(row - 1, col, false) + getRowNeighbors(row, col, true)
				+ getRowNeighbors(row + 1, col, false);
	}

	char getCellFate(int row, int col) {
		Color state = getColor(row, col);
		int neighbors = getTotalNeighbors(row, col);
		if (state.equals(Color.BLACK) && (neighbors < 2 || neighbors > 3))
			return '0';
		else if (neighbors == 3)
			return '1';
		return state.equals(Color.BLACK) ? '1' : '0';
	}

	char[][] getNewGen() {
		char[][] snapshot = new char[gridHeight][gridWidth];
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				snapshot[row][cell] = getCellFate(row, cell);
		return snapshot;
	}

	void updateGrid(char[][] snapshot) {
		for (int row = 0; row < gridHeight; row++)
			for (int cell = 0; cell < gridWidth; cell++)
				toggleCell(row, cell, snapshot[row][cell]);
	}

}
