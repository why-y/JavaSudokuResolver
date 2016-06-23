package gry.sample.sudoku;

import java.util.Arrays;

public aspect RecursionLog {

	// Enable or disable the recursion trace log
	// !! BE AWARE !!!
	// Enabling the trace log dramatically slows down
	// the algorithm.
	private static boolean ENABLED = true;
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut resolveRecursionCall(Position pos) : if(ENABLED) 
		&& call(boolean gry.sample.sudoku.SudokuResolver.resolve(Position))
		&& args(pos);
	
	pointcut matches(int value, Position at): if(false) 
		&& call(boolean gry.sample.sudoku.SudokuResolver.isUnique(int, Position))
		&& args(value, at);
	
	pointcut setValueAt(int value, final Position at) : if(ENABLED) 
		&& call(void gry.sample.sudoku.SudokuResolver.setValueAt(int, Position ))
		&& args(value, at);
		
	// advices
	before(Position pos) : resolveRecursionCall(pos) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = recursionDepth>1 ? "\u2514\u2510" : "\u2500\u2510";
		System.out.println(String.format("%s%s Resolve Position%s  #%d", getIndentStr(recursionDepth-1), enterSymbol, pos, callCounter));
	}
	
	after(Position pos) : resolveRecursionCall(pos) {
		String exitSymbol = recursionDepth>1 ? "\u250c\u2518" : "\u2500\u2518";
		System.out.println(String.format("%s%s Done with %s", getIndentStr(recursionDepth-1), exitSymbol, pos));
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
