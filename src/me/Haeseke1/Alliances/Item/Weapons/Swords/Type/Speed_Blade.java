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

public class Speed_Blade extends Sword implements Listener{

	public Speed_Blade(Player player, int cooldown) {
		super("Speed Blade", player, cooldown);
		
	}
	
	public Speed_Blade() {
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 5){
			strength = 5;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Speed Blade " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Run faster away from your enemies");
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
		if(!(event.getEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getEntity();
		if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR){
			return;
		}
		if(!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() && !player.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String displayname = player.getItemInHand().getItemMeta().getDisplayName();
		if(!displayname.startsWith(ChatColor.AQUA + "Speed Blade ")){
			return;
		}
		int random = new Random().nextInt(8);
		try {
			int strength = getStrength(displayname);
			if(!SwordManager.hasSword(player.getUniqueId(), "Speed Blade") && random == 0){
			    Speed_Blade swift_blade = new Speed_Blade(player,1);
			    swift_blade.giveeffect(player, Sound.ENDERDRAGON_WINGS, strength);
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	public void giveeffect(Player player, Sound sound, int strength){
		player.removePotionEffect(PotionEffectType.SPEED);
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, strength * 20, 1));
		SoundManager.playSoundToPlayer(sound, player);
	}
	
	
}
