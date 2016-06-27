package gry.sample.sudoku;

import java.util.Optional;

public class SudokuResolverNew {

	private static SudokuResolverNew theInstance = new SudokuResolverNew();
	
	private SudokuResolverNew() {}
	
	public static final SudokuResolverNew getInstance() {
		return theInstance;
	}
	
	public Optional<Matrix> resolve(Matrix matrix) {
		Optional<Position> nextFreePosition = matrix.getFirstFreePosition();
		if(!nextFreePosition.isPresent()) {
			return Optional.of(matrix);
		}
		Position position = nextFreePosition.get();
		for (Integer matchingValue : matrix.getMatchingValues(position)) {
			matrix.setValueAt(matchingValue, position);
			Optional<Matrix> result = resolve(matrix);
			if(result.isPresent()) {
				return result;
			}
		}
		return Optional.empty();
	}
	
	
}
