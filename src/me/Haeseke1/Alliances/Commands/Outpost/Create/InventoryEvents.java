package me.Haeseke1.Alliances.Commands.Outpost.Create;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

import me.Haeseke1.Alliances.Outpost.OutpostManager;
import me.Haeseke1.Alliances.Outpost.OutpostType;
import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;


@SuppressWarnings("deprecation")
public class InventoryEvents implements Listener{
	
	public static HashMap<Player,OutpostType> chooseName = new HashMap<Player,OutpostType>();
	
	public static void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, "Create outpost");
		inv.addItem(Blacksmith.createItem());
		inv.addItem(Dock.createItem());
		inv.addItem(Farm.createItem());
		inv.addItem(God.createItem());
		inv.addItem(Magic_Tower.createItem());
		inv.addItem(Mine.createItem());
		inv.addItem(Mob_Farm.createItem());
		player.openInventory(inv);
	}
	
	@EventHandler
	private void onInvClickEvent(InventoryClickEvent event){
		MessageManager.sendInfoMessage(event.getInventory().getName());
		if(event.getInventory().getName().equalsIgnoreCase("Create outpost")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				Player player = (Player) event.getWhoClicked();
				switch(event.getCurrentItem().getType()){
				case DIAMOND_SWORD:
					chooseName.put(player, OutpostType.BLACKSMITH);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case FISHING_ROD:
					chooseName.put(player, OutpostType.DOCK);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case WHEAT:
					chooseName.put(player, OutpostType.FARM);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case GOLDEN_APPLE:
					chooseName.put(player, OutpostType.GOD);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case BLAZE_ROD:
					chooseName.put(player, OutpostType.MAGIC_TOWER);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case DIAMOND_PICKAXE:
					chooseName.put(player, OutpostType.MINE);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				case BONE:
					chooseName.put(player, OutpostType.MOB_FARM);
					MessageManager.sendRemarkMessage(player, "Type the name of the outpost in the chat!");
					player.closeInventory();
					break;
				default:
					break;
					
				}
			}
		}
	}
	
	@EventHandler
	private void onPlayerChat(PlayerChatEvent event){
		if(chooseName.containsKey(event.getPlayer())){
			Player player = event.getPlayer();
			if(!OutpostManager.checkLocation(regionSelect.leftClick.get(player), regionSelect.rightClick.get(player))){
				MessageManager.sendAlertMessage(player, "You are trying to construct a outpost on a existing outpost!");
				return;
			}
			switch(chooseName.get(event.getPlayer())){
			case BLACKSMITH:
				new Blacksmith(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case DOCK:
				new Dock(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case FARM:
				new Farm(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case GOD:
				new God(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case MAGIC_TOWER:
				new Magic_Tower(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case MINE:
				new Mine(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			case MOB_FARM:
				new Mob_Farm(event.getMessage(), regionSelect.leftClick.get(player), regionSelect.rightClick.get(player), null);
				break;
			}
		}
	}
}
