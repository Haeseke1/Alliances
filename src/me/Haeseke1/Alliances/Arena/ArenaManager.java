package me.Haeseke1.Alliances.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ArenaManager {
	
	public static HashMap<Player,List<Player>> battles = new HashMap<Player,List<Player>>();
	
	public static Arena getArena(){
		for(Arena f : Arena.arenas){
			if(!f.inGame){
				return f;
			}
		}
		return null;
	}
	
	public static boolean isArena(){
		for(Arena f : Arena.arenas){
			if(!f.inGame){
				return true;
			}
		}
		return false;
	}
	
	
	public static void One_V_One(Player defender, Player challenger, int coins, Arena arena){
		arena.inGame = true;
		defender.teleport(arena.waitRoom);
		challenger.teleport(arena.waitRoom);
		arena.group1.add(defender);
		arena.group2.add(challenger);
		arena.isStarting = true;
		arena.nameG1 = defender.getName();
		arena.nameG2 = challenger.getName();
	}
	
	
	public static List<ItemStack> types(){
		List<ItemStack> list = new ArrayList<ItemStack>();
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName("1V1");
		item.setItemMeta(im);
		list.add(item);
		return list;
	}
	
	
	
	public static boolean checkLocation(Location b){
		for(Arena f : Arena.arenas){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return true;
				}
			}
		}
		return false;
	}
}
