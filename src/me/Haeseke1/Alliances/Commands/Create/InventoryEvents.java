package me.Haeseke1.Alliances.Commands.Create;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.Alliance.Type.Caith_Sith;
import me.Haeseke1.Alliances.Alliance.Type.Gnome;
import me.Haeseke1.Alliances.Alliance.Type.Imp;
import me.Haeseke1.Alliances.Alliance.Type.Leprechaun;
import me.Haeseke1.Alliances.Alliance.Type.Pooka;
import me.Haeseke1.Alliances.Alliance.Type.Salamander;
import me.Haeseke1.Alliances.Alliance.Type.Spriggan;
import me.Haeseke1.Alliances.Alliance.Type.Sylph;
import me.Haeseke1.Alliances.Alliance.Type.Undine;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Utils.MessageManager;


@SuppressWarnings("deprecation")
public class InventoryEvents implements Listener{
	
	public static HashMap<Player,AllianceType> chooseName = new HashMap<Player,AllianceType>();
	
	public static void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, "Create alliance");
		inv.addItem(Caith_Sith.createItem(player));
		inv.addItem(Gnome.createItem(player));
		inv.addItem(Imp.createItem(player));
		inv.addItem(Leprechaun.createItem(player));
		inv.addItem(Pooka.createItem(player));
		inv.addItem(Salamander.createItem(player));
		inv.addItem(Spriggan.createItem(player));
		inv.addItem(Sylph.createItem(player));
		inv.addItem(Undine.createItem(player));
		player.openInventory(inv);
	}
	
	@EventHandler
	private void onInvClickEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equalsIgnoreCase("Create alliance")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				Player player = (Player) event.getWhoClicked();
				switch(event.getCurrentItem().getType()){
				case MONSTER_EGG:
					if(Coins.getPlayerCoins(player) >= Caith_Sith.cost){
						chooseName.put(player, AllianceType.CAIT_SITH);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case DIRT:
					if(Coins.getPlayerCoins(player) >= Gnome.cost){
						chooseName.put(player, AllianceType.GNOME);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case EYE_OF_ENDER:
					if(Coins.getPlayerCoins(player) >= Imp.cost){
						chooseName.put(player, AllianceType.IMP);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case REDSTONE_TORCH_ON:
					if(Coins.getPlayerCoins(player) >= Leprechaun.cost){
						chooseName.put(player, AllianceType.LEPRECHAUN);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case BLAZE_ROD:
					if(Coins.getPlayerCoins(player) >= Pooka.cost){
						chooseName.put(player, AllianceType.POOKA);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case FIREBALL:
					if(Coins.getPlayerCoins(player) >= Salamander.cost){
						chooseName.put(player, AllianceType.SALAMANDER);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case DIAMOND_BLOCK:
					if(Coins.getPlayerCoins(player) >= Spriggan.cost){
						chooseName.put(player, AllianceType.SPRIGGAN);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case FEATHER:
					if(Coins.getPlayerCoins(player) >= Sylph.cost){
						chooseName.put(player, AllianceType.SYLPH);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
					player.closeInventory();
					break;
				case GOLDEN_APPLE:
					if(Coins.getPlayerCoins(player) >= Undine.cost){
						chooseName.put(player, AllianceType.UNDINE);
						String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
						MessageManager.sendMessage(player, message);
					}else{
						String message = MessageManager.getMessage("Command_Alliance_Create_Not_Enough_Coins");
						MessageManager.sendMessage(player, message);
					}
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
			event.setCancelled(true);
			Player player = event.getPlayer();
			switch(chooseName.get(event.getPlayer())){
			case CAIT_SITH:
				if(Coins.getPlayerCoins(player) < Caith_Sith.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case GNOME:
				if(Coins.getPlayerCoins(player) < Gnome.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case IMP:
				if(Coins.getPlayerCoins(player) < Imp.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case LEPRECHAUN:
				if(Coins.getPlayerCoins(player) < Leprechaun.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case POOKA:
				if(Coins.getPlayerCoins(player) < Pooka.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SALAMANDER:
				if(Coins.getPlayerCoins(player) < Salamander.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SPRIGGAN:
				if(Coins.getPlayerCoins(player) < Spriggan.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SYLPH:
				if(Coins.getPlayerCoins(player) < Sylph.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case UNDINE:
				if(Coins.getPlayerCoins(player) < Undine.cost){
					String message = MessageManager.getMessage("Command_Alliance_Create_Type_Name");
					MessageManager.sendMessage(player, message);
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			}
			if(!AllianceManager.createNewAlliance(event.getMessage(), player, chooseName.get(player))){
				chooseName.remove(player);
				return;
			}
			chooseName.remove(player);
			String message = MessageManager.getMessage("Command_Alliance_Create_Answer");
			message = message.replace("%alli_name%", event.getMessage());
			MessageManager.sendMessage(player, message);
			message = MessageManager.getMessage("Command_Alliance_Create_Broadcast");
			message = message.replace("%alli_name%", event.getMessage());
			MessageManager.sendBroadcast(message);
		}
	}
	
	

}
