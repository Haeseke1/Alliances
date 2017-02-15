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

public class Soul_Stealer extends Sword implements Listener{

	public Soul_Stealer(Player player, int cooldown) {
		super("Soul Stealer", player, cooldown);
		
	}
	
	public Soul_Stealer() {
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 5){
			strength = 5;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GRAY + "Soul Stealer " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Gives you a chance to steal the health of your enemies!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public static int getStrength(String name) throws Exception{
		name = ChatColor.translateAlternateColorCodes('&', name);
		return Integer.parseInt("" + name.charAt(name.length() - 1));
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
		if(!displayname.startsWith(ChatColor.GRAY + "Soul Stealer ")){
			return;
		}
		int number = new Random().nextInt(10) + 1;
		try {
			int strength = getStrength(displayname);
			if(!SwordManager.hasSword(player.getUniqueId(), "Soul Stealer") && strength >= number){
			    Soul_Stealer warrior_sword = new Soul_Stealer(player,1);
			    warrior_sword.stealHealth(event, Sound.LEVEL_UP);
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	public void stealHealth(EntityDamageByEntityEvent event, Sound sound){
		if(player.getHealth() + event.getFinalDamage() <= player.getMaxHealth()){
			player.setHealth(player.getHealth() + event.getFinalDamage());
		}else{
			player.setHealth(player.getMaxHealth());
		}
		SoundManager.playSoundToPlayer(sound, player);
	}
	
	
}
