package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player {
	Set<Card> hand;
	Set<Card> cardsNotSeen;
	Board board;
	Random random = new Random();

	public ComputerPlayer(String name, Color color, int r, int c) {
		super(name, color, r, c);
		hand = new HashSet<Card>();
	}
	
	public Solution createSuggestion(Card room) {
		Solution out = new Solution();
		out.room = room;
		ArrayList<Card> cardArray = new ArrayList<Card>(cardsNotSeen);
		Collections.shuffle(cardArray);
		for (Card c : cardsNotSeen) {
			if (c.getType() == CardType.PERSON) {
				out.person = c;
				break;
			}
		}
		for (Card c : cardsNotSeen) {
			if (c.getType() == CardType.WEAPON) {
				out.weapon = c;
				break;
			}
		}
		return out;
	}
	
	public BoardCell selectMove(int pathLength) {
		BoardCell currentCell = board.getCell(this.row, this.col);
		board.calcTargets(currentCell, pathLength);
		Set<BoardCell> targets = board.getTargets();
		ArrayList<BoardCell> roomTargets = new ArrayList<BoardCell>();
		for(BoardCell i : targets) {
			if(i.isRoom() && cardsNotSeen.contains(i)) {
				roomTargets.add(i);
			}
		}
		
		if(!roomTargets.isEmpty()) {
			return (roomTargets.get(random.nextInt(roomTargets.size())));
		}else {
			ArrayList<BoardCell> cellArray = new ArrayList<BoardCell>(targets);
			return (cellArray.get(random.nextInt(cellArray.size())));
		}
	}
	
	public void setBoard(Board board) {
		this.board = board;
		this.cardsNotSeen = board.getDeck();
		for(Card i : this.hand) {
			updateSeenCards(i);
		}
		
	}
	
	public void updateSeenCards(Card card) {
		if(this.cardsNotSeen.contains(card)) {
			this.cardsNotSeen.remove(card);
		}
	}
	
	public void setSeenCards(Set<Card> deck) {
		this.cardsNotSeen = deck;
	}
	
	public Set<Card> getUnseenCards(){
		return this.cardsNotSeen;
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
