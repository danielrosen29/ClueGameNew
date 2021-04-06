package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Board;
import clueGame.Solution;
import clueGame.CardType;
import clueGame.ComputerPlayer;

public class GameSolutionTest {
	
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
	
	
	//Test all cases for a player disproves a solution
	@Test
	public void testPlayerDisproves()
	{
		//setup a known testcard
		ArrayList<Player> testPList = new ArrayList<Player>(board.getPlayers());
		ArrayList<Card> testCList = new ArrayList<Card>(testPList.get(0).getHand());
		Solution testSol1 = new Solution();
		testSol1.person = testCList.get(0);
		//ensure returned card is the same
		Card returnedCard = testPList.get(0).disproveSuggestion(testSol1);
		
		assertEquals(testSol1.person, returnedCard);
		
		//test null is returned when it doesnt match
		returnedCard = testPList.get(1).disproveSuggestion(testSol1);
		
		Card nullCard = null;
		
		assertEquals(null, returnedCard);
		
		//test that it is random
		testSol1.weapon = testCList.get(1);
		testSol1.room = testCList.get(2);
		
		Set<Card> multipleReturns = new HashSet<Card>();
		
		for(int i = 0; i < 30; i++) {
			returnedCard = testPList.get(0).disproveSuggestion(testSol1);
			multipleReturns.add(returnedCard);
		}
		
		assertEquals(multipleReturns.size(), 3);
	}
	
	//Test all cases for testing an accusation
	@Test
	public void testAccusationTest() {
		Card testP = new Card("Wrong", CardType.PERSON);
		Card testR = new Card("Wrong", CardType.ROOM);
		Card testW = new Card("Wrong", CardType.WEAPON);
		Solution testSolution = new Solution();
		testSolution.person = board.solution.person;
		testSolution.room = board.solution.room;
		testSolution.weapon = board.solution.weapon;
		assert(board.checkAccusation(testSolution));
		
		testSolution.person = testP;
		assertFalse(board.checkAccusation(testSolution));
		
		testSolution.person = board.solution.person;
		testSolution.room = testR;
		assertFalse(board.checkAccusation(testSolution));
		
		testSolution.room = board.solution.room;
		testSolution.weapon = testW;
		assertFalse(board.checkAccusation(testSolution));
		
	}
	
	
	//Test all cases for handling a suggestion
	@Test
	public void handeSuggestionTest() {
		ArrayList<Player> testPList = new ArrayList<Player>(board.getPlayers());
		Player testP = testPList.get(0);
		for(Player p : testPList) {
			if( p instanceof ComputerPlayer) {
				((ComputerPlayer) p).setBoard(board);
			}
		}
		
		ArrayList<Card> testCList = new ArrayList<Card>(testP.getHand());
		Solution testSolution = new Solution();
		testSolution.person = board.solution.person;
		testSolution.room = board.solution.room;
		testSolution.weapon = board.solution.weapon;
		Card testCard = board.handleSuggestion(testSolution, testP);
		
		assertEquals(testCard, null);
		
		for(Card i : testCList) {
			if(i.getType() == CardType.ROOM) {
				testSolution.room = i;
			}
		}
		
		testCard = board.handleSuggestion(testSolution, testP);
		
		assertEquals(testCard, null);
		
		Player testP2 = testPList.get(1);
		ArrayList<Card> testCList2 = new ArrayList<Card>(testP2.getHand());
		for(Card i : testCList2) {
			if(i.getType() == CardType.ROOM) {
				testSolution.room = i;
			}
		}
		testCard = board.handleSuggestion(testSolution, testP);
		
		assertEquals(testCard, testSolution.room);
		
	}

	
}
