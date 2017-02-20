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

public class Blade_Of_Zeus extends Sword implements Listener{

	
	public Blade_Of_Zeus(Player player, int cooldown) {
		super("Blade_Of_Zeus", player, cooldown);
	}
	
	
	
	public Blade_Of_Zeus() {
		
	}

	public static ItemStack getItem(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Blade of Zeus");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Hit your enemies with lighting!");
		lore.add(ChatColor.RED + "15 SECONDS COOLDOWN");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

	@EventHandler
	public void onRightClick(EntityDamageByEntityEvent event){
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
		if(!displayname.startsWith(ChatColor.LIGHT_PURPLE + "Blade of Zeus")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Blade_Of_Zeus")){
			try{
				Blade_Of_Zeus bladeofzeus = new Blade_Of_Zeus(player,15);
				bladeofzeus.playEffect(event);
			}catch(Exception e){
				return;
			}
		}
	}
	
	
	public void playEffect(EntityDamageByEntityEvent event){
		event.setDamage(event.getDamage() + 6);
		event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
	}
}
