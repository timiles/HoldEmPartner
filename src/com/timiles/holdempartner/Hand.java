package com.timiles.holdempartner;
/*
 * Created on 08-Jul-2004
 *
 */
import java.util.Vector;

/**
 * @author default
 *
 */
class Hand {

	/**
	 * must be consecutive ints in lower to higher order.
	 */
	public static final int HIGH_CARD = 300;
	public static final int ONE_PAIR = 301;
	public static final int TWO_PAIR = 302;
	public static final int THREE_OF_A_KIND = 303;
	public static final int STRAIGHT = 304;
	public static final int FLUSH = 305;
	public static final int FULL_HOUSE = 306;
	public static final int FOUR_OF_A_KIND = 307;
	public static final int STRAIGHT_FLUSH = 308;
	
	private Vector cards;
	private ProbabilityFormulae probabilityFormulae;
	
	public static String toString(int hand) {
		switch(hand) {
		case HIGH_CARD : return "High Card";
		case ONE_PAIR : return "One Pair";
		case TWO_PAIR : return "Two Pair";
		case THREE_OF_A_KIND : return "Three of a kind";
		case STRAIGHT : return "Straight";
		case FLUSH : return "Flush";
		case FULL_HOUSE : return "Full House";
		case FOUR_OF_A_KIND : return "Four of a kind";
		case STRAIGHT_FLUSH : return "Straight Flush";
		default : return "ERROR: Hand.toString()";
		}
	}
	
	public static boolean isValid(int hand) {
		switch(hand) {
		case HIGH_CARD : // fall through
		case ONE_PAIR : // fall through
		case TWO_PAIR : // fall through
		case THREE_OF_A_KIND : // fall through
		case STRAIGHT : // fall through
		case FLUSH : // fall through
		case FULL_HOUSE : // fall through
		case FOUR_OF_A_KIND : // fall through
		case STRAIGHT_FLUSH : return true;
		default : return false;
		}
		
	}
	

	public Hand() {
		cards = new Vector(7,0);
		probabilityFormulae = new YourProbabilityFormulae(cards);
	}
	
	public void addCard(Card c) {
		if (cards.size() < 7) {
			cards.addElement(c);
		}
	}
	
	public boolean requiresUpdate() {
		return (cards.size()==2 || cards.size()>=5 );
	}
	
	public boolean isComplete() {
		return (cards.size()==7);
	}
	
	public int getBestHand() {
		
		ProbabilityFormulae probs = new YourProbabilityFormulae(cards);
		Probability p;

		for (int hand = Hand.STRAIGHT_FLUSH; hand>=Hand.HIGH_CARD; hand--) {
			p = calculate(hand, probs);
			if (p.probability == 1.0) return p.hand;
		}
		
		System.out.println("ERROR: Hand.getBestHand()");
		return -1;
	}

	
	public Probability[] getProbabilities() {
		
		Probability[] probs = new Probability[8];
		int calculateTop_ = probs.length;

		if (isComplete()) {
			int bestHand = this.getBestHand();
			calculateTop_ = Hand.STRAIGHT_FLUSH - bestHand;

			probabilityFormulae =
				new TheirProbabilityFormulae(cards);
			probs[calculateTop_] =
				calculate( bestHand,
						new HigherHandProbabilityFormulae(cards) );
			for (int i=calculateTop_+1; i<probs.length; i++) {
				probs[i] = new Probability(Hand.STRAIGHT_FLUSH-i, 0.);
			}
		}

		// not necessary to be in order
		for (int i=0; i<calculateTop_; i++) {
			probs[i] = calculate(Hand.STRAIGHT_FLUSH-i, probabilityFormulae);
		}
		
		return probs;
	}
	
	private Probability calculate(int hand, ProbabilityFormulae probs) {
		
		switch(hand) {
		case Hand.STRAIGHT_FLUSH :
			return new Probability( Hand.STRAIGHT_FLUSH,
					probs.calcStraightFlush() );
		case Hand.FOUR_OF_A_KIND :
			return new Probability( Hand.FOUR_OF_A_KIND, 
					probs.calcXofaKind(4) );
		case Hand.FULL_HOUSE :
			return new Probability( Hand.FULL_HOUSE, 
					probs.calcFullHouse() );
		case Hand.FLUSH :
			return new Probability( Hand.FLUSH, 
					probs.calcFlush() );
		case Hand.STRAIGHT :
			return new Probability( Hand.STRAIGHT, 
					probs.calcStraight() );
		case Hand.THREE_OF_A_KIND :
			return new Probability( Hand.THREE_OF_A_KIND, 
					probs.calcXofaKind(3) );
		case Hand.TWO_PAIR :
			return new Probability( Hand.TWO_PAIR, 
					probs.calcTwoPair() );
		case Hand.ONE_PAIR :
			return new Probability( Hand.ONE_PAIR, 
					probs.calcXofaKind(2) );
		case Hand.HIGH_CARD : // must have at least high card
			return new Probability( Hand.HIGH_CARD, 1.0 );
		default :
			System.out.println("ERROR : Hand.calculate()");
			return null;
		}
		
	}

}
