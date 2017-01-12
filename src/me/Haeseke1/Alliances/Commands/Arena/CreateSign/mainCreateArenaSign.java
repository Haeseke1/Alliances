package me.Haeseke1.Alliances.Commands.Arena.CreateSign;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Arena.Arena_Sign;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class mainCreateArenaSign {
	
	
	public static HashMap<Player,Sign> createArena_Sign = new HashMap<Player,Sign>();
	public static HashMap<Player,Integer> createArena_SignC = new HashMap<Player,Integer>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void onCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("You need to be a player to do this command!");
			return;
		}
		Player player = (Player) sender;
		try{
			createArena_SignC.put(player, Integer.parseInt(args[2]));
		}catch(Exception e){
			MessageManager.sendAlertMessage("This is not a number!");
			return;
		}
		
		
		Block b = player.getTargetBlock((Set)null, 200);
		if(b.getType().equals(Material.SIGN) || b.getType().equals(Material.WALL_SIGN) || b.getType().equals(Material.SIGN_POST)){
			Sign sign = (Sign) b.getState();
			if(Arena_Sign.signs.contains(sign)){
				MessageManager.sendAlertMessage("This sign is already in use!");
			}else{
				createArena_Sign.put(player, sign);
				InventoryEvents.createInventory(player);
			}
		}else{
			MessageManager.sendAlertMessage("You need to look at a sign!");
		}
	}
	
	
	
}
