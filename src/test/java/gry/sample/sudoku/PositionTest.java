package gry.sample.sudoku;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by gry on 10.06.16.
 */
public class PositionTest {
    @Test
    public void getX() throws Exception {
        assertThat(new Position(2,3).getX(), is(2));
    }

    @Test
    public void getY() throws Exception {
        assertThat(new Position(2,3).getY(), is(3));
    }

    @Test
    public void increment() throws Exception {
        Position pos01 = new Position(0,0).increment();
        assertThat(pos01.getY(), is(0));
        assertThat(pos01.getX(), is(1));

        // test jump to next row
        Position pos10 = new Position(8,5).increment();
        assertThat(pos10.getY(), is(6));
        assertThat(pos10.getX(), is(0));

        // test overflow
        Position posNull = new Position(8,8).increment();
        assertThat(posNull, is(nullValue()));

    }

}