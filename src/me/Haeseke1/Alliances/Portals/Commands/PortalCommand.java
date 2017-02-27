package me.Haeseke1.Alliances.Portals.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Portals.Portal;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class PortalCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender.hasPermission("alliance.portals.*")){
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This command needs to be executed by a player");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 2 && args[0].equalsIgnoreCase("create")){
			String name = args[1];
			new Portal(name,player);
			return true;
		}
		
		if(args.length == 2 && args[0].equalsIgnoreCase("remove")){
			String name = args[1];
			Portal.removePortal(player, name);
			return true;
		}
		
		if(args.length == 3 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("location")){
			String name = args[2];
            try{
            	Portal portal = Portal.getPortalByName(name);
            	portal.setLocation(player);
            }catch(NullPointerException e){
            	MessageManager.sendMessage(player, "&cThis portal doesn't exists");
            	e.printStackTrace();
            }
			return true;
		 }
		sender.sendMessage(MessageManager.infoColorCode + "===== Portals =====");
		sender.sendMessage(MessageManager.infoColorCode + "Commands:");
		sender.sendMessage(MessageManager.infoColorCode + "/portal create <name> #Create warp portals");
		sender.sendMessage(MessageManager.infoColorCode + "/portal remove <name> #Remove warp portals");
		sender.sendMessage(MessageManager.infoColorCode + "/portal set location <name> #Set a warp location to the portal");
		}else{
			MessageManager.sendAlertMessage("You don't have the permission to execute this command");
			return true;
		}
		return false;
	}
	
}
