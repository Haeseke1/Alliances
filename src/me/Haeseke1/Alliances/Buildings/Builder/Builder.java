package me.Haeseke1.Alliances.Buildings.Builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Buildings.DataManager;
import me.Haeseke1.Alliances.Main.Main;

public class Builder {
	
	public static List<Builder> builders = new ArrayList<Builder>();
	
	public Material[][][] material;
	public Byte[][][] data;
	public int height;
	public Location mainBlock;
	public String name;
	public BuildingType type;
	
	public Builder(String name, Material[][][] material, Byte[][][] data, int height, Location mainBlock, BuildingType type) {
		this.name = name;
		this.material = material;
		this.data = data;
		this.height = height;
		this.mainBlock = mainBlock;
		this.type = type;
		builders.add(this);
	}
	
	public void construct(Chunk chunk, int height){
		int i = 1;
		for(int y = height; y <= height + this.height; y++){
			for(int x = 0; x < 16; x++){
				for(int z = 0; z < 16; z++){
					final int x2 = x;
					final int y2 = y;
					final int z2 = z;
					final int yLoc = y - height;
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){

						@SuppressWarnings("deprecation")
						@Override
						public void run() {
							Block block = chunk.getBlock(x2, y2, z2);
							block.setType(material[x2][yLoc][z2]);
							block.setData(data[x2][yLoc][z2]);
						}
					},i * 2);
					i++;
				}
			}
		}
		Location loc = chunk.getBlock(mainBlock.getBlockX(), mainBlock.getBlockY() + height, mainBlock.getBlockZ()).getLocation();
		new Building(loc,chunk,height,type);
	}
	
	
	public void saveData(){
		File f = new File(Main.plugin.getDataFolder() + "/Building");
		if(!f.exists()){
			f.mkdirs();
		}
		String main = mainBlock.getWorld().getName() + "," + mainBlock.getBlockX() + "," + mainBlock.getBlockY() + "," + mainBlock.getBlockZ();
		DataManager.Save(material, new File(f,name + "MAT.dat"));
		DataManager.Save(data, new File(f, name + "DAT.dat"));
		DataManager.Save(main, new File(f, name + "MAIN.dat"));
		DataManager.Save(height, new File(f, name + "HEIG.dat"));
		DataManager.Save(type, new File(f, name + "TYPE.dat"));
	}
	
	public ItemStack createItemStack(){
		ItemStack item = new ItemStack(Material.LAPIS_ORE);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + name);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "BuildingType: " + type);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
}
