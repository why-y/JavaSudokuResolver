package gry.sample.sudoku;

import java.time.Duration;
import java.time.Instant;

/**
 * Test the SudokuResolver
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        Instant start = Instant.now();
        System.out.println( "SUDOKU START:" );
		SudokuResolver resolver = new SudokuResolver();
//        resolver.init(Sample.difficult2); // Sample.dificult2 call resolve() 10'377'895 times
		resolver.init(Sample.almostResolved);
        System.out.println("INPUT:");
		System.out.println(resolver.getMatrixAsNiceString());
        boolean resolved = resolver.resolveIt();
        System.out.println(resolved ? "RESOLVED: " : "NOT RESOLVED: ");
		System.out.println(resolver.getMatrixAsNiceString());
        Instant stop = Instant.now();
        System.out.println(String.format("SUDOKU END  -> %s", Duration.between(start, stop)));
    }
}
