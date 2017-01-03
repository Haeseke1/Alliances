package me.Haeseke1.Alliances.Alliance;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Alliance;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AllianceManager {

	public static boolean createAlliance(String name, Player owner, allianceType type) {
		if (!playerIsInAlli(owner)) {
			if(!alliExist(name)){
				int mCoins = Coins.getPlayerCoins(owner);
				int mWins = 0;
				int mLoses = 0;
				Alliance alliance = new Alliance(name, owner, mWins, mLoses, mCoins, type);
				Main.alliances.add(alliance);
				alliance.getMembers().put(owner.getUniqueId(), "");
				return true;
			}
			MessageManager.sendAlertMessage(owner, "Alliance already exist.");
			return false;
		}
		MessageManager.sendAlertMessage(owner, "You're already in an alliance.");
		return false;
	}

	public static boolean playerIsInAlli(Player player) {
		for (Alliance alli : Main.alliances) {
			if (alli.getMembers().containsKey(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean alliExist(String name) {
		for (Alliance alli : Main.alliances) {
			if (alli.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
}
