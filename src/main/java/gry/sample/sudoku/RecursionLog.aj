package gry.sample.sudoku;

import java.util.Arrays;
import java.util.Optional;

public aspect RecursionLog {

	// Enable or disable the recursion trace log
	// !! BE AWARE !!!
	// Enabling the trace log dramatically slows down
	// the algorithm.
	private static boolean ENABLED = true;
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut recursionCall(Matrix matrix) : if(ENABLED) 
		&& call(Optional<Matrix> SudokuResolver.resolve(Matrix))
		&& args(matrix);
	
	pointcut matches(int value, Position at): if(false) 
		&& call(boolean Matrix.isUnique(int, Position))
		&& args(value, at);
	
	pointcut setValueAt(int value, final Position at) : if(ENABLED) 
		&& call(void Matrix.setValueAt(int, Position))
		&& args(value, at);
		
	// advices
	before(Matrix matrix) : recursionCall(matrix) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = recursionDepth>1 ? "\u2514\u2510" : "\u2500\u2510";
		System.out.println(String.format("%s%s Resolve (%d open fields) #%d", getIndentStr(recursionDepth-1), enterSymbol, matrix.streamUnresolvedPositions().count(), callCounter));
	}
	
	after(Matrix matrix) : recursionCall(matrix) {
		String exitSymbol = recursionDepth>1 ? "\u250c\u2518" : "\u2500\u2518";
		if(matrix.streamUnresolvedPositions().count()==0) {
			System.out.println(String.format("%s%s DONE -> ALL FIELDS RESOLVED!", getIndentStr(recursionDepth-1), exitSymbol));
		}
		else {
			System.out.println(String.format("%s%s Roll back", getIndentStr(recursionDepth-1), exitSymbol));
		}
		recursionDepth--;
	}

	before(int value, Position at) : matches(value, at) {
		System.out.print(String.format("%s\u2502  %d @ %s \u2192  ", getIndentStr(recursionDepth), value, at));		
	}

	after(int value, Position at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? "OK!" : "ambiguous!");
	}
	
	before(int value, Position at) : setValueAt(value, at) {
		if(value>0) {
			System.out.println(String.format("%s\u2502  set %d \u2192 %s", getIndentStr(recursionDepth), value, at));				
		}
		else{
			System.out.println(String.format("%s\u2502  reset %s", getIndentStr(recursionDepth), at));				
		}
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, '\u2022');
		return new String(indent);
	}
}
