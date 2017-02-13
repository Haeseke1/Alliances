package me.Haeseke1.Alliances.Item.Weapons.Wands.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Utils.MessageManager;

public class Wand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		aPlayer APlayer = APlayerManager.getAPlayer(player);
		
		if(args.length == 0){
			player.sendMessage(MessageManager.infoColorCode + "===== Wands =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode	+ "/wands mana #Shows your mana balance");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("mana")){
		    APlayer.showMana();
			return true;
		}
		return false;
	}

}
