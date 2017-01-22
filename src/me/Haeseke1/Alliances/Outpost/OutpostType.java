package me.Haeseke1.Alliances.Outpost;


public enum OutpostType {
	
	
	FARM,MINE,BLACKSMITH,MAGIC_TOWER,DOCK,MOB_FARM,GOD;
	
	
	public static OutpostType getOutpostType(String s){
		switch(s){
		case "blacksmith":
			return BLACKSMITH;
		case "dock":
			return DOCK;
		case "farm":
			return FARM;
		case "god":
			return GOD;
		case "magictower":
			return MAGIC_TOWER;
		case "mine":
			return MINE;
		case "mobfarm":
			return MOB_FARM;
		default:
			break;
		}
		return null;
	}
}
