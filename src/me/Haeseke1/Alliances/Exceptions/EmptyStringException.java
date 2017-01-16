package me.Haeseke1.Alliances.Exceptions;

import me.Haeseke1.Alliances.Main.Main;

@SuppressWarnings("serial")
public class EmptyStringException extends Exception {
	/*
	 * Throws an exception when the method can't find a string in a config file
	 */
	public EmptyStringException(String path) {
		super("Couldn't load String in config. Path:" + path);
		Main.pm.disablePlugin(Main.plugin);
	}

}
