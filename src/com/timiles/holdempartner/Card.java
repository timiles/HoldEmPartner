package com.timiles.holdempartner;
/*
 * Created on 21-Oct-2004
 *
 */
/**
 * @author default
 *
 */
class Card {

	// relies on consecutive values in this order
	public static final int DIAMONDS = 100;
	public static final int CLUBS = 101;
	public static final int HEARTS = 102;
	public static final int SPADES = 103;
	
	// relies on consecutive values in this order
	public static final int TWO = 200;
	public static final int THREE = 201;
	public static final int FOUR = 202;
	public static final int FIVE = 203;
	public static final int SIX = 204;
	public static final int SEVEN = 205;
	public static final int EIGHT = 206;
	public static final int NINE = 207;
	public static final int TEN = 208;
	public static final int JACK = 209;
	public static final int QUEEN = 210;
	public static final int KING = 211;
	public static final int ACE = 212;


	private int number;
	private int suit;

	/*
	 * provides information for image filenames
	 */
	public static String toString( int i ) {
		switch(i) {
		case DIAMONDS : return "diamonds"; 
		case CLUBS : return "clubs";
		case HEARTS : return "hearts";
		case SPADES : return "spades";
		
		case TWO : return "2";
		case THREE : return "3";
		case FOUR : return "4";
		case FIVE : return "5";
		case SIX : return "6";
		case SEVEN : return "7";
		case EIGHT : return "8";
		case NINE : return "9";
		case TEN : return "10";
		case JACK : return "jack";
		case QUEEN : return "queen";
		case KING : return "king";
		case ACE : return "ace";
		default : return "ERROR";
		}
	}
	
	public Card(int number, int suit)
	{
		this.number = number;
		this.suit = suit;
	}

	public int getNumber() {
		return number;
	}

	public int getSuit() {
		return suit;
	}

	public boolean isAce() {
		return (number==ACE);
	}

	public boolean isPicture() {
		return (number>TEN);
	}

	public static boolean isValidSuit(int i) {
		return (i==Card.DIAMONDS ||
				i==Card.CLUBS ||
				i==Card.HEARTS ||
				i==Card.SPADES );
	}
	public static boolean isValidNumber(int i) {
		return (i==Card.TWO ||
				i==Card.THREE ||
				i==Card.FOUR ||
				i==Card.FIVE ||
				i==Card.SIX ||
				i==Card.SEVEN ||
				i==Card.EIGHT ||
				i==Card.NINE ||
				i==Card.TEN ||
				i==Card.JACK ||
				i==Card.QUEEN ||
				i==Card.KING ||
				i==Card.ACE	);
	}

}
