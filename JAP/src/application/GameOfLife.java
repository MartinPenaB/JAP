package application;

public class GameOfLife extends Project {
	
	{
//		gridWidth = 191;
//		gridHeight = 95;
		gridWidth = 131;
		gridHeight = 65;
		cellSize=4;
	}

	@Override
	String getFxml() {
		return "GL.fxml";
	}

	@Override
	String getTitle() {
		return "Game of Life";
	}

	public String toString() {
		return "[A22] GL - Game of Life";
	}

}
