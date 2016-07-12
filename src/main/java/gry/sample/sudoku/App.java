package gry.sample.sudoku;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Sample;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Test the SudokuResolver
 *
 */
public class App 
{

    public static void main( String[] args )
    {
    	
    	Sample sample;
    	try {
    		sample = parseArguments(args);    		
    	}
    	catch(Throwable t) {
    		System.err.println(String.format("You must provide exactly one argument!\n"
    				+ "Supported values are:"));
    		Stream.of(Sample.values()).forEach(s -> System.err.print(s.toString() + ", "));
    		System.err.println(System.lineSeparator());
    		return;
    	}
    	
        Instant start = Instant.now();
        System.out.println( "SUDOKU START:" );
        
        Sudoku unresolved = Sudoku.load(sample.getMatrix());
        System.out.println("INPUT:");
		System.out.println(unresolved.toFormatedString());
		
		// Setting all distinct values first (optional)
		unresolved = SudokuResolver.getInstance().recursivelyResolveDisctinctFields(unresolved);
//		unresolved = SudokuResolver.getInstance().resolveDistinctFields(unresolved);
		
		// Recursively resolve it:
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
    
    private static Sample parseArguments(String[] args) {
    	if(args.length!=1) {
    		throw new IllegalArgumentException();
    	}
   		return Sample.valueOf(args[0]);
    }
}
