package me.Haeseke1.Alliances.Weapons.Swords.Events;

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
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	    player = event.getPlayer();
	    if(player.getItemInHand() == null){return;}
		if(event.getAction() != Action.RIGHT_CLICK_AIR){return;}
        sword = new Sword("Night Blade", player, 10, "INVISIBLE");
	    sword.giveEffect(5, 1,Sound.ENDERDRAGON_WINGS);
	}
	
}
