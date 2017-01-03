package me.Haeseke1.Alliances.Alliance;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Alliance;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AllianceManager {
 public static void createAlliance(Player owner){
	if(!playerIsInAlli(owner)){
	 int mCoins = Coins.getPlayerCoins(owner);
	 int mWins = 0;
	 int mLoses = 0;
	 Alliance alliance = new Alliance(owner,mWins,mLoses,mCoins);
	 Main.alliances.add(alliance);
	 alliance.getMembers().put(owner.getUniqueId(), "");
	 return;
	   }
	MessageManager.sendAlertMessage(owner, "You're already in an alliance.");
	return;
 }
 
 public static boolean playerIsInAlli(Player player){
	 for(Alliance alli: Main.alliances){
		 if(alli.getMembers().containsKey(player.getUniqueId())){
			 return true;
		 }
	 }
	 return false;
 }
}
