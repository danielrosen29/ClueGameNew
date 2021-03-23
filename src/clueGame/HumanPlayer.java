package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class HumanPlayer extends Player {
	Set<Card> hand;
	public HumanPlayer(String name, Color color, int r, int c) {
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
