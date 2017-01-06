package me.Haeseke1.Alliances.Outpost;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Outpost.Type.Blacksmith;
import me.Haeseke1.Alliances.Outpost.Type.Dock;
import me.Haeseke1.Alliances.Outpost.Type.Farm;
import me.Haeseke1.Alliances.Outpost.Type.God;
import me.Haeseke1.Alliances.Outpost.Type.Magic_Tower;
import me.Haeseke1.Alliances.Outpost.Type.Mine;
import me.Haeseke1.Alliances.Outpost.Type.Mob_Farm;

public class OutpostManager {
	
	
	public static void giveReward(){
		for(Farm f : Farm.farms){
			if(f.owner != null){
				f.owner.addReward(chooseReward(Farm.rewards));
			}
		}
		for(Blacksmith b : Blacksmith.blacksmiths){
			if(b.owner != null){
				b.owner.addReward(chooseReward(Blacksmith.rewards));
			}
		}
		for(Dock d : Dock.docks){
			if(d.owner != null){
				d.owner.addReward(chooseReward(Dock.rewards));
			}
		}
		for(God g : God.gods){
			if(g.owner != null){
				g.owner.addReward(chooseReward(God.rewards));
			}
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			if(mt.owner != null){
				mt.owner.addReward(chooseReward(Magic_Tower.rewards));
			}
		}
		for(Mine m : Mine.mines){
			if(m.owner != null){
				m.owner.addReward(chooseReward(Mine.rewards));
			}
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			if(mf.owner != null){
				mf.owner.addReward(chooseReward(Mob_Farm.rewards));
			}
		}
	}
	
	public static void checkPlayers(){
		for(Player player : Bukkit.getOnlinePlayers()){
			Location loc = player.getLocation();
			Farm f = checkFarms(loc);
			if(f != null){
				f.inOutpost.add(player);
			}
			Blacksmith b = checkBlacksmiths(loc);
			if(b != null){
				b.inOutpost.add(player);
			}
			Dock d = checkDocks(loc);
			if(d != null){
				d.inOutpost.add(player);
			}
			God g = checkGods(loc);
			if(g != null){
				g.inOutpost.add(player);
			}
			Magic_Tower mt = checkMagic_Towers(loc);
			if(mt != null){
				mt.inOutpost.add(player);
			}
			Mine m = checkMines(loc);
			if(m != null){
				m.inOutpost.add(player);
			}
			Mob_Farm mf = checkMob_Farms(loc);
			if(mf != null){
				mf.inOutpost.add(player);
			}
		}
	}
	
	
	
	private static Farm checkFarms(Location b){
		for(Farm f : Farm.farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Blacksmith checkBlacksmiths(Location b){
		for(Blacksmith f : Blacksmith.blacksmiths){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	
	private static Dock checkDocks(Location b){
		for(Dock f : Dock.docks){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static God checkGods(Location b){
		for(God f : God.gods){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Magic_Tower checkMagic_Towers(Location b){
		for(Magic_Tower f : Magic_Tower.magic_towers){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Mine checkMines(Location b){
		for(Mine f : Mine.mines){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	private static Mob_Farm checkMob_Farms(Location b){
		for(Mob_Farm f : Mob_Farm.mob_farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return f;
				}
			}
		}
		return null;
	}
	
	public static boolean checkLocation(Location b){
		for(Farm f : Farm.farms){
			if(f.world.equals(b.getWorld())){
				if(f.xmin <= b.getX() && f.xmax >= b.getX() &&
						f.zmin <= b.getZ() && f.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Blacksmith bs : Blacksmith.blacksmiths){
			if(bs.world.equals(b.getWorld())){
				if(bs.xmin <= b.getX() && bs.xmax >= b.getX() &&
						bs.zmin <= b.getZ() && bs.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Dock d : Dock.docks){
			if(d.world.equals(b.getWorld())){
				if(d.xmin <= b.getX() && d.xmax >= b.getX() &&
						d.zmin <= b.getZ() && d.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(God g : God.gods){
			if(g.world.equals(b.getWorld())){
				if(g.xmin <= b.getX() && g.xmax >= b.getX() &&
						g.zmin <= b.getZ() && g.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			if(mt.world.equals(b.getWorld())){
				if(mt.xmin <= b.getX() && mt.xmax >= b.getX() &&
						mt.zmin <= b.getZ() && mt.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Mine m : Mine.mines){
			if(m.world.equals(b.getWorld())){
				if(m.xmin <= b.getX() && m.xmax >= b.getX() &&
						m.zmin <= b.getZ() && m.zmax >= b.getZ()){
					return true;
				}
			}
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			if(mf.world.equals(b.getWorld())){
				if(mf.xmin <= b.getX() && mf.xmax >= b.getX() &&
						mf.zmin <= b.getZ() && mf.zmax >= b.getZ()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void updateTakeOver(){
		for(Farm f : Farm.farms){
			f.take_over();
		}
		for(Blacksmith b : Blacksmith.blacksmiths){
			b.take_over();
		}
		for(Dock d : Dock.docks){
			d.take_over();
		}
		for(God g : God.gods){
			g.take_over();
		}
		for(Magic_Tower mt : Magic_Tower.magic_towers){
			mt.take_over();
		}
		for(Mine m : Mine.mines){
			m.take_over();
		}
		for(Mob_Farm mf : Mob_Farm.mob_farms){
			mf.take_over();
		}
	}
	
	
	
	
	private static ItemStack chooseReward(HashMap<ItemStack,Integer> rewards){
		ItemStack reward = null;
		
		int som = 0;
		for(Entry<ItemStack,Integer> entry : rewards.entrySet()){
			som += entry.getValue();
		}
		
		Double random = Math.random() * som + 0.1;
		int lootnumber = 0;
		for(Entry<ItemStack,Integer> entry : rewards.entrySet()){
			lootnumber += entry.getValue();
			if(random < lootnumber){
				reward = entry.getKey();
				break;
			}
		}
		return reward;
	}
	
	
	
	
}
