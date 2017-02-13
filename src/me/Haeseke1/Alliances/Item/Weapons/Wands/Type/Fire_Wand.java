package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Utils.SoundManager;
import net.md_5.bungee.api.ChatColor;

public class Fire_Wand{
	
	public Player user;
	public String name;
	public Vector dir;
	public ItemStack item;
	public Material wand_type;
	public double mana = 1.5;
	
	public Fire_Wand(Player user, Material wand_type){
		this.user = user;
		this.name = this.getClass().getSimpleName();
		this.name = this.name.replace("_", " ");
		dir = user.getLocation().getDirection();
		this.wand_type = wand_type;
	}
	
	public boolean hasName(){
		item = getWand();
	    if(item == null) return false;
		if(ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(name)){
			return true;
		}
		return false;
	}
	
	public ItemStack getWand(){
		if(user.getItemInHand() == null) return null;
		if(!user.getItemInHand().getType().equals(wand_type)) return null;
		if(!user.getItemInHand().hasItemMeta()) return null;
		if(!user.getItemInHand().getItemMeta().hasDisplayName()) return null;
	    return user.getItemInHand();
	}

	public void launchProjectile(Sound sound){
		if(hasName()){
		aPlayer APlayer = APlayerManager.getAPlayer(user);
		if(APlayer.removeMana(mana)){
		Fireball fireball = user.launchProjectile(Fireball.class,dir);
		fireball.setIsIncendiary(false);
		if(sound == null){ return;}
		SoundManager.playSoundToPlayer(sound, user);
		}
		}
	}
	
	public void giveWand(Player player){
	   ItemStack wand = new ItemStack(wand_type,1);
	   wand.getItemMeta().setDisplayName("&c" + name);
	   wand.setItemMeta(wand.getItemMeta());
	   player.getInventory().addItem(wand);
	}
	
}
