package me.Haeseke1.Alliances.WorldGuard.Regions;

import me.Haeseke1.Alliances.Exceptions.EmptyBooleanException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Settings {

	public static boolean getDefaultSetting(Setting setting){
		if(Setting.getName(setting) != null){
			try {
				return ConfigManager.getBooleanFromConfig(Main.WorldGuardConfig, Setting.getName(setting));
			} catch (EmptyBooleanException e) {
				MessageManager.sendAlertMessage("This boolean doesn't exists: &6" + setting.name());
			}
		}
		
		return false;
	}
	
	public static void startUp(){
		for(Setting setting: Setting.values()){
			MessageManager.sendInfoMessage(Setting.getName(setting) + " &2has been set to &e" + Settings.getDefaultSetting(setting));
		}
	}
	
}
