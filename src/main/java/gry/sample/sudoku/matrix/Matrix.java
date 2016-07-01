package gry.sample.sudoku.matrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Matrix {

	static final int BLOCK_SIZE = 3;
	static final int ROWS = BLOCK_SIZE * 3; // each block has 3 rows
	static final int COLUMNS = ROWS; // the sudoku is always a square

	private Map<Position, Value> valueMap;

	private Matrix(final Map<Position, Value> valueMap){
		this.valueMap = valueMap;
	}

	public static Matrix load(int[][] data) {
		Map<Position, Value> valueMap = new TreeMap<>();
		IntStream.range(0, data.length).boxed()
				.map(rowNo -> IntStream.range(0, data[rowNo].length).boxed()
						.map(colNo -> Position.at(rowNo, colNo)))
				.flatMap(p -> p)
				.forEach(p -> valueMap.put(p, Value.fromInt(data[p.getRow()][p.getColumn()])));
		return new Matrix(valueMap);
	}

	@Override
	public Matrix clone() {
		Map<Position, Value> clonedData = new TreeMap<>();
		valueMap.keySet().stream()
				.forEach(position -> clonedData.put(position, valueMap.get(position)));
		return new Matrix(clonedData);
	}

	public Optional<Value> getValueAt(final Position position) {
		validate(position);
		Value value = valueMap.get(position);
		return value!=Value.EMPTY ? Optional.of(value) : Optional.empty();
	}

	public void setValueAt(Value value, final Position position) {
		validate(position);
		valueMap.put(position, value);
	}

	public List<Value> getMatchingValues(Position position) {
		return Arrays.stream(Value.values())
				.filter(value -> isUnique(value, position))
				.collect(Collectors.toList());
	}

	private boolean isUnique(Value value, final Position pos) {
		return isUniqueInRow(value, pos.getRow()) &&
				isUniqueInColumn(value, pos.getColumn()) &&
				isUniqueInBlock(value, pos);
	}

	private boolean isUniqueInRow(Value value, int row) {
		return !IntStream.range(0, COLUMNS)
				.anyMatch(column -> value == valueMap.get(Position.at(row, column)));
	}

	private boolean isUniqueInColumn(Value value, int column) {
		return !IntStream.range(0, ROWS)
				.anyMatch(row -> value == valueMap.get(Position.at(row, column)));
	}

	private boolean isUniqueInBlock(Value value, final Position position) {
		Position blockPosition = getBlockPositionFor(position);
		return IntStream.range(0, value.toInt())
				.anyMatch(blockRow->isUniqueInBlockRow(value, blockPosition, blockRow));
	}

	private boolean isUniqueInBlockRow(Value value, final Position blockPosition, int blockRow) {
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
		return streamPositions().filter(pos -> valueMap.get(pos)==Value.EMPTY);
	}

	private Stream<Position> streamPositions() {
		return valueMap.keySet().stream();
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
			sb.append(valueMap.get(position).toInt()).append(", ");
			if(position.getColumn()%3 == 2) {
				sb.append("  ");
			}
			previousPositionContainer.set(0, position);
		});
		return sb.toString();
	}

}
