package me.Haeseke1.Alliances.Item.Buildings.Storage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingManager;
import me.Haeseke1.Alliances.Buildings.Type.Storage.Storage;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class Storage_Level implements Listener{
	
	
	public static ItemStack getItem(int level){
		ItemStack item = new ItemStack(Material.INK_SACK,1,(short) 15);
		ItemMeta im = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Right click on a storage block to upgrade it to this tier!");
		lore.add(ChatColor.RED + "Warning: You cannot downgrade and not skip a level!");
		switch(level){
		case 0:
			im.setDisplayName(ChatColor.GOLD + "Storage LV\u221E");
			break;
		case 2:
			im.setDisplayName(ChatColor.GOLD + "Storage LV2");
			break;
		case 3:
			im.setDisplayName(ChatColor.GOLD + "Storage LV3");
			break;
		}
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	@EventHandler
	private void playerInteract(PlayerInteractEvent event){
		if(!event.hasItem() || !event.getItem().getType().equals(Material.INK_SACK) || event.getItem().getDurability() != (short) 15){
			return;
		}
		ItemStack item = event.getItem();
		if(!item.getItemMeta().hasDisplayName() || !item.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "Storage")){
			return;
		}
		event.setCancelled(true);
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)  || !event.getClickedBlock().getType().equals(Material.OBSIDIAN)){
			return;
		}
		if(!BuildingManager.isMainBlock(event.getClickedBlock().getLocation())){
			return;
		}
		Player player = event.getPlayer();
		String displayname  = item.getItemMeta().getDisplayName();
		Building b = BuildingManager.getBuilding(event.getClickedBlock().getLocation());
		if(!(b instanceof Storage)){
			MessageManager.sendMessage(player, ChatColor.RED + "This is not a storage building!");
			return;
		}
		Storage s = (Storage) b;
		if(displayname.equalsIgnoreCase(ChatColor.GOLD + "Storage LV\u221E")){
			if(!s.canLevel_Up(0)){
				MessageManager.sendMessage(player, ChatColor.RED + "You cannot upgrade to this tier!");
				return;
			}
			s.level_up(0);
			if(item.getAmount() == 1){
				player.setItemInHand(new ItemStack(Material.AIR));
			}else{
				item.setAmount(item.getAmount() - 1);
				player.setItemInHand(item);
			}
			MessageManager.sendMessage(player, ChatColor.GREEN + "This storage is upgraded to " + ChatColor.GOLD + "tier INF" + ChatColor.GREEN + "!");
			return;
		}
		
		if(displayname.equalsIgnoreCase(ChatColor.GOLD + "Storage LV2")){
			if(!s.canLevel_Up(2)){
				MessageManager.sendMessage(player, ChatColor.RED + "You cannot upgrade to this tier!");
				return;
			}
			s.level_up(2);
			if(item.getAmount() == 1){
				player.setItemInHand(new ItemStack(Material.AIR));
			}else{
				item.setAmount(item.getAmount() - 1);
				player.setItemInHand(item);
			}
			MessageManager.sendMessage(player, ChatColor.GREEN + "This storage is upgraded to " + ChatColor.GOLD + "tier 2" + ChatColor.GREEN + "!");
			return;
		}
		
		if(displayname.equalsIgnoreCase(ChatColor.GOLD + "Storage LV3")){
			if(!s.canLevel_Up(3)){
				MessageManager.sendMessage(player, ChatColor.RED + "You cannot upgrade to this tier!");
				return;
			}
			s.level_up(3);
			if(item.getAmount() == 1){
				player.setItemInHand(new ItemStack(Material.AIR));
			}else{
				item.setAmount(item.getAmount() - 1);
				player.setItemInHand(item);
			}
			MessageManager.sendMessage(player, ChatColor.GREEN + "This storage is upgraded to " + ChatColor.GOLD + "tier 3" + ChatColor.GREEN + "!");
			return;
		}
		
	}
	
	
}
