package me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Time_Bomb_Wand;
import me.Haeseke1.Alliances.Utils.FireworkManager;
import net.md_5.bungee.api.ChatColor;

public class DelayedBomb extends BukkitRunnable{
	
	public int seconds;
	public float power;
	
	public Location explosionloc;
	
	public boolean firework;
	
	public ArmorStand stand;
	public Block bomb;
	
	public DelayedBomb(int seconds, float power, boolean firework, Block bomb, ArmorStand armors){
		this.explosionloc = bomb.getLocation();
		this.bomb = bomb;
		this.seconds = seconds;
		this.power = power;
		this.firework = firework;
	    this.stand = armors;
	}

	@Override
	public void run() {
		if(seconds > 1){
		stand.setCustomName(ChatColor.RED + "Bomb detonates in " + seconds + " seconds");
		}else if(seconds == 1){
		stand.setCustomName(ChatColor.RED + "Bomb detonates in " + seconds + " second");
		}else{
		stand.setCustomName(ChatColor.RED + "DETONATING...");
		}
		if(seconds == -1){
			Time_Bomb_Wand.blocks.remove(this.bomb);
			explosionloc.getWorld().createExplosion(explosionloc.getX(), explosionloc.getY(), explosionloc.getZ(), power, false, false);
			stand.remove();
			bomb.setType(Material.AIR);
			this.cancel();
		}
		seconds --;
		if(this.firework){
			FireworkManager.launchFirework(new Location(explosionloc.getWorld(),explosionloc.getX() + 0.5,explosionloc.getY(),explosionloc.getZ() + 0.5), Type.BALL_LARGE, Color.RED, 0, false);
		}
	}

}
