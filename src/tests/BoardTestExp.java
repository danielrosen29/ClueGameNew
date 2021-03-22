package tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestExp {
	TestBoard board;

	@BeforeEach // Run before each test, @BeforeAll would work just as well
	public void setUp() {
		// board should create adjacecy list
		board = new TestBoard();
	}

	/*
	 * Test adjacencies for several different locations
	 * Test centers and edges
	 */
	@Test
	public void testAdjacency() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());

		cell = board.getCell(1, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertEquals(3, testList.size());

		cell = board.getCell(2, 0);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertEquals(3, testList.size());

		cell = board.getCell(1, 1);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertEquals(4, testList.size());

		cell = board.getCell(3, 3);
		testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
	}

	/*
	 * Test targets with several rolls and start locations
	 */
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));

		cell = board.getCell(2, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

		cell = board.getCell(2, 0);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));

		cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));

		cell = board.getCell(0, 0);
		board.calcTargets(cell, 1);
		targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		cell = board.getCell(1, 1);
		board.calcTargets(cell, 6);
		targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

	}

	/*
	 * Test targets which may lead to a room
	 */
	@Test
	public void testTargetsRoom() {
		// set up rooms, I set to cells as rooms, both should be hit.
		board.getCell(0, 2).setIsRoom(true) ;
		board.getCell(1, 2).setIsRoom(true) ;
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		
		// try same setup but in corner
		cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
	}

	@Test
	public void testTargetsOccupied() {
		// set up occupied cells.
		board.getCell(0, 2).setOccupied(true) ;
		board.getCell(1, 2).setOccupied(true) ;
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		
		// try same setup but in corner
		cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

		board.getCell(0, 2).setOccupied(false) ;
		board.getCell(1, 2).setOccupied(false) ;
	}

	// final mixed test
	@Test
	public void testTargetsMixed() {
		// set up occupied cells.
		board.getCell(0, 2).setOccupied(true) ;
		board.getCell(1, 2).setIsRoom(true) ;
		TestBoardCell cell = board.getCell(1, 1);
		board.calcTargets(cell, 3);
 		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		
		// try same setup but in corner
		cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));

		board.getCell(0, 2).setOccupied(false) ;
		board.getCell(1, 2).setIsRoom(false) ;
	}
}
	
