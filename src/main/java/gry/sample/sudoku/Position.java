package gry.sample.sudoku;

/**
 * Represents the Position within the sudoku [column, row]
 */
public class Position{
	int x; // column
	int y; // row
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;			
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
		
	public Position increment() {
		if (isLastPosition()) return null;
		return isInLastColumn() ? 
				new Position(0, y + 1) : // reset column to 0 and increment row
				new Position(x+1, y);    // increment column and stay in the same row
	}
	
	private boolean isLastPosition(){
		return isInLastColumn() && isInLastRow();
	}

	private boolean isInLastColumn(){
		return x == SudokuResolver.COLUMNS-1;
	}

	private boolean isInLastRow(){
		return y == SudokuResolver.ROWS-1;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
}