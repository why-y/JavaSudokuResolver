package gry.sample.sudoku.matrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sudoku {

	static int ROWS = 9;
	static int COLUMNS = ROWS;
	private Map<Position, Value> valueMap;
	private Grid grid;

	private Sudoku(final Map<Position, Value> valueMap){
		this.valueMap = valueMap;
		this.grid = Grid.createForDimensions(ROWS, COLUMNS);
	}

	public static Sudoku load(int[][] data) {
		validate(data);
		Map<Position, Value> valueMap = new TreeMap<>();
		IntStream.range(0, data.length).boxed()
				.map(rowNo -> IntStream.range(0, data[rowNo].length).boxed()
						.map(colNo -> Position.at(rowNo, colNo)))
				.flatMap(p -> p)
				.forEach(p -> valueMap.put(p, Value.fromInt(data[p.getRow()][p.getColumn()])));
		return new Sudoku(valueMap);
	}
	
	private static void validate(int[][] data) {
		if(data.length!=ROWS) {
			throw new IllegalArgumentException(
					String.format("The data has a wrong amount of rows: %d! Expected: %d.", data.length, ROWS));
		}
		boolean allRowSizesCorrect = IntStream.range(0, ROWS).boxed()
			.map(rowNumber -> data[rowNumber].length)
			.allMatch(rowLength -> rowLength==COLUMNS);
		if(!allRowSizesCorrect) {
			throw new IllegalArgumentException("The data contains rows of wrong lengths");			
		}
	}

	public Optional<Value> getValueAt(final Position position) {
		grid.validate(position);
		Value value = valueMap.get(position);
		return value!=Value.EMPTY ? Optional.of(value) : Optional.empty();
	}

	public void setValueAt(Value value, final Position position) {
		grid.validate(position);
		valueMap.put(position, value);
	}

	public List<Value> getMatchingValues(Position position) {
		return Arrays.stream(Value.values())
				.filter(value -> isUnique(value, position))
				.collect(Collectors.toList());
	}

	boolean isUnique(Value value, final Position position) {
		return isUniqueInRow(value, position) &&
				isUniqueInColumn(value, position) &&
				isUniqueInBlock(value, position);
	}

	boolean isUniqueInRow(Value value, final Position position) {
		return grid.positionsOfSameRow(position)
				.noneMatch(checkPos -> value == valueMap.get(checkPos));		
	}
	
	boolean isUniqueInColumn(Value value, final Position position) {
		return grid.positionsOfSameColumn(position)
				.noneMatch(checkPos -> value == valueMap.get(checkPos));		
	}

	boolean isUniqueInBlock(Value value, final Position position) {
		return grid.positionsOfSameBlock(position)
				.noneMatch(checkPos -> value == valueMap.get(checkPos));		
	}
	
	public Optional<Position> getNextUnresolvedPosition() {
		return unresolvedPositions().findFirst();
	}

	public Stream<Position> unresolvedPositions() {
		return grid.allPositions().filter(pos -> valueMap.get(pos)==Value.EMPTY);
	}

	@Override
	public Sudoku clone() {
		Map<Position, Value> clonedData = new TreeMap<>();
		valueMap.keySet().stream()
				.forEach(position -> clonedData.put(position, valueMap.get(position)));
		return new Sudoku(clonedData);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Sudoku sudoku = (Sudoku) o;

		return valueMap != null ? valueMap.equals(sudoku.valueMap) : sudoku.valueMap == null;

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
