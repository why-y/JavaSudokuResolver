package gry.sample.sudoku;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SudokuResolverNewTest {

	@Test
	public void testGetInstance() {
		SudokuResolverNew resolver = SudokuResolverNew.getInstance();
		assertThat(resolver, is(notNullValue()));
	}
	
	@Test
	public void testResolvableSample() {
		Matrix matrixToSolve = Matrix.load(Sample.almostResolved);
		
		Matrix expected = Matrix.load(new int[][]{
			{3,6,5, 9,8,2, 4,7,1},
			{4,7,1, 3,6,5, 2,8,9},
			{8,2,9, 7,4,1, 6,3,5},
			
			{5,1,8, 6,2,3, 7,9,4},
			{7,4,3, 8,5,9, 1,2,6},
			{2,9,6, 4,1,7, 3,5,8},
			
			{6,5,2, 1,3,8, 9,4,7},
			{9,3,4, 5,7,6, 8,1,2},
			{1,8,7, 2,9,4, 5,6,3}});
		
		Matrix result = SudokuResolverNew.getInstance()
				.resolve(matrixToSolve)
				.orElseThrow(UnresolvableException::new);
		assertThat(result, is(notNullValue()));
		assertThat(result, equalTo(expected));
	}

}
