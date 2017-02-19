package me.Haeseke1.Alliances.Item.Totems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class HealingTotem {
	
	public static HashMap<UUID,HealingTotem> htotems = new HashMap<>();
	public static List<Block> totemBlocks = new ArrayList<>();
	public static List<ArmorStand> armorStands = new ArrayList<>();
	
	public List<Block> blocks = new ArrayList<>();
	
	public ArmorStand armorstand;
	
	public Player owner;

	public Block block;
	
	public final int max_health = 50;
	public int health = max_health;
	
	public HealingTotem(Player owner){
		this.owner = owner;
	}
	
	public void spawnTotem(){
		if(htotems.containsKey(owner.getUniqueId())){ MessageManager.sendMessage(owner, "&cYou can't place two totems at the same time");return;}
        Location loc = owner.getLocation();
        
        block = loc.getBlock();
        block.getState().update();
        block.setType(Material.BREWING_STAND);
        totemBlocks.add(block);
        blocks.add(block);
        
        Location loc2 = new Location(loc.getWorld(),loc.getX(),loc.getY() + 1,loc.getZ());
        block = loc2.getBlock();
        block.getState().update();
        block.setType(Material.EMERALD_BLOCK);
        totemBlocks.add(block);
        blocks.add(block);
        
        Location loc3 = new Location(loc.getWorld(),loc.getX(),loc.getY() + 2,loc.getZ());
        block = loc3.getBlock();
        ArmorStand stand = (ArmorStand) block.getWorld().spawnEntity(loc3, EntityType.ARMOR_STAND);
        stand.setCustomName(ChatColor.translateAlternateColorCodes('&',"&2" + owner.getName() + "'s healing totem &8[&9" + health + "&8/&9" + this.max_health + "&8]"));
        stand.setCustomNameVisible(true);
        stand.setVisible(false);
        stand.setFallDistance(0);
        armorStands.add(stand);
        armorstand = stand;
        
        loc.setY(loc.getY() + 3);
        owner.teleport(loc,TeleportCause.ENDER_PEARL);
	    htotems.put(owner.getUniqueId(), this);
	}
	
	public static void removeAllTotems(){
		for(Block block: totemBlocks){
			block.setType(Material.AIR);
            block.getState().update();
		}
		for(ArmorStand stand: armorStands){
			stand.remove();
		}
		totemBlocks.clear();
		armorStands.clear();
	}
	
	public void removeTotem(){
		armorstand.remove();
		for(Block block: blocks){
			block.setType(Material.AIR);
			block.getState().update();
		}
		htotems.remove(owner.getUniqueId()); 
		SoundManager.playSoundToPlayer(Sound.NOTE_BASS, owner);
		MessageManager.sendMessage(owner, "&cYour &2healing totem&c has been destroyed!");
	}
	
	public void damage(){
		this.health = this.health - 1;
		if(this.health == 0){
			this.removeTotem();
			return;
		}
		owner.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour totem is under attack"));
		SoundManager.playSound(Sound.BLAZE_HIT, block.getLocation());
        armorstand.setCustomName(ChatColor.translateAlternateColorCodes('&',"&2" + owner.getName() + "'s healing totem &8[&9" + health + "&8/&9" + this.max_health +"&8]"));
	}
	
}
