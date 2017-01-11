package me.Haeseke1.Alliances.Shop;

import org.bukkit.inventory.ItemStack;

public class SItem {
	
	
	public ItemStack item;
	public boolean buy;
	public boolean sell;
	public int buyV;
	public int sellV;
	
	public SItem(ItemStack item, boolean buy, boolean sell, int buyV, int sellV){
		this.item = item;
		this.buy = buy;
		this.sell = sell;
		this.buyV = buyV;
		this.sellV = sellV;
	}
}
