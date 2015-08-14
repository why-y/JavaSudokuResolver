package gry.sample.sudoku;


public class SudokuResolver {

	public static final int BLOCK_WIDTH = 3;
	public static final int FIELD_WIDTH = BLOCK_WIDTH * BLOCK_WIDTH;
	
	public static final int ROWS = BLOCK_WIDTH*3; // each block has 3 rows
	public static final int COLUMNS = ROWS; // the sudoku is always a square
	
	private int[][] matrix;
	
	public void init(int[][] unresolvedMatrix) {
		matrix = unresolvedMatrix;
	}
	
	
	/**
	 * Represents the Position within the sudoku [column, row]
	 */
	public static class Pos{
		int x; // column
		int y; // row
		
		public Pos(int x, int y) {
			this.x = x;
			this.y = y;			
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public Pos inc() {
			// increment position
			if (x<COLUMNS-1) {
				// next column in the same row
				return new Pos(x+1, y);
			}
			else {
				if(y<ROWS-1) {
					// first column in the next row
					return new Pos(0, y+1);
				}
				else {
					// there is no next pos
					return null;
				}
			}
		}

		@Override
		public String toString() {
			return "Pos [x=" + x + ", y=" + y + "]";
		}
		
	}
	
	public boolean resolve(final Pos pos) {
		if(pos==null) {
			return true;
		}
		for (int i=1; i<=9; ++i) {
			if (matches(i, pos)){
				setValueAt(i, pos);				
				if(resolve(getNextPos(pos))) {
					// completed
					return true;
				}
				else {
					setValueAt(0, pos);
				}
			}
		}
		// not yet resolved
		return false;
	}

	private boolean matches(int i, final Pos pos) {
		return matchesRow(i, pos.getY()) && 
				matchesColumn(i, pos.getX()) && 
				matchesBlock(i, pos);
	}
	
	private boolean matchesRow(int i, int row) {
		for (int col=0; col<COLUMNS; ++col) {
			if (i==matrix[row][col]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchesColumn(int i, int col) {
		for (int row=0; row<ROWS; ++row) {
			if (i==matrix[row][col]) {
				return false;
			}
		}
		return true;
	}
	
	private boolean matchesBlock(int i, final Pos pos) {
		Pos blockPos = new Pos(pos.getX()/3, pos.getY()/3);
		for(int rowOffset=0; rowOffset<3; ++rowOffset) {
			int row = blockPos.getY()*3 + rowOffset;
			for(int colOffset=0; colOffset<3; ++colOffset) {
				int col = blockPos.getX()*3 + colOffset;
				if(i==matrix[row][col]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public Pos getFirstPos() {
		Pos firstPos = new Pos(0, 0);
		if(getValueAt(firstPos)==0) {
			return firstPos;
		} else {
			return getNextPos(firstPos);
		}
	}
	
	private Pos getNextPos(final Pos pos) {
		Pos nextPos = pos.inc();
		if(nextPos==null) {
			return null;
		}
		while(getValueAt(nextPos)!=0) {
			nextPos = nextPos.inc();
		}
		return nextPos;
	}
	
	private int getValueAt(final Pos pos) {
		return matrix[pos.getY()][pos.getX()];
	}
	
	private void setValueAt(int val, final Pos pos) {
		matrix[pos.getY()][pos.getX()] = val;
	}
	
	
	
	public void showIt() {
		for (int y = 0; y < FIELD_WIDTH; y++) {
			for (int x = 0; x < FIELD_WIDTH; x++) {
				System.out.print(matrix[y][x]+ " ");
				if (x % BLOCK_WIDTH == 2) {
					System.out.print(" ");
				}
			}
			if (y % BLOCK_WIDTH == 2) {
				System.out.println();
			}
			System.out.println();
		}		
	}


}
