package me.Haeseke1.Alliances.Buildings.Builder;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

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
			Builder b = BuilderManager.getBuilder(name);
			b.construct(event.getBlock().getChunk(), event.getBlock().getY());
			MessageManager.sendMessage(event.getPlayer(), ChatColor.GREEN + "Constructing building!");
		}
	}
}
