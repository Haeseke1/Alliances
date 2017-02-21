package me.Haeseke1.Alliances.Buildings.Type.Storage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Haeseke1.Alliances.Buildings.Building;
import me.Haeseke1.Alliances.Buildings.BuildingType;
import me.Haeseke1.Alliances.Main.Main;
import net.md_5.bungee.api.ChatColor;

public class Alchemy extends Building{
	
	public static List<Alchemy> alchemies = new ArrayList<Alchemy>();
	
	public List<Player> openInventory = new ArrayList<Player>();
	
	public Alchemy(Location mainBlock, Chunk chunk, int ymin, int ymax) {
		super(mainBlock, chunk, ymin, ymax, BuildingType.ALCHEMY, true);
		setHolo();
		alchemies.add(this);
	}
	
	public void setHolo(){
		Location loc = new Location(mainBlock.getWorld(), mainBlock.getX() + 0.5, mainBlock.getY(), mainBlock.getZ() + 0.5);
		removeArmorStand(loc);
		ArmorStand as = (ArmorStand) mainBlock.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		as.setGravity(false);
		as.setCanPickupItems(false);
		as.setCustomName(ChatColor.GOLD + "Alchemy");
		as.setCustomNameVisible(true);
		as.setVisible(false);
	}
	
	public void removeArmorStand(Location loc){
		for(Entity e : loc.getWorld().getNearbyEntities(loc, 5, 5, 5)){
			if(e instanceof ArmorStand && !((ArmorStand) e).isVisible()){
				e.remove();
			}
		}
	}
	
	
	public ItemStack createPanel(short color, String name){
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1 ,color);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}
	
	public void openAlchemy(Player player){
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Alchemy");
		ItemStack panel = createPanel((short) 15, "");
		for(int i = 4; i < 53; i = i + 9){
			inv.setItem(i, panel);
		}
		openInventory.add(player);
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public void updateInventory(Player player){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			
			@Override
			public void run() {
				InventoryView inv = player.getOpenInventory();
				List<ItemStack> transferItems = new ArrayList<ItemStack>();
				for(int i = 0; i < 53; i = i + 9){
					for(int j = 0; j < 4; j++){
						ItemStack slot = inv.getItem(i + j);
						if(slot != null && slot.getType() != Material.AIR){
							transferItems.addAll(getValue(slot));
						}
					}
				}
				int slot = 0;
				for(int i = 0; i < 53; i = i + 9){
					for(int j = 5; j < 9; j++){
						if(transferItems.size() > slot){
							inv.setItem(i + j, transferItems.get(slot));
						}else{
							inv.setItem(i + j, new ItemStack(Material.AIR));
						}
						slot++;
					}
				}
				player.updateInventory();
				
			}
		}, 10);

	}
	
	public boolean removeSide(Inventory inv, ItemStack item){
		for(int i = 0; i < 53; i = i + 9){
			for(int j = 0; j < 4; j++){
				ItemStack slot = inv.getItem(i + j);
				if(slot != null && slot.getType() != Material.AIR){
					for(ItemStack value : getValue(slot)){
						if(value.getAmount() > value.getMaxStackSize()){
							value.setAmount(value.getMaxStackSize());
						}
						if(value.equals(item)){
							if(getValue(slot, item.getType()) > 64){
								Bukkit.broadcastMessage(slot.getAmount() + " " + (getValue(slot, item.getType()) / 64) + " " + (getValue(slot, item.getType()) % 64));
								slot.setAmount((int) (slot.getAmount() - ((double)slot.getAmount() / (double)((double) getValue(slot, item.getType()) / 64))));
								inv.setItem(i + j, slot);
							}else{
								inv.setItem(i + j, new ItemStack(Material.AIR));
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	private int getValue(ItemStack item, Material type){
		switch(item.getType()){
		case IRON_INGOT:
			switch(type){
			case IRON_INGOT:
				return item.getAmount();
			case GOLD_INGOT:
				return item.getAmount() / 4;
			case DIAMOND:
				return item.getAmount() / 16;
			case EMERALD:
				return item.getAmount() / 8;
			case COAL:
				return item.getAmount() * 4;
			case REDSTONE:
				return item.getAmount() * 4;
			case INK_SACK:
				return item.getAmount() * 4;
			default:
				break;
			}
			break;
		case GOLD_INGOT:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() * 4;
			case GOLD_INGOT:
				return item.getAmount();
			case DIAMOND:
				return item.getAmount() / 4;
			case EMERALD:
				return item.getAmount() / 2;
			case COAL:
				return item.getAmount() * 16;
			case REDSTONE:
				return item.getAmount() * 16;
			case INK_SACK:
				return item.getAmount() * 16;
			default:
				break;
			}
			break;
		case DIAMOND:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() * 16;
			case GOLD_INGOT:
				return item.getAmount() * 4;
			case DIAMOND:
				return item.getAmount();
			case EMERALD:
				return item.getAmount() * 2;
			case COAL:
				return item.getAmount() * 64;
			case REDSTONE:
				return item.getAmount() * 64;
			case INK_SACK:
				return item.getAmount() * 64;
			default:
				break;
			}
			break;
		case EMERALD:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() * 8;
			case GOLD_INGOT:
				return item.getAmount() * 2;
			case DIAMOND:
				return item.getAmount() / 2;
			case EMERALD:
				return item.getAmount();
			case COAL:
				return item.getAmount() * 32;
			case REDSTONE:
				return item.getAmount() * 32;
			case INK_SACK:
				return item.getAmount() * 32;
			default:
				break;
			}
			break;
		case COAL:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() / 4;
			case GOLD_INGOT:
				return item.getAmount() / 16;
			case DIAMOND:
				return item.getAmount() / 64;
			case EMERALD:
				return item.getAmount() / 32;
			case COAL:
				return item.getAmount();
			case REDSTONE:
				return item.getAmount();
			case INK_SACK:
				return item.getAmount();
			default:
				break;
			}
			break;
		case REDSTONE:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() / 4;
			case GOLD_INGOT:
				return item.getAmount() / 16;
			case DIAMOND:
				return item.getAmount() / 64;
			case EMERALD:
				return item.getAmount() / 32;
			case COAL:
				return item.getAmount();
			case REDSTONE:
				return item.getAmount();
			case INK_SACK:
				return item.getAmount();
			default:
				break;
			}
			break;
		case INK_SACK:
			switch(type){
			case IRON_INGOT:
				return item.getAmount() / 4;
			case GOLD_INGOT:
				return item.getAmount() / 16;
			case DIAMOND:
				return item.getAmount() / 64;
			case EMERALD:
				return item.getAmount() / 32;
			case COAL:
				return item.getAmount();
			case REDSTONE:
				return item.getAmount();
			case INK_SACK:
				return item.getAmount();
			default:
				break;
			}
			break;
		default:
			break;
		}
		return 0;
	}
	
	private List<ItemStack> getValue(ItemStack item){
		List<ItemStack> items = new ArrayList<ItemStack>();
		switch(item.getType()){
		case IRON_INGOT:
			if(item.getAmount() / 4 >= 1){
				items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() / 4));
			}
			if(item.getAmount() / 16 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 16));
			}
			if(item.getAmount() / 8 >= 1){
				items.add(new ItemStack(Material.EMERALD, item.getAmount() / 8));
			}
			items.add(new ItemStack(Material.COAL, item.getAmount() * 4));
			items.add(new ItemStack(Material.REDSTONE, item.getAmount() * 4));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount() * 4, (short) 4));
			break;
		case GOLD_INGOT:
			if(item.getAmount() / 4 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 4));
			}
			if(item.getAmount() / 2 >= 1){
				items.add(new ItemStack(Material.EMERALD, item.getAmount() / 2));
			}
			items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() * 4));
			items.add(new ItemStack(Material.COAL, item.getAmount() * 16));
			items.add(new ItemStack(Material.REDSTONE, item.getAmount() * 16));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount() * 16, (short) 4));
			break;
		case DIAMOND:
			items.add(new ItemStack(Material.EMERALD, item.getAmount() * 2));
			items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() * 4));
			items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() * 16));
			items.add(new ItemStack(Material.COAL, item.getAmount() * 64));
			items.add(new ItemStack(Material.REDSTONE, item.getAmount() * 64));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount() * 64, (short) 4));
			break;
		case EMERALD:
			if(item.getAmount() / 2 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 2));
			}
			items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() * 2));
			items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() * 8));
			items.add(new ItemStack(Material.COAL, item.getAmount() * 32));
			items.add(new ItemStack(Material.REDSTONE, item.getAmount() * 32));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount() * 32, (short) 4));
			break;
		case COAL:
			if(item.getAmount() / 4 >= 1){
				items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() / 4));
			}
			if(item.getAmount() / 16 >= 1){
				items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() / 16));
			}
			if(item.getAmount() / 32 >= 1){
				items.add(new ItemStack(Material.EMERALD, item.getAmount() / 32));
			}
			if(item.getAmount() / 64 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 64));
			}
			items.add(new ItemStack(Material.REDSTONE, item.getAmount()));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount(), (short) 4));
			break;
		case REDSTONE:
			if(item.getAmount() / 4 >= 1){
				items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() / 4));
			}
			if(item.getAmount() / 16 >= 1){
				items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() / 16));
			}
			if(item.getAmount() / 32 >= 1){
				items.add(new ItemStack(Material.EMERALD, item.getAmount() / 32));
			}
			if(item.getAmount() / 64 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 64));
			}
			items.add(new ItemStack(Material.COAL, item.getAmount()));
			items.add(new ItemStack(Material.INK_SACK, item.getAmount(), (short) 4));
			break;
		case INK_SACK:
			if(item.getAmount() / 4 >= 1){
				items.add(new ItemStack(Material.IRON_INGOT, item.getAmount() / 4));
			}
			if(item.getAmount() / 16 >= 1){
				items.add(new ItemStack(Material.GOLD_INGOT, item.getAmount() / 16));
			}
			if(item.getAmount() / 32 >= 1){
				items.add(new ItemStack(Material.EMERALD, item.getAmount() / 32));
			}
			if(item.getAmount() / 64 >= 1){
				items.add(new ItemStack(Material.DIAMOND, item.getAmount() / 64));
			}
			items.add(new ItemStack(Material.COAL, item.getAmount()));
			items.add(new ItemStack(Material.REDSTONE, item.getAmount()));
			break;
		default:
			break;
		}
		
		return items;
	}
	
	
	
	
	
	
	
	
	
}
