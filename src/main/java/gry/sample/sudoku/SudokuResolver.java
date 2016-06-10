package gry.sample.sudoku;

import java.util.stream.IntStream;

public class SudokuResolver {

	public static final int BLOCK_SIZE = 3;

	public static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
	public static final int COLUMNS = ROWS; // the sudoku is always a square
	
	private int[][] matrix;
	
	public void init(int[][] unresolvedMatrix) {
		matrix = unresolvedMatrix;
	}

	public boolean resolveIt() {
		Position position = getFirstFreePosition();
		if (position == null) {
			return true;
		}
		return resolve(position);
	}

	/**
	 * Recursive resolve method
	 * @param position
	 * @return
     */
	private boolean resolve(final Position position) {
		for (int value=1; value<=9; ++value) {
			if (isUnique(value, position)){
				setValueAt(value, position);
				Position nextPosition = getNextFreePosition(position);
				if(nextPosition == null || resolve(nextPosition)) {
					return true;
				}
				setValueAt(0, position);
			}
		}
		// no value matches on this position -> Roll back.
		return false;
	}

	private boolean isUnique(int value, final Position pos) {
		return isUniqueInRow(value, pos.getY()) && 
				isUniqueInColumn(value, pos.getX()) && 
				isUniqueInBlock(value, pos);
	}
	
	private boolean isUniqueInRow(int value, int row) {
		return !IntStream.range(0, COLUMNS)
				.anyMatch(i->value==matrix[row][i]);
	}
	
	private boolean isUniqueInColumn(int value, int column) {
		return !IntStream.range(0, ROWS)
				.anyMatch(i->value==matrix[i][column]);
	}
	
	private boolean isUniqueInBlock(int value, final Position position) {
		Position blockPosition = getBlockPositionFor(position);
		return IntStream.range(0, value)
				.anyMatch(blockRow->isUniqueInBlockRow(value, blockPosition, blockRow));
	}
	
	private boolean isUniqueInBlockRow(int value, final Position blockPosition, int blockRow) {
		int row = blockPosition.getY()*BLOCK_SIZE + blockRow;
		return !IntStream.range(0, BLOCK_SIZE)
				.anyMatch(blockColumn->value==matrix[row][blockPosition.getX()*BLOCK_SIZE + blockColumn]);		
	}
	
	private Position getBlockPositionFor(final Position position) {
		return new Position(position.getX()/BLOCK_SIZE, position.getY()/BLOCK_SIZE);
	}
		
	private Position getFirstFreePosition() {
		Position firstPosition = new Position(0, 0);
		return getValueAt(firstPosition)==0 ? firstPosition : getNextFreePosition(firstPosition);
	}

	/**
	 * Returns the next Position in the matrix, which contains '0', or null if there isn't a '0' anymore (i.e. resolved).
	 * @param position
	 * @returns
     */
	private Position getNextFreePosition(final Position position) {
		Position nextPosition = position;
		while((nextPosition = nextPosition.increment()) != null && getValueAt(nextPosition)!=0) {};
		return nextPosition;
	}
	
	private int getValueAt(final Position pos) {
		return matrix[pos.getY()][pos.getX()];
	}
	
	private void setValueAt(int val, final Position pos) {
		matrix[pos.getY()][pos.getX()] = val;
	}

	public String getMatrixAsNiceString() {
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
				.append(matrix[y][x])
				.append(x % BLOCK_SIZE == 2 ? "  " : " "));
		return rowStringBuilder.toString();
	}

}
