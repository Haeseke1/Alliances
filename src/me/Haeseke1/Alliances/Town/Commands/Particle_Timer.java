package me.Haeseke1.Alliances.Town.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.Haeseke1.Alliances.Alliance.Alliance;
import me.Haeseke1.Alliances.Alliance.AllianceManager;
import me.Haeseke1.Alliances.Town.Town;
import me.Haeseke1.Alliances.Town.TownManager;
import me.Haeseke1.Alliances.Utils.ParticleManager;
import net.minecraft.server.v1_8_R2.EnumParticle;

public class Particle_Timer implements Runnable{

	public static List<Player> showRegion = new ArrayList<Player>();
	
	@Override
	public void run() {
		for(Player player : showRegion){
			if(!AllianceManager.playerIsInAlli(player)){
				showRegion.remove(player);
				return;
			}
			Alliance alli = AllianceManager.getAlliance(player);
			if(alli.getTowns().isEmpty()){
				showRegion.remove(player);
				return;
			}
			for(Town t : alli.getTowns()){
				for(Chunk mChunk : t.chunks){
					boolean xmin = true;
					boolean xmax = true;
					boolean zmin = true;
					boolean zmax = true;
					for(Chunk chunk : t.chunks){
						if(TownManager.isNextTo(chunk, mChunk)){
							if(mChunk.getX() < chunk.getX() && mChunk.getX() != chunk.getX()){
								xmax  = false;
							}
							if(mChunk.getZ() < chunk.getZ() && mChunk.getZ() != chunk.getZ()){
								zmax  = false;
							}
							if(mChunk.getX() > chunk.getX() && mChunk.getX() != chunk.getX()){
								xmin  = false;
							}
							if(mChunk.getZ() < chunk.getZ() && mChunk.getZ() != chunk.getZ()){
								zmax  = false;
							}
						}
					}
					if(xmin){
						Location loc = mChunk.getBlock(0, 1, 0).getLocation();
						for(int z = loc.getBlockZ(); z < loc.getBlockZ() + 17; z++){
							for(int y = 1; y < 128; y = y + 3){
								ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc.getWorld(),loc.getBlockX(),y,z), 0.1F, 1, player);
							}
						}
					}
					if(zmin){
						Location loc = mChunk.getBlock(0, 1, 0).getLocation();
						for(int x = loc.getBlockX(); x < loc.getBlockX() + 17; x++){
							for(int y = 1; y < 128; y = y + 3){
								ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc.getWorld(),x,y,loc.getBlockZ()), 0.1F, 1, player);
							}
						}
					}
					if(xmax){
						Location loc = mChunk.getBlock(15, 1, 0).getLocation();
						for(int z = loc.getBlockZ(); z < loc.getBlockZ() + 17; z++){
							for(int y = 1; y < 128; y = y + 3){
								ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc.getWorld(),loc.getBlockX() + 1,y,z), 0.1F, 1, player);
							}
						}
					}
					if(zmax){
						Location loc = mChunk.getBlock(0, 1, 15).getLocation();
						for(int x = loc.getBlockX(); x < loc.getBlockX() + 17; x++){
							for(int y = 1; y < 128; y = y + 3){
								ParticleManager.playParticle(EnumParticle.FLAME, new Location(loc.getWorld(),x,y,loc.getBlockZ() + 1), 0.1F, 1, player);
							}
						}
					}
				}
			}
		}
	}

}
