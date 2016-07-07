package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Position;
import gry.sample.sudoku.matrix.Sample;
import gry.sample.sudoku.matrix.Value;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by gry on 10.06.16.
 */
public class SudokuResolverTest {

    private static final Sudoku EXPECTED = Sudoku.load(new int[][]{
            {3,6,5, 9,8,2, 4,7,1},
            {4,7,1, 3,6,5, 2,8,9},
            {8,2,9, 7,4,1, 6,3,5},

            {5,1,8, 6,2,3, 7,9,4},
            {7,4,3, 8,5,9, 1,2,6},
            {2,9,6, 4,1,7, 3,5,8},

            {6,5,2, 1,3,8, 9,4,7},
            {9,3,4, 5,7,6, 8,1,2},
            {1,8,7, 2,9,4, 5,6,3}});

    @Test
    public void testGetInstance() {
        SudokuResolver resolver = SudokuResolver.getInstance();
        assertThat(resolver, is(notNullValue()));
    }

    @Test
    public void testResolveSimpliest() {
        Sudoku matrixToSolve = Sudoku.load(Sample.SIMPLIEST.getMatrix());

        Sudoku result = SudokuResolver.getInstance()
                .resolve(matrixToSolve).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo(EXPECTED));
    }

    @Test
    public void testResolveVerySimple() {
        Sudoku matrixToSolve = Sudoku.load(Sample.SIMPLIEST.getMatrix());

        Sudoku result = SudokuResolver.getInstance()
                .resolve(matrixToSolve).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo(EXPECTED));
    }

    @Test
    public void testUnresolvableMatrix() {
        Sudoku matrixToSoMatrix = Sudoku.load(Sample.ALMOSTRESOLVED.getMatrix());
        matrixToSoMatrix.setValueAt(Value.SEVEN, Position.at(8,7));
        Optional<Sudoku> result = SudokuResolver.getInstance()
                .resolve(matrixToSoMatrix);
        assertThat(result.isPresent(), is(false));
    }
}