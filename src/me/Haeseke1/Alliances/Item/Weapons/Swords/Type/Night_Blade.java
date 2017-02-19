package me.Haeseke1.Alliances.Item.Weapons.Swords.Type;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Sword;
import me.Haeseke1.Alliances.Item.Weapons.Swords.SwordManager;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Night_Blade extends Sword implements Listener{

	
	public Night_Blade(Player player, int cooldown) {
		super("Night Blade", player, cooldown);
	}
	
	
	
	public Night_Blade() {
		
	}

	public static ItemStack getItem(){
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.LIGHT_PURPLE + "Night Blade");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GREEN + "Right click to become one with the night!");
		lore.add(ChatColor.RED + "120 SECONDS COOLDOWN");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
	    Player player = event.getPlayer();
	    if(player.getItemInHand() == null){
	    	return;
	    }
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
			return;
		}
		if(!player.getItemInHand().hasItemMeta() || !player.getItemInHand().getItemMeta().hasDisplayName() && !player.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String displayname = player.getItemInHand().getItemMeta().getDisplayName();
		if(!displayname.equals(ChatColor.LIGHT_PURPLE + "Night Blade")){
			return;
		}
		if(!SwordManager.hasSword(player.getUniqueId(), "Night Blade")){
			Night_Blade night_blade = new Night_Blade(player, 120);
			night_blade.giveEffect(Sound.ENDERDRAGON_WINGS);
		}else{
			MessageManager.sendMessage(player, "&cThis ability is cooling down (&6" + SwordManager.getSword(player.getUniqueId(), "Night Blade").cooldown + "&cs left)"); 
		}
	}
	
	
	public void giveEffect(Sound sound){
		playerRemoveEffect(PotionEffectType.INVISIBILITY);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,160,1,false));
		if(sound != null){ 
			SoundManager.playSoundToPlayer(sound, player);
		}
		for(Player player: Bukkit.getOnlinePlayers()){
			Alliance al = AllianceManager.getAlliance(this.player);
			if(al.equals(AllianceManager.getAlliance(player))){
				continue;
			}
			player.hidePlayer(this.player);
			final Player hider = this.player;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				
				@Override
				public void run() {
					player.showPlayer(hider);
					
				}
			},160);
		}
	}
}
