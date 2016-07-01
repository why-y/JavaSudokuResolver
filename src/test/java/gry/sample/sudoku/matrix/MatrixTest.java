package gry.sample.sudoku.matrix;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gry.sample.sudoku.matrix.Matrix;
import gry.sample.sudoku.matrix.Position;
import gry.sample.sudoku.matrix.Sample;
import org.junit.Test;


/**
 * Created by gry on 10.06.16.
 */
public class MatrixTest {

	private static Matrix EXPECTED_RESULT = Matrix.load(new int[][] {
		{3,6,5, 9,8,2, 4,7,1},
		{4,7,1, 3,6,5, 2,8,9},
		{8,2,9, 7,4,1, 6,3,5},
		
		{5,1,8, 6,2,3, 7,9,4},
		{7,4,3, 8,5,9, 1,2,6},
		{2,9,6, 4,1,7, 3,5,8},
		
		{6,5,2, 1,3,8, 9,4,7},
		{9,3,4, 5,7,6, 8,1,2},
		{1,8,7, 2,9,4, 5,6,3}
	});


    @Test
    public void testInit() {
    	Matrix testee = Matrix.load(Sample.almostResolved);
    	assertThat(testee, is(notNullValue()));
    }
    
    @Test
    public void testGetValueAtValidPosition() {
    	Matrix testee = Matrix.load(Sample.almostResolved);
    	assertThat(testee.getValueAt(Position.at(0, 0)).get(), is(3));
    	assertThat(testee.getValueAt(Position.at(4, 4)).get(), is(5));
    	assertThat(testee.getValueAt(Position.at(8, 8)).isPresent(), is(false));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetValueAtInvalidPosition() {
    	Matrix.load(Sample.almostResolved).getValueAt(Position.at(0, 9));
    }
    
    @Test
    public void testGetEachFreePosition() {
		System.out.println(Matrix.load(Sample.almostResolved).toString());
    	List<Position> freePositions = Matrix.load(Sample.almostResolved).streamUnresolvedPositions().collect(Collectors.toList());
    	assertThat(freePositions, equalTo(Arrays.asList(
    			Position.at(6,1), Position.at(6,4), Position.at(6,8),
    			Position.at(7,0), Position.at(7,1), Position.at(7,4), Position.at(7,5), Position.at(7,6),
    			Position.at(8,2), Position.at(8,3), Position.at(8,4), Position.at(8,7), Position.at(8,8))));
    }
    
    @Test
    public void testGetFirstFreePosition() {
    	assertThat(Matrix.load(Sample.simpliest).getNextUnresolvedPosition(),
    			equalTo(Optional.of(Position.at(8,8))));
    }
    
    @Test
    public void testCloneIsEqual() {
    	Matrix matrix = Matrix.load(Sample.simpliest);
		Matrix clone = matrix.clone();
    	assertThat(clone, equalTo(matrix));
    }
    
    @Test
    public void testClone() {
		Matrix matrix = Matrix.load(Sample.simpliest);
    	Matrix clone = matrix.clone();
		clone.setValueAt(0, Position.at(0,0));
		assertThat(clone, not(equalTo(matrix)));
    }
    
}