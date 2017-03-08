package me.Haeseke1.Alliances.WorldGuard.Regions;

public enum Setting {

	CAN_BREAK_BLOCKS,CAN_PLACE_BLOCKS,
	CAN_HARM_PLAYERS,CAN_HARM_MOBS,
	CAN_RECEIVE_FALL_DAMAGE,CAN_RECEIVE_FIRE_DAMAGE,
	CAN_RIGHT_CLICK_CHEST,CAN_RIGHT_CLICK_FURNACE,
	CAN_RIGHT_CLICK_ENCHANT,CAN_RIGHT_CLICK_ANVIL,
	CAN_RIGHT_CLICK_CRAFTING,CAN_RIGHT_CLICK_DOORS,
	CAN_RIGHT_CLICK_BLOCKS,
	SHOW_MESSAGE_ON_ENTER,SHOW_MESSAGE_ON_LEAVE,
	CAN_LOSE_FOOD,CAN_DROWN;
	
	public static String getName(Setting setting){
		for(Setting check: Setting.values()){
			if(check == setting){
				return setting.name().replace("_", "-").toLowerCase();
			}
		}
		return null;
	}
}
