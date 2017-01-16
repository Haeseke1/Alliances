package me.Haeseke1.Alliances.Exceptions;

import me.Haeseke1.Alliances.Main.Main;

@SuppressWarnings("serial")
public class InvalidConfigTypeException extends Exception {

	/*
	 * Throws an exception when a file isn't .yml
	 */
	public InvalidConfigTypeException(String filename) {
		super(filename + " isn't a valid config file type");
	}

}
