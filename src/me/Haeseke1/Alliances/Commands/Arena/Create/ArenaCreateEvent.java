package me.Haeseke1.Alliances.Commands.Arena.Create;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Arena.Arena;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;

public class ArenaCreateEvent implements Listener{
	
	public static HashMap<Player, Location> spawn1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> spawn2 = new HashMap<Player, Location>();
	
	
	
	
	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		if(event.hasBlock() && event.hasItem()){
			if(event.getItem().hasItemMeta()){
				if(event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Arena selector")){
					if(!spawn1.containsKey(player) && mainCreate.createArena.containsKey(player)){
						spawn1.put(player, event.getClickedBlock().getLocation().add(0, 1, 0));
						MessageManager.sendInfoMessage(player, "Select second teamspawn!");
						return;
					}
					if(!spawn2.containsKey(player) && mainCreate.createArena.containsKey(player)){
						spawn2.put(player, event.getClickedBlock().getLocation().add(0, 1, 0));
						MessageManager.sendInfoMessage(player, "Select observation room spawn!");
						return;
					}
					if(mainCreate.createArena.containsKey(player)){
						player.setItemInHand(new ItemStack(Material.AIR));
						new Arena(mainCreate.createArena.get(player),regionSelect.leftClick.get(player), regionSelect.rightClick.get(player),spawn1.get(player), spawn2.get(player), event.getClickedBlock().getLocation().add(0, 1, 0));
						MessageManager.sendRemarkMessage(player, "Arena created!");
					}
				}
			}
		}
		
	}
	
	
}
