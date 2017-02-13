package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
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

public class Wither_Blade extends Sword implements Listener{

	public int strength;
	
	public Wither_Blade(Player player, int cooldown, int strength) {
		super("Wither Sword", player, cooldown);
		this.strength = strength;
	}
	
	public Wither_Blade(){
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 3){
			strength = 3;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wither Sword " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Give your enemies wither!");
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
		LivingEntity le = (LivingEntity) event.getEntity();
		Player player = (Player) event.getDamager();
		if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR){
			return;
		}
		if(!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() && !player.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String displayname = player.getItemInHand().getItemMeta().getDisplayName();
		if(!displayname.startsWith(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Wither Sword ")){
			return;
		}
		int number = new Random().nextInt(10);
		if(!SwordManager.hasSword(player.getUniqueId(), "Wither Sword") && 0 == number){
			try{
				int strenght = getStrength(displayname);
				if(strenght > 3){
					strenght = 3;
				}
				Wither_Blade wither_blade = new Wither_Blade(player,1, strenght);
				wither_blade.witherEntity(Sound.WITHER_HURT, le);
			}catch(Exception e){
				return;
			}
		   
		}
	}
	
	
	public void witherEntity(Sound sound, LivingEntity le){
		le.removePotionEffect(PotionEffectType.WITHER);
		le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, strength - 1), false);
		SoundManager.playSound(sound, le.getLocation());
	}
	
	
}
