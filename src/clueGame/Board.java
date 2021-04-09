package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import com.sun.jdi.Field;

/**
 * Board for clueGame
 * 
 * @author Mark Baldwin
 *
 */
public class Board extends JPanel {
	// secondary layout characters
	public static final char C_UP = '^' ;
	public static final char C_RIGHT = '>' ;
	public static final char C_DOWN = 'v' ;
	public static final char C_LEFT = '<' ;
	public static final char C_CENTER = '*' ;
	public static final char C_LABEL = '#' ;
	public static final char C_WALKWAY = 'W';
	public static final char C_UNUSED = 'X';
	
	// the container for the cells
	private BoardCell[][] grid;

	// Dimensions of the grid, determined from config file
	private int numRows;
	private int numColumns;

	// File names for the config files
	private String layoutConfigFile = "data/ClueLayout.csv";
	private String setupConfigFile = "data/ClueSetup.txt";

	// map to hold the game rooms, key is the character (note, walkways and closets are included)
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	// Set to hold the different players
	private Set<Player> playerSet = new HashSet<Player>();
	
	//Set to hold the different weapons
	private Set<String> weaponSet = new HashSet<String>();
	
	private Set<BoardCell> startLocations = new HashSet<BoardCell>();

	// Data structure containing the targets
	private Set<BoardCell> targets;
	// Data structure used during path calculation
	// Used an instance var for efficiency (since recursive call)
	private Set<BoardCell> visited;
	
	//The card deck
	private Set<Card> deck = new HashSet<Card>();
	
	//The solution
	public Solution solution = new Solution();
	
	/*
	 * 
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * initialize the grid (since we are using singleton pattern)
	 */
	public void initialize() {
		loadConfigFiles();
		createDeck();
		dealCards();
		generateStarts();
		calcAdjacencies();
	}

