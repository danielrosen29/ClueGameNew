package clueGame;

import java.util.HashSet;
import java.util.Set;

/**
 * BoardCell for clueGame
 * 
 * A single cell of the game board
 * 
 * @author Mark Baldwin
 * 
 */
public class BoardCell {
	private int row, col; // row and column of cell
	// initial of the room (or walkway). Used to display room name
	// and to track last room visited.
	private char initial;
	// if door, it's direction
	private DoorDirection doorDirection = DoorDirection.NONE;
	// name cell
	private boolean roomLabel = false  ;
	// center cell 
	private boolean roomCenter = false ;  
	// secret passage
	private char secretPassage = 0 ;
	// cell is occupied by another player
	private boolean isOccupied = false ;
	
	private Set<BoardCell> adjList = new HashSet<BoardCell>(); // cells adjacent to this cell

	/**
	 * Constructor
	 * 
	 * @param row - row of cell
	 * @param col - column of cell
	 */
	public BoardCell(int row, int col, char key, char secondary) {
		super();
		this.row = row;
		this.col = col;
		initial = key ;

		// flag secondary characteristics
		switch( secondary ) {
		case Board.C_LEFT:
			doorDirection = DoorDirection.LEFT  ;
			break ;
		case Board.C_UP:
			doorDirection = DoorDirection.UP  ;
			break ;
		case Board.C_RIGHT:
			doorDirection = DoorDirection.RIGHT  ;
			break ;
		case Board.C_DOWN:
			doorDirection = DoorDirection.DOWN  ;
			break ;
		case Board.C_LABEL:
			roomLabel = true ;
			break ;
		case Board.C_CENTER:
			roomCenter = true ;
			break ;
		default:
			secretPassage = secondary ;
			break ;
		}

	}

	/**
	 * Getter for adjacency list
	 * 
	 * @return - adjacency list
	 */
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	/**
	 * Add a cell to the adjacency list
	 * 
	 * @param adj
	 */
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	/**
	 * Standard toString
	 */
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}

	/*
	 * Auxillery methods
	 * 
	 */
	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return initial;
	}

	public boolean isRoomCenter() {
		return roomCenter ;
	}

	public boolean isLabel() {
		return roomLabel ;
	}
	
	public char getSecretPassage() {
		return secretPassage ;
	}

	public boolean isWalkway() {
		return initial == Board.C_WALKWAY ;
	}

	public boolean isRoom() {
		return initial != Board.C_WALKWAY && 
				initial != Board.C_UNUSED ;
	}

	public boolean isUnused() {
		return initial == Board.C_UNUSED ;
	}

	public boolean isOccupied() {
		return isOccupied ;
	}

	public void setOccupied( boolean occupied ) {
		if( initial == Board.C_WALKWAY ) {
			isOccupied = occupied ;
		}
	}
}