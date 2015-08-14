package gry.sample.sudoku;

import java.util.Arrays;

import gry.sample.sudoku.SudokuResolver.Pos;

public aspect RecursionLog {

	// Switch RecursionLog on/off
	private static boolean isTraceEnabled = false;
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut resolveRecursionCall(Pos pos) : if(isTraceEnabled) &&
		call(boolean gry.sample.sudoku.SudokuResolver.resolve(Pos))
		&& args(pos);
	
	pointcut matches(int value, Pos at): if(isTraceEnabled) &&
		call(boolean gry.sample.sudoku.SudokuResolver.matches(int, Pos))
		&& args(value, at);
	
	// advices
	before(Pos pos): resolveRecursionCall(pos) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = recursionDepth>1 ? "\u2514\u2510" : "\u2500\u2510";
		System.out.println(String.format("%s%s ENTER resolve(%s): callCounter:%d", getIndentStr(recursionDepth-1), enterSymbol, pos, callCounter));
	}
	
	after(Pos pos): resolveRecursionCall(pos) {
		String exitSymbol = recursionDepth>1 ? "\u250c\u2518" : "\u2500\u2518";
		System.out.println(String.format("%s%s EXIT   resolve(%s): returned!", getIndentStr(recursionDepth-1), exitSymbol, pos));
		recursionDepth--;
	}

	before(int value, Pos at): matches(value, at) {
		System.out.print(String.format("%s\u2502 Value %d ... ", getIndentStr(recursionDepth), value));		
	}

	after(int value, Pos at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? "matches, HURRAY!!!" : "doesn't match!");
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, '\u2022');
		return new String(indent);
	}
}
