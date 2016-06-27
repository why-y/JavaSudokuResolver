package gry.sample.sudoku;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
    	assertThat(testee.getValueAt(new Position(0, 0)), is(3));
    	assertThat(testee.getValueAt(new Position(4, 4)), is(5));
    	assertThat(testee.getValueAt(new Position(8, 8)), is(0));
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetValueAtInvalidPosition() {
    	Matrix.load(Sample.almostResolved).getValueAt(new Position(0, 9));
    }
    
    @Test
    public void testGetEachFreePosition() {
    	List<Position> freePositions = Matrix.load(Sample.almostResolved).getEachFreePosition().collect(Collectors.toList());
    	assertThat(freePositions, equalTo(Arrays.asList(
    			new Position(6,1), new Position(6,4), new Position(6,8),
    			new Position(7,0), new Position(7,1), new Position(7,4), new Position(7,5), new Position(7,6), 
    			new Position(8,2), new Position(8,3), new Position(8,4), new Position(8,7), new Position(8,8))));
    }
    
    @Test
    public void testGetFirstFreePosition() {
    	assertThat(Matrix.load(Sample.reallyAlmostResolved).getFirstFreePosition(), 
    			equalTo(Optional.of(new Position(8,8))));
    }
    
    @Test
    public void testEquals() {
    	Matrix expected = Matrix.load(new int[][] {
    		{3,6,5, 9,8,2, 4,7,1},
    		{4,7,1, 3,6,5, 2,8,9},
    		{8,2,9, 7,4,1, 6,3,5},
    		
    		{5,1,8, 6,2,3, 7,9,4},
    		{7,4,3, 8,5,9, 1,2,6},
    		{2,9,6, 4,1,7, 3,5,8},
    		
    		{6,5,2, 1,3,8, 9,4,7},
    		{9,3,4, 5,7,6, 8,1,2},
    		{1,8,7, 2,9,4, 5,6,0}
    	});
    	assertThat(Matrix.load(Sample.reallyAlmostResolved), equalTo(expected));
    }
    
    @Test
    public void testClone() {
    	Matrix clone = Matrix.load(Sample.reallyAlmostResolved).clone();
    }
    
}