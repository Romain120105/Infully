package fr.romain120105.infully.server.utils;

public class Validate {

	public static void isTrue(boolean b, String string) {
		if (!b) {
			new Exception(string).printStackTrace();
		}
	}

}
