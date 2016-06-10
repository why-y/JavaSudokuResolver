package gry.sample.sudoku;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * Created by gry on 10.06.16.
 */
public class SudokuResolverTest {

    private static int[][] testData = {
            {0,0,0, 8,9,0, 0,0,0},
            {0,6,9, 0,0,4, 3,2,0},
            {8,2,0, 0,0,5, 4,0,7},

            {3,0,0, 9,0,0, 1,0,2},
            {0,4,0, 5,0,0, 0,7,0},
            {0,0,0, 0,0,0, 5,3,0},

            {7,1,0, 0,0,0, 0,0,0},
            {6,0,0, 0,2,0, 8,0,0},
            {0,8,4, 0,6,0, 0,1,0}
    };
    private static final String RESULT = new StringBuilder()
            .append("1 3 2  8 9 6  7 5 4  ").append(System.lineSeparator())
            .append("5 6 9  1 7 4  3 2 8  ").append(System.lineSeparator())
            .append("8 2 3  6 1 5  4 9 7  ").append(System.lineSeparator())
            .append(System.lineSeparator())
            .append("3 5 6  9 4 7  1 8 2  ").append(System.lineSeparator())
            .append("2 4 1  5 3 8  6 7 9  ").append(System.lineSeparator())
            .append("4 9 7  2 8 1  5 3 6  ").append(System.lineSeparator())
            .append(System.lineSeparator())
            .append("7 1 8  4 5 2  9 6 3  ").append(System.lineSeparator())
            .append("6 7 5  3 2 9  8 4 1  ").append(System.lineSeparator())
            .append("9 8 4  7 6 3  2 1 5  ").append(System.lineSeparator())
            .append(System.lineSeparator()).toString();

    @Test
    public void testIt() throws Exception {
        SudokuResolver testee = new SudokuResolver();
        testee.init(testData);
        testee.resolveIt();
        String resultString = testee.getMatrixAsNiceString();
        assertThat(resultString, equalTo(RESULT));
    }

}