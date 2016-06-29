package gry.sample.sudoku;

/**
 * Represents the Position within the sudoku [column, row]
 */
public class Position implements Comparable<Position> {
	int row;
	int column; 
	
	private Position(int row, int column) {
		this.row = row;			
		this.column = column;
	}

	static public Position at(Integer row, Integer column) {
		return new Position(row, column);
	}

	public int getRow() {
		return row;
	}
		
	public int getColumn() {
		return column;
	}
	
	public Position getNextPosition() {
		if (isLastPosition()) return null;
		return isInLastColumn() ? 
				new Position(row + 1, 0) : // reset column to 0 and increment row
				new Position(row, column+1);    // increment column and stay in the same row
	}
	
	private boolean isLastPosition(){
		return isInLastRow() && isInLastColumn();
	}

	private boolean isInLastRow(){
		return row == Matrix.ROWS-1;
	}

	private boolean isInLastColumn(){
		return column == Matrix.COLUMNS-1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Position position = (Position) o;

		if (row != position.row) return false;
		return column == position.column;

	}

	@Override
	public int hashCode() {
		int result = row;
		result = 31 * result + column;
		return result;
	}

	@Override
	public String toString() {
		return String.format("[%d-%d]", row, column);
	}

	@Override
	public int compareTo(Position other) {
		return (this.getRow()*1000 + this.getColumn())
				- (other.getRow()*1000 + other.getColumn());
	}
}