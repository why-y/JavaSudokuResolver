package gry.sample.sudoku.matrix;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;


/**
 * Created by gry on 10.06.16.
 */
public class SudokuTest {

    @Test
    public void testInit() {
    	Sudoku testee = Sudoku.load(Sample.ALMOSTRESOLVED.getMatrix());
    	assertThat(testee, is(notNullValue()));
    }
    
    @Test
    public void testGetValueAtValidPosition() {
    	Sudoku testee = Sudoku.load(Sample.ALMOSTRESOLVED.getMatrix());
    	assertThat(testee.getValueAt(Position.at(0, 0)).get(), is(Value.THREE));
    	assertThat(testee.getValueAt(Position.at(4, 4)).get(), is(Value.FIVE));
    	assertThat(testee.getValueAt(Position.at(8, 8)).isPresent(), is(false));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValueAtInvalidPosition() {
    	Sudoku.load(Sample.ALMOSTRESOLVED.getMatrix()).getValueAt(Position.at(0, 9));
    }

    @Test
    public void testGetEachFreePosition() {
    	List<Position> freePositions = Sudoku.load(Sample.ALMOSTRESOLVED.getMatrix()).unresolvedPositions().collect(Collectors.toList());
    	assertThat(freePositions, equalTo(Arrays.asList(
    			Position.at(6,1), Position.at(6,4), Position.at(6,8),
    			Position.at(7,0), Position.at(7,1), Position.at(7,4), Position.at(7,5), Position.at(7,6),
    			Position.at(8,2), Position.at(8,3), Position.at(8,4), Position.at(8,7), Position.at(8,8))));
    }
    
    @Test
    public void testGetFirstFreePosition() {
    	assertThat(Sudoku.load(Sample.SIMPLIEST.getMatrix()).getFirstUnresolvedPosition(),
    			equalTo(Optional.of(Position.at(8,8))));
    }
    
    @Test
    public void testGetNextFreePosition() {
    	assertThat(Sudoku.load(Sample.DIFFICULT1.getMatrix()).getNextUnresolvedPosition(Position.at(0, 0)),
    			equalTo(Optional.of(Position.at(0, 1))));
    	assertThat(Sudoku.load(Sample.DIFFICULT1.getMatrix()).getNextUnresolvedPosition(Position.at(0, 7)),
    			equalTo(Optional.of(Position.at(1, 0))));
    }
    
    @Test
    public void testCloneIsEqual() {
    	Sudoku matrix = Sudoku.load(Sample.SIMPLIEST.getMatrix());
		Sudoku clone = matrix.clone();
    	assertThat(clone, equalTo(matrix));
    }
    
    @Test
    public void testClone() {
		Sudoku matrix = Sudoku.load(Sample.SIMPLIEST.getMatrix());
    	Sudoku clone = matrix.clone();
		clone.setValueAt(Value.EMPTY, Position.at(0,0));
		assertThat(clone, not(equalTo(matrix)));
    }
    
    @Test
    public void testIsResolved() {
		Sudoku matrix = Sudoku.load(Sample.SIMPLIEST.getMatrix());
		assertThat(matrix.isResolved(), is(false));
		matrix.setValueAt(Value.THREE, Position.at(8, 8));
		assertThat(matrix.isResolved(), is(true));
    }
}