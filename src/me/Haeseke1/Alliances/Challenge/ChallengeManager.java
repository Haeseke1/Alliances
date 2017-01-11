package me.Haeseke1.Alliances.Challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.bukkit.configuration.file.FileConfiguration;

import me.Haeseke1.Alliances.Exceptions.EmptyBooleanException;
import me.Haeseke1.Alliances.Exceptions.EmptyIntException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ConfigManager;

public class ChallengeManager extends TimerTask{
	
	public static void registerChallenges(){
		FileConfiguration file = Main.challengeConfig;
		for(challengeType type : challengeType.values()){
			try {
				Challenge.challengeName.put(type, ConfigManager.getStringFromConfig(file, "Player." + type + ".name"));
				Challenge.challengeMax_Points.put(type, ConfigManager.getIntFromConfig(file, "Player." + type + ".Points"));
				Challenge.challengeTimer.put(type, ConfigManager.getIntFromConfig(file, "Player." + type + ".Timer"));
				Challenge.challengeReward.put(type, ConfigManager.getIntFromConfig(file, "Player." + type + ".Reward"));
				Challenge.challengeEnabled.put(type, ConfigManager.getBooleanFromConfig(file, "Player." + type + ".enabled"));
			} catch (EmptyStringException e) {
				e.printStackTrace();
			} catch (EmptyIntException e) {
				e.printStackTrace();
			} catch (EmptyBooleanException e) {
				e.printStackTrace();
			}
		}
		List<challengeType> randomTypes = new ArrayList<challengeType>();
		while(randomTypes.size() != 3){
			challengeType ct = challengeType.randomtype();
			if(!randomTypes.contains(ct) && Challenge.challengeEnabled.get(ct)){
				randomTypes.add(ct);
			}
		}
		for(challengeType ct : randomTypes){
			new Challenge(Challenge.challengeName.get(ct), Challenge.challengeTimer.get(ct), Challenge.challengeMax_Points.get(ct), Challenge.challengeReward.get(ct), ct);
		}
	}

	@Override
	public void run() {
		Challenge.challenges.clear();
		List<challengeType> randomTypes = new ArrayList<challengeType>();
		while(randomTypes.size() != 3){
			challengeType ct = challengeType.randomtype();
			if(!randomTypes.contains(ct) && Challenge.challengeEnabled.get(ct)){
				randomTypes.add(ct);
			}
		}
		for(challengeType ct : randomTypes){
			new Challenge(Challenge.challengeName.get(ct), Challenge.challengeTimer.get(ct), Challenge.challengeMax_Points.get(ct), Challenge.challengeReward.get(ct), ct);
		}
	}
}
