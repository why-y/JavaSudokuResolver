package gry.sample.sudoku;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

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
        Matrix unsolvedMatrix = Matrix.load(Sample.difficult1);
//        Matrix unsolvedMatrix = Matrix.load(Sample.difficult2); // Sample.difficult2 call resolve() 10'377'895 times

        System.out.println("INPUT:");
		System.out.println(unsolvedMatrix.toFormatedString());

        Optional<Matrix> resolvedMatrix = SudokuResolver.getInstance().resolve(unsolvedMatrix);

        if(resolvedMatrix.isPresent()) {
            System.out.println("RESOLVED:\n" + resolvedMatrix.get().toFormatedString());
        }
        else {
            System.out.println("UNRESOLVED!");
        }

        Instant stop = Instant.now();
        System.out.println(String.format("SUDOKU END  -> %s", Duration.between(start, stop)));
    }
}
