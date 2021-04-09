<<<<<<< HEAD
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
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
		
		
		//test Computer create suggestion properly returns a suggestion with the room it is given and a random weapon/person it hasn't seen
		@Test
		public void createSuggestionTest() {
			ComputerPlayer testPlayer = new ComputerPlayer("Test", Color.red ,5 ,5);
			testPlayer.setBoard(board);
			Card testRoom = new Card("Test", CardType.ROOM);
			Solution testSol = testPlayer.createSuggestion(testRoom);
			Set<Card> testCards = testPlayer.getUnseenCards();
			assertTrue(testCards.contains(testSol.person));
			assertTrue(testCards.contains(testSol.weapon));
			assertTrue(testRoom == testSol.room);
			
			Set<Card> testDeck = new HashSet<Card>();
			Card testWeapon = new Card("TestWeapon", CardType.WEAPON);
			Card testPerson = new Card("TestPerson", CardType.PERSON);
			testDeck.add(testWeapon);
			testDeck.add(testPerson);
			
			testPlayer.setSeenCards(testDeck);
			
			testSol = testPlayer.createSuggestion(testRoom);
			assertTrue(testPerson == testSol.person);
			assertTrue(testWeapon == testSol.weapon);
			assertTrue(testRoom == testSol.room);
		}
		
		
		
		//Test to ensure the a legal space to move is selected
		@Test
		public void moveTest() {
			ComputerPlayer testPlayer = new ComputerPlayer("Test", Color.red ,5 ,5);
			testPlayer.setBoard(board);
			
			BoardCell testCell = testPlayer.selectMove(4);
			
			board.calcTargets(board.getCell(5, 5), 4);
			Set<BoardCell> targets = board.getTargets();		
			assertTrue(targets.contains(testCell));

		}

}
=======
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.Color;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
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
		
		
		//test Computer create suggestion properly returns a suggestion with the room it is given and a random weapon/person it hasn't seen
		@Test
		public void createSuggestionTest() {
			ComputerPlayer testPlayer = new ComputerPlayer("Test", Color.red ,5 ,5);
			testPlayer.setBoard(board);
			Card testRoom = new Card("Test", CardType.ROOM);
			Solution testSol = testPlayer.createSuggestion(testRoom);
			Set<Card> testCards = testPlayer.getUnseenCards();
			assertTrue(testCards.contains(testSol.person));
			assertTrue(testCards.contains(testSol.weapon));
			assertTrue(testRoom == testSol.room);
			
			Set<Card> testDeck = new HashSet<Card>();
			Card testWeapon = new Card("TestWeapon", CardType.WEAPON);
			Card testPerson = new Card("TestPerson", CardType.PERSON);
			testDeck.add(testWeapon);
			testDeck.add(testPerson);
			
			testPlayer.setSeenCards(testDeck);
			
			testSol = testPlayer.createSuggestion(testRoom);
			assertTrue(testPerson == testSol.person);
			assertTrue(testWeapon == testSol.weapon);
			assertTrue(testRoom == testSol.room);
		}
		
		
		
		//Test to ensure the a legal space to move is selected
		@Test
		public void moveTest() {
			ComputerPlayer testPlayer = new ComputerPlayer("Test", Color.red ,5 ,5);
			testPlayer.setBoard(board);
			
			BoardCell testCell = testPlayer.selectMove(4);
			
			board.calcTargets(board.getCell(5, 5), 4);
			Set<BoardCell> targets = board.getTargets();		
			assertTrue(targets.contains(testCell));

		}

}
>>>>>>> 9d4428d22e362b77308e17ca4903d41b18837c6e
