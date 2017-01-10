package me.Haeseke1.Alliances.Challenge.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.Haeseke1.Alliances.Challenge.Challenge;
import me.Haeseke1.Alliances.Challenge.challengeType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Lost_Hearts implements Listener{
	
	@EventHandler
	private void playerDamage(EntityDamageEvent event){
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Lost_Hearts)){
				if(!(event.getEntity() instanceof Player)){
					return;
				}
				Player player = (Player) event.getEntity();
				if(!challenge.done.contains(player.getUniqueId())){
					if(challenge.points.containsKey(player.getUniqueId())){
						if(challenge.points.get(player.getUniqueId()) >= challenge.max_Points){
							Coins.addPlayerCoins(player, challenge.reward);
							MessageManager.sendInfoMessage(player, "Challenge " + challenge.name + " is completed, your reward is " + challenge.reward + " coins!");
							challenge.done.add(player.getUniqueId());
							return;
						}
						challenge.points.replace(player.getUniqueId(), challenge.points.get(player.getUniqueId()) + event.getDamage());
					}else{
						challenge.points.put(player.getUniqueId(), event.getDamage());
					}
				}
			}
		}
	}

}
