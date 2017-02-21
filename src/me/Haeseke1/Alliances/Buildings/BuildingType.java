package me.Haeseke1.Alliances.Buildings;

public enum BuildingType {
	STORAGE,ALCHEMY;
	
	
	public static BuildingType getType(String string){
		switch(string.toLowerCase()){
		case "storage":
			return STORAGE;
		case "alchemy":
			return ALCHEMY;
		default:
			return null;
		}
	}
}
