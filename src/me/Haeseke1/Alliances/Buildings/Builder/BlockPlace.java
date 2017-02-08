package me.Haeseke1.Alliances.Buildings.Builder;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class BlockPlace implements Listener{

	
	@EventHandler
	private void placeBlock(BlockPlaceEvent event){
		if(!event.getItemInHand().getType().equals(Material.LAPIS_ORE)){
			return;
		}
		if(!event.getItemInHand().hasItemMeta() || !event.getItemInHand().getItemMeta().hasDisplayName() || !event.getItemInHand().getItemMeta().hasLore()){
			return;
		}
		String name = ChatColor.stripColor(event.getItemInHand().getItemMeta().getDisplayName());
		if(BuilderManager.nameExist(name)){
			event.setCancelled(true);
			Chunk chunk = event.getBlock().getChunk();
			if(!TownManager.isClaimed(chunk)){
				MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "You can only place these buildings in a town!");
				return;
			}
			Town town = TownManager.getTown(chunk);
			if(!town.canBuild(chunk)){
				MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "There is already a building in this chunk!");
				return;
			}
			ItemStack item = event.getPlayer().getItemInHand();
			if(item.getAmount() == 1){
				event.getPlayer().setItemInHand(null);
			}else{
				item.setAmount(item.getAmount() - 1);
				event.getPlayer().setItemInHand(item);
			}
			
			Builder b = BuilderManager.getBuilder(name);
			Building building = b.construct(chunk, event.getBlock().getY());
			town.addBuilding(building);
			MessageManager.sendMessage(event.getPlayer(), ChatColor.GREEN + "Constructing building!");
		}
	}
}
