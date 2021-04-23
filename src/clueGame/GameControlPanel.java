package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

	ClueGame game;
	
	JTextField playerText;
	JTextField rollText;
	JTextField guessText;
	JTextField guessResultText;
	private final Color backgroundColor = new Color(18, 132, 181, 255);
	private final Color textColor = new Color(186, 233, 253, 255);
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel(ClueGame game, int width, int height)  {
		super();
		//Layout for whole panel
		this.game = game;
		JPanel layoutOrganizer = new JPanel();
		layoutOrganizer.setLayout(new GridLayout(2,0));
		
		JPanel upperPanel = upperPanel(width,height);
		JPanel lowerPanel = lowerPanel();
		
		layoutOrganizer.add(upperPanel);
		layoutOrganizer.add(lowerPanel);
		this.add(layoutOrganizer);
		
	}

	
	private JPanel upperPanel(int w, int h){
		//name
		JPanel returnedPanel = new JPanel();
		returnedPanel.setLayout(new GridLayout(1,4));
		returnedPanel.setPreferredSize(new Dimension(w, (int) (h*0.076)));
		JPanel namePanel = new JPanel();
		namePanel.setBackground(backgroundColor);
		namePanel.setLayout(new GridLayout(2,1));
		JLabel playerTurnText = new JLabel("Player Turn", SwingConstants.CENTER);
		playerTurnText.setForeground(Color.white);
		namePanel.add(playerTurnText);
		playerText = new JTextField();
		playerText.setColumns(30);
		playerText.setEditable(false);
		playerText.setHorizontalAlignment(JTextField.CENTER);
		playerText.setBackground(textColor);
		namePanel.add(playerText, BorderLayout.NORTH);
		returnedPanel.add(namePanel, BorderLayout.SOUTH);
		//roll
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(2,1));
		rollPanel.setBackground(backgroundColor);
		JLabel rollLabel = new JLabel("Roll", SwingConstants.CENTER);
		rollLabel.setForeground(Color.white);
		rollPanel.add(rollLabel);
		rollText = new JTextField();
		rollText.setColumns(2);
		rollText.setEditable(false);
		rollText.setHorizontalAlignment(JTextField.CENTER);
		rollText.setBackground(textColor);
		rollPanel.add(rollText, BorderLayout.EAST);
		returnedPanel.add(rollPanel, BorderLayout.WEST);
		//Button
		JButton makeAccusation = new JButton("Make Accusation");
		//makeAccusation.addActionListener(new ButtonListener());
		returnedPanel.add(makeAccusation);
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new NextButtonListener());
		returnedPanel.add(nextButton);
		return returnedPanel;
	}

	private JPanel lowerPanel() {
		JPanel returnedPanel = new JPanel();
		returnedPanel.setLayout(new GridLayout(0,2));
		JPanel Guess = new JPanel();
		Guess.setBackground(backgroundColor);
		Guess.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
		TitledBorder border = new TitledBorder( new EtchedBorder(), "Guess");
		border.setTitleColor(Color.white);
		border.setTitleJustification(TitledBorder.CENTER);
		Guess.setBorder(border);
		guessText = new JTextField();
		guessText.setEditable(false);
		guessText.setHorizontalAlignment(JTextField.CENTER);
		guessText.setBackground(textColor);
		Guess.add(guessText,gbc);
		returnedPanel.add(Guess);
		
		JPanel GuessResult = new JPanel(new GridBagLayout());
		GuessResult.setBackground(backgroundColor);
		TitledBorder borderTwo = new TitledBorder( new EtchedBorder(), "Guess Result");
		borderTwo.setTitleColor(Color.white);
		borderTwo.setTitleJustification(TitledBorder.CENTER);
		GuessResult.setBorder(borderTwo);
		guessResultText = new JTextField();
		guessResultText.setEditable(false);
		guessResultText.setHorizontalAlignment(JTextField.CENTER);
		guessResultText.setBackground(textColor);
		GuessResult.add(guessResultText, gbc);
		returnedPanel.add(GuessResult);
		return returnedPanel;
	}
	
	public void setTurn(Player cpu, Integer roll) {
		playerText.setText(cpu.getName());
		playerText.setBackground(cpu.getColor());
		rollText.setText(roll.toString());
	}
	
	public void setGuess(String g) {
		guessText.setText(g);
	}
	
	public void setGuessResult(String g) {
		guessResultText.setText(g);
	}
	
	private class NextButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (!game.board.humanPlayerTurn) {//game.board.nextPressed();
				game.board.nextPressed(game.GCPanel);
				game.board.repaint();
			} else {
				JOptionPane.showMessageDialog(game,
						"Please wait for player to finish turn!",
						"Error!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}