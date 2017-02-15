package me.Haeseke1.Alliances.Item.Totems.Scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Item.Totems.HealingTotem;
import me.Haeseke1.Alliances.Utils.ParticleManager;
import net.minecraft.server.v1_8_R2.EnumParticle;

public class Checker implements Runnable{

	@Override
	public void run() {
        for(Player player: Bukkit.getOnlinePlayers()){
        	if(HealingTotem.htotems.containsKey(player.getUniqueId())){
        		ParticleManager.playParticle(EnumParticle.VILLAGER_HAPPY, HealingTotem.htotems.get(player.getUniqueId()).block.getLocation(), 2, 1);
        		if(player.getLocation().distance(HealingTotem.htotems.get(player.getUniqueId()).block.getLocation()) > 20){
        			HealingTotem.htotems.get(player.getUniqueId()).removeTotem();
        		}
        	}
        }
	}

}
