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
	
	
	private int recursionCounter = 0;
	private int callStackDepth = 0;
	private long openFields = 0;
	
	// pointcuts
	pointcut recursionCall(Sudoku sudoku) : if(ENABLED)
		&& call(Optional<Sudoku> SudokuResolver.resolve(Sudoku))
		&& args(sudoku);
	
	pointcut setDistinctFieldsCall(Sudoku sudoku) : if(ENABLED)
		&& call(Sudoku SudokuResolver.recursivelySetDisctinctFields(Sudoku))
		&& args(sudoku);
	
	pointcut matches(Value value, Position at): if(false)
		&& call(boolean Sudoku.isUnique(Value, Position))
		&& args(value, at);
	
	pointcut setValueAt(Value value, final Position at) : if(ENABLED) 
		&& call(void Sudoku.setValueAt(Value, Position))
		&& args(value, at);
		
	// advices
	before(Sudoku sudoku) : recursionCall(sudoku) {
		recursionCounter++;
		callStackDepth++;
		System.out.println(String.format("%s%s Resolve (%d open fields) #%d", 
				getIndentStr(callStackDepth-1), 
				getEnterSymbol(callStackDepth), 
				sudoku.unresolvedPositions().count(), 
				recursionCounter));
	}
	
	after(Sudoku sudoku) : recursionCall(sudoku) {
		if(sudoku.isResolved()) {
			System.out.println(String.format("%s%s DONE -> ALL FIELDS RESOLVED!", getIndentStr(callStackDepth-1), getExitSymbol(callStackDepth)));
		}
		else {
			System.out.println(String.format("%s%s Roll back", getIndentStr(callStackDepth-1), getExitSymbol(callStackDepth)));
		}
		callStackDepth--;
	}

	before(Sudoku sudoku) : setDistinctFieldsCall(sudoku) {
		callStackDepth++;
		openFields = sudoku.unresolvedPositions().count();
		System.out.println(String.format("%s%s Setting distinct fields", getIndentStr(callStackDepth-1), getEnterSymbol(callStackDepth)));
	}
	
	after(Sudoku sudoku) : setDistinctFieldsCall(sudoku) {
		long remainingFields = sudoku.unresolvedPositions().count();
		System.out.println(String.format("%s%s %d of %d open fields could be distinctively filled. (i.e. %d fields remaining)", 
				getIndentStr(callStackDepth-1), 
				getExitSymbol(callStackDepth),
				openFields-remainingFields,
				openFields,
				remainingFields));
		callStackDepth--;	
	}
	
	before(Value value, Position at) : matches(value, at) {
		System.out.print(String.format("%s%s  %d @ %s %s  ", getIndentStr(callStackDepth), UP_DOWN, value.toInt(), at, RIGHT_ARROW));		
	}

	after(Value value, Position at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? SMILEY : CROSS);
	}
	
	before(Value value, Position at) : setValueAt(value, at) {
		if(value == Value.EMPTY) {
			System.out.println(String.format("%s%s  reset %s", getIndentStr(callStackDepth), UP_DOWN, at));				
		}
		else {
			System.out.println(String.format("%s%s  set %d %s %s", getIndentStr(callStackDepth), UP_DOWN, value.toInt(), RIGHT_ARROW, at));				
		}
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, DOT);
		return new String(indent);
	}
	
	private String getEnterSymbol(int callStackDepth) {
		return new StringBuilder().append(callStackDepth>1 ? UP_RIGHT : LEFT_RIGHT).append(LEFT_DOWN).toString();
	}
	
	private String getExitSymbol(int callStackDepth) {
		return new StringBuilder().append(callStackDepth>1 ? DOWN_RIGHT : LEFT_RIGHT).append(LEFT_UP).toString();
	}
}
