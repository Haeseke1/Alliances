package me.Haeseke1.Alliances.Commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Commands.Admin.Admin;
import me.Haeseke1.Alliances.Commands.Create.mainCreate;
import me.Haeseke1.Alliances.Commands.Join.mainJoin;
import me.Haeseke1.Alliances.Commands.Member.Member;
import me.Haeseke1.Alliances.Commands.Owner.Owner;
import me.Haeseke1.Alliances.Exceptions.InvalidConfigTypeException;
import me.Haeseke1.Alliances.Main.Config;
import me.Haeseke1.Alliances.Main.Main;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Alli implements CommandExecutor {
	
	public static HashMap<Player,List<Alliance>> invited = new HashMap<Player,List<Alliance>>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== Alliances =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances create #Create a new alliance");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances join #join a alliance that invited you");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances member #Get a list of commands for a member");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances admin #Get a list of commands for a admin");
			sender.sendMessage(MessageManager.infoColorCode + "/Alliances owner #Get a list of commands for a owner");
			return false;
		}
		
		if(!(sender instanceof Player)){
			MessageManager.sendAlertMessage("This message can only be executed by ingame players");
			return false;
		}
		

		Player player = (Player) sender;
		
		MessageManager.sendMessage(player, "&cWrong argument do: /alli");
		
		
		if(args[0].equalsIgnoreCase("create")){
			mainCreate.onCommand(player, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("join")){
			mainJoin.onCommand(player, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("member")){
			Member.onCommand(player, args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("Admin")){
			Admin.onCommand(player,args);
			return true;
		}

		
		if(args[0].equalsIgnoreCase("Owner")){
			Owner.onCommand(player,args);
			return true;
		}
		
		if(args[0].equalsIgnoreCase("reload")){
			try {
				Main.createConfigs();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigTypeException e) {
				e.printStackTrace();
			}
			Config.registerConfigFile(Main.plugin);
			MessageManager.sendMessage(player, "&2Config reloaded!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("saveconfig")){
			Main.saveAllCustomConfigs();
			Config.saveConfigFile(Main.plugin);
			MessageManager.sendMessage(player, "&2Config saved!");
			return false;
		}
		
		MessageManager.sendMessage(player, "&cWrong argument do: /alli");
		return false;
	}

}
