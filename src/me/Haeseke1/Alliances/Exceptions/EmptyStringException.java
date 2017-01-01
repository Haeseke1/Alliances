package me.Haeseke1.Alliances.Exceptions;

@SuppressWarnings("serial")
public class EmptyStringException extends Exception{
	
	public EmptyStringException(String path){
		super("Couldn't load String in config. Path:" + path);
	}

}
