package gry.sample.sudoku.matrix;

import gry.sample.sudoku.matrix.Position;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by gry on 01.07.16.
 */
public class PositionTest {

    @Test
    public void createTest() {
        assertThat(Position.at(0,0), is(not(nullValue())));
    }

    @Test
    public void validPositionTest() {
        Position validPosition = Position.at(3,8);
        assertThat(validPosition.getRow(), is(3));
        assertThat(validPosition.getColumn(), is(8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalNegativeRowTest() {
        Position.at(-3,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalNegativeColumnTest() {
        Position.at(3,-1);
    }

}
