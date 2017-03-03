package com.timiles.holdempartner;
/*
 * Created on 24-Oct-2004
 *
 */
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * @author default
 *
 */
class SplashScreen {

	private static final String TITLE =
		"Beta Version";

	private static final String COPYRIGHT =
		"© Tim Iles 2004";
	private static final String WEBSITE =
		"https://github.com/timiles/HoldEmPartner";

	private static final String MESSAGE = COPYRIGHT+", "+WEBSITE;

	private static final String ICON_FILENAME =
		"images/icons/splash_icon.gif";


	public static void show( Component owner ) {

		JOptionPane.showMessageDialog(
				owner, // Component parentComponent
				MESSAGE, // Object message
				TITLE, // String title
				JOptionPane.INFORMATION_MESSAGE, // int messageType
				new ImageIcon(ICON_FILENAME) ); // Icon icon
	}

}
