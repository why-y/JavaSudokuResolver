package gry.sample.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Matrix {
	
	public static final int BLOCK_SIZE = 3;
	public static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
	public static final int COLUMNS = ROWS; // the sudoku matrix is always a square

	private int[][] values;
	
	private Matrix(int[][] values) {
		this.values = values;
	}
	
	public static Matrix load(int[][] matrix){
		return new Matrix(matrix);
	}
	
	public Matrix clone() {
		return new Matrix(new int[][]{values[0], values[1], values[2], values[3]
				, values[4], values[5], values[6], values[7], values[8]});
	}
	
	public Optional<Position> getFirstFreePosition() {
		return getEachFreePosition().findFirst();
	}
	
	Stream<Position> getEachFreePosition() {
		return getEachPosition().filter(pos -> getValueAt(pos)==0);
	}
	
	Stream<Position> getEachPosition() {
    	return IntStream.range(0, ROWS).boxed()
    			.flatMap(row -> IntStream.range(0, COLUMNS).mapToObj(column -> new Position(row, column)));
	}
	
	int getValueAt(final Position position) {
		return this.values[position.getRow()][position.getColumn()];
	}
	
	void setValueAt(int val, final Position position) {
		this.values[position.getRow()][position.getColumn()] = val;
	}
	

	public List<Integer> getMatchingValues(Position position) {		
		return IntStream.range(0, 10)
				.filter(value -> isUnique(value, position))
				.boxed()
				.collect(Collectors.toList());
	}	

	private boolean isUnique(int value, final Position pos) {
		return isUniqueInRow(value, pos.getRow()) && 
				isUniqueInColumn(value, pos.getColumn()) && 
				isUniqueInBlock(value, pos);
	}
	
	private boolean isUniqueInRow(int value, int row) {
		return !IntStream.range(0, COLUMNS)
				.anyMatch(i->value==this.values[row][i]);
	}
	
	private boolean isUniqueInColumn(int value, int column) {
		return !IntStream.range(0, ROWS)
				.anyMatch(i->value==this.values[i][column]);
	}
	
	private boolean isUniqueInBlock(int value, final Position position) {
		Position blockPosition = getBlockPositionFor(position);
		return IntStream.range(0, value)
				.anyMatch(blockRow->isUniqueInBlockRow(value, blockPosition, blockRow));
	}

	private boolean isUniqueInBlockRow(int value, final Position blockPosition, int blockRow) {
		int row = blockPosition.getRow()*BLOCK_SIZE + blockRow;
		return !IntStream.range(0, BLOCK_SIZE)
				.anyMatch(blockColumn->value==this.values[row][blockPosition.getColumn()*BLOCK_SIZE + blockColumn]);		
	}
	
	private Position getBlockPositionFor(final Position position) {
		return new Position(position.getRow()/BLOCK_SIZE, position.getColumn()/BLOCK_SIZE);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (!Arrays.deepEquals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder matrixStringBuilder = new StringBuilder();
		IntStream.range(0, ROWS).forEach(y -> matrixStringBuilder
				.append(getRowAsNiceString(y))
				.append(System.lineSeparator())
				.append(y % BLOCK_SIZE == 2 ? System.lineSeparator() : ""));
		return matrixStringBuilder.toString();
	}

	private String getRowAsNiceString(int y) {
		StringBuilder rowStringBuilder = new StringBuilder();
		IntStream.range(0, COLUMNS).forEach(x -> rowStringBuilder
				.append(values[y][x])
				.append(x % BLOCK_SIZE == 2 ? "  " : " "));
		return rowStringBuilder.toString();
	}


	
}
