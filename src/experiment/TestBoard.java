package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * TestBoard for experiment
 * 
 * @author Mark Baldwin
 *
 */
public class TestBoard {
	// Constants related to board size
	final static int ROWS = 4;
	final static int COLS = 4;

	private TestBoardCell[][] grid;
	
	// Data structure to hold adjacency list
	private Set<TestBoardCell> targets ;
	// Data structure use for path calcs
	private Set<TestBoardCell> visited;

	/**
	 * Constructor
	 */
	public TestBoard()
	{
		super() ;
		grid = new TestBoardCell[ROWS][COLS];
		for (int row=0; row<ROWS; row++)
			for (int col=0; col<COLS; col++)
				grid[row][col] = new TestBoardCell(row, col);
		
		// adjacencies are calculated once at beginning of program
		// because they don't change
		for (int row=0; row<ROWS; row++) {
			for (int col=0; col<COLS; col++)
			{
				calcAdjacencies(row, col);
			}
		}
	}
	
	/**
	 * Calculate all cells adjacent to a cell
	 * 
	 * @param row - row of cell
	 * @param col - col of cell
	 */
	private void calcAdjacencies(int row, int col) {
		TestBoardCell cell = getCell( row, col) ;

		// Valid row numbers are 0..ROW-1. Similar for col numbers
		if ((row-1) >= 0)
			cell.addAdj(grid[row-1][col]);
		if ((row+1) < ROWS)
			cell.addAdj(grid[row+1][col]);
		if ((col-1) >= 0)
			cell.addAdj(grid[row][col-1]);
		if ((col+1) < COLS)
			cell.addAdj(grid[row][col+1]);
	}
	
	/**
	 * calculate targets for a path length
	 * 
	 * @param startCell - cell to start from
	 * @param pathLength - length (die roll)
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength)
	{
		// Reset the data structures
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		
		// Initialize a search from this start node
		visited.add(startCell);
		
		// start the recursive procedure
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		// for each adjacent cell not already visited, add it to the 
		// visited list (to guard against backtracking). 
		// If we've taken the desired number of steps, add to targets,
		// otherwise continue to next set of adjacent cells (recursive call)
		for (TestBoardCell adjCell : thisCell.getAdjList()) {	
			if (!visited.contains(adjCell) &&
					!adjCell.isOccupied() ) {
				visited.add(adjCell);
				if (numSteps == 1 || adjCell.isRoom() ) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, numSteps - 1);
				}

				// after recursive call we've explored all adjacencies, 
				// so remove this one from the visited list
				visited.remove(adjCell);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}

	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}	

}
