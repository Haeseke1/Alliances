package me.Haeseke1.Alliances.Shop;

public class ShopManager {
	
	
	
	public static void despawnVendors(){
		for(Shop s : Shop.shops){
			s.despawnVendors();
		}
	}

}
