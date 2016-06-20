package gry.sample.sudoku;

import java.util.Arrays;

public aspect RecursionLog {

	// Switch RecursionLog on/off
	private static boolean isTraceEnabled = true;
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut resolveRecursionCall(Position pos) : if(isTraceEnabled) &&
		call(boolean gry.sample.sudoku.SudokuResolver.resolve(Position))
		&& args(pos);
	
	pointcut matches(int value, Position at): if(isTraceEnabled) &&
		call(boolean gry.sample.sudoku.SudokuResolver.isUnique(int, Position))
		&& args(value, at);
	
	// advices
	before(Position pos): resolveRecursionCall(pos) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = recursionDepth>1 ? "\u2514\u2510" : "\u2500\u2510";
		System.out.println(String.format("%s%s Resolve %s  (call # %d)", getIndentStr(recursionDepth-1), enterSymbol, pos, callCounter));
	}
	
	after(Position pos): resolveRecursionCall(pos) {
		String exitSymbol = recursionDepth>1 ? "\u250c\u2518" : "\u2500\u2518";
		System.out.println(String.format("%s%s Done with %s", getIndentStr(recursionDepth-1), exitSymbol, pos));
		recursionDepth--;
	}

	before(int value, Position at): matches(value, at) {
		System.out.print(String.format("%s\u2502  %d \u2192  ", getIndentStr(recursionDepth), value));		
	}

	after(int value, Position at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? "OK!" : "ambiguous!");
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, '\u2022');
		return new String(indent);
	}
}
