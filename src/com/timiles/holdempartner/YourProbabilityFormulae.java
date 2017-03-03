package com.timiles.holdempartner;
/*
 * Created on 23-Oct-2004
 *
 */
import java.util.Vector;
import com.timiles.holdempartner.myUtils.MyMath;

/**
 * @author default
 *
 */
class YourProbabilityFormulae extends ProbabilityFormulae {

	public YourProbabilityFormulae(Vector cards) {
		super(cards);
	}
	
	public double calcStraightFlush() {
		
		double sum = 0.;
		
		for (int suit=Card.DIAMONDS; suit<=Card.SPADES; suit++) {
			for (int number=Card.FIVE; number<=Card.ACE; number++) {
				int cardsInStraightFlush = 0;
				// account for low-ace straight!
				if (number==Card.FIVE) {
					if (contains(Card.ACE, suit, cards))
						cardsInStraightFlush++;
					for (int i=Card.TWO; i<=Card.FIVE; i++) {
						if (contains(i, suit, cards))
							cardsInStraightFlush++;
					}
				}
				else for (int i=number-4; i<=number; i++) {
					if (contains(i, suit, cards)) 
						cardsInStraightFlush++;
				}
				if (cardsInStraightFlush>=5) return 1.;
				final int CARDS_IN_STRAIGHT_FLUSH = cardsInStraightFlush;
				final int NEEDED = 5 - CARDS_IN_STRAIGHT_FLUSH;
				if (NEEDED+cards.size()>7) continue;
				
				double numerator = 1.;
				double denominator = 1.;
				
				for (int i=0; i<NEEDED; i++) {
					denominator *= (52-cards.size()-i);
				}

				sum += MyMath.nCr(7-cards.size(), NEEDED) * numerator/denominator;
			}
		}
		
		return sum;
	}
	
	public double calcFlush() {

		double sum = 0.;
		
		for (int suit=Card.DIAMONDS; suit<=Card.SPADES; suit++) {
			final int CARDS_IN_FLUSH = count(ANY_NUMBER, suit, cards);
			final int NEEDED = 5 - CARDS_IN_FLUSH;
			if (NEEDED+cards.size()>7) continue;
			if (NEEDED<=0) return 1.;
			
			double numerator = 1.;
			double denominator = 1.;
			for (int i=0; i<NEEDED; i++) {
				numerator *= (13-CARDS_IN_FLUSH-i);
				denominator *= (52-cards.size()-i);
			}
			
			sum += MyMath.nCr(7-cards.size(), NEEDED) * numerator/denominator;
		}
		
		return sum;
	}
	
	public double calcFullHouse() {
		// NOTES: disallow Four of a Kind *
		//        allow two 3s **
		//        allow 3,2,2. ***
		
		final int PAIRS = countXofaKind(2, cards);
		final int TRIPS = countXofaKind(3, cards);
		
		// ** detected here
		if (TRIPS == 1 && PAIRS >=1 ) return 1.;
		// *** detected here
		if (TRIPS == 2) return 1.;
		
		switch(cards.size()) {
		case 2 : {
			if (PAIRS==1) {
				// * must get >=2 different cards
				// 48/50 * 44/49 * 6/48 * 4/47 * 45/46
				return (2376./264845.);
		}
			else { // only 2 cards -> must have nothing special
				// * must get >=1 different card
				// 44/50 * 9/49 * 6/48 * (4/47 * 45/46 + 3/47 * 2/46)
				return (9207./5296900.);
			}
		}
		case 5 : {
			if (TRIPS==1) {
				// 6/47 * 45/46 + 40/47 * 3/46
				return (195./1081.);
			}
			switch(PAIRS) {
			case 2 : {
				// 4/47 * 45/46
				return (90./1081.);
			}
			case 1 : {
				// 9/47 * 4/46
				return (18./1081.);
			}
			case 0 : { // no trips either, already checked
				// impossible
				return 0.;
			}
			}
		}
		case 6 : {
			if (TRIPS==1) {
				// 6/46
				return (3./23.);
			}
			// trips==2 should be already detected
			switch (PAIRS) {
			case 3 : {
				// 6/46
				return (3./23.);
			}
			case 2 : {
				// 4/46
				return (2./23.);
			}
			case 1 : {
				// impossible
				return 0.;
			}
			case 0 : {
				// impossible
				return 0.;
			}
			}
		}
		case 7 : {
			// if reached here, impossible
			return 0.;
		}
		default : System.out.println("ERROR: YourProbabilityFormulae.calcFullHouse()");
			return -1;
		}
	}

