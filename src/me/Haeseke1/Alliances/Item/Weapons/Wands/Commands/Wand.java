package me.Haeseke1.Alliances.Item.Weapons.Wands.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.APlayer.APlayerManager;
import me.Haeseke1.Alliances.APlayer.aPlayer;
import me.Haeseke1.Alliances.Item.Weapons.Wands.Type.Healing_Wand;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Wand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		
		Player player = (Player) sender;
		aPlayer APlayer = APlayerManager.getAPlayer(player);
		
		if(args.length == 0){
			player.sendMessage(MessageManager.infoColorCode + "===== Wands =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode	+ "/wand mana #Shows your mana balance");
			player.sendMessage(MessageManager.infoColorCode	+ "/wand give <type> #Gives you a wand");
			return true;
		}
		else
		if(args[0].equalsIgnoreCase("mana")){
		    APlayer.showMana();
			return true;
		}
		else
		if(args[0].equalsIgnoreCase("give") && args.length == 2){
			String wandtype = args[1].toLowerCase();
			switch(wandtype){
			case "healing":
				Healing_Wand.giveWand(player);
				break;
			default:
				MessageManager.sendMessage(player, "&This wand type doesn't exists");
				return false;
			}
			SoundManager.playSoundToPlayer(Sound.LEVEL_UP, player);
			MessageManager.sendMessage(player, "&2You've received a &6" + wandtype);
			return true;
		}
		
		MessageManager.sendMessage(player, "&cWrong argument do: /wand");
		return false;
	}

}
