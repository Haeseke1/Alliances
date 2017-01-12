package me.Haeseke1.Alliances.Commands.Arena.CreateSign;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Arena.ArenaManager;
import me.Haeseke1.Alliances.Arena.Arena_Sign;
import me.Haeseke1.Alliances.Arena.Fight_Type;
import me.Haeseke1.Alliances.Utils.MessageManager;


public class InventoryEvents implements Listener{
	
	public static void createInventory(Player player){
		List<ItemStack> list = ArenaManager.types();
		Inventory inv = Bukkit.createInventory(null, (list.size() - list.size() % 9) + 9 , "Create arena_sign");
		for(ItemStack item : list){
			inv.addItem(item);
		}
		player.openInventory(inv);
	}
	
	@EventHandler
	private void onInvClickEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Create arena_sign")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()){
				Player player = (Player) event.getWhoClicked();
				switch(event.getCurrentItem().getItemMeta().getDisplayName()){
				case "1V1":
					new Arena_Sign(mainCreateArenaSign.createArena_Sign.get(player), Fight_Type.ONE_V_ONE , mainCreateArenaSign.createArena_SignC.get(player));
					player.closeInventory();
					MessageManager.sendRemarkMessage(player, "Sign is created!");
					break;
				default:
					break;
					
				}
			}
		}
	}
	

}
