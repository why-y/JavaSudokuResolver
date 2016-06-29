package gry.sample.sudoku;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Matrix {

	static final int BLOCK_SIZE = 3;
	static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
	static final int COLUMNS = ROWS; // the sudoku is always a square

	private Map<Position, Integer> valueMap;

	private Matrix(final Map<Position, Integer> valueMap){
		this.valueMap = valueMap;
	}

	public static Matrix load(int[][] data) {
		Map<Position, Integer> valueMap = new TreeMap<>();
		IntStream.range(0, data.length).boxed()
				.map(rowNo -> IntStream.range(0, data[rowNo].length).boxed()
						.map(colNo -> Position.at(rowNo, colNo)))
				.flatMap(p -> p)
				.forEach(p -> valueMap.put(p, data[p.getRow()][p.getColumn()]));
		return new Matrix(valueMap);
	}

	public Matrix clone() {
		Map<Position, Integer> clonedData = new TreeMap<>();
		valueMap.keySet().stream()
				.forEach(position -> clonedData.put(position, valueMap.get(position)));
		return new Matrix(clonedData);
	}

	public Optional<Integer> getValueAt(final Position position) {
		validate(position);
		Integer value = valueMap.get(position);
		return value!=0 ? Optional.of(value) : Optional.empty();
	}

	public void setValueAt(int value, final Position position) {
		validate(position);
		validate(value);
		valueMap.put(position, value);
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
				.anyMatch(column -> value == valueMap.get(Position.at(row, column)));
	}

	private boolean isUniqueInColumn(int value, int column) {
		return !IntStream.range(0, ROWS)
				.anyMatch(row -> value == valueMap.get(Position.at(row, column)));
	}

	private boolean isUniqueInBlock(int value, final Position position) {
		Position blockPosition = getBlockPositionFor(position);
		return IntStream.range(0, value)
				.anyMatch(blockRow->isUniqueInBlockRow(value, blockPosition, blockRow));
	}

	private boolean isUniqueInBlockRow(int value, final Position blockPosition, int blockRow) {
		int row = blockPosition.getRow()*BLOCK_SIZE + blockRow;
		return !IntStream.range(0, BLOCK_SIZE)
				.anyMatch(blockColumn -> value == valueMap.get(Position.at(row, blockPosition.getColumn()*BLOCK_SIZE + blockColumn)));
	}

	private Position getBlockPositionFor(final Position position) {
		return Position.at(position.getRow()/BLOCK_SIZE, position.getColumn()/BLOCK_SIZE);
	}

	public Optional<Position> getNextUnresolvedPosition() {
		return streamUnresolvedPositions().findFirst();
	}

	public Stream<Position> streamUnresolvedPositions() {
		return streamPositions().filter(pos -> valueMap.get(pos)==0);
	}

	private Stream<Position> streamPositions() {
		return valueMap.keySet().stream();
	}

	private void validate(Integer value) {
		if(value<0 || value>9)
			throw new IllegalArgumentException(String.format("Invalid Value: %d! Allowed values are 0..9.", value));
	}

	private void validate(final Position position) {
		if (!valueMap.containsKey(position))
			throw new IndexOutOfBoundsException(String.format("Invalid Position: %s!", position));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Matrix matrix = (Matrix) o;

		return valueMap != null ? valueMap.equals(matrix.valueMap) : matrix.valueMap == null;

	}

	@Override
	public int hashCode() {
		return valueMap != null ? valueMap.hashCode() : 0;
	}

	@Override
	public String toString() {
		return valueMap.toString();
	}

	public String toFormatedString() {
		StringBuilder sb = new StringBuilder();
		final List<Position> previousPositionContainer = Arrays.asList(Position.at(0,0));
		valueMap.keySet().stream().forEach(position -> {
			if(position.getRow() != previousPositionContainer.get(0).getRow()) {
				sb.append(System.lineSeparator());
			}
			sb.append(valueMap.get(position)).append(", ");
			if(position.getColumn()%3 == 2) {
				sb.append("  ");
			}
			previousPositionContainer.set(0, position);
		});
		return sb.toString();
	}

	/////////////////////////////////////////////////////////////////////////
	
//	public static final int BLOCK_SIZE = 3;
//	public static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
//	public static final int COLUMNS = ROWS; // the sudoku matrix is always a square
//
//	private int[][] values;
//
//	private Matrix(int[][] values) {
//		this.values = values;
//	}
//
//	public static Matrix load(int[][] matrix){
//		return new Matrix(matrix);
//	}
//
//	public Matrix clone() {
//		return new Matrix(new int[][]{
//				Arrays.copyOf(values[0], ROWS),
//				Arrays.copyOf(values[1], ROWS),
//				Arrays.copyOf(values[2], ROWS),
//				Arrays.copyOf(values[3], ROWS),
//				Arrays.copyOf(values[4], ROWS),
//				Arrays.copyOf(values[5], ROWS),
//				Arrays.copyOf(values[6], ROWS),
//				Arrays.copyOf(values[7], ROWS),
//				Arrays.copyOf(values[8], ROWS)});
//	}
//
//	public Optional<Position> getFirstFreePosition() {
//		return getEachFreePosition().findFirst();
//	}
//
//	Stream<Position> getEachFreePosition() {
//		return getEachPosition().filter(pos -> getValueAt(pos)==0);
//	}
//
//	Stream<Position> getEachPosition() {
//    	return IntStream.range(0, ROWS).boxed()
//    			.flatMap(row -> IntStream.range(0, COLUMNS).mapToObj(column -> new Position(row, column)));
//	}
//
//	int getValueAt(final Position position) {
//		return this.values[position.getRow()][position.getColumn()];
//	}
//
//	void setValueAt(int val, final Position position) {
//		this.values[position.getRow()][position.getColumn()] = val;
//	}
//
//
//	public List<Integer> getMatchingValues(Position position) {
//		return IntStream.range(0, 10)
//				.filter(value -> isUnique(value, position))
//				.boxed()
//				.collect(Collectors.toList());
//	}
//
//	private boolean isUnique(int value, final Position pos) {
//		return isUniqueInRow(value, pos.getRow()) &&
//				isUniqueInColumn(value, pos.getColumn()) &&
//				isUniqueInBlock(value, pos);
//	}
//
//	private boolean isUniqueInRow(int value, int row) {
//		return !IntStream.range(0, COLUMNS)
//				.anyMatch(i->value==this.values[row][i]);
//	}
//
//	private boolean isUniqueInColumn(int value, int column) {
//		return !IntStream.range(0, ROWS)
//				.anyMatch(i->value==this.values[i][column]);
//	}
//
//	private boolean isUniqueInBlock(int value, final Position position) {
//		Position blockPosition = getBlockPositionFor(position);
//		return IntStream.range(0, value)
//				.anyMatch(blockRow->isUniqueInBlockRow(value, blockPosition, blockRow));
//	}
//
//	private boolean isUniqueInBlockRow(int value, final Position blockPosition, int blockRow) {
//		int row = blockPosition.getRow()*BLOCK_SIZE + blockRow;
//		return !IntStream.range(0, BLOCK_SIZE)
//				.anyMatch(blockColumn->value==this.values[row][blockPosition.getColumn()*BLOCK_SIZE + blockColumn]);
//	}
//
//	private Position getBlockPositionFor(final Position position) {
//		return new Position(position.getRow()/BLOCK_SIZE, position.getColumn()/BLOCK_SIZE);
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Arrays.deepHashCode(values);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Matrix other = (Matrix) obj;
//		if (!Arrays.deepEquals(values, other.values))
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		StringBuilder matrixStringBuilder = new StringBuilder();
//		IntStream.range(0, ROWS).forEach(y -> matrixStringBuilder
//				.append(getRowAsNiceString(y))
//				.append(System.lineSeparator())
//				.append(y % BLOCK_SIZE == 2 ? System.lineSeparator() : ""));
//		return matrixStringBuilder.toString();
//	}
//
//	private String getRowAsNiceString(int y) {
//		StringBuilder rowStringBuilder = new StringBuilder();
//		IntStream.range(0, COLUMNS).forEach(x -> rowStringBuilder
//				.append(values[y][x])
//				.append(x % BLOCK_SIZE == 2 ? "  " : " "));
//		return rowStringBuilder.toString();
//	}
//

	
}
