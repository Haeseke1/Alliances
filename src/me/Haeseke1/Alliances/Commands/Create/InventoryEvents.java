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
		MessageManager.sendInfoMessage(event.getInventory().getName());
		if(event.getInventory().getName().equalsIgnoreCase("Create alliance")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				Player player = (Player) event.getWhoClicked();
				switch(event.getCurrentItem().getType()){
				case MONSTER_EGG:
					if(Coins.getPlayerCoins(player) >= Caith_Sith.cost){
						chooseName.put(player, AllianceType.CAIT_SITH);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case DIRT:
					if(Coins.getPlayerCoins(player) >= Gnome.cost){
						chooseName.put(player, AllianceType.GNOME);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case EYE_OF_ENDER:
					if(Coins.getPlayerCoins(player) >= Imp.cost){
						chooseName.put(player, AllianceType.IMP);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case REDSTONE_TORCH_ON:
					if(Coins.getPlayerCoins(player) >= Leprechaun.cost){
						chooseName.put(player, AllianceType.LEPRECHAUN);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case BLAZE_ROD:
					if(Coins.getPlayerCoins(player) >= Pooka.cost){
						chooseName.put(player, AllianceType.POOKA);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case FIREBALL:
					if(Coins.getPlayerCoins(player) >= Salamander.cost){
						chooseName.put(player, AllianceType.SALAMANDER);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case DIAMOND_BLOCK:
					if(Coins.getPlayerCoins(player) >= Spriggan.cost){
						chooseName.put(player, AllianceType.SPRIGGAN);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case FEATHER:
					if(Coins.getPlayerCoins(player) >= Sylph.cost){
						chooseName.put(player, AllianceType.SYLPH);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					}
					player.closeInventory();
					break;
				case GOLDEN_APPLE:
					if(Coins.getPlayerCoins(player) >= Undine.cost){
						chooseName.put(player, AllianceType.UNDINE);
						MessageManager.sendRemarkMessage(player, "Type your name of your alliance in the chat!");
					}else{
						MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
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
			Player player = event.getPlayer();
			switch(chooseName.get(event.getPlayer())){
			case CAIT_SITH:
				if(Coins.getPlayerCoins(player) < Caith_Sith.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case GNOME:
				if(Coins.getPlayerCoins(player) < Gnome.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case IMP:
				if(Coins.getPlayerCoins(player) < Imp.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case LEPRECHAUN:
				if(Coins.getPlayerCoins(player) < Leprechaun.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case POOKA:
				if(Coins.getPlayerCoins(player) < Pooka.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SALAMANDER:
				if(Coins.getPlayerCoins(player) < Salamander.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SPRIGGAN:
				if(Coins.getPlayerCoins(player) < Spriggan.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case SYLPH:
				if(Coins.getPlayerCoins(player) < Sylph.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			case UNDINE:
				if(Coins.getPlayerCoins(player) < Undine.cost){
					MessageManager.sendAlertMessage(player, "You don't have enough coins for this alliance!");
					chooseName.remove(event.getPlayer());
					return;
				}
				break;
			}
			if(AllianceManager.createAlliance(event.getMessage(), player, chooseName.get(player))){
				event.setCancelled(true);
				chooseName.remove(player);
				MessageManager.sendRemarkMessage(player, "Congratulations, the alliance " + event.getMessage() + " is created!");
				MessageManager.sendInfoBroadcast("The alliance " + event.getMessage() + " is created! the owner is " + player.getName() + "!");
			}
		}
	}
	
	

}
