/*
 * Created on 22-Oct-2004
 *
 */

package com.timiles.holdempartner.myUtils;

/**
 * @author default
 *
 */
public class MyMath {

	public static int nCr(int n, int r) {
		if (n<r) {
			System.out.println("n less than r");
			return -1; // throw exception
		}
		
		return MyMath.factorial(n)/
			( MyMath.factorial(n-r) * MyMath.factorial(r) );
	}
	
	public static int factorial(int x) {
		if (x<=0) return 1;
		
		int product = 1;
		while (x>0) product *= x--;
		
		return product;
	}
}
