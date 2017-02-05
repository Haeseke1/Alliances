package me.Haeseke1.Alliances.regionSelect;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Utils.MessageManager;


public class regionSelect implements Listener{
	
	public static HashMap<Player,Location> leftClick = new HashMap<Player,Location>();
	public static HashMap<Player,Location> rightClick = new HashMap<Player,Location>();
	

	
	@EventHandler
	private void playerInteract(PlayerInteractEvent event){
		if(!event.hasItem()){
			return;
		}
		if(!event.hasBlock()){
			return;
		}
		if(event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && 
				event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Region Selector")){
			Player player = event.getPlayer();
			event.setCancelled(true);
			if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
				Location loc = event.getClickedBlock().getLocation();
				leftClick.put(event.getPlayer(), loc);
				String message = "&2Set pos1 (&6%x%&2,&6%y%&2,&6%z%&2)";
				message = message.replace("%x%", "" + player.getLocation().getBlockX())
						.replace("%y%", "" + player.getLocation().getBlockY())
						.replace("%z%", "" + player.getLocation().getBlockZ())
						.replace("%world_name%", player.getLocation().getWorld().getName());
				MessageManager.sendMessage(player, message);
				return;
			}
			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
				Location loc = event.getClickedBlock().getLocation();
				rightClick.put(event.getPlayer(), loc);
				String message = "&2Set pos2 (&6%x%&2,&6%y%&2,&6%z%&2)";
				message = message.replace("%x%", "" + player.getLocation().getBlockX())
						.replace("%y%", "" + player.getLocation().getBlockY())
						.replace("%z%", "" + player.getLocation().getBlockZ())
						.replace("%world_name%", player.getLocation().getWorld().getName());
				MessageManager.sendMessage(player, message);
				return;
			}
		}
	}
	
	
	public static ItemStack createItem(){
		ItemStack i = new ItemStack(Material.WOOD_HOE, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Region Selector");
		i.setItemMeta(im);
		return i;
	}
	
	public static boolean hasRegion(Player player){
		if(leftClick.containsKey(player) && rightClick.containsKey(player)){
			return true;
		}
		return false;
	}
	
	public static Location getRegion(Player player,String type){
		switch(type){
		case "left":
			return leftClick.get(player);
		case "right":
		    return rightClick.get(player);
		default:
			return null;
		}
	}
}
