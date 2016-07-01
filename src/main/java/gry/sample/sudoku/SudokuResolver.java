package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Matrix;
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
    
    public Optional<Matrix> resolve(Matrix matrix) {
        Optional<Position> nextFreePosition = matrix.getNextUnresolvedPosition();
        if(!nextFreePosition.isPresent()) {
            return Optional.of(matrix);
        }
        Position position = nextFreePosition.get();
        List<Value> matchingValues = matrix.getMatchingValues(position);
        for (Value matchingValue : matchingValues) {
            Matrix nextMatrix = matrix.clone();
            nextMatrix.setValueAt(matchingValue, position);
            Optional<Matrix> result = resolve(nextMatrix);
            if(result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

}
