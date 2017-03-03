package com.timiles.holdempartner;
/*
 * Created on 21-Oct-2004
 *
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;

/**
 * @author default
 *
 */
class Display extends JPanel {

	private ProbabilityDisplay[] probDisplays;
	private ProbabilityOptions probOptions;
	private JPanel probs;
	
	private static final int DISPLAY_TOP_ = 8;
	private static final String YOUR_PROBS =
		"Probabilities of you achieving:";
	private static final String YOU_HAVE =
		"Your Hand:";
	private static final String THEIR_PROBS =
		"Probabilities of being beaten by:";

	public Display() {

		setLayout( new BorderLayout() );

		probOptions = new ProbabilityOptions() {
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				
				if (source == hideCheckBox) {
					Display.this.hide(hideCheckBox.isSelected());
				}
				else for (int i=0; i<formatRadios.length; i++) {
					if (source == formatRadios[i]) {
						formatRadioSelected(i);
					}
				}
				
				updateAllDisplays();
			}

			public void stateChanged(ChangeEvent e) {
				ProbabilityDisplay.setDecimalPlaces(
					((Integer)numOfDecimalPlacesSpinner.getValue()).intValue());
				
				updateAllDisplays();
			}
		};
		
		probs = new JPanel();
		probs.setLayout( new GridLayout(2,(int)Math.ceil(DISPLAY_TOP_/2.0)) );
		probs.setBorder( new TitledBorder(YOUR_PROBS) );

		probDisplays = new ProbabilityDisplay[DISPLAY_TOP_];

		for (int i=0; i<DISPLAY_TOP_ ; i++) {
			probDisplays[i] = new ProbabilityDisplay(
					Hand.toString(getHand(i)));
			probs.add(probDisplays[i]);
		}

		add(probOptions, BorderLayout.WEST);
		add(probs, BorderLayout.CENTER);
	}
	
	public void reset() {
		probs.setBorder( new TitledBorder(YOUR_PROBS) );
		for (int i=0; i<DISPLAY_TOP_ ; i++) {
			probDisplays[i].reset();
		}
	}
	
	public void hide(boolean hidden) {
		ProbabilityDisplay.setHidden(hidden);
	}
	
	public void setProbabilities( Probability[] probs ) {
		for (int i=0; i<probs.length; i++)
		probDisplays[getDisplay(probs[i].hand)]
					 .setProbability(probs[i].probability);
	}
	
	private void updateAllDisplays() {
		for (int i=0; i<DISPLAY_TOP_; i++) {
			probDisplays[i].updateProbabilityDisplay();
		}
	}
	
	public void setAllDisplaysEnabled(boolean enabled) {
		for (int i=0; i<DISPLAY_TOP_; i++) {
			probDisplays[i].setProbabilityEnabled(enabled);
		}
	}
	
	private int getHand(int i) {
		switch(i) {
		case 0 : return Hand.STRAIGHT_FLUSH;
		case 1 : return Hand.FOUR_OF_A_KIND;
		case 2 : return Hand.FULL_HOUSE;
		case 3 : return Hand.FLUSH;
		case 4 : return Hand.STRAIGHT;
		case 5 : return Hand.THREE_OF_A_KIND;
		case 6 : return Hand.TWO_PAIR;
		case 7 : return Hand.ONE_PAIR;
		case 8 : return Hand.HIGH_CARD;
		default : System.out.println("ERROR : Display.getHand("+i+")");
			return -1;
		}
	}
	
	private int getDisplay(int i) {
		switch(i) {
		case Hand.STRAIGHT_FLUSH : return 0;
		case Hand.FOUR_OF_A_KIND : return 1;
		case Hand.FULL_HOUSE : return 2;
		case Hand.FLUSH : return 3;
		case Hand.STRAIGHT : return 4;
		case Hand.THREE_OF_A_KIND : return 5;
		case Hand.TWO_PAIR : return 6;
		case Hand.ONE_PAIR : return 7;
		case Hand.HIGH_CARD : return 8;
		default : System.out.println("ERROR : Display.getDisplay("+i+")");
			return -1;
		}
	}
	
	public void setComplete(String yourHand) {
		probs.setBorder( new TitledBorder(
				YOU_HAVE+" "+yourHand+". "+THEIR_PROBS) );
		for (int j=0; j<DISPLAY_TOP_; j++) {
			probDisplays[j].reset();
		}

	}
}
