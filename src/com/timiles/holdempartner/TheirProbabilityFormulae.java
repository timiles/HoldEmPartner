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
class TheirProbabilityFormulae extends ProbabilityFormulae {

	private Vector yourCards;
	private Vector communityCards;
	
	public TheirProbabilityFormulae(Vector cards) {
		super(cards);

		yourCards = new Vector(2);
		communityCards = new Vector(5);
		
		for (int i=0; i<2; i++)
			yourCards.add(cards.elementAt(i));
		for (int i=2; i<7; i++)
			communityCards.add(cards.elementAt(i));
	}

	public double calcStraightFlush() {

		double sum = 0.;
		
		for (int suit=Card.DIAMONDS; suit<=Card.SPADES; suit++) {
			for (int number=Card.FIVE; number<=Card.ACE; number++) {
					
				int yourCardsInStraightFlush = 0;
				int communityCardsInStraightFlush = 0;

				// account for low-ace straight!
				if (number==Card.FIVE) {
					if (contains(Card.ACE, suit, yourCards)) {
						continue;
					}
					for (int i=Card.TWO; i<=Card.FIVE; i++) {
						if (contains(i, suit, yourCards)) {
							yourCardsInStraightFlush++;
							break;
						}
					}
					if (yourCardsInStraightFlush>0) continue;
					if (contains(Card.ACE, suit, communityCards))
						communityCardsInStraightFlush++;
					for (int i=Card.TWO; i<=Card.FIVE; i++) {
						if (contains(i, suit, communityCards))
							communityCardsInStraightFlush++;
					}
				}
				else for (int i=number-4; i<=number; i++) {
					if (contains(i, suit, yourCards)) {
						yourCardsInStraightFlush++;
						break;
					}
					if (contains(i, suit, communityCards)) {
						communityCardsInStraightFlush++;
					}
				}
				if (yourCardsInStraightFlush>0) continue;
				if (communityCardsInStraightFlush==5) return 1.;
				if (communityCardsInStraightFlush<3) continue;
				int needed = 5 - communityCardsInStraightFlush;
				
				double numerator = 1.;
				double denominator = 1.;
				
				for (int i=0; i<needed; i++) {
					denominator *= (45-i);
				}

				sum += MyMath.nCr(2,needed) * numerator/denominator;
			}
		}

		return opponentsCount * sum;
	}

	public double calcFlush() {
		
		double sum = 0.;
		
		for (int suit=Card.DIAMONDS; suit<Card.SPADES; suit++) {
			final int COMMUNITY_CARDS_IN_FLUSH =
				count( ANY_NUMBER, suit, communityCards );
			if (COMMUNITY_CARDS_IN_FLUSH==5) return 1.;
			if (COMMUNITY_CARDS_IN_FLUSH<3) continue;
			final int NEEDED = 5 - COMMUNITY_CARDS_IN_FLUSH;
			
			final int AVAILABLE_IN_THIS_SUIT =
				13 - COMMUNITY_CARDS_IN_FLUSH - 
				count( ANY_NUMBER, suit, yourCards );
			
			double numerator = 1.;
			double denominator = 1.;
			
			for (int i=0; i<NEEDED; i++) {
				numerator *= (AVAILABLE_IN_THIS_SUIT-i);
				denominator *= (45-i);
			}
			
			double opponentsFactor = 1.;
			
			final int AVAILABLE_NOT_IN_THIS_SUIT =
				45 - AVAILABLE_IN_THIS_SUIT;
			final int UNSEEN = 45 - NEEDED;
			
			for (int i=opponentsCount-2; i>=0; i--) {
				opponentsFactor *= ((double)(AVAILABLE_NOT_IN_THIS_SUIT-i))
									/((double) (UNSEEN-i));
				opponentsFactor += 1.0;
			}
System.out.println(Card.toString(suit)+" : "+opponentsFactor);
			
			sum += MyMath.nCr(2,NEEDED) *
				numerator/denominator *
				opponentsFactor;
		}
		
		return sum;
	}

	public double calcFullHouse() {
		return 0;
	}

	public double calcXofaKind(int x) {
		return 0;
	}

	public double calcStraight() {
		return 0;
	}

	public double calcTwoPair() {
		return 0;
	}

}
