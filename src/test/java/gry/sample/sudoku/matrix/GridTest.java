package gry.sample.sudoku.matrix;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;


public class GridTest {
	
	private Grid testee; 
	
	@Before
	public void init() {
		testee = Grid.createForDimensions(9, 9);
	}
	
	@Test
	public void createGrid() {
		assertThat(testee, is(not(nullValue())));
	}
	
	@Test
	public void getAllPositions() {
		List<Position> allPositions = testee.allPositions().collect(Collectors.toList());
		assertThat(allPositions.size(), is(81));
		for(int row=0; row<9; row++){
			for(int col=0; col<9; col++){
				assertThat(allPositions.contains(Position.at(row, col)), is(true));
			}
		}
	}
	
	@Test
	public void getPositionsOfSameRow() {
		List<Position> row5Positions = testee.positionsOfSameRow(Position.at(5, 0)).collect(Collectors.toList());
		assertThat(row5Positions.size(), is(9));
		for(int columnNo=0; columnNo<9; columnNo++) {
			assertThat(row5Positions.contains(Position.at(5, columnNo)), is(true));			
		}
	}
	
	@Test
	public void getPositionsOfSameColumn() {
		List<Position> column8Positions = testee.positionsOfSameColumn(Position.at(0, 8)).collect(Collectors.toList());
		assertThat(column8Positions.size(), is(9));
		for(int rowNo=0; rowNo<9; rowNo++) {
			assertThat(column8Positions.contains(Position.at(rowNo, 8)), is(true));			
		}
	}

	@Test
	public void getPositionsOfSameBlock() {
		List<Position> blockPositions = testee.positionsOfSameBlock(Position.at(4, 7)).collect(Collectors.toList());
		assertThat(blockPositions.size(), is(9));
		assertThat(blockPositions.contains(Position.at(3, 6)), is(true));
		assertThat(blockPositions.contains(Position.at(3, 7)), is(true));
		assertThat(blockPositions.contains(Position.at(3, 8)), is(true));
		assertThat(blockPositions.contains(Position.at(4, 6)), is(true));
		assertThat(blockPositions.contains(Position.at(4, 7)), is(true));
		assertThat(blockPositions.contains(Position.at(4, 8)), is(true));
		assertThat(blockPositions.contains(Position.at(5, 6)), is(true));
		assertThat(blockPositions.contains(Position.at(5, 7)), is(true));
		assertThat(blockPositions.contains(Position.at(5, 8)), is(true));
	}
	
}
