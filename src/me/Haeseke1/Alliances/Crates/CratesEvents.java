package me.Haeseke1.Alliances.Crates;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Crates.Crate.CountDown;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class CratesEvents implements Listener {
	
	@EventHandler
	private void onPlayerRightClick(PlayerInteractEvent event){
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			return;
		}
		if(event.getClickedBlock().getType() != Material.CHEST){
			return;
		}
		Player player = event.getPlayer();
		for(Crate crate : Crate.crates){
			if(crate.locs.contains(event.getClickedBlock().getLocation())){
				event.setCancelled(true);
				if(crate.key == null){
					MessageManager.sendMessage(player, ChatColor.RED + "You don't have a key to open this crate!");
					return;
				}
				if(crate.key.checkItemStack(event.getItem())){
					ItemStack item = player.getItemInHand();
					if(item.getAmount() == 1){
						player.setItemInHand(new ItemStack(Material.AIR));
					}else{
						item.setAmount(item.getAmount() - 1);
						player.setItemInHand(item);
					}
					crate.openCrate(player);
					return;
				}
				MessageManager.sendMessage(player, ChatColor.RED + "You don't have a key to open this crate!");
			}
		}
	}
	
	
	@EventHandler
	private void closeInv(InventoryCloseEvent event){
		Player player = (Player) event.getPlayer();
		if(!Crate.players.containsKey(player)){
			return;
		}
		CountDown cd = Crate.players.get(player);
		cd.forceReward();
	}
	
	@EventHandler
	private void clickInv(InventoryClickEvent event){
		Player player =  (Player) event.getWhoClicked();
		if(Crate.players.containsKey(player)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	private void blockBreak(BlockBreakEvent event){
		for(Crate crate : Crate.crates){
			if(crate.locs.contains(event.getBlock().getLocation())){
				event.setCancelled(true);
				MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "You cannot break crates!");
			}
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
    		for(Crate crate : Crate.crates){
    			if(crate.locs.contains(block.getLocation())){
    				event.setCancelled(true);
    			}
    		}
        }
    }
}