	public double calcXofaKind(int x) {
		
		// 2 or 3 take into account achieving higher cards!! FIX!!!!
		
		
		double sum = 0.;
		
		for (int i=0; i<cards.size(); i++) {
			final int NUMBER = ((Card) cards.elementAt(i)).getNumber();
			final int NEEDED = x - count(NUMBER, ANY_SUIT, cards);
			if (NEEDED+cards.size()>7) continue;
			if (NEEDED<=0) return 1.;
			
			double numerator = MyMath.factorial(NEEDED);
			
			double denominator = 1.;
			
			for (int k=0; k<NEEDED; k++) {
				denominator *= (52-cards.size()-k);
			}
			
			sum += MyMath.nCr(7-cards.size(), NEEDED) * numerator/denominator;
		}
		
		return sum;
	}

	public double calcStraight() {
		
		double sum = 0.;
		
		for (int number=Card.FIVE; number<=Card.ACE; number++) {
			int cardsInStraight = 0;
			// account for low-ace straight!
			if (number==Card.FIVE) {
				if (contains(Card.ACE, ANY_SUIT, cards))
					cardsInStraight++;
				for (int i=Card.TWO; i<=Card.FIVE; i++) {
					if (contains(i, ANY_SUIT, cards))
						cardsInStraight++;
				}
			}
			else for (int i=number-4; i<=number; i++) {
				if (contains(i, ANY_SUIT, cards)) 
					cardsInStraight++;
			}
			if (cardsInStraight>=5) return 1.;
			final int CARDS_IN_STRAIGHT = cardsInStraight;
			final int NEEDED = 5 - CARDS_IN_STRAIGHT;
			if (NEEDED+cards.size()>7) continue;
			
			double numerator = Math.pow(4, NEEDED);
			
			double denominator = 1.;
			
			for (int i=0; i<NEEDED; i++) {
				denominator *= (52-cards.size()-i);
			}

			sum += MyMath.nCr(7-cards.size(), NEEDED) * numerator/denominator;
		}
		
		return sum;
		
	}

	public double calcTwoPair() {
		
		// disallow already having a triple!
		if (countXofaKind(3, cards)>0) return 0.;
		
		final int PAIRS = countXofaKind(2, cards);
		
		// allow for 3 pairs!
		if (PAIRS>=2) return 1.;

		switch(cards.size()) {
		case 2 : {
			if (PAIRS==0) {
				// 5 * (3/50 * 3/49 * 44/48 * 40/47 * 36/46)
				return (594./52969.);
			}
			if (PAIRS==1) {
				// 48/50 * 44/49 * 40/48 * 36/47 * 12/46
				return (38016./264845.);
			}
		}
		case 5 : {
			if (PAIRS==0) {
				// 15/47 * 12/46
				return (90./1081.);
			}
			if (PAIRS==1) {
				// 2 * (3 * 3/47 * 36/46) + 4 * (13-4)/47 * 3/46
				return (378./1081.);
			}
		}
		case 6 : {
			if (PAIRS==0) {
				// not possible
				return 0.;
			}
			if (PAIRS==1) {
				// 12/46 DEFINITELY CORRECT
				return (6./23.);
			}
		}
		case 7 : {
			// if reached here, impossible
			return 0.;
		}
		default : System.out.println("ERROR: YourProbabilityFormulae.calcTwoPair()");
			return -1;
		}

	}
	
}
