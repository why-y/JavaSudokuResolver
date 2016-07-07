package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Sample;

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
        
//        Sudoku unresolved = Sudoku.load(Sample.almostResolved);
//        Sudoku unresolved = Sudoku.load(Sample.intermediate2);
        Sudoku unresolved = Sudoku.load(Sample.difficult2);
        System.out.println("INPUT:");
		System.out.println(unresolved.toFormatedString());
		
        Optional<Sudoku> resolved = SudokuResolver.getInstance().resolve(unresolved);
        
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
