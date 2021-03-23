package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {
	Set<Card> hand;
	public HumanPlayer(String name, Color color, int r, int c) {
		super(name, color, r, c);
	}
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}

}
