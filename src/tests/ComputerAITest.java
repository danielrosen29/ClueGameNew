package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.CardType;

public class ComputerAITest {
	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;

		@BeforeAll
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			// set the file names to use my config files
			board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");		
			// Initialize will load config files 
			board.initialize();
		}

		@Test
		public void createSuggestionTest() {


		}

		@Test
		public void moveTest() {
			ComputerPlayer testPlayer = new ComputerPlayer("Test", Color.red ,5 ,5);
			testPlayer.setBoard(board);
			ArrayList<Card> testCards = new ArrayList<Card>();
			for(Card i : testPlayer.getHand()) {
				if(i.getType() == CardType.ROOM) {
					testCards.add(i);
				}
			}
			
			board.calcTargets(board.getCell(5, 5), 4);
			Set<BoardCell> targets = board.getTargets();
			
			

		}

}
