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
	
	private int callCounter = 0;
	private int recursionDepth = 0;
	
	// pointcuts
	pointcut recursionCall(Sudoku sudoku) : if(ENABLED)
		&& call(Optional<Sudoku> SudokuResolver.resolve(Sudoku))
		&& args(sudoku);
	
	pointcut matches(Value value, Position at): if(ENABLED)
		&& call(boolean Sudoku.isUnique(Value, Position))
		&& args(value, at);
	
	pointcut setValueAt(Value value, final Position at) : if(ENABLED) 
		&& call(void Sudoku.setValueAt(Value, Position))
		&& args(value, at);
		
	// advices
	before(Sudoku sudoku) : recursionCall(sudoku) {
		callCounter++;
		recursionDepth++;
		String enterSymbol = recursionDepth>1 ? "\u2514\u2510" : "\u2500\u2510";
		System.out.println(String.format("%s%s Resolve (%d open fields) #%d", getIndentStr(recursionDepth-1), enterSymbol, sudoku.unresolvedPositions().count(), callCounter));
	}
	
	after(Sudoku sudoku) : recursionCall(sudoku) {
		String exitSymbol = recursionDepth>1 ? "\u250c\u2518" : "\u2500\u2518";
		if(sudoku.unresolvedPositions().count()==0) {
			System.out.println(String.format("%s%s DONE -> ALL FIELDS RESOLVED!", getIndentStr(recursionDepth-1), exitSymbol));
		}
		else {
			System.out.println(String.format("%s%s Roll back", getIndentStr(recursionDepth-1), exitSymbol));
		}
		recursionDepth--;
	}

	before(Value value, Position at) : matches(value, at) {
		System.out.print(String.format("%s\u2502  %d @ %s \u2192  ", getIndentStr(recursionDepth), value.toInt(), at));		
	}

	after(Value value, Position at) returning (boolean ret): matches(value, at) {
		System.out.println(ret ? "\u263a" : "\u2a2f");
	}
	
	before(Value value, Position at) : setValueAt(value, at) {
		if(value == Value.EMPTY) {
			System.out.println(String.format("%s\u2502  reset %s", getIndentStr(recursionDepth), at));				
		}
		else {
			System.out.println(String.format("%s\u2502  set %d \u2192 %s", getIndentStr(recursionDepth), value.toInt(), at));				
		}
	}
	
	private String getIndentStr(int width) {
		char[] indent = new char[width];
		Arrays.fill(indent, '\u2022');
		return new String(indent);
	}
}
