package me.Haeseke1.Alliances.PVE.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import me.Haeseke1.Alliances.PVE.Group;
import me.Haeseke1.Alliances.PVE.Settings;

public class PlayerClickInventory implements Listener {
	
	
	@EventHandler
	private void playerClick(InventoryClickEvent event){
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		if(!inv.getName().equalsIgnoreCase(ChatColor.RED + "Settings")){
			return;
		}
		event.setCancelled(true);
		Settings set = null;
		for(Group group : Group.groups){
			if(group.members.contains(player)){
				set = group.settings;
			}
		}
		if(set == null){
			return;
		}
		int rawslot = event.getRawSlot();
		if(rawslot > 9 && rawslot < 17){
			return;
		}
		if(rawslot == 17){
			if(set.level == set.MAX_LEVEL){
				set.level = 1;
			}else{
				set.level = set.level + 1;
			}
			set.updateGUI(player);
			return;
		}
		if(rawslot == 0){
			if(set.placing == 0){
				return;
			}
			set.placing = set.placing - 1;
			set.updateGUI(player);
			return;
		}
		if(rawslot == 8){
			if(set.placing == set.MAX_PLACING){
				return;
			}
			set.placing = set.placing + 1;
			set.updateGUI(player);
			return;
		}
		if(rawslot > 0 && rawslot < 8){
			set.changeMobCount(rawslot + set.placing, 1);
		}
		if(rawslot > 18 && rawslot < 26){
			set.changeMobCount(rawslot + set.placing - 18, -1);
		}
		set.updateGUI(player);
	}

}
