package clueGame;

/**
 * Room for clueGame
 * 
 * Info about a single room
 * 
 * @author Mark Baldwin
 * 
 */
public class Room {
	private String name ;  // name of the room
	private char initial ;
	private BoardCell centerCell ;  // center cell
	private BoardCell labelCell ;   // cell where to start writing label on GUI
	
	/**
	 * Constructor for Room
	 * 
	 * @param initial - rooms initial
	 * @param name - rooms name
	 */
	public Room(char initial, String name ) {
		this.initial = initial ;
		this.name = name ;
	}

	/*
	 * Getters and setters
	 */
	public String getName() {
		return name ;
	}
	
	public void setCenterCell( BoardCell center ) {
		this.centerCell = center ;
	}

	public void setLabelCell(BoardCell boardCell) {
		this.labelCell = boardCell ;
	}

	public BoardCell getLabelCell() {
		return labelCell ;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
}
