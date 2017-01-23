package me.Haeseke1.Alliances.Commands.Join;

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

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;


public class InventoryEvents implements Listener{
	
	public static HashMap<Player,AllianceType> chooseName = new HashMap<Player,AllianceType>();
	
	public static void createInventory(List<Alliance> allis,Player player){
		int alength = (allis.size() - allis.size() % 9) + 9;
		Inventory inv = Bukkit.createInventory(null, alength, "Invites");
		for(Alliance alli : allis){
			ItemStack paper = new ItemStack(Material.PAPER);
			ItemMeta im = paper.getItemMeta();
			im.setDisplayName(ChatColor.GOLD + alli.getName());
			paper.setItemMeta(im);
			inv.addItem(paper);
		}
		player.openInventory(inv);
	}
	
	@EventHandler
	private void onInvClickEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Invites")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				Player player = (Player) event.getWhoClicked();
				if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()){
					if(AllianceManager.alliExist(event.getCurrentItem().getItemMeta().getDisplayName())){
						Alliance alli = AllianceManager.getAlliance(event.getCurrentItem().getItemMeta().getDisplayName());
						alli.addMember(player.getUniqueId());
						String message = MessageManager.getMessage("Command_Alliance_Join_Answer");
						message = message.replace("%alli_name%", alli.getName());
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Join_Not_Exist");
						message = message.replace("%alli_name%", event.getCurrentItem().getItemMeta().getDisplayName());
						MessageManager.sendMessage(player, message);
					}
				}
				player.closeInventory();
			}
		}
	}
}
