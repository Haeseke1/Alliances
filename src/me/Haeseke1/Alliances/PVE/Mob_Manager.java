package me.Haeseke1.Alliances.PVE;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;

import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV1;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV2;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV3;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV4;
import me.Haeseke1.Alliances.CustomEntity.Blaze.BlazeLV5;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV1;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV2;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV3;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV4;
import me.Haeseke1.Alliances.CustomEntity.Creeper.CreeperLV5;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV1;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV2;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV3;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV4;
import me.Haeseke1.Alliances.CustomEntity.Enderman.EndermanLV5;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV1;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV2;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV3;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV4;
import me.Haeseke1.Alliances.CustomEntity.Skeleton.SkeletonLV5;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV1;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV2;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV3;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV4;
import me.Haeseke1.Alliances.CustomEntity.Spider.SpiderLV5;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV1;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV2;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV3;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV4;
import me.Haeseke1.Alliances.CustomEntity.Wither.WitherLV5;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV1;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV2;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV3;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV4;
import me.Haeseke1.Alliances.CustomEntity.Wither_Skeleton.Wither_SkeletonLV5;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV1;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV2;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV3;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV4;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV5;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV1;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV2;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV3;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV4;
import me.Haeseke1.Alliances.CustomEntity.Zombie_Pigman.Zombie_PigmanLV5;
import net.md_5.bungee.api.ChatColor;

public class Mob_Manager {
	
	public static Zombie spawnZombie(int level, Location loc){
		switch(level){
		case 1:
			return ZombieLV1.spawn(loc, ChatColor.RED + "Zombie LV1");
		case 2:
			return ZombieLV2.spawn(loc, ChatColor.RED + "Zombie LV2");
		case 3:
			return ZombieLV3.spawn(loc, ChatColor.RED + "Zombie LV3");
		case 4:
			return ZombieLV4.spawn(loc, ChatColor.RED + "Zombie LV4");
		case 5:
			return ZombieLV5.spawn(loc, ChatColor.RED + "Zombie LV5");
		}
		return null;
	}
	
	public static Spider spawnSpider(int level, Location loc){
		switch(level){
		case 1:
			return SpiderLV1.spawn(loc, ChatColor.RED + "Spider LV1");
		case 2:
			return SpiderLV2.spawn(loc, ChatColor.RED + "Spider LV2");
		case 3:
			return SpiderLV3.spawn(loc, ChatColor.RED + "Spider LV3");
		case 4:
			return SpiderLV4.spawn(loc, ChatColor.RED + "Spider LV4");
		case 5:
			return SpiderLV5.spawn(loc, ChatColor.RED + "Spider LV5");
		}
		return null;
	}
	
	public static Skeleton spawnSkeleton(int level, Location loc){
		switch(level){
		case 1:
			return SkeletonLV1.spawn(loc, ChatColor.RED + "Skeleton LV1");
		case 2:
			return SkeletonLV2.spawn(loc, ChatColor.RED + "Skeleton LV2");
		case 3:
			return SkeletonLV3.spawn(loc, ChatColor.RED + "Skeleton LV3");
		case 4:
			return SkeletonLV4.spawn(loc, ChatColor.RED + "Skeleton LV4");
		case 5:
			return SkeletonLV5.spawn(loc, ChatColor.RED + "Skeleton LV5");
		}
		return null;
	}
	
	public static Blaze spawnBlaze(int level, Location loc){
		switch(level){
		case 1:
			return BlazeLV1.spawn(loc, ChatColor.RED + "Blaze LV1");
		case 2:
			return BlazeLV2.spawn(loc, ChatColor.RED + "Blaze LV2");
		case 3:
			return BlazeLV3.spawn(loc, ChatColor.RED + "Blaze LV3");
		case 4:
			return BlazeLV4.spawn(loc, ChatColor.RED + "Blaze LV4");
		case 5:
			return BlazeLV5.spawn(loc, ChatColor.RED + "Blaze LV5");
		}
		return null;
	}
	
