package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Warrior_Sword extends Sword implements Listener{

	public Warrior_Sword(Player player, int cooldown) {
		super("Warrior Sword", player, cooldown);
		
	}
	
	public Warrior_Sword() {
		
	}
	
	public static ItemStack getItem(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Warrior Sword");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Gives you a chance on hit to heal half a hearth!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	
	@EventHandler
	public static void onRightClick(EntityDamageByEntityEvent event){
		if(!(event.getDamager() instanceof Player)){
			return;
		}
		Player player = (Player) event.getDamager();
		if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR){
			return;
		}
		if(!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() && !player.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String displayname = player.getItemInHand().getItemMeta().getDisplayName();
		if(!displayname.equals(ChatColor.RED + "Warrior Sword")){
			return;
		}
		int number = new Random().nextInt(10);
		if(!SwordManager.hasSword(player.getUniqueId(), "Warrior Sword") && 0 == number){
		    Warrior_Sword warrior_sword = new Warrior_Sword(player,1);
		    warrior_sword.healPlayer(Sound.LEVEL_UP);
		}
	}
	
	
	public void healPlayer(Sound sound){
		if(player.getHealth() <= player.getMaxHealth() - 1){
			player.setHealth(player.getHealth() + 1);
		}
		SoundManager.playSoundToPlayer(sound, player);
	}
	
	
}
