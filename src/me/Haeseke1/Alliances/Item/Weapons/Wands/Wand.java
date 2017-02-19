package me.Haeseke1.Alliances.Item.Weapons.Wands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import net.md_5.bungee.api.ChatColor;

public class Wand {

	public Player user;
	public static String name;
	public static Material wand_type;
	public ItemStack item;
	
	public double mana;
	
	public aPlayer APlayer;
	
   @SuppressWarnings("static-access")
public Wand(String name,Player user, Material wand_type, double mana){
		this.user = user;
		this.name = name;
		this.name = this.name.replace("_", " ");
		this.wand_type = wand_type;
		this.mana = mana;
		this.APlayer = APlayerManager.getAPlayer(user);
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
	
	public static void giveWand(String name,Player player, String color){
		   color = ChatColor.translateAlternateColorCodes('&', color);
		   ItemStack wand = new ItemStack(Material.STICK);
		   ItemMeta mwand = wand.getItemMeta();
		   Bukkit.broadcastMessage(name);
		   mwand.setDisplayName(color + name);
		   wand.setItemMeta(mwand);
		   player.getInventory().addItem(wand);
		}
   
}
