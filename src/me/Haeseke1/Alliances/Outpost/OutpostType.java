package me.Haeseke1.Alliances.Outpost;


public enum OutpostType {
	
	
	FARM,MINE,BLACKSMITH,MAGIC_TOWER,DOCK,MOB_FARM,GOD;
	
	
	public static OutpostType getOutpostType(String s){
		s = s.replace(" ", "");
		switch(s.toLowerCase()){
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
	
	
	public static String getOutpostType(OutpostType s){
		switch(s){
		case BLACKSMITH:
			return "Blacksmith";
		case DOCK:
			return "Dock";
		case FARM:
			return "Farm";
		case GOD:
			return "God";
		case MAGIC_TOWER:
			return "Magic Tower";
		case MINE:
			return "Mine";
		case MOB_FARM:
			return "Mob Farm";
		default:
			break;
		}
		return null;
	}
}
