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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Swift_Blade extends Sword implements Listener{

	public Swift_Blade(Player player, int cooldown) {
		super("Swift Blade", player, cooldown);
		
	}
	
	public Swift_Blade() {
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 3){
			strength = 3;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Swift Blade " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Hit faster then the wind!");
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
		if(!displayname.startsWith(ChatColor.AQUA + "Swift Blade ")){
			return;
		}
		int number = new Random().nextInt(8) + 1;
		try {
			int strength = getStrength(displayname);
			if(!SwordManager.hasSword(player.getUniqueId(), "Swift Blade") && 1 == number){
			    Swift_Blade swift_blade = new Swift_Blade(player,1);
			    swift_blade.giveEffect(player, Sound.ANVIL_LAND, strength);
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	public void giveEffect(Player player, Sound sound, int strength){
		player.removePotionEffect(PotionEffectType.FAST_DIGGING);
		player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, strength - 1));
		SoundManager.playSoundToPlayer(sound, player);
	}
	
	
}
