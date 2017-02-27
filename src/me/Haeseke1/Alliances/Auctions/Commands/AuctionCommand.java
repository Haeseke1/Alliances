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
			return true;
		}else if(args.length == 3 && args[0].equalsIgnoreCase("start")){
			try{
				int price = Integer.parseInt(args[1]);
				int raise = Integer.parseInt(args[2]);
				new Auction(0,0,player.getUniqueId(),null,price,raise,null);
			}catch(Exception e){
				MessageManager.sendMessage(player, "&cWrong usage: /auc start <price> <raise>");
				e.printStackTrace();
			}
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("remove")){
			Auction.removeAuction(player);
			return true;
		}else if(args.length == 1 && args[0].equalsIgnoreCase("close")){
			if(!Auction.playerHasAuction(player)){
				MessageManager.sendMessage(player, "&cYou haven't an active auction in the auction house");
				return false;
			}
			Auction auction = Auction.getAuctionFromPlayer(player);
			auction.closeAuction();
			return true;
		}
		sender.sendMessage(MessageManager.infoColorCode + "===== Auctions =====");
		sender.sendMessage(MessageManager.infoColorCode + "Commands:");
		sender.sendMessage(MessageManager.infoColorCode + "/auc start <price> <raise> #Sell items in the auction house");
		sender.sendMessage(MessageManager.infoColorCode + "/auc remove #Remove your offer from the auction house");
		sender.sendMessage(MessageManager.infoColorCode + "/auc close #Close your offer and obtain the price");
		sender.sendMessage(MessageManager.infoColorCode + "/auc rewards #Let you earn your rewards");
		return false;
	}

	
	
}
