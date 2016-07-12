package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Position;
import gry.sample.sudoku.matrix.Value;

import java.util.List;
import java.util.Optional;

public class SudokuResolver {

    private static SudokuResolver theInstance = new SudokuResolver();

    private SudokuResolver() {}

    public static final SudokuResolver getInstance() {
        return theInstance;
    }
    
    public Sudoku recursivelyResolveDisctinctFields(Sudoku sudoku) {
    	long previousUnresolvedPositions = 81l;
    	while(sudoku.unresolvedPositions().count() < previousUnresolvedPositions) {
    		previousUnresolvedPositions = sudoku.unresolvedPositions().count();
    		sudoku = resolveDistinctFields(sudoku);
    	}
    	return sudoku;
    }
    
    private Sudoku resolveDistinctFields(final Sudoku sudoku) {
    	Optional<Position> unresolvedPosition = sudoku.getFirstUnresolvedPosition();
    	while(unresolvedPosition.isPresent()) {
    		List<Value> values = sudoku.getMatchingValues(unresolvedPosition.get());
    		if(values.size()==1) {
    			sudoku.setValueAt(values.get(0), unresolvedPosition.get());
    		}
    		unresolvedPosition = sudoku.getNextUnresolvedPosition(unresolvedPosition.get());
    	}
		return sudoku;
	}

	public Optional<Sudoku> resolve(Sudoku sudoku) {
        Optional<Position> nextFreePosition = sudoku.getFirstUnresolvedPosition();
        if(!nextFreePosition.isPresent()) {
        	// all Positions are solved! -> DONE!
            return Optional.of(sudoku);
        }
        Position position = nextFreePosition.get();
        List<Value> matchingValues = sudoku.getMatchingValues(position);
        for (Value matchingValue : matchingValues) {
            Sudoku nextMatrix = sudoku.clone();
            nextMatrix.setValueAt(matchingValue, position);
            Optional<Sudoku> result = resolve(nextMatrix);
            if(result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

}
