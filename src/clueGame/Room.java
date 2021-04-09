package clueGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

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
	public void drawLabels(Graphics g) {
		BoardCell center = this.getCenterCell();
		System.out.println(center);
		if (center != null) {
			g = (Graphics2D)g;
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawString(name, center.getCol()*59, center.getRow()*32);
		}
	}
}
