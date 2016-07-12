package gry.sample.sudoku.aspects;

import gry.sample.sudoku.matrix.Sudoku;
import gry.sample.sudoku.matrix.Value;
import gry.sample.sudoku.matrix.Position;
import java.util.Optional;
import gry.sample.sudoku.SudokuResolver;

import java.util.Arrays;

public aspect RecursionLog {

	// Enable or disable the recursion trace log
	// !! BE AWARE !!!
	// Enabling the trace log dramatically slows down
	// the algorithm.
	private static boolean ENABLED = true;
	
	private static final char RIGHT_ARROW = '\u2192';
	private static final char SMILEY = '\u263a';
	private static final char CROSS = '\u2a2f';
	private static final char DOT = '\u2022';
	
	private static final char UP_DOWN = '\u2502';
	private static final char UP_RIGHT = '\u2514';
	private static final char LEFT_RIGHT = '\u2500';
	private static final char LEFT_DOWN = '\u2510';
	private static final char LEFT_UP = '\u2518';
	private static final char DOWN_RIGHT = '\u250c';
	
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut recursionCall(Sudoku sudoku) : if(ENABLED)
		&& call(Optional<Sudoku> SudokuResolver.resolve(Sudoku))
		&& args(sudoku);
	
	pointcut matches(Value value, Position at): if(false)
		&& call(boolean Sudoku.isUnique(Value, Position))
		&& args(value, at);
	
	pointcut setValueAt(Value value, final Position at) : if(ENABLED) 
		&& call(void Sudoku.setValueAt(Value, Position))
		&& args(value, at);
		
	// advices
	before(Sudoku sudoku) : recursionCall(sudoku) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = new StringBuilder().append(recursionDepth>1 ? UP_RIGHT : LEFT_RIGHT).append(LEFT_DOWN).toString();
		System.out.println(String.format("%s%s Resolve (%d open fields) #%d", getIndentStr(recursionDepth-1), enterSymbol, sudoku.unresolvedPositions().count(), callCounter));
	}
	
	after(Sudoku sudoku) : recursionCall(sudoku) {
		String exitSymbol = new StringBuilder().append(recursionDepth>1 ? DOWN_RIGHT : LEFT_RIGHT).append(LEFT_UP).toString();
		if(sudoku.unresolvedPositions().count()==0) {
			System.out.println(String.format("%s%s DONE -> ALL FIELDS RESOLVED!", getIndentStr(recursionDepth-1), exitSymbol));
		}
		else {
			System.out.println(String.format("%s%s Roll back", getIndentStr(recursionDepth-1), exitSymbol));
		}
		recursionDepth--;
	}

	before(Value value, Position at) : matches(value, at) {
		System.out.print(String.format("%s%s  %d @ %s %s  ", getIndentStr(recursionDepth), UP_DOWN, value.toInt(), at, RIGHT_ARROW));		
	}

	after(Value value, Position at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? SMILEY : CROSS);
	}
	
	before(Value value, Position at) : setValueAt(value, at) {
		if(value == Value.EMPTY) {
			System.out.println(String.format("%s%s  reset %s", getIndentStr(recursionDepth), UP_DOWN, at));				
		}
		else {
			System.out.println(String.format("%s%s  set %d %s %s", getIndentStr(recursionDepth), UP_DOWN, value.toInt(), RIGHT_ARROW, at));				
		}
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, DOT);
		return new String(indent);
	}
}
