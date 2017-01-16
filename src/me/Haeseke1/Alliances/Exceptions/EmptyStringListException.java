package me.Haeseke1.Alliances.Exceptions;

import me.Haeseke1.Alliances.Main.Main;

@SuppressWarnings("serial")
public class EmptyStringListException extends Exception{
	/*
	 * Throws an exception when the method can't find a string in a config file
	 */
	public EmptyStringListException(String path) {
		super("Couldn't load StringList in config. Path:" + path);
	}
}
