package clueGame;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player {
	Set<Card> hand;
	Map<Card, Player> seenCards;
	public HumanPlayer(String name, Color color, int r, int c) {
		super(name, color, r, c);
		hand = new HashSet<Card>();
		seenCards = new HashMap<Card, Player>();
	}
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card card, Player p) {
		seenCards.put(card, p);
	}
	
	@Override
	public Set<Card> getHand(){
		return hand;
	}
	
	public Map<Card, Player> getSeen(){
		return seenCards;
	}

}
