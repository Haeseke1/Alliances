package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Wand;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Scheduler.DelayedBomb;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Time_Bomb_Wand extends Wand{

	public org.bukkit.util.Vector dir;
	public static List<Block> blocks = new ArrayList<>();
	
	public Time_Bomb_Wand(String name, Player user, Material wand_type, double mana) {
		super(name, user, wand_type, mana);
		dir = user.getLocation().getDirection();
	}

	@SuppressWarnings("deprecation")
	public void placeTimeBomb(){
		if(!this.hasName()) return;
		if(!user.isOnGround()){
			MessageManager.sendMessage(user, "&cYou can't use this wand in the air");
			return;
		}
		if(this.user.getLocation().getY() % 1 != 0) { 
			MessageManager.sendMessage(user, "&cYou need to stand on a solid block to use this wand");
			return;
		}
		if(APlayer.removeMana(mana)){
		Block block = user.getWorld().getBlockAt(user.getLocation());
		block.setType(Material.JUKEBOX);
		blocks.add(block);
		ArmorStand stand = (ArmorStand) user.getWorld().spawnEntity(new Location(user.getWorld(),user.getLocation().getX(),user.getLocation().getY() + 1,user.getLocation().getZ()), EntityType.ARMOR_STAND);
		stand.setVisible(false);
		stand.setCustomNameVisible(true);
		DelayedBomb bomb = new DelayedBomb(10,10,true,block,stand);
		bomb.runTaskTimer(Main.plugin, 0, 20);
		user.teleport(new Location(user.getWorld(),user.getLocation().getX(),user.getLocation().getY() + 2,user.getLocation().getZ()));
		}
	}
	
}
