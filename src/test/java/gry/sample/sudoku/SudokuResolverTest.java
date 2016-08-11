package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Position;
import gry.sample.sudoku.matrix.Sample;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import static gry.sample.sudoku.matrix.Value.*;
import static gry.sample.sudoku.matrix.Sample.*;

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
    
    private SudokuResolver resolver;

    @Before
    public void initResolver() {
    	resolver = SudokuResolver.getInstance();
    }
    
    @Test
    public void testGetInstance() {
        assertThat(resolver, is(notNullValue()));
    }

    @Test
    public void testResolveSimpliest() {
        Sudoku matrixToSolve = Sudoku.load(SIMPLIEST.asMatrix());

        Sudoku result = resolver
                .resolve(matrixToSolve).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo(EXPECTED));
    }

    @Test
    public void testResolveVerySimple() {
        Sudoku matrixToSolve = Sudoku.load(ALMOSTRESOLVED.asMatrix());

        Sudoku result = resolver
                .resolve(matrixToSolve).orElse(null);
        assertThat(result, is(notNullValue()));
        assertThat(result, equalTo(EXPECTED));
    }

    @Test
    public void testUnresolvableMatrix() {
        Sudoku matrixToSolve = Sudoku.load(ALMOSTRESOLVED.asMatrix());
        matrixToSolve.setValueAt(SEVEN, Position.at(8,7));
        Optional<Sudoku> result = resolver
                .resolve(matrixToSolve);
        assertThat(result.isPresent(), is(false));
    }
        
    @Test
    public void testRecursivelyPrefillDistinctFields() {
        Sudoku matrixToSolve = Sudoku.load(INTERMEDIATE1.asMatrix());
        assertThat(matrixToSolve.unresolvedPositions().count(), is(51l));
        Sudoku prefilledMatrix = resolver.recursivelySetDisctinctFields(matrixToSolve);
        List<Position> unresolvedPositions = prefilledMatrix.unresolvedPositions().collect(Collectors.toList());
        assertThat(unresolvedPositions.size(), is(0));
    }
    
}