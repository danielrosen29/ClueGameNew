package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	Set<Card> hand;
	
	public ComputerPlayer(String name, Color color, int r, int c) {
		super(name, color, r, c);
		hand = new HashSet<Card>();
	}
	
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	@Override
	public Set<Card> getHand(){
		return hand;
	}
	
}
