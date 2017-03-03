package com.timiles.holdempartner;
/*
 * Created on 22-Oct-2004
 *
 */
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * @author default
 *
 */
public abstract class ProbabilityOptions extends JPanel
  implements ActionListener, ChangeListener {

	private static final int INIT_DECIMAL_PLACES = 2;
	private static final String TITLE = "Prob Opts";
	private static final String HIDE = "hide";
	private static final String DECIMAL_PLACES = "dps";
	
	protected JRadioButton[] formatRadios;
	private ButtonGroup buttonGroup;
	protected JCheckBox hideCheckBox;
	private JPanel numOfDecimalPlaces;
	protected JSpinner numOfDecimalPlacesSpinner;

	public ProbabilityOptions() {
		setLayout( new GridLayout(5,1) );
		setBorder( new TitledBorder(TITLE) );
		
		formatRadios = new JRadioButton[3];

		buttonGroup = new ButtonGroup();
		for (int i=0; i<3; i++) {
			formatRadios[i] = new JRadioButton(
					ProbabilityDisplay.toString(
							getFormat(i)));
			formatRadios[i].addActionListener(this);
			add(formatRadios[i]);
			buttonGroup.add(formatRadios[i]);
		}
		
		hideCheckBox = new JCheckBox(HIDE, false);
		hideCheckBox.addActionListener(this);
		add(hideCheckBox);
		
		SpinnerNumberModel numOfDecimalPlacesModel = new SpinnerNumberModel(
				INIT_DECIMAL_PLACES, // initial value
				0, // min
				16, // max
				1); // increments
		ProbabilityDisplay.setDecimalPlaces(INIT_DECIMAL_PLACES);
		numOfDecimalPlacesSpinner = new JSpinner(numOfDecimalPlacesModel);
		numOfDecimalPlacesSpinner.addChangeListener(this);
		
		numOfDecimalPlaces = new JPanel();
		numOfDecimalPlaces.setLayout(new FlowLayout());
		numOfDecimalPlaces.add( new JLabel(DECIMAL_PLACES) );
		numOfDecimalPlaces.add( numOfDecimalPlacesSpinner );
		add(numOfDecimalPlaces);
		
		
		formatRadios[0].setSelected(true);
		formatRadioSelected(0);
	}
	
	public void formatRadioSelected(int i) {
		ProbabilityDisplay.setFormat( getFormat(i) );
	}
	
	private int getFormat(int i) {
		switch(i) {
		case 0 : return ProbabilityDisplay.DECIMAL;
		case 1 : return ProbabilityDisplay.FRACTION;
		case 2 : return ProbabilityDisplay.PERCENT;
		default : System.out.println("ERROR"); return -1;
		}
	}
	
	
}
