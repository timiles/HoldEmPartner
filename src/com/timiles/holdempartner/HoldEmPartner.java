package com.timiles.holdempartner;
/*
 * Created on 21-Oct-2004
 */
import java.applet.Applet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author default
 */
public class HoldEmPartner extends Applet
  implements ActionListener {

	private static final String TITLE =
		"Texas Hold 'em Partner!";
	
	private static final String ICON_FILENAME =
		"images/icons/small_icon.gif";

	private CardsButtons cardsButtons;
	private CardsDisplay cardsDisplay;
	private Display display;
	private JButton resetButton;
	private OpponentsCounter opponentsCounter;

	private Hand hand;
	
	private static boolean isRunningAsApplet = true;
	
	
	public static void main(String[] args) {
		
		isRunningAsApplet = false;
		
		JFrame jf = new JFrame(TITLE);
		final HoldEmPartner hep = new HoldEmPartner();
		hep.init();

	    // Successful exit on window close events
	    jf.addWindowListener( new WindowAdapter()
			{
				public void windowClosing( WindowEvent e )
				{
					hep.destroy();
					System.exit(0);
				}
            });
		
	    ImageIcon icon = new ImageIcon(ICON_FILENAME);
		jf.setIconImage(icon.getImage());
		jf.setResizable(false);
		jf.getContentPane().add(hep);
		jf.pack();
		
		// centre on screen
		Dimension screenSize =
			Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize =
			jf.getContentPane().getPreferredSize(); 
		jf.setLocation( (int) (screenSize.getWidth() - frameSize.getWidth())/2, 
				(int) (screenSize.getHeight() - frameSize.getHeight())/2);

		jf.show();
		SplashScreen.show(jf);
	}
	
	public void init() {

		hand = new Hand();

		cardsButtons = new CardsButtons() {
			public void actionPerformed(ActionEvent e) {
				if (hand.isComplete()) return;
				Card c = null;
				if (e.getSource() == dealRandom) c = dealRandom();
				else c = getSelected(e);
				cardsDisplay.setCard(c);
				hand.addCard(c);
				updateDisplay();
				
			}
		};
		
		cardsDisplay = new CardsDisplay();

		display = new Display();

		opponentsCounter = new OpponentsCounter() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(fold)) {
					fold();
				}
			}
			
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.SELECTED &&
						e.getSource().equals(counter)) {
					comboChange();
					if (hand.isComplete()) updateDisplay();
				}		
			}
		};
		resetButton = new JButton("Reset");
		
		JPanel buttonsPanel = new JPanel( new BorderLayout() );
		buttonsPanel.add(opponentsCounter, BorderLayout.CENTER);
		buttonsPanel.add(resetButton, BorderLayout.SOUTH);
		
		this.setLayout( new BorderLayout() );
		this.add(cardsButtons, BorderLayout.NORTH);
		this.add(cardsDisplay, BorderLayout.CENTER);
		this.add(buttonsPanel, BorderLayout.WEST);
		this.add(display, BorderLayout.SOUTH);
		
		resetButton.addActionListener(this);
		
		if (isRunningAsApplet) SplashScreen.show(this);
	}
	
	public void start() {

	}
	
	private void reset() {
		cardsButtons.reset();
		cardsDisplay.reset();
		display.reset();
		opponentsCounter.reset();
		
		hand = new Hand(); // is this the best way to reset hand??
	}

	public void actionPerformed(ActionEvent e) {
		
		if ( e.getSource().equals(resetButton) )
			reset();
	}
	
	private void updateDisplay() {
		if ( hand.isComplete() ) {
			display.setComplete(Hand.toString(hand.getBestHand()));
		}
		if ( hand.requiresUpdate() ) {
			display.setAllDisplaysEnabled(true);
			display.setProbabilities( hand.getProbabilities() );
		}
		else {
			display.setAllDisplaysEnabled(false);
		}
	}
	
}
