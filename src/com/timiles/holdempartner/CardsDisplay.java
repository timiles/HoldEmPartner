package com.timiles.holdempartner;
/*
 * Created on 21-Oct-2004
 *
 */
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author default
 *
 */
class CardsDisplay extends JPanel {

	private JPanel pocket;
	private JPanel community;
	private JLabel[] cardLabels;
	public static final Color GREEN = new Color(0,128,0);
	private static final String CARDS_IMAGES_PATH =
		"images/cards/";
	
	private ImageIcon nullImage;
	
	private int nextCard;
	
	public CardsDisplay() {
		
		nullImage = new ImageIcon(
			CARDS_IMAGES_PATH+"null.gif");

		cardLabels = new JLabel[7];
		for (int i=0; i<cardLabels.length; i++)
		{
			cardLabels[i] = new JLabel(nullImage);
		}
		
	
		pocket = new JPanel();
		pocket.setBorder( new TitledBorder("Pocket") );
		pocket.setLayout( new GridLayout(1,2) );
		pocket.setBackground( GREEN );
		
		pocket.add( cardLabels[0] );
		pocket.add( cardLabels[1] );
		
		community = new JPanel();
		community.setLayout( new GridLayout(1,7) );
		community.setBorder( new TitledBorder("Community") );
		community.setBackground( GREEN );
		
		community.add( cardLabels[2] );
		community.add( cardLabels[3] );
		community.add( cardLabels[4] );
		community.add( new JLabel() );
		community.add( cardLabels[5] );
		community.add( new JLabel() );
		community.add( cardLabels[6] );
		
		this.setLayout( new FlowLayout() );
		this.add( pocket );
		this.add( community );
		
		nextCard = 0;
	}
	
	public void setCard(Card c) {
		
		// can't be more than 7 cards
		if (nextCard>=7) return;
		
		ImageIcon i = new ImageIcon(
				CARDS_IMAGES_PATH +
				Card.toString(c.getNumber()) + "of" +
				Card.toString(c.getSuit()) + ".gif");
		
		cardLabels[nextCard++].setIcon( i );

	}
	
	public void reset() {
		for (int i=0; i<cardLabels.length; i++) {
			cardLabels[i].setIcon( nullImage );
		}
		
		nextCard = 0;
	}
}
