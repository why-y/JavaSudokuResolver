package gry.sample.sudoku.matrix;

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

	@Override
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

}
