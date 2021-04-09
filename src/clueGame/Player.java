<<<<<<< HEAD
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

public abstract class Player {
	private Random random = new Random();
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
		}
		return tempCard;

	}
	
	public abstract Set<Card> getHand();
	
	public Color getColor() {
		return color;
	}
=======
package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

public abstract class Player {
	private Random random = new Random();
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
		}
		return tempCard;

	}
	
	public abstract Set<Card> getHand();
	
	public Color getColor() {
		return color;
	}
>>>>>>> 9d4428d22e362b77308e17ca4903d41b18837c6e
}