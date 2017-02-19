package me.Haeseke1.Alliances.Item.Weapons.Wands.Events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Cloud_Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Fire_Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Healing_Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Wither_Wand;

public class RightClickWand implements Listener{

	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
	    Player player = event.getPlayer();
	    Fire_Wand fire_wand = new Fire_Wand("Fire_Wand",player,Material.STICK,2);
	    fire_wand.launchProjectile(Sound.GHAST_FIREBALL);
	    Wither_Wand wither_wand = new Wither_Wand("Wither_Wand",player,Material.STICK,5);
	    wither_wand.launchProjectile(Sound.WITHER_HURT);
	    Cloud_Wand cloud_wand = new Cloud_Wand("Cloud Wand",player,Material.STICK,15);
	    cloud_wand.launchPlayer(Sound.BAT_TAKEOFF);
	    if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
	    Healing_Wand healing_wand = new Healing_Wand("Healing_Wand",player,Material.STICK,10);
	    healing_wand.spawnTotem(player.getLocation().getBlock());
	    }
	}
	
}
