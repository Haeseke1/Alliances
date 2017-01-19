package me.Haeseke1.Alliances.Exp;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;

public class Exp {
	
	public static HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
	
	
	public static int getLevel(int exp){
		for(int i = 30; i >= 2; i--){
			if(table.get(i) <= exp){
				return i;
			}
		}
		return 1;
	}
	
	
	public static int addAllianceEXP(Alliance alli, int amount){
		alli.setExp(alli.getExp() + amount);
		return alli.getExp();
	}
	
	public static int addAllianceEXP(Player player, int amount){
		Alliance alli = AllianceManager.getAlliance(player);
		alli.setExp(alli.getExp() + amount);
		return alli.getExp();
	}
	
	public static int setAllianceEXP(Alliance alli, int amount){
		alli.setExp(amount);
		return alli.getExp();
	}
	
	public static int setAllianceEXP(Player player, int amount){
		Alliance alli = AllianceManager.getAlliance(player);
		alli.setExp(amount);
		return alli.getExp();
	}
	
	public static int removeAllianceEXP(Alliance alli, int amount){
		if(alli.getExp() - amount < 0){
			alli.setExp(0);
		}else{
			alli.setExp(alli.getExp() - amount);
		}
		return alli.getExp();
	}
	
	public static int removeAllianceEXP(Player player, int amount){
		Alliance alli = AllianceManager.getAlliance(player);
		if(alli.getExp() - amount < 0){
			alli.setExp(0);
		}else{
			alli.setExp(alli.getExp() - amount);
		}
		return alli.getExp();
	}
	
}
