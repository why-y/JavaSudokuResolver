package gry.sample.sudoku.matrix;

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
		validate(row);
		validate(column);
		return new Position(row, column);
	}
	
	static private void validate(int index) {
		if(index < 0) {
			throw new IllegalArgumentException("Indexes for Positions must be positive!");
		}
	}

	public int getRow() {
		return row;
	}
		
	public int getColumn() {
		return column;
	}
	
	public boolean isAfter(final Position otherPosition) {
		return this.compareTo(otherPosition)>0;
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
	public int compareTo(final Position other) {
		return (this.getRow()*1000 + this.getColumn())
				- (other.getRow()*1000 + other.getColumn());
	}
}