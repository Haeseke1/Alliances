package me.Haeseke1.Alliances.PVE.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.PVE.Arena;
import me.Haeseke1.Alliances.PVE.Group;
import me.Haeseke1.Alliances.PVE.GroupManager;
import me.Haeseke1.Alliances.PVE.PVE;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;
import net.md_5.bungee.api.ChatColor;

public class PVEC implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(MessageManager.infoColorCode + "===== PVE =====");
			sender.sendMessage(MessageManager.infoColorCode + "Commands:");
			sender.sendMessage(MessageManager.infoColorCode + "/pve createmain #Create lobby and set spawn point at your location");
			sender.sendMessage(MessageManager.infoColorCode + "/pve createarena <Name> #creates a new arena");
			sender.sendMessage(MessageManager.infoColorCode + "/pve addplayerspawn <Name> #add playerspawn to arena");
			sender.sendMessage(MessageManager.infoColorCode + "/pve addmobspawn <Name> #add mobspawn to arena");
			sender.sendMessage(MessageManager.infoColorCode + "/pve join <amount> #create new group for pve with given amount of players");
			sender.sendMessage(MessageManager.infoColorCode + "/pve join #join your alliance for a PVE groug");
			sender.sendMessage(MessageManager.infoColorCode + "/pve settings #change settings for your next battle");
			sender.sendMessage(MessageManager.infoColorCode + "/pve ready #add yourself to the queue");
			return false;
		}
		
		if (!(sender instanceof Player)) {
			String message = MessageManager.getMessage("Command_Error_Not_A_User");
			MessageManager.sendAlertMessage(message);
			return false;
		}
		Player player = (Player) sender;
		String wrong_arg = MessageManager.getMessage("Command_Error_Wrong_Arguments");
		
		if(args[0].equalsIgnoreCase("createmain")){
			if(PVE.main != null){
				String message = MessageManager.getMessage("Commands_PVE_CreateMain_Already_Exist");
				MessageManager.sendMessage(player, message);
				return false;
			}
			new PVE(player.getLocation());
			String message = MessageManager.getMessage("Commands_PVE_CreateMain_Answer");
			MessageManager.sendMessage(player, message);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("createarena") && args.length > 1){
			if(PVE.main == null){
				String message = MessageManager.getMessage("Commands_PVE_Not_Exist");
				MessageManager.sendMessage(player, message);
				return false;
			}
			if(PVE.main.arenaAlreadyExist(args[1])){
				String message = MessageManager.getMessage("Commands_PVE_Arena_Exist");
				MessageManager.sendMessage(player, message);
				return false;
			}
			if (regionSelect.leftClick.containsKey(player) && regionSelect.rightClick.containsKey(player)) {
				new Arena(args[1], regionSelect.leftClick.get(player), regionSelect.rightClick.get(player));
				String message = MessageManager.getMessage("Commands_PVE_CreateArena_Answer");
				MessageManager.sendMessage(player, message);
			} else {
				String message = MessageManager.getMessage("Command_Error_Select_Region");
				MessageManager.sendMessage(player, message);
				return false;
			}
		}
		
		if(args[0].equalsIgnoreCase("addplayerspawn") && args.length > 1){
			if(PVE.main == null){
				String message = MessageManager.getMessage("Commands_PVE_Not_Exist");
				MessageManager.sendMessage(player, message);
				return false;
			}
			if(!PVE.main.arenaAlreadyExist(args[1])){
				MessageManager.sendMessage(player, ChatColor.RED + "This arena does not exist!");
				return false;
			}
			Arena arena = PVE.main.getArena(args[1]);
			if(arena.playerSpawns.size() >= 4){
				MessageManager.sendMessage(player, ChatColor.RED + "You can't add more then 4 spawns!");
				return false;
			}else{
				List<Location> locs = arena.playerSpawns;
				locs.add(player.getLocation());
				arena.playerSpawns = locs;
				MessageManager.sendMessage(player, ChatColor.GREEN + "Added a new spawn!");
				return false;
			}
		}
		
		if(args[0].equalsIgnoreCase("addmobspawn") && args.length > 1){
			if(PVE.main == null){
				String message = MessageManager.getMessage("Commands_PVE_Not_Exist");
				MessageManager.sendMessage(player, message);
				return false;
			}
			if(!PVE.main.arenaAlreadyExist(args[1])){
				MessageManager.sendMessage(player, ChatColor.RED + "This arena does not exist!");
				return false;
			}
			Arena arena = PVE.main.getArena(args[1]);
			List<Location> locs = arena.mobSpawns;
			locs.add(player.getLocation());
			arena.mobSpawns = locs;
			MessageManager.sendMessage(player, ChatColor.GREEN + "Added a new spawn!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("join") && args.length > 1){
			try{
				int amount = Integer.parseInt(args[1]);
				if(amount > 4 || amount < 1){
					MessageManager.sendMessage(player, ChatColor.RED + "Number must be between 1 and 4!");
					return false;
				}
				if(amount == 1){
					List<Player> players = new ArrayList<Player>();
					players.add(player);
					new Group(players, player);
					return false;
				}
				if(AllianceManager.getAlliance(player) == null){
					MessageManager.sendMessage(player, ChatColor.RED + "You need to be in a alliance to fight in a group!");
					return false;
				}
				Alliance alli = AllianceManager.getAlliance(player);
				if(alli.PVE){
					MessageManager.sendMessage(player, ChatColor.RED + "Only 1 group can be made in a alliance!");
					return false;
				}
				alli.PVE = true;
				alli.PVE_amount = amount;
				alli.sendPlayersMessage(ChatColor.YELLOW + "A member want to create a group for PVE! Use /pve join to join him!");
				alli.addPVE_Player(player);
				return false;
			}catch(Exception e){
				MessageManager.sendMessage(player, ChatColor.RED + "Given argument was not a number!");
			}
			return false;
		}
		
		if(args[0].equalsIgnoreCase("join")){
			if(AllianceManager.getAlliance(player) == null){
				MessageManager.sendMessage(player, ChatColor.RED + "You need to be in a alliance to fight in a group!");
				return false;
			}
			Alliance alli = AllianceManager.getAlliance(player);
			if(!alli.PVE){
				MessageManager.sendMessage(player, ChatColor.RED + "No group waiting for players, create one yourself!");
				return false;
			}
			if(alli.PVE_players.contains(player)){
				MessageManager.sendMessage(player, ChatColor.RED + "You are already in this group!");
				return false;
			}
			alli.addPVE_Player(player);
			MessageManager.sendMessage(player, ChatColor.GREEN + "You joined the group!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("leave")){
			if(GroupManager.hasGroup(player)){
				GroupManager.getGroup(player).disband();
				player.sendMessage(ChatColor.YELLOW + "You left the group!");
				return false;
			}
			if(AllianceManager.getAlliance(player) == null){
				MessageManager.sendMessage(player, ChatColor.RED + "You need to be in a alliance to fight in a group!");
				return false;
			}
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli.PVE && alli.removePVE_Player(player)){
				MessageManager.sendMessage(player, ChatColor.GREEN + "You left the group!");
				return false;
			}
			MessageManager.sendMessage(player, ChatColor.RED + "You were not in a group!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("settings")){
			if(!GroupManager.hasGroup(player)){
				player.sendMessage(ChatColor.RED + "You are not in a group!");
				return false;
			}
			Group group = GroupManager.getGroup(player);
			if(!group.owner.equals(player)){
				player.sendMessage(ChatColor.RED + "You are not owner of this group!");
				return false;
			}
			group.settings.createGUI(player);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("ready")){
			if(!GroupManager.hasGroup(player)){
				player.sendMessage(ChatColor.RED + "You are not in a group!");
				return false;
			}
			Group group = GroupManager.getGroup(player);
			if(!group.owner.equals(player)){
				player.sendMessage(ChatColor.RED + "You are not owner of this group!");
				return false;
			}
			PVE.main.addQueue(group);
			return false;
		}
		
		MessageManager.sendMessage(player, wrong_arg);
		return false;
	}
	
	

}
