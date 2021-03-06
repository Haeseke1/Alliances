package me.Haeseke1.Alliances.Exceptions;


@SuppressWarnings("serial")
public class EmptyBooleanException extends Exception{
	/*
	 * Throws an exception when the method can't find a string in a config file
	 */
	public EmptyBooleanException(String path) {
		super("Couldn't load Boolean in config. Path:" + path);
	}
}
