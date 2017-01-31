package me.Haeseke1.Alliances.Weapons.Swords.Events;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Haeseke1.Alliances.Weapons.Swords.Sword;

public class RightClickSword implements Listener{
	
	public static Player player;
	public static Sword sword;
	
	public static HashMap<Player,Sword> swords = new HashMap<>();
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	    player = event.getPlayer();
	    if(player.getItemInHand() == null){return;}
		if(event.getAction() != Action.RIGHT_CLICK_AIR || event.getAction() != Action.RIGHT_CLICK_BLOCK){return;}
        sword = new Sword("Night Blade", player, 120, "INVISIBLE");
	    sword.giveEffect(10, 1,Sound.ENDERDRAGON_WINGS,true);
	}
	
}
