package me.Haeseke1.Alliances.Alliance.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Alliance.AllianceType;
import me.Haeseke1.Alliances.CustomEntity.Zombie.ZombieLV1;
import me.Haeseke1.Alliances.Economy.Coins;
import me.Haeseke1.Alliances.Main.Main;

public class Caith_Sith implements Listener {
	
	public static int cost;
	
	public static List<Player> players = new ArrayList<Player>();
	
	
	public static ItemStack createItem(Player player){
		ItemStack i = new ItemStack(Material.MONSTER_EGG, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Caith Sith");
		List<String> list = new ArrayList<String>();
		if(Coins.getPlayerCoins(player) >= cost){
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.GREEN + cost + "$");
		}else{
			list.add(ChatColor.DARK_PURPLE + "Cost: " + ChatColor.RED + cost + "$");
		}
		list.add(ChatColor.DARK_PURPLE + "Bonus:");
		list.add(ChatColor.DARK_PURPLE + "- Mobs will fight by your side");
		im.setLore(list);
		i.setItemMeta(im);
		return i;
	}
	
	
	public static void update(){
		List<Player> recent_Players = new ArrayList<Player>();
		for(Player player : Bukkit.getOnlinePlayers()){
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli == null){
				continue;
			}
			if(alli.getType().equals(AllianceType.CAIT_SITH)){
				recent_Players.add(player);
			}
		}
		players = recent_Players;
	}
	
	
	
	public List<Player> active_Players = new ArrayList<Player>();
	public HashMap<Player,Zombie> zombies = new HashMap<Player,Zombie>();
	
	@EventHandler
	private void entityhitentity(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof LivingEntity)){
			return;
		}
		Player player = (Player) event.getEntity();
		if(!AllianceManager.playerIsInAlli(player)){
			return;
		}
		Alliance alli = AllianceManager.getAlliance(player);
		if(!alli.getType().equals(AllianceType.CAIT_SITH)){
			return;
		}
		if(active_Players.contains(player)){
			return;
		}
		active_Players.add(player);
		Zombie zombie = ZombieLV1.spawn(player.getLocation(), ChatColor.AQUA + player.getName() + "'s " + ChatColor.GOLD + "Beast");
		LivingEntity target = (LivingEntity) event.getDamager();
		zombie.setTarget(target);
		zombies.put(player, zombie);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				if(zombies.containsKey(player)){
					Zombie zombie = zombies.get(player);
					if(zombie != null){
						zombie.remove();
					}
					zombies.remove(player);
				}
				active_Players.remove(player);
			}
		}, 200);
	}
	
	@EventHandler
	private void unloadChunk(ChunkUnloadEvent event){
		for(Entity entity : event.getChunk().getEntities()){
			if(entity instanceof Zombie){
				if(entity.getCustomName() == null){
					continue;
				}
				if(entity.getCustomName().endsWith(ChatColor.GOLD + "Beast")){
					entity.remove();
				}
			}
		}
	}
	
	@EventHandler
	private void playerLeave(PlayerQuitEvent event){
		if(zombies.containsKey(event.getPlayer())){
			Zombie zombie = zombies.get(event.getPlayer());
			if(zombie != null){
				zombie.remove();
			}
			zombies.remove(event.getPlayer());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
