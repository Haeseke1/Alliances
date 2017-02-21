package me.Haeseke1.Alliances.Auctions.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Auctions.Auction;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class AuctionCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by an online player");
			return false;
		}
		Player player = (Player) sender;
		if(args.length == 0){
			Auction.openInventory(player);
			return false;
		}else if(args.length == 3 && args[0].equalsIgnoreCase("start")){
			try{
				int price = Integer.parseInt(args[1]);
				int raise = Integer.parseInt(args[2]);
				@SuppressWarnings("unused")
				Auction auction = new Auction(0,0,player.getUniqueId(),price,raise,null);
			}catch(Exception e){
				MessageManager.sendMessage(player, "&cWrong usage: /auc start <price> <raise>");
				e.printStackTrace();
			}
		}
		return false;
	}

	
	
}
