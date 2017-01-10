package me.Haeseke1.Alliances.Challenge.Type;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.Haeseke1.Alliances.Challenge.Challenge;
import me.Haeseke1.Alliances.Challenge.challengeType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Fishing implements Listener{
	
	 @EventHandler
     public void playerFish(PlayerFishEvent e) {
		 if(e.getCaught() == null && !(e.getCaught() instanceof Item)){
			 return;
		 }
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Fishing)){
				Player player = (Player) e.getPlayer();
				if(!challenge.done.contains(player.getUniqueId())){
					if(challenge.points.containsKey(player.getUniqueId())){
						if(challenge.points.get(player.getUniqueId()) >= challenge.max_Points){
							Coins.addPlayerCoins(player, challenge.reward);
							MessageManager.sendInfoMessage(player, "Challenge " + challenge.name + " is completed, your reward is " + challenge.reward + " coins!");
							challenge.done.add(player.getUniqueId());
							return;
						}
						challenge.points.replace(player.getUniqueId(), challenge.points.get(player.getUniqueId()) + 1);
					}else{
						challenge.points.put(player.getUniqueId(), (double) 1);
					}
				}
			}
		}
	 }
	

}
