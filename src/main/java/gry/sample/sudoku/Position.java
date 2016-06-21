package gry.sample.sudoku;

/**
 * Represents the Position within the sudoku [column, row]
 */
public class Position{
	int row;
	int column; 
	
	public Position(int row, int column) {
		this.row = row;			
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
		
	public int getColumn() {
		return column;
	}
	
	public Position increment() {
		if (isLastPosition()) return null;
		return isInLastColumn() ? 
				new Position(row + 1, 0) : // reset column to 0 and increment row
				new Position(row, column+1);    // increment column and stay in the same row
	}
	
	private boolean isLastPosition(){
		return isInLastRow() && isInLastColumn();
	}

	private boolean isInLastRow(){
		return row == SudokuResolver.ROWS-1;
	}

	private boolean isInLastColumn(){
		return column == SudokuResolver.COLUMNS-1;
	}
	
	@Override
	public String toString() {
		return String.format("Pos[%d-%d]", row, column);
	}
	
}