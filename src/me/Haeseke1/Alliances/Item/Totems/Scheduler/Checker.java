package me.Haeseke1.Alliances.Item.Totems.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Item.Totems.HealingTotem;
import me.Haeseke1.Alliances.Utils.ParticleManager;
import net.minecraft.server.v1_8_R2.EnumParticle;

public class Checker implements Runnable{

	int ticks = 0;
	int MAX_TICKS = 40;
	
	@Override
	public void run() {
		ticks++;
        for(Player player: Bukkit.getOnlinePlayers()){
        	player.setDisplayName(player.getName());
        	if(HealingTotem.htotems.containsKey(player.getUniqueId())){
        		if(ticks == MAX_TICKS){
        			ticks = 0;
        			HealingTotem.htotems.get(player.getUniqueId()).heal(player);
        		}
        		ParticleManager.playParticle(EnumParticle.VILLAGER_HAPPY, HealingTotem.htotems.get(player.getUniqueId()).block.getLocation(), 2, 1);
        		if(player.getLocation().distance(HealingTotem.htotems.get(player.getUniqueId()).block.getLocation()) > 20){
        			HealingTotem.htotems.get(player.getUniqueId()).removeTotem();
        		}
        	}
        }
	}

}
