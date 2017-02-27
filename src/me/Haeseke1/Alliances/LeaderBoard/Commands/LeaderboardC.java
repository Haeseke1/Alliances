package me.Haeseke1.Alliances.LeaderBoard.Commands;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.LeaderBoard.Head_Board;
import me.Haeseke1.Alliances.LeaderBoard.LeaderBoard;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class LeaderboardC implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 && sender.hasPermission("Alliances.leaderboard.*")) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Leaderboard =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/leaderboard create <Type> <Place> #Create a new leaderboard stand");
			return false;
		}

		if (!(sender instanceof Player)) {
			String message = "This command needs to be executed by a player";
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		
		if(args[0].equalsIgnoreCase("create") && args.length > 2){
			int place = 0;
			try{
				place = Integer.parseInt(args[2]);
			}catch (Exception e) {
				MessageManager.sendMessage(sender, "&cThis is not a number!");
				return false;
			}
			Block b = player.getLocation().getBlock();
			Block b2 = player.getTargetBlock((Set<Material>) null, 100);
			if(!(b.getState() instanceof Skull) || b2 == null || !(b2.getState() instanceof Sign)){
				MessageManager.sendMessage(sender, "&cDid not found all the blocks!");
				return false;
			}
			Head_Board hb = new Head_Board((Sign)b2.getState(), (Skull) b.getState());
			if(LeaderBoard.addPlace(args[1], place, hb)){
				MessageManager.sendMessage(sender, "&2Created succesfully!");
				return false;
			}
			MessageManager.sendMessage(sender, "&cNot a existing type!");
			return false;
		}
		
		
		return false;
	}

}
