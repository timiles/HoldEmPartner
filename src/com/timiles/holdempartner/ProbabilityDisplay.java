package com.timiles.holdempartner;
/*
 * Created on 22-Oct-2004
 *
 */
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * @author default
 *
 */
public class ProbabilityDisplay extends JPanel {

	public static final int DECIMAL = 400;
	public static final int FRACTION = 401;
	public static final int PERCENT = 402;

	private static final String DEFAULT = "###";
	private static final String GUARANTEED = "guaranteed";
	private static final String IMPOSSIBLE = "impossible";

	private JLabel probability;
	private static int format;
	private static int decimalPlaces;
	private static boolean isHidden;
	
	private double prob;
	private String name;
	
	public ProbabilityDisplay( String name ) {
		this.name = name;
		this.setBorder( new TitledBorder(name) );
		this.probability = new JLabel(DEFAULT);
		setProbabilityEnabled(false);
		ProbabilityDisplay.format = DECIMAL;
		ProbabilityDisplay.decimalPlaces = 2;
		
		this.add(probability, SwingConstants.CENTER);
	}
	
	public static String toString( int formatType ) {
		switch(formatType) {
		case DECIMAL : return "decimal";
		case FRACTION : return "fraction";
		case PERCENT : return "percent";
		default : return "ERROR";
		}
	}
	
	public void reset() {
		this.probability.setText(DEFAULT);
		setProbabilityEnabled(false);
		this.setBorder( new TitledBorder(name) );
//		this.setBorder( new TitledBorder(
//				BorderFactory.createRaisedBevelBorder(),
//				name) );
		setVisible(true);
	}
	
	public void setProbability(double prob) {
		this.prob = prob;
		updateProbabilityDisplay();
	}
	
	public void setProbabilityEnabled(boolean enabled) {
		this.probability.setEnabled(enabled);
	}
	
	public void updateProbabilityDisplay() {
		String s = format(prob);
		if (s.equals(GUARANTEED)) {
			//this.probability.setEnabled(false);
//			this.setBorder( new TitledBorder(
//					BorderFactory.createLoweredBevelBorder(),
//					name ) );
//			this.probability.setForeground(Color.green);
		}
		if (s.equals(IMPOSSIBLE)) {
			setProbabilityEnabled(false);
		}
		this.probability.setText(s);
	}
	
	private String format(double prob) {
		if (isHidden) return "hidden";
		if (prob == 1.0) return GUARANTEED;
		if (prob == 0.0) return IMPOSSIBLE;
		
		switch(format) {
		case DECIMAL : return cutDecimalPlaces(prob);
		case FRACTION : return "1/"+cutDecimalPlaces(1.0/prob);
		case PERCENT : return cutDecimalPlaces(100*prob)+"%";
		default : return "ERROR";
		}
	}
	
	public static void setFormat( int f ) {
		ProbabilityDisplay.format = f;
	}
	
	public static void setDecimalPlaces( int dp ) {
		ProbabilityDisplay.decimalPlaces = dp;
	}
	
	public static void setHidden( boolean h ) {
		ProbabilityDisplay.isHidden = h;
	}
	
	/**
	 * cuts double to number of decimal places.
	 * technically, doesn't round. just cuts off.
	 *  
	 * @param d
	 * @return
	 */
	private String cutDecimalPlaces( double d ) {
		String s = Double.toString(d);

		int exp = s.indexOf("E");
		String end = (exp == -1) ? "" : s.substring(exp);
		
		int decimalPoint = s.indexOf(".");
		if (decimalPoint == -1) return s;
		
		int offset = (decimalPlaces==0) ? 0 : decimalPlaces+1;

		int i = (exp == -1)
			? Math.min(decimalPoint+offset, s.length())
			: Math.min(decimalPoint+offset, exp);
		return s.substring(0,i) + end;
	}

}
