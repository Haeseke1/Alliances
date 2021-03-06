package me.Haeseke1.Alliances.Item.Weapons.Wands.Type;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Haeseke1.Alliances.Item.Weapons.Wands.Wand;
import me.Haeseke1.Alliances.Utils.MessageManager;
import me.Haeseke1.Alliances.Utils.SoundManager;

public class Cloud_Wand extends Wand{

	public static List<Player> players_in_air = new ArrayList<>();
	
	public Vector dir = user.getLocation().getDirection();
	
	public Cloud_Wand(String name, Player user, Material wand_type, double mana) {
		super(name, user, wand_type, mana);
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public void launchPlayer(Sound sound){		
		if(this.hasName()){
			if(!user.isOnGround()){ MessageManager.sendMessage(user, "&cYou can't use the cloud wand in mid air"); return;}
			if(APlayer.removeMana(mana)){
				user.setVelocity(dir.add(new Vector(1,4,0)));
				if(sound == null){ return;}
				SoundManager.playSoundToPlayer(sound, user);
				this.players_in_air.add(this.user);
			}
		}
	}
	public class Countdown extends BukkitRunnable{

		public Player player;
		
		public int time = 10;
		
		public Countdown(Player player){
			this.player = player;
		}
		
		@Override
		public void run() {
			if(time == 0){
				Cloud_Wand.players_in_air.remove(player);
				this.cancel();
			}
			//ParticleManager.playParticle(, player.getLocation(), 1);
			time = time - 1;
		}
		
	}
}