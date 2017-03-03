package com.timiles.holdempartner;
/*
 * Created on 09-Jul-2004
 *
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author default
 *
 */
abstract class CardsButtons extends JPanel
  implements ActionListener {

	private JButton[][] cardButton;
	private JPanel buttonsPanel;
	protected JButton dealRandom;

	private static final String SUITS_IMAGES_PATH =
		"images/suits/";

	
	/**
     * cards class for all players
	 */
	public CardsButtons() {
		super( new BorderLayout() );

		dealRandom = new JButton("Deal random");
		dealRandom.addActionListener(this);
		
		
		buttonsPanel = new JPanel( new GridLayout(5,14) );
		
		cardButton = new JButton[13][4];

		buttonsPanel.add( new JLabel("", JLabel.CENTER) );
		for (int i=2; i<11; i++)
			buttonsPanel.add( new JLabel(""+i, JLabel.CENTER) );
		buttonsPanel.add( new JLabel("J", JLabel.CENTER) );
		buttonsPanel.add( new JLabel("Q", JLabel.CENTER) );
		buttonsPanel.add( new JLabel("K", JLabel.CENTER) );
		buttonsPanel.add( new JLabel("A", JLabel.CENTER) );

		for (int j=0; j<cardButton[0].length; j++) {
			String suit = Card.toString(getSuit(j));
			buttonsPanel.add( new JLabel(
					new ImageIcon(SUITS_IMAGES_PATH+suit+".gif") ));
			for (int i=0; i<cardButton.length; i++) {
				cardButton[i][j] = new JButton();
				cardButton[i][j].addActionListener(this);
				buttonsPanel.add(cardButton[i][j]);
			}
		}
		
		add(dealRandom, BorderLayout.WEST);
		add(buttonsPanel, BorderLayout.CENTER);

	}
	
	Card getSelected(ActionEvent e) {
		Object source = e.getSource();
		
		int number=-1;
		int suit=-1;

		for (int j=0; j<cardButton[0].length; j++) {
			for (int i=0; i<cardButton.length; i++) {
				if (source == cardButton[i][j]) {
					number = i;
					suit = j;
					break;
				}					
			}
		}

		if (number == -1 || suit == -1) {
			System.out.println("ERROR");
			return null;
		}
		
		cardButton[number][suit].setEnabled(false);
		return new Card( getNumber(number), getSuit(suit) );

	}
	
	Card dealRandom() {
		int suit;
		int number;
		
		do {
			number = (int) Math.floor(Math.random()*13);
			suit = (int) Math.floor(Math.random()*4);
		} while (!cardButton[number][suit].isEnabled());
		
		cardButton[number][suit].setEnabled(false);
		return new Card( getNumber(number), getSuit(suit) );
	}
	
	private int getSuit( int i ) {
		switch(i) {
		case 0 : return Card.DIAMONDS;
		case 1 : return Card.CLUBS;
		case 2 : return Card.HEARTS;
		case 3 : return Card.SPADES;
		default : System.out.println("ERROR");
                  return -1;
		}
	}
	
	private int getNumber( int i ) {
		switch(i) {
		case 0 : return Card.TWO;
		case 1 : return Card.THREE;
		case 2 : return Card.FOUR;
		case 3 : return Card.FIVE;
		case 4 : return Card.SIX;
		case 5 : return Card.SEVEN;
		case 6 : return Card.EIGHT;
		case 7 : return Card.NINE;
		case 8 : return Card.TEN;
		case 9 : return Card.JACK;
		case 10 : return Card.QUEEN;
		case 11 : return Card.KING;
		case 12 : return Card.ACE;
		default : System.out.println("ERROR");
		          return -1;
		}
	}
	
	public void reset() {
		for (int j=0; j<cardButton[0].length; j++) {
			for (int i=0; i<cardButton.length; i++) {
				cardButton[i][j].setEnabled(true);
			}
		}
	}
}
