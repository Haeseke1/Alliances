package me.Haeseke1.Alliances.Item;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

public class Outpost_Compass {
	
	public static HashMap<UUID,OutpostType> chosenOutpost = new HashMap<UUID, OutpostType>();
	
	public ItemStack item;
	
	
	public Outpost_Compass(ItemStack item) {
		this.item = item;
	}
	
	
	public boolean checkItem(ItemStack item){
		if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore() || !item.getType().equals(Material.COMPASS)){
			return false;
		}
		if(item.getItemMeta().getDisplayName().equals(this.item.getItemMeta().getDisplayName())){
			return true;
		}
		return false;
	}
	
	public OutpostType checkOutpostType(ItemStack item){
		if(!checkItem(item)){
			return null;
		}
		List<String> lore = item.getItemMeta().getLore();
		String line = ChatColor.stripColor(lore.get(0));
		line = line.substring(6);
		return OutpostType.getOutpostType(line);
	}
	
	public String checkName(ItemStack item){
		if(!checkItem(item)){
			return null;
		}
		List<String> lore = item.getItemMeta().getLore();
		String line = ChatColor.stripColor(lore.get(1));
		line = line.substring(6);
		return line;
	}
	
	public boolean setName(ItemStack item, String name){
		if(!checkItem(item)){
			return false;
		}
		List<String> lore = item.getItemMeta().getLore();
		lore.set(1, ChatColor.DARK_PURPLE + "Name: " + name);
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
		return true;
	}
	
	
	public boolean setType(ItemStack item, OutpostType outpost){
		if(!checkItem(item)){
			return false;
		}
		List<String> lore = item.getItemMeta().getLore();
		lore.set(0, ChatColor.DARK_PURPLE + "Type: " + OutpostType.getOutpostType(outpost));
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		item.setItemMeta(im);
		return true;
	}
	
	
	public void getNearestOutpost(ItemStack compass, Player player){
		switch(checkOutpostType(item)){
		case BLACKSMITH:
			Blacksmith nearest = Blacksmith.blacksmiths.get(0);
			for(Blacksmith b : Blacksmith.blacksmiths){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest.world, nearest.xmin + (nearest.xmax - nearest.xmin) / 2, player.getLocation().getY(), nearest.zmin + (nearest.zmax - nearest.zmin) / 2).distance(player.getLocation())){
					nearest = b;
				}
			}
			setName(compass, nearest.name);
			player.setCompassTarget(new Location(nearest.world, nearest.xmin + (nearest.xmax - nearest.xmin) / 2, player.getLocation().getY(), nearest.zmin + (nearest.zmax - nearest.zmin) / 2));
			break;
		case DOCK:
			Dock nearest1 = Dock.docks.get(0);
			for(Dock b : Dock.docks){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest1.world, nearest1.xmin + (nearest1.xmax - nearest1.xmin) / 2, player.getLocation().getY(), nearest1.zmin + (nearest1.zmax - nearest1.zmin) / 2).distance(player.getLocation())){
					nearest1 = b;
				}
			}
			setName(compass, nearest1.name);
			player.setCompassTarget(new Location(nearest1.world, nearest1.xmin + (nearest1.xmax - nearest1.xmin) / 2, player.getLocation().getY(), nearest1.zmin + (nearest1.zmax - nearest1.zmin) / 2));
			break;
		case FARM:
			Farm nearest11 = Farm.farms.get(0);
			for(Farm b : Farm.farms){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest11.world, nearest11.xmin + (nearest11.xmax - nearest11.xmin) / 2, player.getLocation().getY(), nearest11.zmin + (nearest11.zmax - nearest11.zmin) / 2).distance(player.getLocation())){
					nearest11 = b;
				}
			}
			setName(compass, nearest11.name);
			player.setCompassTarget(new Location(nearest11.world, nearest11.xmin + (nearest11.xmax - nearest11.xmin) / 2, player.getLocation().getY(), nearest11.zmin + (nearest11.zmax - nearest11.zmin) / 2));
			break;
		case GOD:
			God nearest111 = God.gods.get(0);
			for(God b : God.gods){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest111.world, nearest111.xmin + (nearest111.xmax - nearest111.xmin) / 2, player.getLocation().getY(), nearest111.zmin + (nearest111.zmax - nearest111.zmin) / 2).distance(player.getLocation())){
					nearest111 = b;
				}
			}
			setName(compass, nearest111.name);
			player.setCompassTarget(new Location(nearest111.world, nearest111.xmin + (nearest111.xmax - nearest111.xmin) / 2, player.getLocation().getY(), nearest111.zmin + (nearest111.zmax - nearest111.zmin) / 2));
			break;
		case MAGIC_TOWER:
			Magic_Tower nearest1111 = Magic_Tower.magic_towers.get(0);
			for(Magic_Tower b : Magic_Tower.magic_towers){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest1111.world, nearest1111.xmin + (nearest1111.xmax - nearest1111.xmin) / 2, player.getLocation().getY(), nearest1111.zmin + (nearest1111.zmax - nearest1111.zmin) / 2).distance(player.getLocation())){
					nearest1111 = b;
				}
			}
			setName(compass, nearest1111.name);
			player.setCompassTarget(new Location(nearest1111.world, nearest1111.xmin + (nearest1111.xmax - nearest1111.xmin) / 2, player.getLocation().getY(), nearest1111.zmin + (nearest1111.zmax - nearest1111.zmin) / 2));
			break;
		case MINE:
			Mine nearest11111 = Mine.mines.get(0);
			for(Mine b : Mine.mines){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest11111.world, nearest11111.xmin + (nearest11111.xmax - nearest11111.xmin) / 2, player.getLocation().getY(), nearest11111.zmin + (nearest11111.zmax - nearest11111.zmin) / 2).distance(player.getLocation())){
					nearest11111 = b;
				}
			}
			setName(compass, nearest11111.name);
			player.setCompassTarget(new Location(nearest11111.world, nearest11111.xmin + (nearest11111.xmax - nearest11111.xmin) / 2, player.getLocation().getY(), nearest11111.zmin + (nearest11111.zmax - nearest11111.zmin) / 2));
			break;
		case MOB_FARM:
			Mob_Farm nearest111111 = Mob_Farm.mob_farms.get(0);
			for(Mob_Farm b : Mob_Farm.mob_farms){
				if(new Location(b.world, b.xmin + (b.xmax - b.xmin) / 2, player.getLocation().getY(), b.zmin + (b.zmax - b.zmin) / 2).distance(player.getLocation()) < 
						new Location(nearest111111.world, nearest111111.xmin + (nearest111111.xmax - nearest111111.xmin) / 2, player.getLocation().getY(), nearest111111.zmin + (nearest111111.zmax - nearest111111.zmin) / 2).distance(player.getLocation())){
					nearest111111 = b;
				}
			}
			setName(compass, nearest111111.name);
			player.setCompassTarget(new Location(nearest111111.world, nearest111111.xmin + (nearest111111.xmax - nearest111111.xmin) / 2, player.getLocation().getY(), nearest111111.zmin + (nearest111111.zmax - nearest111111.zmin) / 2));
			break;
		default:
			return;	
		}
	}
	
	
	
	
}
