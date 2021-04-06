package experiment;

import java.util.HashSet;
import java.util.Set;

/**
 * TestBoardCell for experiment
 * 
 * A single cell of the game board
 * 
 * @author Mark Baldwin
 * 
 */
public class TestBoardCell {
	private int row, col; // row and column of cell
	private boolean isRoom, isOccupied ;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell>(); // cells adjacent to this cell

	/**
	 * Constructor
	 * 
	 * @param row - row of cell
	 * @param col - column of cell
	 */
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	/**
	 * Getter for adjacency list
	 * 
	 * @return - adjacency list
	 */
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}

	/**
	 * Add a cell to the adjacency list
	 * 
	 * @param adj
	 */
	public void addAdj(TestBoardCell adj) {
		adjList.add(adj);
	}

	/**
	 * Setters and testers
	 */
	public void setIsRoom( boolean flag ) {
		isRoom = flag ;
	}
	
	public boolean isRoom() {
		return isRoom;
	}

	public void setOccupied( boolean flag ) {
		isOccupied = flag ;
	}
	
	public boolean isOccupied() {
		return isOccupied ;
	}
	
	/**
	 * Standard toString
	 */
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}


}
