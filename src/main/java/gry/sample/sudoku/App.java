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
        
//        Matrix unresolved = Matrix.load(Sample.almostResolved);
        Matrix unresolved = Matrix.load(Sample.difficult2);
        System.out.println("INPUT:");
		System.out.println(unresolved.toFormatedString());
		
        Optional<Matrix> resolved = SudokuResolver.getInstance().resolve(unresolved);
        
        if(resolved.isPresent()) {
        	System.out.println("RESOLVED!");
        	System.out.println(resolved.get().toFormatedString());
        }
        else{
        	System.err.println("UNRESOLVABLE!!!");
        }
        Instant stop = Instant.now();
        System.out.println(String.format("SUDOKU END  -> %s", Duration.between(start, stop)));
    }
}
