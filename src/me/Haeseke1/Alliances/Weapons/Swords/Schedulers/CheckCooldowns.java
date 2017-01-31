package me.Haeseke1.Alliances.Weapons.Swords.Schedulers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.SoundManager;
import me.Haeseke1.Alliances.Weapons.Swords.Events.RightClickSword;

public class CheckCooldowns extends RightClickSword implements Runnable{

	private int cooldown;
	
	@Override
	public void run() {
      for(Player player: Bukkit.getOnlinePlayers()){
    	    if(sword == null){
    	    	continue;
    	    }
    		if(sword.name == null){
    			continue;
    		}
			Class<?> SwordClass;
			try {
				SwordClass = Class.forName("me.Haeseke1.Alliances.Weapons.Swords.Type." + sword.name, false, this.getClass().getClassLoader());
			try {
				@SuppressWarnings("unchecked")
				HashMap<Player,Integer> cooldown = (HashMap<Player, Integer>) SwordClass.getDeclaredField("cooldown").get(HashMap.class);
				if(cooldown.containsKey(player)){
			    	this.cooldown = cooldown.get(player);
			    	if(sword.effect.equalsIgnoreCase("invisible")){
			    	  if(cooldown.get(sword.player) == 10){
			    		  for(Player onlinePlayer: Bukkit.getOnlinePlayers()){
			    			  onlinePlayer.showPlayer(player);
			    		  }
			    	  }
			    	}
			    
			    	if(this.cooldown == 0){
			    		cooldown.remove(player);
				    	SoundManager.playSoundToPlayer(Sound.NOTE_PLING, player);
			    	}else{
			    		cooldown.remove(player);
			    		cooldown.put(player, this.cooldown - 1);
			    	}
			    }
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			}
	} catch (ClassNotFoundException e1) {
			}
      }
	}
  }

