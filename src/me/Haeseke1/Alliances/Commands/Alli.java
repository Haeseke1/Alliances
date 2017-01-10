package me.Haeseke1.Alliances.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Commands.Coin.Coin;
import me.Haeseke1.Alliances.Commands.Create.mainCreate;
import me.Haeseke1.Alliances.Commands.Outpost.Outpost;
import me.Haeseke1.Alliances.Commands.Region.region;
import me.Haeseke1.Alliances.Main.Alliance;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.PlayerManager;

public class Alli implements CommandExecutor {
	
	public static HashMap<Player,List<Alliance>> invited = new HashMap<Player,List<Alliance>>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Alliances =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances coin #Get list of coin commands");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances create #Create a new alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances outpost #Get list of outpost commands");
			return false;
		}

		if (args[0].equalsIgnoreCase("coin")) {
			Coin.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("create")){
			mainCreate.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("outpost")){
			Outpost.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("region")){
			region.onCommand(sender, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("disband")){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return false;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId())){
				Alliance alli = AllianceManager.getAlliance(player);
				String name = alli.getName();
				alli = null;
				// error next line (maybe)
				Main.alliances.remove(alli);
				MessageManager.sendRemarkMessage(player, "You disbanded your alliance!");
				MessageManager.sendInfoMessage("Alliance " + name + "is disbanded!");
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner of a alliance!");
			}
			return true;
		}
		
		if(args[0].equalsIgnoreCase("join") && args.length > 1){

		}
		
		
		if(args[0].equalsIgnoreCase("invite") && args.length > 1){
			if(!(sender instanceof Player)){
				MessageManager.sendAlertMessage("You need to be a player to do this command!");
				return false;
			}
			Player player = (Player) sender;
			if(AllianceManager.playerIsInAlli(player) && (AllianceManager.getAlliance(player).getOwner().equals(player.getUniqueId()) || AllianceManager.getAlliance(player).getAdmins().contains(player.getUniqueId()))){
				if(!PlayerManager.isPlayerOnline(args[1])){
					MessageManager.sendAlertMessage(player, "This player is not online!");
					return false;
				}
				Player pinvite = PlayerManager.getPlayer(args[1]);
				if(invited.containsKey(pinvite)){
					List<Alliance> allis = invited.get(pinvite);
					allis.add(AllianceManager.getAlliance(player));
					invited.replace(pinvite, allis);
				}else{
					List<Alliance> allis = new ArrayList<Alliance>();
					allis.add(AllianceManager.getAlliance(player));
					invited.put(pinvite, allis);
				}
				MessageManager.sendInfoMessage(pinvite, "You are invited for the alliance " + AllianceManager.getAlliance(player).getName() + "!");
				MessageManager.sendRemarkMessage(player, "You have invited " + pinvite.getName() + "!");
				return true;
			}else{
				MessageManager.sendAlertMessage(player, "You are not owner/admin of a alliance!");
			}
			return true;
		}
		
		
		MessageManager.sendAlertMessage((Player) sender, "Unknown command use /alliances for more help!");
		return false;
	}

}
