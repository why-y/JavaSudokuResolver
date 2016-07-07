package gry.sample.sudoku.matrix;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Grid {
	
	static private final int BLOCK_SIZE = 3;

	private int numberOfRows;
	private int numberOfColumns;
	
	private Grid(int numberOfRows, int numberOfColumns) {
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
	}
	
	public static Grid createForDimensions(int numberOfRows, int numberOfColumns) {
		return new Grid(numberOfRows, numberOfColumns);
	}
	
	public Stream<Position> allPositions() {
		return IntStream.range(0, numberOfRows).boxed()
				.map(rowNumber -> IntStream.range(0, numberOfColumns).boxed()
						.map(columnNumber -> Position.at(rowNumber, columnNumber)))
				.flatMap(pos -> pos);
	}
	
	public Stream<Position> positionsOfSameRow(final Position position) {
		validate(position);
		return IntStream.range(0, numberOfColumns).boxed()
				.map(columnNumber -> Position.at(position.getRow(), columnNumber));
	}
	
	public Stream<Position> positionsOfSameColumn(final Position position) {
		validate(position);
		return IntStream.range(0, numberOfRows).boxed()
				.map(rowNumber -> Position.at(rowNumber, position.getColumn()));
	}
	
	public Stream<Position> positionsOfSameBlock(final Position position) {
		validate(position);
		Position blockPosition = getBlockPositionOf(position);
		return IntStream.range(0, BLOCK_SIZE).boxed()
				.map(rowOffset -> IntStream.range(0, BLOCK_SIZE).boxed()
						.map(columnOffset-> Position.at(blockPosition.getRow()*BLOCK_SIZE + rowOffset, blockPosition.getColumn()*BLOCK_SIZE + columnOffset)))
			.flatMap(pos -> pos);
	}
	
	private Position getBlockPositionOf(final Position position) {
		return Position.at(position.getRow()/BLOCK_SIZE, position.getColumn()/BLOCK_SIZE);
	}

	void validate(final Position position) {
		validateRow(position.getRow());
		validateColumn(position.getColumn());
	}
	
	private void validateRow(int rowNo) {
		if(rowNo<0 || rowNo>=numberOfRows) {
			throw new IndexOutOfBoundsException(String.format("Invalid rowNo: %d! Expected is 0..%d", rowNo, numberOfRows-1));
		}		
	}
	
	private void validateColumn(int columnNo) {
		if(columnNo<0 || columnNo>=numberOfColumns) {
			throw new IndexOutOfBoundsException(String.format("Invalid columnNo: %d! Expected is 0..%d", columnNo, numberOfColumns-1));
		}
	}

}
