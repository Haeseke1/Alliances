package me.Haeseke1.Alliances.LeaderBoard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.Haeseke1.Alliances.Utils.MessageManager;

public class Head_Board implements Listener{
	
	public Sign sign;
	public Skull skull;
	
	public static List<Head_Board> headboards = new ArrayList<Head_Board>();
	
	public Head_Board() {
		
	}
	
	public Head_Board(Sign sign, Skull skull) {
		this.sign = sign;
		this.skull = skull;
		headboards.add(this);
	}
	
	
	public void setLineMessage(int line, String message){
		if(message == null){
			sign.setLine(line, "");
			return;
		}
		sign.setLine(line, ChatColor.translateAlternateColorCodes('&', message));
		sign.update();
	}
	
	public void setOwner(String owner){
		skull.setSkullType(SkullType.PLAYER);
		skull.setOwner(owner);
		skull.update();
	}
	
	
	
	
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getState() instanceof Sign || event.getBlock().getState() instanceof Skull){
			for(Head_Board headboard : headboards){
				if(event.getBlock().getState() instanceof Sign){
					Sign sign = (Sign) event.getBlock().getState();
					if(headboard.sign.equals(sign)){
						event.setCancelled(true);
						String message = "&cYou can't break blocks from the leaderboard";
						MessageManager.sendMessage(event.getPlayer(), message);
					}
				}
				if(event.getBlock().getState() instanceof Skull){
					Skull skull = (Skull) event.getBlock().getState();
					if(headboard.skull.equals(skull)){
						event.setCancelled(true);
						String message = "&cYou can't break blocks from the leaderboard";
						MessageManager.sendMessage(event.getPlayer(), message);
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
			for(Head_Board headboard : headboards){
				if(block.getState() instanceof Sign){
					Sign sign = (Sign) block.getState();
					if(headboard.sign.equals(sign)){
						event.setCancelled(true);
					}
				}
				if(block.getState() instanceof Skull){
					Skull skull = (Skull) block.getState();
					if(headboard.skull.equals(skull)){
						event.setCancelled(true);
					}
				}
			}
        }
    }
	
	
	
	
}