	public static Creeper spawnCreeper(int level, Location loc){
		switch(level){
		case 1:
			return CreeperLV1.spawn(loc, ChatColor.RED + "Creeper LV1");
		case 2:
			return CreeperLV2.spawn(loc, ChatColor.RED + "Creeper LV2");
		case 3:
			return CreeperLV3.spawn(loc, ChatColor.RED + "Creeper LV3");
		case 4:
			return CreeperLV4.spawn(loc, ChatColor.RED + "Creeper LV4");
		case 5:
			return CreeperLV5.spawn(loc, ChatColor.RED + "Creeper LV5");
		}
		return null;
	}
	
	public static Enderman spawnEnderman(int level, Location loc){
		switch(level){
		case 1:
			return EndermanLV1.spawn(loc, ChatColor.RED + "Enderman LV1");
		case 2:
			return EndermanLV2.spawn(loc, ChatColor.RED + "Enderman LV2");
		case 3:
			return EndermanLV3.spawn(loc, ChatColor.RED + "Enderman LV3");
		case 4:
			return EndermanLV4.spawn(loc, ChatColor.RED + "Enderman LV4");
		case 5:
			return EndermanLV5.spawn(loc, ChatColor.RED + "Enderman LV5");
		}
		return null;
	}
	
	
	public static Wither spawnWither(int level, Location loc){
		switch(level){
		case 1:
			return WitherLV1.spawn(loc, ChatColor.RED + "Wither LV1");
		case 2:
			return WitherLV2.spawn(loc, ChatColor.RED + "Wither LV2");
		case 3:
			return WitherLV3.spawn(loc, ChatColor.RED + "Wither LV3");
		case 4:
			return WitherLV4.spawn(loc, ChatColor.RED + "Wither LV4");
		case 5:
			return WitherLV5.spawn(loc, ChatColor.RED + "Wither LV5");
		}
		return null;
	}
	
	public static Skeleton spawnWither_Skeleton(int level, Location loc){
		switch(level){
		case 1:
			return Wither_SkeletonLV1.spawn(loc, ChatColor.RED + "Wither_Skeleton LV1");
		case 2:
			return Wither_SkeletonLV2.spawn(loc, ChatColor.RED + "Wither_Skeleton LV2");
		case 3:
			return Wither_SkeletonLV3.spawn(loc, ChatColor.RED + "Wither_Skeleton LV3");
		case 4:
			return Wither_SkeletonLV4.spawn(loc, ChatColor.RED + "Wither_Skeleton LV4");
		case 5:
			return Wither_SkeletonLV5.spawn(loc, ChatColor.RED + "Wither_Skeleton LV5");
		}
		return null;
	}
	
	public static PigZombie spawnZombie_Pigman(int level, Location loc){
		switch(level){
		case 1:
			return Zombie_PigmanLV1.spawn(loc, ChatColor.RED + "Zombie_Pigman LV1");
		case 2:
			return Zombie_PigmanLV2.spawn(loc, ChatColor.RED + "Zombie_Pigman LV2");
		case 3:
			return Zombie_PigmanLV3.spawn(loc, ChatColor.RED + "Zombie_Pigman LV3");
		case 4:
			return Zombie_PigmanLV4.spawn(loc, ChatColor.RED + "Zombie_Pigman LV4");
		case 5:
			return Zombie_PigmanLV5.spawn(loc, ChatColor.RED + "Zombie_Pigman LV5");
		}
		return null;
	}

	
	public static void control_Mobs(){
		if(PVE.main == null){
			return;
		}
		for(Arena arena : PVE.main.arenas){
			for(LivingEntity le : arena.alive){
				Monster mob = (Monster) le;
				Player closest = null;
				for(Player player : arena.group.members){
					if((closest == null || player.getLocation().distance(mob.getLocation()) < closest.getLocation().distance(mob.getLocation())) && !player.getGameMode().equals(GameMode.SPECTATOR)){
						closest = player;
					}
				}
				mob.setTarget(closest);
			}
		}
	}
	
}
