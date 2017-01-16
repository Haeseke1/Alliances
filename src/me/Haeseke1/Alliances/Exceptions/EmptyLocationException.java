package me.Haeseke1.Alliances.Exceptions;

import me.Haeseke1.Alliances.Main.Main;

@SuppressWarnings("serial")
public class EmptyLocationException extends Exception{
	
	/*
	 * Throws an exception when the method can't find a string in a config file
	 */
	public EmptyLocationException(String path) {
		super("Couldn't load ItemStack in config. Path:" + path);
		Main.pm.disablePlugin(Main.plugin);
	}
}
