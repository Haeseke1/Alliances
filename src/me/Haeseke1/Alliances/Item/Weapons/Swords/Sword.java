package me.Haeseke1.Alliances.Item.Weapons.Swords;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Item.Weapons.Swords.Type.SwordEffects;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Sword{
	
	public static HashMap<UUID,List<Sword>> cooldowns = new HashMap<>();
	
	public String name;
	public Player player;
	public int cooldown;
	public String effect;
	public PotionEffectType type;
	public UUID playerUuid;
	
	public Sword(String name, Player player, int cooldown,String effect) {
		this.name = name.replace(" ", "_");
		this.player = player;
		this.cooldown = cooldown;
		this.effect = effect;
	}

	public boolean itemHasEffect(ItemStack item, String effect){
		if(!(item.hasItemMeta())){ return false; }
		if(!(item.getItemMeta().hasLore())){ return false;}
		List<String> lore = item.getItemMeta().getLore();
		for(String loreItem: lore){
			if(ChatColor.stripColor(loreItem).equalsIgnoreCase(effect)){
				return true;
			}
		}
		return false;
	}
   
	public boolean hasSwordName(ItemStack item){
		if(!(item.hasItemMeta())){ return false; }
		if(!(item.getItemMeta().hasDisplayName())){ return false;}
		if(ChatColor.stripColor(item.getItemMeta().getDisplayName().replace("_", " ")).equalsIgnoreCase(name.replace("_", " "))){return true;}
		return false;
	}
	
	public void playerRemoveEffect(PotionEffectType currentEffect){
		for(PotionEffect effect: player.getActivePotionEffects()){
			if(effect.getType().equals(currentEffect)){
				player.removePotionEffect(effect.getType());
				return;
			}
		}
		return;
	}
	
	public void giveEffect(int duration,int strength,Sound sound, boolean hidePlayer) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		type = SwordEffects.getEffect(SwordEffects.valueOf(name));
		if(this.hasSwordName(player.getItemInHand())){
			if(this.itemHasEffect(player.getItemInHand(), this.effect)){
			    if(SwordManager.hasSword(player.getUniqueId(), name)){
			    	MessageManager.sendMessage(player, "&cThis ability is cooling down (&6" + SwordManager.getSword(player.getUniqueId(), name).cooldown + "&cs left)"); 
			    	return;
			    	}
					playerRemoveEffect(type);
					player.addPotionEffect(new PotionEffect(type,duration*20,strength,true));
					if(sound != null){ SoundManager.playSoundToPlayer(sound, player);}
					SwordManager.putSword(player.getUniqueId(), this);
					if(hidePlayer){
						for(Player player: Bukkit.getOnlinePlayers()){
							Alliance al = AllianceManager.getAlliance(this.player);
							if(al.equals(AllianceManager.getAlliance(player))){
								continue;
							}
							player.hidePlayer(this.player);
						}
					}
			  }
		 }
	}
	
	
	public void cooldown(UUID uuid){
		cooldown = cooldown - 1;
		if(cooldown <= 0){
			SwordManager.removeSword(uuid, this);
		}
	}
	
	
	
	
}
