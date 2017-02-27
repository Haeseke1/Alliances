package me.Haeseke1.Alliances.Auctions.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Auctions.GUI.GUI;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Rewards implements CommandExecutor{
   List<Integer> i = new ArrayList<>();

@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if(!(sender instanceof Player)){
		return true;
	}
	
	Player player = (Player) sender;
	
	if(args.length == 0){
		GUI gui = new GUI(player,"REWARDS",18);
		gui.openInv();
		return true;
	}
		MessageManager.sendMessage(player, "&cWrong usage do: /rewards");
	return false;
}


}
