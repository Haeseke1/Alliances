package me.Haeseke1.Alliances.Challenge.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

public class Mob_Killing_Time implements Listener,Runnable{
	
	
	public static HashMap<Player,Integer> timer = new HashMap<Player,Integer>();
	
	@EventHandler
	private void onEntityKill(EntityDeathEvent event){
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Mob_Killing_Time)){
				EntityDamageEvent devent = event.getEntity().getLastDamageCause();
				if(devent.getCause().equals(DamageCause.ENTITY_ATTACK)){
					if(event.getEntity().getKiller() != null){
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
								timer.put(player, 0);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void run() {
		for(Challenge challenge : Challenge.challenges){
			if(challenge.type.equals(challengeType.Mob_Killing_Time)){
				List<Player> players = new ArrayList<Player>();
				for(Entry<Player,Integer> entry : timer.entrySet()){
					if(entry.getValue() >= challenge.timer){
						players.add(entry.getKey());
					}
					entry.setValue(entry.getValue() + 1);
				}
				for(Player player : players){
					timer.remove(player);
					challenge.points.remove(player.getUniqueId());
				}
			}
		}
	}
}


