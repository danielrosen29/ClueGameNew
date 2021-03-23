package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	Set<Card> hand;
	
	public ComputerPlayer(String name, Color color, int r, int c) {
		super(name, color, r, c);
	}
	
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
}
