package com.timiles.holdempartner;
/*
 * Created on 23-Oct-2004
 *
 */
import java.util.Vector;

/**
 * @author default
 *
 */
public abstract class ProbabilityFormulae {
	
	protected static final int ANY_NUMBER = 400;
	protected static final int ANY_SUIT = 401;
	
	protected Vector cards;
	protected static int opponentsCount;
	
	public ProbabilityFormulae( Vector cards ) {
		this.cards = cards;
	}

	public static void setOpponentsCount(int opponentsCount) {
		ProbabilityFormulae.opponentsCount = opponentsCount;
	}

	protected int count(int number, int suit, Vector cards) {
		int count=0;

		if (number == ANY_NUMBER) {
			for (int i=0; i<cards.size(); i++) {
				if (((Card) cards.elementAt(i)).getSuit() == suit)
					count++;
			}
			return count;
		}
		
		if (suit == ANY_SUIT) {
			for (int i=0; i<cards.size(); i++) {
				if (((Card) cards.elementAt(i)).getNumber() == number)
					count++;
			}
			return count;
		}

		
		for (int i=0; i<cards.size(); i++) {
			Card c = (Card) cards.elementAt(i);
			if (c.getNumber() == number &&
					c.getSuit() == suit)
				count++;
		}

		return count;
	}
	
	protected int countXofaKind(int x, Vector cards) {
		
		int count = 0;
		
		for (int i=0; i<cards.size(); i++) {
			Card c = (Card) cards.elementAt(i);
			if (count(c.getNumber(), ANY_SUIT, cards)==x)
				count++;
		}
		
		return count/x; // because it would have counted each e.g. pair twice
	}
	
	protected boolean contains(int number, int suit, Vector cards) {
		
		if (number == ANY_NUMBER) {
			for (int i=0; i<cards.size(); i++) {
				if (((Card) cards.elementAt(i)).getSuit() == suit)
					return true;
			}
		}
		
		if (suit == ANY_SUIT) {
			for (int i=0; i<cards.size(); i++) {
				if (((Card) cards.elementAt(i)).getNumber() == number)
					return true;
			}
		}

		for (int i=0; i<cards.size(); i++) {
			Card c = (Card) cards.elementAt(i);
			if (c.getNumber() == number &&
					c.getSuit() == suit)
				return true;
		}
		
		// else
		return false;
	}
	
	public abstract double calcStraightFlush();
	public abstract double calcFlush();
	public abstract double calcFullHouse();
	public abstract double calcXofaKind(int x);
	public abstract double calcStraight();
	public abstract double calcTwoPair();
}
