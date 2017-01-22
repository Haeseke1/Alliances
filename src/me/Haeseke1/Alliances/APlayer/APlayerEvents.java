package me.Haeseke1.Alliances.APlayer;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Haeseke1.Alliances.Main.Main;

public class APlayerEvents implements Listener{
	
	
	
	@EventHandler
	private void playerjoin(PlayerJoinEvent event){
		File f = new File(Main.plugin.getDataFolder(), "PlayerData");
		if(!f.exists()){
			f.mkdir();
		}
		Player player = event.getPlayer();
		f = new File(f, player.getUniqueId().toString() + ".yml");
		FileConfiguration file = YamlConfiguration.loadConfiguration(f);
		new aPlayer(player, f, file);
	}
	
	@EventHandler
	private void playerleave(PlayerQuitEvent event){
		aPlayer aplayer = APlayerManager.getAPlayer(event.getPlayer());
		aplayer.saveConfig();
	}
}
