package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;

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

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Karma_Blade extends Sword implements Listener{

	public Karma_Blade(Player player, int cooldown) {
		super("Karma Blade", player, cooldown);
		
	}
	
	public Karma_Blade() {
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 3){
			strength = 3;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.AQUA + "Karma Blade " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "When blocking with this sword,");
		lore.add(ChatColor.DARK_GREEN + "will deal damage back!");
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
		if(!displayname.startsWith(ChatColor.AQUA + "Karma Blade ") || !player.isBlocking() || !(event.getDamager() instanceof LivingEntity)){
			return;
		}
		try {
			int strength = getStrength(displayname);
			if(!SwordManager.hasSword(player.getUniqueId(), "Karma Blade")){
			    Karma_Blade swift_blade = new Karma_Blade(player,1);
			    swift_blade.giveDamage((LivingEntity) event.getDamager(), Sound.BLAZE_HIT, strength);
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	public void giveDamage(LivingEntity player, Sound sound, int strength){
		player.damage(strength * 2);
		SoundManager.playSound(sound, player.getLocation());
	}
	
	
}
