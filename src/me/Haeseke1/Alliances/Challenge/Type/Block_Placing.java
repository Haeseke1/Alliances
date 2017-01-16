package me.Haeseke1.Alliances.Challenge.Type;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.Haeseke1.Alliances.Challenge.Challenge;
import me.Haeseke1.Alliances.Challenge.challengeType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Block_Placing implements Listener{
	
	@EventHandler
	private void blockPlace(BlockPlaceEvent event){
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Block_Placing)){
				Player player = event.getPlayer();
				if(!challenge.done.contains(player.getUniqueId())){
					if(challenge.points.containsKey(player.getUniqueId())){
						challenge.points.replace(player.getUniqueId(), challenge.points.get(player.getUniqueId()) + 1);
						if(challenge.points.get(player.getUniqueId()) >= challenge.max_Points){
							Coins.addPlayerCoins(player, challenge.reward);
							MessageManager.sendMessage(player, "Challenge " + challenge.name + " is completed, your reward is " + challenge.reward + " coins!");
							challenge.done.add(player.getUniqueId());
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
