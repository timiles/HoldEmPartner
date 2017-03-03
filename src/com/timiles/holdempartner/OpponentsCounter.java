package com.timiles.holdempartner;
/*
 * Created on 21-Oct-2004
 *
 */
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * @author default
 *
 */
abstract class OpponentsCounter extends JPanel
  implements ActionListener, ItemListener {

	// default value of 3 opponents
	private static final int INIT_SELECTED_DEFAULT = 3;
	// can theoretically deal in 23 players, including you.
	private static final int MAX_OPPONENTS = 22;
	// number of options to display in drop down box
	private static final int MAXIMUM_ROW_COUNT = 6;
	private static final String TITLE = "# opps";
	private static final String FOLD = "fold";

	private int initSelected;
	
	protected JComboBox counter;
	protected JButton fold;

	public OpponentsCounter() {
		setBorder( new TitledBorder(TITLE) );
		
		fold = new JButton(FOLD);
		
		Integer[] countOptions = new Integer[MAX_OPPONENTS];
		for (int i=0; i<MAX_OPPONENTS; i++) {
			countOptions[i] = new Integer(i+1);
		}
		counter = new JComboBox(countOptions);
		counter.setMaximumRowCount(MAXIMUM_ROW_COUNT);

		this.setLayout( new GridLayout(2,1) );
		this.add(counter);
		this.add(fold);
		
		counter.setSelectedIndex(INIT_SELECTED_DEFAULT);
			// would fire off itemStateChanged if registered already
		updateInitSelected();
		ProbabilityFormulae.setOpponentsCount(
				this.getOpponentsCount() );
		
		fold.addActionListener(this);
		counter.addItemListener(this);
	}

	public int getOpponentsCount() {
		return ((Integer) counter.getSelectedItem()).intValue();
	}
	
	public void reset() {
		if (initSelected<0 || initSelected>counter.getItemCount())
			initSelected = INIT_SELECTED_DEFAULT;
		counter.setSelectedIndex(initSelected);
		fold.setEnabled(true);
		ProbabilityFormulae.setOpponentsCount(getOpponentsCount());
	}
	
	protected void fold() {
		int selected = counter.getSelectedIndex();
		if (selected>0) counter.setSelectedIndex(selected-1);
			// fires off itemStateChanged
		// testFold();
		ProbabilityFormulae.setOpponentsCount(getOpponentsCount());
	}
	
	protected void comboChange() {
		updateInitSelected();
		testFold();
		ProbabilityFormulae.setOpponentsCount(getOpponentsCount());
	}
		
	protected void updateInitSelected() {
		initSelected = counter.getSelectedIndex();
	}
	
	protected void testFold() {
		if (counter.getSelectedIndex() == 0) fold.setEnabled(false);
		else fold.setEnabled(true);
	}

}
