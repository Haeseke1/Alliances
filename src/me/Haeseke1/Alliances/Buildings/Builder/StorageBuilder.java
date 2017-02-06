package me.Haeseke1.Alliances.Buildings.Builder;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Main.Main;

public class StorageBuilder {
	
	public Material[][][] material;
	public Byte[][][] data;
	public int height;
	public Location mainBlock;
	
	public StorageBuilder(Material[][][] material, Byte[][][] data, int height, Location mainBlock) {
		this.material = material;
		this.data = data;
		this.height = height;
		this.mainBlock = mainBlock;
	}
	
	public void construct(Chunk chunk, int height){
		int i = 1;
		for(int y = height; y < height + this.height; y++){
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					final int x2 = x;
					final int y2 = x;
					final int z2 = x;
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							Block block = chunk.getBlock(x2, y2, z2);
							block.setType(material[x2][z2][y2]);
							block.setData(data[x2][z2][y2]);
						}
					},i * 2);
				}
			}
		}
		new Building(mainBlock,chunk,height,BuildingType.STORAGE);
	}
	
}
