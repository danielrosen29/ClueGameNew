package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Board;

public class gameSetupTests {
	
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
		
		//Test to see if 1 human player and 5 computers were created
		@Test
		public void testPlayers()
		{
			// Check if the right amount of players were initialized
			Set<Player> testList = board.getPlayers();
			assertEquals(6, testList.size());
			
			//See if there is only 1 human player
			//order gets messed up so just see if there is the right amount
			int count = 0;
			for(Player i : testList) {
				if(i instanceof HumanPlayer) {
					count++;
				}
			}
			assertEquals(1, count);
			
			
		}

		//Test to see if the deck was created 
		@Test
		public void testCards()
		{
			// Check if the right amount of cards were added
			Set<Card> testList = board.getDeck();
			//size should be 20 because walkways and unused spaces exist then the solution cards are removed
			assertEquals(20, testList.size());

		}
		
		//Test to see if cards were dealt
		@Test
		public void testDeal()
		{
			Set<Player> testPList = board.getPlayers();
			
			//test if each player got the right amount of cards
			
			for(Player i : testPList) {
				Set<Card> testList = i.getHand();
				assertTrue(testList.size() == 3);
				
			}
			
			//test to make sure no card was dealt twice
			
			ArrayList<Player> testList = new ArrayList<Player>(testPList);
			
			// check some random cards from player hands against each other
			
			ArrayList<Card> testHand1 = new ArrayList<Card>(testList.get(0).getHand());
			ArrayList<Card> testHand2 = new ArrayList<Card>(testList.get(2).getHand());
			
			assertFalse(testHand1.get(0) == testHand2.get(0));
			assertFalse(testHand1.get(0) == testHand2.get(1));
			assertFalse(testHand1.get(0) == testHand2.get(2));
			assertFalse(testHand1.get(1) == testHand2.get(2));
			assertFalse(testHand1.get(2) == testHand2.get(2));
			assertFalse(testHand1.get(1) == testHand2.get(1));
			assertFalse(testHand1.get(1) == testHand2.get(0));


		}
}