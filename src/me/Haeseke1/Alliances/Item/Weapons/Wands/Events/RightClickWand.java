package me.Haeseke1.Alliances.Item.Weapons.Wands.Events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Fire_Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Healing_Wand;

public class RightClickWand implements Listener{

	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
	    Player player = event.getPlayer();
	    Fire_Wand fire_wand = new Fire_Wand(player,Material.STICK);
	    fire_wand.launchProjectile(Sound.GHAST_FIREBALL);
	    if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
	    Healing_Wand healing_wand = new Healing_Wand(player,Material.STICK);
	    healing_wand.spawnTotem(player.getLocation().getBlock());
	    }
	}
	
}
