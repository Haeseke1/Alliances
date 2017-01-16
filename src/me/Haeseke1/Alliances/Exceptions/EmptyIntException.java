package me.Haeseke1.Alliances.Exceptions;


@SuppressWarnings("serial")
public class EmptyIntException extends Exception{
	/*
	 * Throws an exception when the method can't find a string in a config file
	 */
	public EmptyIntException(String path) {
		super("Couldn't load Int in config. Path:" + path);
	}
}
