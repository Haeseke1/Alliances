package me.Haeseke1.Alliances.Challenge.Type;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Challenge.Challenge;
import me.Haeseke1.Alliances.Challenge.challengeType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Time_On implements Runnable{

	@Override
	public void run() {
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Time_On)){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(!challenge.done.contains(player.getUniqueId())){
						if(challenge.points.containsKey(player.getUniqueId())){
							challenge.points.replace(player.getUniqueId(), challenge.points.get(player.getUniqueId()) + 1);
							if(challenge.max_Points <= challenge.points.get(player.getUniqueId())){
								challenge.done.add(player.getUniqueId());
								Coins.addPlayerCoins(player, challenge.reward);
								MessageManager.sendMessage(player, "&6You've completed the &l" + challenge.name + "&6 challenge (&c+" + challenge.reward + "&6coins)");
								return;
							}
						}else{
							challenge.points.put(player.getUniqueId(), (double) 1);
						}
					}
				}
			}
		}
	}
	
	
	
	
}
