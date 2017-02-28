package me.Haeseke1.Alliances.Main;

public class AutoSave implements Runnable{

	@Override
	public void run() {
		Main.saveAllCustomConfigs();
	}

}
