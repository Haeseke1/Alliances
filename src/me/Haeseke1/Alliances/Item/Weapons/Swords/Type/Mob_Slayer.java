package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;

public class Mob_Slayer extends Sword implements Listener{

	public int strength;
	
	public Mob_Slayer(Player player, int cooldown, int strength) {
		super("Mob Slayer", player, cooldown);
		this.strength = strength;
	}
	
	public Mob_Slayer(){
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 5){
			strength = 5;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "Mob Slayer " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "More damage to mobs!");
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
		if(!(event.getDamager() instanceof Player) || event.getEntity() instanceof Player){
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
		if(!displayname.startsWith(ChatColor.YELLOW + "Mob Slayer ")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Mob Slayer")){
			try{
				int strenght = getStrength(displayname);
				if(strenght > 5){
					strenght = 5;
				}
				event.setDamage(event.getDamage() + (strenght * 2));
			}catch(Exception e){
				return;
			}
		}
	}
}
