package gry.sample.sudoku;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


/**
 * Created by gry on 10.06.16.
 */
public class SudokuResolverTest {

    private static final String EXPECTED_RESULT = new StringBuilder()
    		.append("3 6 5  9 8 2  4 7 1  ").append(System.lineSeparator())  
    		.append("4 7 1  3 6 5  2 8 9  ").append(System.lineSeparator())  
    		.append("8 2 9  7 4 1  6 3 5  ").append(System.lineSeparator())  
    		.append(System.lineSeparator())
    		.append("5 1 8  6 2 3  7 9 4  ").append(System.lineSeparator())  
    		.append("7 4 3  8 5 9  1 2 6  ").append(System.lineSeparator())  
    		.append("2 9 6  4 1 7  3 5 8  ").append(System.lineSeparator())  
    		.append(System.lineSeparator())
    		.append("6 5 2  1 3 8  9 4 7  ").append(System.lineSeparator())  
    		.append("9 3 4  5 7 6  8 1 2  ").append(System.lineSeparator())  
    		.append("1 8 7  2 9 4  5 6 3  ").append(System.lineSeparator()) 
    		.append(System.lineSeparator()).toString();

    @Test
    public void testIt() throws Exception {
        SudokuResolver testee = new SudokuResolver();
        testee.init(Sample.almostResolved);
        testee.resolveIt();
        String resultString = testee.getMatrixAsNiceString();
        System.out.println(resultString);
        assertThat(resultString, equalTo(EXPECTED_RESULT));
    }
    
}