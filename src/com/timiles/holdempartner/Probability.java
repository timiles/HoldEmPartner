package com.timiles.holdempartner;
/*
 * Created on 22-Oct-2004
 *
 */
/**
 * @author default
 *
 */
public class Probability {

	public int hand;
	public double probability;
	
	public Probability(int hand, double probability) {
		
		if (!Hand.isValid(hand)) System.out.println(
				"ERROR : Probability() invalid hand="+hand);
		if (probability<0 || probability>1) System.out.println(
				"ERROR : Probability() invalid probability="+probability);
		
		this.hand = hand;
		this.probability = probability;
	}
}
