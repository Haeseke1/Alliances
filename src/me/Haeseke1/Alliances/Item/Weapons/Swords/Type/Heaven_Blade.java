package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
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
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Heaven_Blade extends Sword implements Listener{

	public int strength;
	
	public Heaven_Blade(Player player, int cooldown, int strength) {
		super("Heaven Blade", player, cooldown);
		this.strength = strength;
	}
	
	public Heaven_Blade(){
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 3){
			strength = 3;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Heaven Blade " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Strike your enemies multiple times with lightning!");
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
		if(!displayname.startsWith(ChatColor.RED + "Heaven Blade ")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Heaven Blade")){
			try{
				int strenght = getStrength(displayname);
				if(strenght > 3){
					strenght = 3;
				}
				if(new Random().nextInt(10) < strenght){
					Heaven_Blade fatal_blade = new Heaven_Blade(player,1, strenght);
					fatal_blade.fataldamageEntity(player, le, strenght);
				}
			}catch(Exception e){
				return;
			}
		}
	}
	
	List<Integer> schedulers = new ArrayList<Integer>();
	
	public void fataldamageEntity(Player player, LivingEntity le, int strength){
		SoundManager.playSound(Sound.ENDERDRAGON_GROWL, player.getLocation());
		schedulers.add(Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
			int i = 0;
			
			@Override
			public void run() {
				if(i == 4){
					Bukkit.getScheduler().cancelTask(schedulers.get(0));
					schedulers.remove(0);
					return;
				}
				le.getWorld().strikeLightning(le.getLocation());
				i++;
			}
		}, 40, 40));
	}
}
