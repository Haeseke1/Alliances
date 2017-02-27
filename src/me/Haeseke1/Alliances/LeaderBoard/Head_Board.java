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

import me.Haeseke1.Alliances.Outpost.OutpostManager;
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
	}
	
	
	public void setLineMessage(int line, String message){
		sign.setLine(line, ChatColor.translateAlternateColorCodes('&', message));
		sign.update();
	}
	
	public void setOwner(String owner){
		skull.setSkullType(SkullType.PLAYER);
		skull.setOwner(owner);
	}
	
	@EventHandler
	private void onBlockBreak(BlockBreakEvent event){
		if(event.getBlock().getState() instanceof Sign || event.getBlock().getState() instanceof Skull){
			event.setCancelled(true);
			String message = "You can't break blocks from the leaderboard";
			MessageManager.sendAlertMessage(message);
		}
	}
	
	@EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        List<Block> destroyed = event.blockList();
        Iterator<Block> it = destroyed.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(OutpostManager.checkLocation(block.getLocation())){
            	it.remove();
            }
        }
    }
	
	
	
	
}
