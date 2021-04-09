package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.Set;
import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.*;
import clueGame.CardType;
import clueGame.Card;

public class KnownCardsGUI extends JPanel {

	private final Color backgroundColor = new Color(18, 132, 181, 255);
	private final Color textColor = new Color(186, 233, 253, 255);
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public KnownCardsGUI(HumanPlayer player)  {
		super();
		//Layout for whole panel
		JPanel layoutOrganizer = new JPanel();
		layoutOrganizer.setLayout(new GridLayout(3,3));
		
		
		JPanel peoplePanel = peoplePanel(player);
		JPanel roomsPanel = roomsPanel(player);
		JPanel weaponsPanel = weaponsPanel(player);
		
		layoutOrganizer.add(peoplePanel);
		layoutOrganizer.add(roomsPanel);
		layoutOrganizer.add(weaponsPanel);
		this.add(layoutOrganizer);
	}

	
	private JPanel peoplePanel(HumanPlayer player){
		//name
		Set<Card> hand = player.getHand();
		Set<Card> seenCards = player.getSeen().keySet();
		JPanel returnedPanel = new JPanel();
		returnedPanel.setLayout(new GridLayout(0, 1));
		JLabel topLabel = new JLabel("In Hand:");
		JLabel botLabel = new JLabel("Seen:");
		topLabel.setForeground(textColor);
		botLabel.setForeground(textColor);
		returnedPanel.setBackground(backgroundColor);
		returnedPanel.setPreferredSize(new Dimension(155, 240));
		returnedPanel.setMaximumSize(new Dimension(155, 240));
		returnedPanel.add(topLabel);
		for(Card i : hand) {
			if (i.getType() == CardType.PERSON) {
				returnedPanel.add(addTextField(i.getCard(), player));
			}
		}
		returnedPanel.add(botLabel);
		for(Card i : seenCards) {
			if (i.getType() == CardType.PERSON) {
				returnedPanel.add(addTextField(i.getCard(), player.getSeen().get(i)));
			}
		}
		returnedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		TitledBorder border = new TitledBorder( new EtchedBorder(), "People");
		border.setTitleColor(Color.white);
		returnedPanel.setBorder(border);
		return returnedPanel;
	}
	
	private JPanel roomsPanel(HumanPlayer player) {
		Set<Card> hand = player.getHand();
		Set<Card> seenCards = player.getSeen().keySet();
		JPanel returnedPanel = new JPanel();
		returnedPanel.setLayout(new GridLayout(0, 1));
		JLabel topLabel = new JLabel("In Hand:");
		JLabel botLabel = new JLabel("Seen:");
		topLabel.setForeground(textColor);
		botLabel.setForeground(textColor);
		returnedPanel.setBackground(backgroundColor);
		returnedPanel.setPreferredSize(new Dimension(155, 240));
		returnedPanel.setMaximumSize(new Dimension(155, 240));
		returnedPanel.add(topLabel);
		for(Card i : hand) {
			if (i.getType() == CardType.ROOM) {
				returnedPanel.add(addTextField(i.getCard(), player));
			}
		}
		returnedPanel.add(botLabel);
		for(Card i : seenCards) {
			if (i.getType() == CardType.ROOM) {
				returnedPanel.add(addTextField(i.getCard(), player.getSeen().get(i)));
			}
		}
		returnedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		TitledBorder border = new TitledBorder( new EtchedBorder(), "Rooms");
		border.setTitleColor(Color.white);
		returnedPanel.setBorder(border);
		return returnedPanel;
	}
	
	private JPanel weaponsPanel(HumanPlayer player){
		//name
		Set<Card> hand = player.getHand();
		Set<Card> seenCards = player.getSeen().keySet();
		JPanel returnedPanel = new JPanel();
		returnedPanel.setLayout(new GridLayout(0, 1));
		JLabel topLabel = new JLabel("In Hand:");
		JLabel botLabel = new JLabel("Seen:");
		topLabel.setForeground(textColor);
		botLabel.setForeground(textColor);
		returnedPanel.setBackground(backgroundColor);
		returnedPanel.setPreferredSize(new Dimension(155, 240));
		returnedPanel.setMaximumSize(new Dimension(155, 240));
		returnedPanel.add(topLabel);
		for(Card i : hand) {
			if (i.getType() == CardType.WEAPON) {
				returnedPanel.add(addTextField(i.getCard(), player));
			}
		}
		returnedPanel.add(botLabel);
		for(Card i : seenCards) {
			if (i.getType() == CardType.WEAPON) {
				returnedPanel.add(addTextField(i.getCard(), player.getSeen().get(i)));
			}
		}
		returnedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		TitledBorder border = new TitledBorder( new EtchedBorder(), "Weapons");
		border.setTitleColor(Color.white);
		returnedPanel.setBorder(border);
		return returnedPanel;
	}
	
	public JTextField addTextField(String text, Player p) {
		JTextField returnField = new JTextField(text);
		returnField.setEditable(false);
		returnField.setBackground(p.getColor());
		returnField.setForeground(Color.white);
		return returnField;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//Create a test player with known cards to see if everything displays
		HumanPlayer test = new HumanPlayer("Test", Color.cyan, 6, 6);
		HumanPlayer test2 = new HumanPlayer("Test2", Color.red, 6, 7);
		HumanPlayer test3 = new HumanPlayer("Test3", Color.green, 6, 8);
		test.updateHand(new Card("Halberd", CardType.WEAPON));
		test.updateHand(new Card("Lounge", CardType.ROOM));
		test.updateHand(new Card("Test2", CardType.PERSON));
		test.updateSeen(new Card("Test2", CardType.PERSON), test2);
		test.updateSeen(new Card("Pistol", CardType.WEAPON),test2);
		test.updateSeen(new Card("Bathroom", CardType.ROOM),test2);
		test.updateSeen(new Card("Test3", CardType.PERSON), test3);
		KnownCardsGUI panel = new KnownCardsGUI(test);  // create the panel
		JFrame frame = new JFrame(); // create the frame 
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel);
		//update the hand by simply re-adding a new GUIPanel, recreated with the update player cards
		frame.getContentPane().remove(panel);
		test.updateSeen(new Card("Puppy", CardType.WEAPON), test3);
		test.updateSeen(new Card("Atrium", CardType.ROOM), test3);
		frame.getContentPane().add(new KnownCardsGUI(test));
		frame.setSize(200, 1000);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		//panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		//panel.setGuess( "I have no guess!");
		//panel.setGuessResult( "So you have nothing?");
	}
}