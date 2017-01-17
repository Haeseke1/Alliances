package me.Haeseke1.Alliances.regionSelect.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Utils.ParticleManager;
import me.Haeseke1.Alliances.regionSelect.regionSelect;
import net.minecraft.server.v1_8_R2.EnumParticle;

public class Particle_Timer implements Runnable{

	public static List<Player> showRegion = new ArrayList<Player>();
	
	@Override
	public void run() {
		for(Player player : showRegion){
			Location loc1 = regionSelect.leftClick.get(player);
			Location loc2 = regionSelect.rightClick.get(player);
			int xmin;
			int xmax;
			int ymin;
			int ymax;
			int zmin;
			int zmax;
			if(loc1.getBlockX() > loc2.getBlockX()){
				xmin = loc2.getBlockX();
				xmax = loc1.getBlockX();
			}else{
				xmin = loc1.getBlockX();
				xmax = loc2.getBlockX();
			}
			if(loc1.getBlockY() > loc2.getBlockY()){
				ymin = loc2.getBlockY();
				ymax = loc1.getBlockY();
			}else{
				ymin = loc1.getBlockY();
				ymax = loc2.getBlockY();
			}
			if(loc1.getBlockZ() > loc2.getBlockZ()){
				zmin = loc2.getBlockZ();
				zmax = loc1.getBlockZ();
			}else{
				zmin = loc1.getBlockZ();
				zmax = loc2.getBlockZ();
			}
			xmax++;
			ymax++;
			zmax++;
			for(int x = xmin; x <= xmax; x++){
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),x,ymin,zmin), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),x,ymin,zmax), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),x,ymax,zmin), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),x,ymax,zmax), 0.1F, 1, player);
			}
			for(int y = ymin; y <= ymax; y++){
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmin,y,zmin), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmin,y,zmax), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmax,y,zmin), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmax,y,zmax), 0.1F, 1, player);
			}
			for(int z = zmin; z <= zmax; z++){
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmin,ymin,z), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmin,ymax,z), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmax,ymin,z), 0.1F, 1, player);
				ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc1.getWorld(),xmax,ymax,z), 0.1F, 1, player);
			}
		}
	}

}
