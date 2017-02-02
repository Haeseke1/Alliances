package me.Haeseke1.Alliances.Arena;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.Haeseke1.Alliances.Exceptions.EmptyLocationException;
import me.Haeseke1.Alliances.Exceptions.EmptyStringException;

public class WinnerCountdown extends BukkitRunnable{
    private int count = 20;
    private Arena mArena;
    
    public WinnerCountdown(Arena arena){
    	this.mArena = arena;
    }
    
  	@Override
	public void run() {
        if(mArena.getCurrentSize() != 0){
          if(count > 0){
        	for(UUID playerUUID: mArena.getPlayersInArena().keySet()){
        		Player player = Bukkit.getPlayer(playerUUID);
        		Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        		FireworkMeta fwmeta = fw.getFireworkMeta();
        		Random random = new Random();
        		int randomizerColor = random.nextInt(5);
        		int randomizerType = random.nextInt(3);
        		Color color = null;
        		Type type = null;
        		switch(randomizerColor){
        		case 1:
                    color = Color.RED;
        			break;
        		case 2:
        			color = Color.AQUA;
        			break;
        		case 3:
        			color = Color.PURPLE;
        			break;
        		case 4:
        			color = Color.GREEN;
        			break;
        		case 5:
        			color = Color.ORANGE;
        			break;
        		default:
        			color = Color.LIME;
        			break;
        		}
        		switch(randomizerType){
        		case 1:
        		type = Type.BALL;
        		break;
        		case 2:
        		type = Type.BALL_LARGE;
        		break;
        		case 3:
        		type = Type.BURST;
        		break;
        		default:
        		type = Type.STAR;
        		break;
        		}
        		fwmeta.addEffect(FireworkEffect.builder().flicker(true).withColor(color).withTrail().with(type).build());
        		fwmeta.setPower(1);
        		fw.setFireworkMeta(fwmeta);
        	}
         }else{
         	for(UUID playerUUID: mArena.getPlayersInArena().keySet()){
         	  Player player = Bukkit.getPlayer(playerUUID);
			  player.teleport(ArenaManager.pastLocations.get(playerUUID),TeleportCause.ENDER_PEARL);
			  try {
				ArenaManager.setStatus(mArena.getName(), "PLAYABLE", null);
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			}
			  ArenaManager.pastLocations.remove(playerUUID);
			  mArena.getPlayersInArena().remove(playerUUID);
         	}
			  try {
				  if(ArenaManager.checkSign(mArena.getName())){
					  ArenaManager.updateSign(ArenaManager.getSign(mArena.getName()), mArena);
					  }
				} catch (EmptyLocationException | EmptyStringException e) {
					e.printStackTrace();
				}
        	 this.cancel();
         }
        }else{
			  try {
				ArenaManager.setStatus(mArena.getName(), "PLAYABLE", null);
			} catch (EmptyLocationException e) {
				e.printStackTrace();
			}
			  try {
					ArenaManager.updateSign(ArenaManager.getSign(mArena.getName()), mArena);
				} catch (EmptyLocationException | EmptyStringException e) {
					e.printStackTrace();
				}
			  this.cancel();
        }
  		count --;	
	}

}
