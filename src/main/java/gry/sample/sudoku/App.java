package gry.sample.sudoku;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import gry.sample.sudoku.matrix.Sample;
import gry.sample.sudoku.matrix.Sudoku;

/**
 * Test the SudokuResolver
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	AppArgs arguments = AppArgs.load(args);
    	Sudoku toResolve = Sudoku.load(arguments.getSample().asMatrix());

    	Instant startInstant = startLog(toResolve);
    	
		// Setting all distinct values first (optional)
		if(arguments.doOptimize()) {
			toResolve = SudokuResolver.getInstance().recursivelySetDisctinctFields(toResolve);			
		}
		
		// Recursively resolve it:
		Optional<Sudoku> resolved = toResolve.isResolved() ? Optional.of(toResolve) : SudokuResolver.getInstance().resolve(toResolve);			
        
		endLog(startInstant, resolved);

    }
     
    private static Instant startLog(final Sudoku sudoku) {
        Instant start = Instant.now();
        System.out.println( "SUDOKU START:" );
        System.out.println("INPUT:");
		System.out.println(sudoku.toFormatedString());
		return start;
    }
    
    private static void endLog(final Instant startInstant, final Optional<Sudoku> result) {
        if(result.isPresent()) {
        	System.out.println("RESOLVED!");
        	System.out.println(result.get().toFormatedString());
        }
        else{
        	System.err.println("UNRESOLVABLE!!!");
        }
        Instant stop = Instant.now();
        System.out.println(String.format("SUDOKU END  -> %s", Duration.between(startInstant, stop)));    	
    }
    
	private static class AppArgs{
		private Sample sample;
		private boolean doOptimize = false;
		private AppArgs(){};
		public static AppArgs load(String[] args){
			AppArgs ret = new AppArgs();
			if(args.length==1) {
				ret.sample = Sample.valueOf(args[0]);
			} else if (args.length==2) {
				if(!args[0].toLowerCase().equals("-o"))
					throw new IllegalArgumentException(String.format("Unknown option %s! Accepted is -o.", args[0]));
				ret.doOptimize = true;
				ret.sample = Sample.valueOf(args[1]);
			} else {
				throw new IllegalArgumentException("Must have one or two arguments");
			}
			return ret;
		}
		public Sample getSample() {
			return sample;
		}
		public boolean doOptimize() {
			return doOptimize;
		}
	}

}
