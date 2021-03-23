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
			
			//See if the first player is Human
			ArrayList<Player> Players = new ArrayList<Player>(testList);
			System.out.println(Players.get(0).getClass());
			assertTrue(Players.get(0) instanceof HumanPlayer);
			
			
		}

		//Test to see if the deck was created 
		@Test
		public void testCards()
		{
			// Check if the right amount of cards were added
			Set<Card> testList = board.getDeck();
			//size should be 23 because walkways and unused spaces exist
			assertEquals(23, testList.size());

		}
		//Test to see if 1 human player and 5 computers were created
		@Test
		public void testDeal()
		{
			// Check if the right amount of players were initialized
			Set<Player> testList = board.getPlayers();
			assertEquals(6, testList.size());

			//See if the first player is Human
			ArrayList<Player> Players = new ArrayList<Player>(testList);
			System.out.println(Players.get(0).getClass());
			assertTrue(Players.get(0) instanceof HumanPlayer);


		}
}