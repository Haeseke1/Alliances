package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Double_Strike extends Sword implements Listener{

	public Double_Strike(Player player, int cooldown) {
		super("Double Strike", player, cooldown);
		
	}
	
	public Double_Strike() {
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 5){
			strength = 5;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.DARK_RED + "Double Strike " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Hit 2 targets at once!");
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
		if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof LivingEntity)){
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
		if(!displayname.startsWith(ChatColor.DARK_RED + "Double Strike ")){
			return;
		}
		int number = new Random().nextInt(10) + 1;
		try {
			int strength = getStrength(displayname);
			if(!SwordManager.hasSword(player.getUniqueId(), "Double Strike") && strength <= number){
			    Double_Strike swift_blade = new Double_Strike(player,1);
			    swift_blade.giveDamage(player,(LivingEntity) event.getEntity(), Sound.BLAZE_HIT, (int) event.getDamage());
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	public void giveDamage(Player damager, LivingEntity damaged, Sound sound, int damage){
		for(Entity e : damaged.getNearbyEntities(5, 5, 5)){
			if(e instanceof Player){
				Player player = (Player) e;
				if(player.equals(damager)){
					continue;
				}
			}
			if(e instanceof LivingEntity){
				((LivingEntity) e).damage(damage);
				SoundManager.playSound(sound, e.getLocation());
				return;
			}
		}
		
	}
	
	
}
