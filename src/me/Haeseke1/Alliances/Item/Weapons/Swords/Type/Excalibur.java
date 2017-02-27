package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;

public class Excalibur extends Sword implements Listener{

	public Excalibur(Player player, int cooldown) {
		super("Excalibur", player, cooldown);
		
	}
	
	public Excalibur() {
		
	}
	
	public static ItemStack getItem(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Excalibur");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Most legendary sword!");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
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
		if(!displayname.startsWith(ChatColor.GOLD + "" + ChatColor.BOLD + "Excalibur")){
			return;
		}
		try {
			if(!SwordManager.hasSword(player.getUniqueId(), "Excalibur")){
				((LivingEntity)event.getEntity()).damage(10000);;
				event.getEntity().sendMessage(ChatColor.DARK_RED + "You got hit by excalibur!");
				player.setItemInHand(new ItemStack(Material.AIR));
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	
}
