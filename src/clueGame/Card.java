 package clueGame;

public class Card {

	private String card;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.card = name;
		this.type = type;
	}
	
	public String getCard() {
		return this.card;
	}
	
	public CardType getType() {
		return this.type;
	}
	
	public boolean equals(Card target) {
		return false;
	}
}