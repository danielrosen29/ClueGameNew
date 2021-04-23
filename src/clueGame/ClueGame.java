package clueGame;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	int startW = 1400;
	int startH = 900;
	
	Board board = Board.getInstance(); 
	GameControlPanel GCPanel = new GameControlPanel(this, startW, startH);
	KnownCardsGUI KCPanel;
	public  HumanPlayer hp = null;
	
	public ClueGame() {
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
		
		Set<Player> playerSet = board.getPlayers();
		for (Player p : playerSet) {	
			if ( p instanceof HumanPlayer )
				hp = (HumanPlayer) p;
		}	
		KCPanel = new KnownCardsGUI(hp);
		setSize(startW , startH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		add(GCPanel, BorderLayout.SOUTH);
		add(KCPanel, BorderLayout.EAST);
	}
	
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.board.setFrame(game);
		game.setVisible(true);
		
		JOptionPane.showMessageDialog(game,
				"You are " + game.hp.getName() + ".\nCan you find the solution\nbefore the Computer Players?",
				"Welcome to Clue",
				JOptionPane.INFORMATION_MESSAGE);
		
		game.board.nextPressed(game.GCPanel);
		game.board.repaint();
	}
	
	
}