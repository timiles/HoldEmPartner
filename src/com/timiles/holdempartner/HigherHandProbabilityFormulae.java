package com.timiles.holdempartner;
/*
 * Created on 25-Oct-2004
 *
 */

import java.util.Vector;

/**
 * @author default
 * 
 * This class is for calculating the probability that you will be beaten
 * by a hand of equal status but higher rank, e.g. a higher pair.
 *
 */
class HigherHandProbabilityFormulae extends ProbabilityFormulae {

	private Vector yourCards;
	private Vector communityCards;

	public HigherHandProbabilityFormulae(Vector cards) {
		super(cards);

		yourCards = new Vector(2);
		communityCards = new Vector(5);
		
		for (int i=0; i<2; i++)
			yourCards.add(cards.elementAt(i));
		for (int i=2; i<7; i++)
			communityCards.add(cards.elementAt(i));
	}

	public double calcStraightFlush() {
		
		return 1;
	}

	public double calcFlush() {
		
		// compare each card until differs
		return 1;
	}

	public double calcFullHouse() {
		return 1;
	}

	public double calcXofaKind(int x) {
		return 1;
	}

	public double calcStraight() {
		return 1;
	}

	public double calcTwoPair() {
		return 1;
	}

}
