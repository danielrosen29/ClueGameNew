package clueGame;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	Board board = Board.getInstance(); 
	GameControlPanel GCPanel = new GameControlPanel();
	KnownCardsGUI KCPanel;
	
	public ClueGame() {
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
		
		HumanPlayer hp = null;
		Set<Player> playerSet = board.getPlayers();
		for (Player p : playerSet) {
			if ( p instanceof HumanPlayer )
				hp = (HumanPlayer) p;
		}	
		KCPanel = new KnownCardsGUI(hp);
		setSize(1400 , 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		add(GCPanel, BorderLayout.SOUTH);
		add(KCPanel, BorderLayout.EAST);
	}
	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
	}
	
	
}