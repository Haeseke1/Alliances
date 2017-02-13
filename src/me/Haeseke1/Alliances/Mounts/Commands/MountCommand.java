package me.Haeseke1.Alliances.Mounts.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Mounts.Mount;
import me.Haeseke1.Alliances.Utils.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class MountCommand implements CommandExecutor{

	public static HashMap<Player,Mount> mounts = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			MessageManager.sendAlertMessage("This command is meant for players");
			return false;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			player.sendMessage(MessageManager.infoColorCode + "===== Mounts =====");
			player.sendMessage(MessageManager.infoColorCode + "Commands:");
			player.sendMessage(MessageManager.infoColorCode	+ "/mount <donkey/prince/elite> #Mount on your very own horse or donkey");
			return false;
		}
		if (args.length == 1) {
			if (mounts.containsKey(player)) {
				MessageManager.sendMessage(player, ChatColor.RED + "You already have a mount");
				return false;
			}
			String type = args[0].toLowerCase();
			switch (type) {
			case "elite":
				if (player.hasPermission("mount.diamond")) {
					Mount mount = new Mount(player, type);
					if (mount.mobIsSpawned()) {
						MessageManager.sendMessage(player, ChatColor.RED + "You're already on a mount");
						return false;
					}
					mount.spawnMount();
					mounts.put(player, mount);
					MessageManager.sendMessage(player, ChatColor.GREEN + "You equipped a horse");
					return true;
				}
				MessageManager.sendMessage(player,
						ChatColor.RED + "You don't have permissions to execute this command");
				break;
			case "prince":
				if (player.hasPermission("mount.gold")) {
					Mount mount = new Mount(player, type);
					if (mount.mobIsSpawned()) {
						MessageManager.sendMessage(player, ChatColor.RED + "You already have a mount");
						return false;
					}
					mount.spawnMount();
					mounts.put(player, mount);
					MessageManager.sendMessage(player, ChatColor.GREEN + "You equipped a horse");
					return true;
				}
				MessageManager.sendMessage(player,
						ChatColor.RED + "You don't have permissions to execute this command");
				break;
			case "donkey":
				if (player.hasPermission("mount.donkey")) {
					Mount mount = new Mount(player, type);
					if (mount.mobIsSpawned()) {
						MessageManager.sendMessage(player, ChatColor.RED + "You already have a mount");
						return false;
					}
					mount.spawnMount();
					mounts.put(player, mount);
					MessageManager.sendMessage(player, ChatColor.GREEN + "You equipped a donkey");
					return true;
				}
				MessageManager.sendMessage(player,
						ChatColor.RED + "You don't have permissions to execute this command");
				break;
			default:
				MessageManager.sendMessage(player, ChatColor.RED + "This mount type doesn't exists");
				return false;
			}
		}
		return false;
	}

}
