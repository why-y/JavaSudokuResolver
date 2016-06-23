package gry.sample.sudoku;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuResolver {

	public static final int BLOCK_SIZE = 3;

	public static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
	public static final int COLUMNS = ROWS; // the sudoku is always a square
	
	private int[][] matrix;
	
	public void init(int[][] unresolvedMatrix) {
		matrix = unresolvedMatrix;
	}

	public boolean resolveIt() {
		// 1) Values which are distinct can easily be filled in advance.
		//    This step is optional!
//		fillDistinctValues();
		
		// 2) Start resolve recursion for the open positions
		Position firstFreePosition = getFirstFreePosition();
		if (firstFreePosition == null) {
			return true;
		}
		return resolve(firstFreePosition);
	}
	
	private void fillDistinctValues() {
		getEachFreePosition().forEach(pos -> {
			List<Integer> matchingValues = getMatchingValues(pos);
			if(matchingValues.size()==1) {
				setValueAt(matchingValues.get(0), pos);
			}
		});		
	}
	
	private Stream<Position> getEachFreePosition() {
		return getEachPosition().filter(pos -> getValueAt(pos)==0);
	}
	
	private Stream<Position> getEachPosition() {
    	return IntStream.range(0, ROWS).boxed()
    			.flatMap(row -> IntStream.range(0, COLUMNS).mapToObj(column -> new Position(row, column)));
	}

	/**
	 * Recursive resolve method
	 * @param position
	 * @return
     */
	private boolean resolve(final Position position) {
		
		for (Integer matchingValue : getMatchingValues(position)) {
			setValueAt(matchingValue, position);
			if(!hasNextFreePosition(position) || resolve(getNextFreePosition(position))) {
				return true; // Resolved!!
			}
			else {
				resetValueAt(position);
			}
		}
		return false; // step back
	}

	private List<Integer> getMatchingValues(Position position) {		
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
		int row = blockPosition.getRow()*BLOCK_SIZE + blockRow;
		return !IntStream.range(0, BLOCK_SIZE)
				.anyMatch(blockColumn->value==matrix[row][blockPosition.getColumn()*BLOCK_SIZE + blockColumn]);		
	}
	
	private Position getBlockPositionFor(final Position position) {
		return new Position(position.getRow()/BLOCK_SIZE, position.getColumn()/BLOCK_SIZE);
	}
		
	private boolean hasNextFreePosition(final Position startPosition) {
		return getNextFreePosition(startPosition)!=null;
	}
	
	private Position getFirstFreePosition() {
		Position firstPosition = new Position(0, 0);
		return getValueAt(firstPosition)==0 ? firstPosition : getNextFreePosition(firstPosition);
	}

	/**
	 * Returns the next Position in the matrix, which contains '0', or null if there isn't a '0' anymore.
	 * @param position
	 * @returns
     */
	private Position getNextFreePosition(final Position position) {
		Position nextPosition = position;
		while((nextPosition = nextPosition.getNextPosition()) != null && getValueAt(nextPosition)!=0) {};
		return nextPosition;
	}
	
	private int getValueAt(final Position position) {
		return matrix[position.getRow()][position.getColumn()];
	}
	
	private void setValueAt(int val, final Position position) {
		matrix[position.getRow()][position.getColumn()] = val;
	}
	
	private void resetValueAt(final Position position) {
		setValueAt(0, position);
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
