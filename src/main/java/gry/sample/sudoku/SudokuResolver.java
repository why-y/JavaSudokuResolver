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
    
    public Optional<Sudoku> resolve(Sudoku sudoku) {
        Optional<Position> nextFreePosition = sudoku.getNextUnresolvedPosition();
        if(!nextFreePosition.isPresent()) {
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
