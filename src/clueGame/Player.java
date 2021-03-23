package clueGame;

import java.awt.Color;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	protected int row, col;
	
	public Player(String name, Color color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	public abstract void updateHand(Card card);
	
	public String getName() {
		return this.name;
	}
	public abstract Set<Card> getHand();
}