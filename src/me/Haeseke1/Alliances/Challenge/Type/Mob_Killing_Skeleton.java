package me.Haeseke1.Alliances.Challenge.Type;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import me.Haeseke1.Alliances.Challenge.Challenge;
import me.Haeseke1.Alliances.Challenge.challengeType;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Mob_Killing_Skeleton implements Listener{
	
	
	@EventHandler
	private void onEntityKill(EntityDeathEvent event){
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Mob_Killing_Skeleton)){
				EntityDamageEvent devent = event.getEntity().getLastDamageCause();
				if(devent.getCause().equals(DamageCause.ENTITY_ATTACK)){
					if(event.getEntity().getKiller() != null && event.getEntity().getType().equals(EntityType.SKELETON)){
						Player player = event.getEntity().getKiller();
						if(!challenge.done.contains(player.getUniqueId())){
							if(challenge.points.containsKey(player.getUniqueId())){
								challenge.points.replace(player.getUniqueId(), challenge.points.get(player.getUniqueId()) + 1);
								if(challenge.points.get(player.getUniqueId()) >= challenge.max_Points){
									Coins.addPlayerCoins(player, challenge.reward);
									String message = MessageManager.getMessage("Challenges_Get_Reward");
									message = message.replace("%challenge_name", challenge.name)
											.replace("%reward_coin%", "" + challenge.reward);
									MessageManager.sendMessage(player, message);
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
	}

}
