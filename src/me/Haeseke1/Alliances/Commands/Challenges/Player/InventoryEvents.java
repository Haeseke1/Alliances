package me.Haeseke1.Alliances.Commands.Challenges.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Challenge.Challenge;
import net.md_5.bungee.api.ChatColor;


public class InventoryEvents implements Listener{
	
	public static HashMap<Player,AllianceType> chooseName = new HashMap<Player,AllianceType>();
	
	public static void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, "Challenges");
		int i = 0;
		for(Challenge ch : Challenge.challenges){
			ItemStack item = null;
			ItemMeta im = null;
			if(ch.done.contains(player.getUniqueId())){
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
			}else{
				item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
			}
			im = item.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + ch.name);
			List<String> lore = new ArrayList<String>();
			if(ch.points.containsKey(player.getUniqueId())){
				lore.add(ChatColor.DARK_PURPLE + "Points: " + ch.points.get(player.getUniqueId()).intValue() + "/" + (int) ch.max_Points);
			}else{
				lore.add(ChatColor.DARK_PURPLE + "Points: 0/" + (int) ch.max_Points);
			}
			if(ch.timer != -1){
				lore.add(ChatColor.DARK_PURPLE + "Time: " + (int) ch.timer);
			}
			lore.add(ChatColor.DARK_PURPLE + "Reward: " + (int) ch.reward + " coins");
			im.setLore(lore);
			item.setItemMeta(im);
			inv.setItem(i,item);
			i = i + 4;
		}
		player.openInventory(inv);
	}
	
	@EventHandler
	private void onInvClickEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Challenges")){
			event.setCancelled(true);
		}
	}
}
