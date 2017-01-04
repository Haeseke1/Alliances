package me.Haeseke1.Alliances.Alliance;

public enum AllianceType {
	CAIT_SITH,GNOME,IMP,LEPRECHAUN,POOKA,SALAMANDER,SPRIGGAN,SYLPH,UNDINE;
	
	
	
	public static AllianceType getType(String s){
		switch (s.toLowerCase()) {
		case "cait_sith":
			return CAIT_SITH;
		case "gnome":
			return GNOME;
		case "imp":
			return IMP;
		case "leprechaun":
			return LEPRECHAUN;
		case "pooka":
			return POOKA;
		case "salamander":
			return SALAMANDER;
		case "spriggan":
			return SPRIGGAN;
		case "sylph":
			return SYLPH;
		case "undine":
			return UNDINE;
		default:
			return null;
		}
	}
}
