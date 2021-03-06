
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public abstract class Player {
	private Random random = new Random();
	private String name;
	private Color color;
	protected int row, col;
	private BoardCell currentCell; 
	
	public Player(String name, Color color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	public abstract void updateHand(Card card);
	
	public void updateLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> cardList = new ArrayList<Card>(this.getHand());
		Card tempCard = null;
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		
		for(Card c : cardList) {
			if(suggestion.room == c || suggestion.weapon == c || suggestion.person == c) {
				matchingCards.add(c);
			}
		}
		
		if(!matchingCards.isEmpty()) {
			tempCard = matchingCards.get(random.nextInt(matchingCards.size()));
			return tempCard;
		}else {
			return null;
		}

	}
	
	public abstract Set<Card> getHand();
	
	public Color getColor() {
		return color;
	}
	
	public void drawPlayer(BoardCell cell, Graphics g, int c, int r) {
		
		g.setColor(this.color);
		g.fillOval(cell.getCol()*c, cell.getRow()*r+3, c, r);
	}
	public void setCurrentCell(BoardCell selected) {
		this.currentCell = selected;
	}
	public BoardCell getCurrentCell() {
		return this.currentCell;
	}
}
