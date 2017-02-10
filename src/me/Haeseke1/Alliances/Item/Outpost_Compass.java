package me.Haeseke1.Alliances.Item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Outpost.OutpostType;
import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Outpost_Compass implements Listener{
	
	
	public static void createInventory(Player player){
		Inventory inv = Bukkit.createInventory(null, 9, "Select outpost");
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
		if(event.getInventory().getName().equalsIgnoreCase("Select outpost")){
			event.setCancelled(true);
			if(event.getCurrentItem() != null){
				Player player = (Player) event.getWhoClicked();
				ItemStack compass = player.getItemInHand();
				switch(event.getCurrentItem().getType()){
				case DIAMOND_SWORD:
					compass = setType(compass, OutpostType.BLACKSMITH);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case FISHING_ROD:
					compass = setType(compass, OutpostType.DOCK);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case WHEAT:
					compass = setType(compass, OutpostType.FARM);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case GOLDEN_APPLE:
					compass = setType(compass, OutpostType.GOD);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case BLAZE_ROD:
					compass = setType(compass, OutpostType.MAGIC_TOWER);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case DIAMOND_PICKAXE:
					compass = setType(compass, OutpostType.MINE);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				case BONE:
					compass = setType(compass, OutpostType.MOB_FARM);
					player.setItemInHand(compass);
					player.closeInventory();
					break;
				default:
					break;
				}
				MessageManager.sendMessage(player, ChatColor.GREEN + "Outpost type is now set to " + ChatColor.GOLD + checkOutpostType(compass));
			}
		}
	}
	
	
	@EventHandler
	private void playerInteract(PlayerInteractEvent event){
		if(!event.hasItem() || !event.getItem().getType().equals(Material.COMPASS) || 
				!event.getItem().getItemMeta().hasDisplayName() || !event.getItem().getItemMeta().hasLore() ||
				!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Outpost Compass")){
			return;
		}
		Player player = event.getPlayer();
		event.setCancelled(true);
		if(event.getAction().equals(Action.LEFT_CLICK_AIR)){
			createInventory(player);
			return;
		}
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			player.setItemInHand(getNearestOutpost(event.getItem(), player));
			MessageManager.sendMessage(player, ChatColor.GREEN + "Target changed to " + ChatColor.GOLD + checkName(player.getItemInHand()));
			return;
		}
	}
	
	
	
	
	
	
	
	public static ItemStack getItem(Player player){
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta im = compass.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Outpost Compass");
		List<String> lore = new ArrayList<String>();
		lore.add("lore");
		lore.add("lore2");
		im.setLore(lore);
		compass.setItemMeta(im);
		compass = setType(compass, OutpostType.FARM);
		compass = getNearestOutpost(compass, player);
		return compass;
	}
	
	
	public static boolean checkItem(ItemStack item){
		if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore() || !item.getType().equals(Material.COMPASS)){
			return false;
		}
		if(item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Outpost Compass")){
			return true;
		}
		return false;
	}
	
	public static OutpostType checkOutpostType(ItemStack item){
		if(!checkItem(item)){
			return null;
		}
		List<String> lore = item.getItemMeta().getLore();
		String line = ChatColor.stripColor(lore.get(0));
		line = line.substring(6);
		return OutpostType.getOutpostType(line);
	}
	
	public static String checkName(ItemStack item){
		if(!checkItem(item)){
			return null;
		}
		List<String> lore = item.getItemMeta().getLore();
		String line = ChatColor.stripColor(lore.get(1));
		line = line.substring(6);
		return line;
	}
	
	public static ItemStack setName(ItemStack item, String name){
		if(!checkItem(item)){
			return item;
		}
		List<String> lore = item.getItemMeta().getLore();
		lore.set(1, ChatColor.DARK_PURPLE + "Name: " + name);
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	public static ItemStack setType(ItemStack item, OutpostType outpost){
		if(!checkItem(item)){
			return item;
		}
		List<String> lore = item.getItemMeta().getLore();
		lore.set(0, ChatColor.DARK_PURPLE + "Type: " + OutpostType.getOutpostType(outpost));
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	public static ItemStack getNearestOutpost(ItemStack compass, Player player){
		switch(checkOutpostType(compass)){
		case BLACKSMITH:
			if(Blacksmith.blacksmiths.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Blacksmith nearest = Blacksmith.blacksmiths.get(0);
			for(Blacksmith b : Blacksmith.blacksmiths){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest.world, nearest.xmin + (nearest.xmax - nearest.xmin) / 2, player.getLocation().getY(), nearest.zmin + (nearest.zmax - nearest.zmin) / 2).distance(player.getLocation())){
					nearest = b;
				}
			}
			compass = setName(compass, nearest.name);
			player.setCompassTarget(new Location(nearest.world, nearest.xmin + (nearest.xmax - nearest.xmin) / 2, player.getLocation().getY(), nearest.zmin + (nearest.zmax - nearest.zmin) / 2));
			break;
		case DOCK:
			if(Dock.docks.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Dock nearest1 = Dock.docks.get(0);
			for(Dock b : Dock.docks){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest1.world, nearest1.xmin + (nearest1.xmax - nearest1.xmin) / 2, player.getLocation().getY(), nearest1.zmin + (nearest1.zmax - nearest1.zmin) / 2).distance(player.getLocation())){
					nearest1 = b;
				}
			}
			compass = setName(compass, nearest1.name);
			player.setCompassTarget(new Location(nearest1.world, nearest1.xmin + (nearest1.xmax - nearest1.xmin) / 2, player.getLocation().getY(), nearest1.zmin + (nearest1.zmax - nearest1.zmin) / 2));
			break;
		case FARM:
			if(Farm.farms.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Farm nearest11 = Farm.farms.get(0);
			for(Farm b : Farm.farms){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest11.world, nearest11.xmin + (nearest11.xmax - nearest11.xmin) / 2, player.getLocation().getY(), nearest11.zmin + (nearest11.zmax - nearest11.zmin) / 2).distance(player.getLocation())){
					nearest11 = b;
				}
			}
			compass = setName(compass, nearest11.name);
			player.setCompassTarget(new Location(nearest11.world, nearest11.xmin + (nearest11.xmax - nearest11.xmin) / 2, player.getLocation().getY(), nearest11.zmin + (nearest11.zmax - nearest11.zmin) / 2));
			break;
		case GOD:
			if(God.gods.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			God nearest111 = God.gods.get(0);
			for(God b : God.gods){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest111.world, nearest111.xmin + (nearest111.xmax - nearest111.xmin) / 2, player.getLocation().getY(), nearest111.zmin + (nearest111.zmax - nearest111.zmin) / 2).distance(player.getLocation())){
					nearest111 = b;
				}
			}
			compass = setName(compass, nearest111.name);
			player.setCompassTarget(new Location(nearest111.world, nearest111.xmin + (nearest111.xmax - nearest111.xmin) / 2, player.getLocation().getY(), nearest111.zmin + (nearest111.zmax - nearest111.zmin) / 2));
			break;
		case MAGIC_TOWER:
			if(Magic_Tower.magic_towers.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Magic_Tower nearest1111 = Magic_Tower.magic_towers.get(0);
			for(Magic_Tower b : Magic_Tower.magic_towers){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest1111.world, nearest1111.xmin + (nearest1111.xmax - nearest1111.xmin) / 2, player.getLocation().getY(), nearest1111.zmin + (nearest1111.zmax - nearest1111.zmin) / 2).distance(player.getLocation())){
					nearest1111 = b;
				}
			}
			compass = setName(compass, nearest1111.name);
			player.setCompassTarget(new Location(nearest1111.world, nearest1111.xmin + (nearest1111.xmax - nearest1111.xmin) / 2, player.getLocation().getY(), nearest1111.zmin + (nearest1111.zmax - nearest1111.zmin) / 2));
			break;
		case MINE:
			if(Mine.mines.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Mine nearest11111 = Mine.mines.get(0);
			for(Mine b : Mine.mines){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest11111.world, nearest11111.xmin + (nearest11111.xmax - nearest11111.xmin) / 2, player.getLocation().getY(), nearest11111.zmin + (nearest11111.zmax - nearest11111.zmin) / 2).distance(player.getLocation())){
					nearest11111 = b;
				}
			}
			compass = setName(compass, nearest11111.name);
			player.setCompassTarget(new Location(nearest11111.world, nearest11111.xmin + (nearest11111.xmax - nearest11111.xmin) / 2, player.getLocation().getY(), nearest11111.zmin + (nearest11111.zmax - nearest11111.zmin) / 2));
			break;
		case MOB_FARM:
			if(Mob_Farm.mob_farms.size() == 0){
				compass = setName(compass, "null");
				return compass;
			}
			Mob_Farm nearest111111 = Mob_Farm.mob_farms.get(0);
			for(Mob_Farm b : Mob_Farm.mob_farms){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest111111.world, nearest111111.xmin + (nearest111111.xmax - nearest111111.xmin) / 2, player.getLocation().getY(), nearest111111.zmin + (nearest111111.zmax - nearest111111.zmin) / 2).distance(player.getLocation())){
					nearest111111 = b;
				}
			}
			compass = setName(compass, nearest111111.name);
			player.setCompassTarget(new Location(nearest111111.world, nearest111111.xmin + (nearest111111.xmax - nearest111111.xmin) / 2, player.getLocation().getY(), nearest111111.zmin + (nearest111111.zmax - nearest111111.zmin) / 2));
			break;
		default:
			break;
		}
		return compass;
	}
	
	
	
	
}
