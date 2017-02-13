package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
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
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.ParticleManager;

public class Fatal_Blade extends Sword implements Listener{

	public int strength;
	private static HashMap<Player, Extension> damaged = new HashMap<>();
	
	public Fatal_Blade(Player player, int cooldown, int strength) {
		super("Fatal Sword", player, cooldown);
		this.strength = strength;
	}
	
	public Fatal_Blade(){
		
	}
	
	public static ItemStack getItem(int strength){
		if(strength > 3){
			strength = 3;
		}
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.RED + "Fatal Blade " + strength);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Make your enemies bleed! This will do extra damage after your last hit!");
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
		if(!displayname.startsWith(ChatColor.RED + "Fatal Blade ")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Fatal Sword")){
			try{
				int strenght = getStrength(displayname);
				if(strenght > 3){
					strenght = 3;
				}
				Fatal_Blade fatal_blade = new Fatal_Blade(player,1, strenght);
				fatal_blade.fataldamageEntity(player, le, strenght);
			}catch(Exception e){
				return;
			}
		}
	}
	
	
	public void fataldamageEntity(Player player, LivingEntity le, int strength){
		if(damaged.containsKey(player)){
			Extension e = damaged.get(player);
			e.isLivingEntity(le);
			return;
		}
		damaged.put(player, new Extension(player, le, strength));
	}
	
	
	public class Extension {
		
		public Player damager;
		public LivingEntity damaged;
		public int scheduler;
		public int strength;
		
		public Extension(Player damager, LivingEntity damaged, int strength) {
			this.damager = damager;
			this.damaged = damaged;
			this.strength = strength;
			startScheduler();
		}
		
		private void startScheduler(){
			scheduler = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					ParticleManager.playParticle(Effect.STEP_SOUND, damaged.getEyeLocation(), Material.REDSTONE_BLOCK.getId(), 100);
					damaged.damage(strength * 3);
					Fatal_Blade.damaged.remove(player);
				}
			},80);
		}
		
		private void resetScheduler(){
			Bukkit.getScheduler().cancelTask(scheduler);
			startScheduler();
		}
		
		public void isLivingEntity(LivingEntity le){
			if(le.equals(this.damaged)){
				resetScheduler();
			}
		}
	}
	
	
	
}
