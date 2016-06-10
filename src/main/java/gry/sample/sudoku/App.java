package gry.sample.sudoku;




/**
 * Hello world!
 *
 */
public class App 
{
	private static int[][] sudokuMatrix1 = {
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
	
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
	
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
	};		

// mittel
	private static int[][] sudokuMatrix2 = {
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
	
// mittel
	private static int[][] sudokuMatrix3 = {
		{0,5,7 ,8,0,6, 4,0,0},
		{0,0,0 ,0,0,0, 0,3,8},
		{1,4,0 ,0,3,7, 9,0,0},
	
		{0,0,0 ,0,1,2, 0,0,0},
		{0,0,2 ,4,0,3, 0,0,6},
		{0,3,0 ,0,6,0, 0,0,7},
	
		{0,9,5 ,0,0,0, 0,0,0},
		{7,2,0 ,0,5,0, 0,8,0},
		{0,0,1 ,0,0,4, 0,9,0}
	};	

// mittel
	private static int[][] sudokuMatrix4 = {
		{0,4,7, 0,0,8, 0,5,0},
		{9,8,0, 0,0,0, 0,0,0},
		{5,0,0, 4,0,0, 0,3,0},
	
		{4,3,0, 7,9,0, 0,0,1},
		{0,0,0, 0,0,0, 4,6,0},
		{0,5,6, 2,0,3, 0,0,7},
	
		{0,0,0, 1,0,2, 0,0,0},
		{0,7,0, 0,0,9, 1,0,0},
		{2,0,0, 8,3,0, 6,0,0},
	};		

// schwer
	private static int[][] sudokuMatrix5 = {
		{0,0,8, 0,3,0, 0,9,1},
		{0,2,9, 7,1,0, 6,0,8},
		{7,1,0, 8,0,0, 3,0,0},
	
		{0,0,0, 9,0,7, 0,1,5},
		{9,0,6, 0,5,0, 0,3,0},
		{0,0,0, 0,0,0, 0,0,0},
	
		{6,0,0, 5,0,0, 4,7,0},
		{8,5,0, 0,7,3, 0,0,0},
		{0,0,0, 0,0,0, 0,0,0},
	};	
	
// schwer
	private static int[][] sudokuMatrix6 = {
		{0,0,0, 0,8,2, 0,7,1},
		{0,0,0, 0,0,0, 0,0,0},
		{0,2,9, 7,0,0, 0,0,5},
		
		{0,0,0, 0,0,0, 0,0,0},
		{7,4,0, 8,5,0, 0,0,0},
		{0,9,0, 0,0,7, 3,0,8},
		
		{6,0,2, 1,0,8, 9,4,0},
		{0,0,4, 5,0,0, 0,1,2},
		{1,8,0, 0,0,4, 5,0,0}
	};
	
    // matrix 6 ... almost resolved
	private static int[][] sudokuMatrix7 = {
		{3,6,5, 9,8,2, 4,7,1},
		{4,7,1, 3,6,5, 2,8,9},
		{8,2,9, 7,4,1, 6,3,5},
		
		{5,1,8, 6,2,3, 7,9,4},
		{7,4,3, 8,5,9, 1,2,6},
		{2,9,6, 4,1,7, 3,5,8},
		
		{6,0,2, 1,0,8, 9,4,0},
		{0,0,4, 5,0,0, 0,1,2},
		{1,8,0, 0,0,4, 5,0,0}
	};

    public static void main( String[] args )
    {
    	long startTime = System.currentTimeMillis();
        System.out.println( "SUDOKU START:" );
        SudokuResolver resolver = new SudokuResolver();
        resolver.init(sudokuMatrix7);
        System.out.println("INPUT:");
        resolver.showIt();
        boolean resolved = resolver.resolve(resolver.getFirstFreePosition());
        System.out.println(resolved ? "RESOLVED: " : "NOT RESOLVED: ");
        resolver.showIt();
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("SUDOKU END  -> Duration: %d ms", endTime-startTime));
    }
}