	/*
	 * Methods to load the configuration files
	 */
	public void loadConfigFiles() {
		try {
			// Rooms are needed for guesses, detective notes, and the grid display
			loadSetupConfig();
			// grid must then load its own config file and calculate adjacencies
			loadLayoutConfig();
			// load the remaining types of data
			// loadPeopleConfig();
			// loadWeaponConfig();
		} catch (Exception e) {
			// Handles various configuration file issues
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Load the room data
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		//Clear players and weapons before each instance creation
		playerSet.clear();
		weaponSet.clear();
		// open up the file
		FileReader is = new FileReader(setupConfigFile);
		Scanner roomConfig = new Scanner(is);

		// read in the data
		while (roomConfig.hasNextLine()) {
			String line = roomConfig.nextLine();
			
			// ignore comment
			if( line.substring(0, 2).contentEquals("//")) {
				continue ;
			}
					
			// break up the line
			String[] tokens = line.split(",");
			// clean out spaces
			for( int i = 0 ; i < tokens.length ; i++ ) {
				tokens[i] = tokens[i].trim();
			}
					
			// process rooms
			if (tokens[0].contentEquals("Room")) {
				char key = tokens[2].charAt(0) ;
				Room room = new Room( key, tokens[1] ) ;
				roomMap.put(key, room);
			} else if (tokens[0].contentEquals("Space")) {
				char key = tokens[2].charAt(0) ;
				Room room = new Room( key, tokens[1] ) ;
				roomMap.put(key, room);
			} else if (tokens[0].contentEquals("Player")) {
				String name = tokens[1];
				String type = tokens[2];
				String colorStr = tokens[3];
				java.lang.reflect.Field f = Class.forName("java.awt.Color").getField(colorStr);
				Color color = (Color)f.get(null);
				
				String r = tokens[4];
				String c = tokens[5];
				Player p;
				if (type.equals("H")) {
					p = new HumanPlayer(name, color, Integer.parseInt(r), Integer.parseInt(c));
				}
				else {
					p = new ComputerPlayer(name, color, Integer.parseInt(r), Integer.parseInt(c));
				}
				playerSet.add(p);
			} else if (tokens[0].contentEquals("Weapon")) {
				weaponSet.add(tokens[1]);
			}
			else {
				roomConfig.close();
				throw new BadConfigFormatException("Room file format incorrect at line: " + line);
			}
		}
		// close her up
		roomConfig.close();
	}

	/**
	 * loadLayoutConfig - Load the grid
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String> dataLines = new ArrayList<String>(); // holds the data for reprocessing

		// open up the grid config file
		FileReader is = new FileReader(layoutConfigFile);
		Scanner boardConfig = new Scanner(is);

		// load the data into dataLines as strings
		int row = 0;
		while (boardConfig.hasNextLine()) {
			String line = boardConfig.nextLine();
			dataLines.add(line);

			// Use the first row to set the number of columns
			if (row == 0) {
				String[] tokens = line.split(",");
				numColumns = tokens.length;
			}
			// Update the number of rows
			row++;
		}

		// close the data file
		boardConfig.close();

		// we now have rows and columns, initialize the grid array
		numRows = row;
		grid = new BoardCell[numRows][numColumns];

		// now add cells to the grid
		row = 0;
		
		for (String line : dataLines) {
			String[] tokens = line.split(",");

			// For all other rows, ensure same number of columns
			if (numColumns != tokens.length) {
				boardConfig.close();
				throw new BadConfigFormatException("Rows do not all have the same number of columns");
			}

			for (int col = 0; col < numColumns; col++) {
				// Ensure it's a valid room
				char key = tokens[col].charAt(0) ;
				char secondary = 0 ;
				if( tokens[col].length() > 1 ) {
					secondary = tokens[col].charAt(1) ;
				}
				
				Room room = roomMap.get( key ) ;
				if (room == null) {
					boardConfig.close();
					throw new BadConfigFormatException("Room not defined " + tokens[col]);
				} 
				
				// finish processing the cell
				grid[row][col] = new BoardCell(row, col, key, secondary );
				
				// update room info if necessary
				if( secondary == Board.C_CENTER ) {
					room.setCenterCell(grid[row][col]);
				}
				if( secondary == Board.C_LABEL) {
					room.setLabelCell( grid[row][col]) ;
				}
			}
			row++;
		}
	}
	
	/*
	 * Method to create deck of cards
	 */
	private void createDeck() {
		deck.clear();
		for (Map.Entry r : roomMap.entrySet()) { 
			Room tempRoom = (Room) r.getValue();
			deck.add(new Card(tempRoom.getName(), CardType.ROOM));
		}
		for (Player p : playerSet) {
			deck.add(new Card(p.getName(), CardType.PERSON));
		}
		for (String w : weaponSet) {
			deck.add(new Card(w, CardType.WEAPON));
		}
	}
	
	/*
	 * Method to find all possible starting locations, will be assigned in initialize
	 */
	private void generateStarts() {
		for(int i = 0; i < grid[0].length; i++) {
			if(grid[0][i].isWalkway()) {
				startLocations.add(grid[0][i]);
			}
		}
		for(int i = 0; i < grid[0].length; i++) {
			if(grid[grid.length-1][i].isWalkway()) {
				startLocations.add(grid[grid.length-1][i]);
			}
		}
		for(int i = 0; i < grid.length; i++) {
			if(grid[i][0].isWalkway()) {
				startLocations.add(grid[i][0]);
			}
		}
		for(int i = 0; i < grid.length; i++) {
			if(grid[i][grid[0].length-1].isWalkway()) {
				startLocations.add(grid[i][grid[0].length-1]);
			}
		}
	}
	
	/*
	 * Method to set start locations for all the players
	 */
	
	private void setStarts () {
		
	}
	
	/*
	 * Method to deal cards
	 */
	private void dealCards() {
		//Get Solution Room
		ArrayList<Character> shuffledRooms = new ArrayList<Character>(roomMap.keySet());
		Collections.shuffle(shuffledRooms);
		Character solutionRoom = shuffledRooms.get(0);
		String roomTemp = roomMap.get(solutionRoom).getName();
		
		//Get Solution Player
		ArrayList<Player> shuffledPlayers = new ArrayList<Player>(playerSet);
		Collections.shuffle(shuffledPlayers);
		Player solutionPlayer = shuffledPlayers.get(0);
		String playerTemp = solutionPlayer.getName();

		
		//Get Solution Weapon
		ArrayList<String> shuffledWeapons = new ArrayList<String>(weaponSet);
		Collections.shuffle(shuffledWeapons);
		String solutionWeapon = shuffledWeapons.get(0);
		
		for (Card c : deck) {
			if (c.getCard() == roomTemp) {
				solution.room = c;
			} else if (c.getCard() == playerTemp) {
				solution.person = c;
			} else if (c.getCard() == solutionWeapon) {
				solution.weapon = c;
			}
		}
		
		deck.remove(solution.room);
		deck.remove(solution.person);
		deck.remove(solution.weapon);
		
		ArrayList<Player> playerList = new ArrayList<Player>(playerSet);
		int index = 0;
		for (Card c : deck) {
			if (index == playerList.size())
				index = 0;
			if(playerList.get(index).getHand().size() != 3) {
				playerList.get(index).updateHand(c);
			}
			index++;
		}
		
	}

	/*
	 *  Methods to calculate adjacencies. Called once at beginning of program.
	 */
	private void calcAdjacencies() {
		// Calculate the adjacency list for each location
		for (int row=0; row<numRows; row++)
			for (int col=0; col<numColumns; col++) {
				addAdjacencies(row, col);
			}
	}

	private void addAdjacencies(int row, int col) {
		// Only need to calculate adjacency lists for walkway and room centers
		BoardCell cell = grid[row][col];

		// Just check walkways.   Room centers connect to them so we can handle it
		if (cell.isWalkway()) {
			// Check four neighbors, no diagonals
			// check square below, can only enter if doorway is UP
			checkNeighbor(cell, row-1, col, cell.getDoorDirection() == DoorDirection.UP);
			// check square below, can only enter if doorway is DOWN
			checkNeighbor(cell, row+1, col, cell.getDoorDirection() == DoorDirection.DOWN);
			// check square to left, can only enter if doorway is LEFT
			checkNeighbor(cell, row, col-1, cell.getDoorDirection() == DoorDirection.LEFT);
			// check square to right, can only enter if doorway is RIGHT
			checkNeighbor(cell, row, col+1, cell.getDoorDirection() == DoorDirection.RIGHT);
		}
		// if secret passage)
		char sp = cell.getSecretPassage() ;
		if( sp != 0 ) {
			// long chain that connects from room to secret passage room
			roomMap.get(cell.getInitial()).getCenterCell().addAdj(roomMap.get(sp).getCenterCell()) ;
		}
	}


	/**
	 * 
	 * @param fromCell  the from cell
	 * @param toRow the to cell row
	 * @param toCol - the to cell column
	 * @param door - is there a door from fromCell to toCell
	 */
	private void checkNeighbor(BoardCell fromCell, int toRow, int toCol, boolean door ) {
		// First ensure a valid index
		if (toRow < 0 || toCol < 0 || toRow >= numRows || toCol >= numColumns)
			return;

		// get the cell
		BoardCell toCell = grid[toRow][toCol];
		
		if( toCell.isUnused() )
			return ;
		
		// if it is a room
		if( toCell.isRoom() ) {
			// no door, return
			if( !door ) {
				return ;
			}
			
			// if the toCell is in a room, replace with Center
			char key = toCell.getInitial() ;	
			toCell = roomMap.get(key).getCenterCell() ;
		}
		
		// add adjacencies
		fromCell.addAdj(toCell);
		toCell.addAdj(fromCell);
	}

	/*
	 * Methods to calculate the set of targets
	 */
	public void calcTargets(BoardCell startNode, int pathLength) {
		// Reset the data structures
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		
		// Initialize a search from this start node
		visited.add(startNode);
		// start the recursive procedure
		findAllTargets(startNode, pathLength);
	}
	
	/**
	 * Recursive call for CalcTargets
	 * 
	 * @param thisCell - current cell being checked
	 * @param numSteps - number of steps left 
	 */
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		// for each adjacent cell not already visited, add it to the 
		// visited list (to guard against backtracking). 
		// If we've taken the desired number of steps, add to targets,
		// otherwise continue to next set of adjacent cells (recursive call)
		for (BoardCell adjCell : thisCell.getAdjList()) {	
			if (!visited.contains(adjCell) && 	// can't retrace steps
				!adjCell.isOccupied() ) { 		// cannot enter occupied location either
				
				visited.add(adjCell);
				
				if (numSteps == 1 || 		// if we've used the entire roll of the die
					adjCell.isRoom() ) {	// or we entered a room, this is a target
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, numSteps - 1);
				}

				// after recursive call we've explored all adjacencies, 
				// so remove this one from the visited list
				visited.remove(adjCell);
			}
		}
	}
	
	//Method for testing accusations
	
	public Boolean checkAccusation(Solution suggestion) {
		if(suggestion.person.getCard() == solution.person.getCard() &&
				suggestion.room.getCard() == solution.room.getCard() &&
				suggestion.weapon.getCard() == solution.weapon.getCard()) {
			return true;
		}else {
			return false;
		}
	}
	
	//Method for handling suggestions
	
	public Card handleSuggestion(Solution suggestion, Player accuser) {
		for(Player p : playerSet) {
			Card c = p.disproveSuggestion(suggestion);
			if(c != null) {
				for(Player x : playerSet) {
					if(x instanceof ComputerPlayer) {
						((ComputerPlayer)x).updateSeenCards(c);
					}
					else {
						((HumanPlayer)x).updateSeen(c, p);
					}
				}
				if(p != accuser) {
					return c;
				}
			}
		}
		return null;
	}


	/**
	 * Getters
	 */

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public Room getRoom( BoardCell cell ) {
		return roomMap.get(cell.getInitial()) ;
	}
	
	public Room getRoom(char c) {
		return roomMap.get( c );
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return targets ;
	}

	/*
	 * Methods just used for JUnit testing
	 */
	public void setConfigFiles(String layoutConfig, String setupConfig) {
		layoutConfigFile = "data/" + layoutConfig ;
		setupConfigFile = "data/" + setupConfig;
	}

	public Map<Character, Room> getRooms() {
		return roomMap;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public Set<Player> getPlayers() {
		return playerSet;
	}
	
	public Set<String> getWeapons() {
		return weaponSet;
	}
	
	public Set<Card> getDeck() {
		return deck;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int cellWidth = 59;
		int cellHeight = 32;
		
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j].draw(cellWidth, cellHeight, g);
			}
		}
		
		for (Character c : roomMap.keySet()) {
			//System.out.println(roomMap.get(c).getCenterCell());
			roomMap.get(c).drawLabels(g);
		}
		
		for (Player i : this.playerSet) {
			i.drawPlayer(cellWidth, cellHeight, g);
		}
		
		
	}

}
