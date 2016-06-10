package gry.sample.sudoku;

/**
 * Test the SudokuResolver
 *
 */
public class App 
{

    public static void main( String[] args )
    {
    	long startTime = System.currentTimeMillis();
        System.out.println( "SUDOKU START:" );
		SudokuResolver resolver = new SudokuResolver();
        resolver.init(Sample.almostResolved);
        System.out.println("INPUT:");
		System.out.println(resolver.getMatrixAsNiceString());
        boolean resolved = resolver.resolveIt();
        System.out.println(resolved ? "RESOLVED: " : "NOT RESOLVED: ");
		System.out.println(resolver.getMatrixAsNiceString());
		long endTime = System.currentTimeMillis();
        System.out.println(String.format("SUDOKU END  -> Duration: %d ms", endTime-startTime));
    }
}
