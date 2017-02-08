package me.Haeseke1.Alliances.Buildings;

public enum BuildingType {
	STORAGE;
	
	
	public static BuildingType getType(String string){
		switch(string.toLowerCase()){
		case "storage":
			return STORAGE;
		default:
			return null;
		}
	}
}
