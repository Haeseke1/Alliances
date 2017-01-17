package me.Haeseke1.Alliances.Exp;

import java.util.HashMap;

public class Exp {
	
	public static HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
	
	
	public static int getLevel(int exp){
		int level;
		level = table.get(2) > exp ? 1 : 2;
		level = table.get(3) > exp ? 2 : 3;
		level = table.get(4) > exp ? 3 : 4;
		level = table.get(5) > exp ? 4 : 5;
		level = table.get(6) > exp ? 5 : 6;
		level = table.get(7) > exp ? 6 : 7;
		level = table.get(8) > exp ? 7 : 8;
		level = table.get(9) > exp ? 8 : 9;
		level = table.get(10) > exp ? 9 : 10;
		level = table.get(11) > exp ? 10 : 11;
		level = table.get(12) > exp ? 11 : 12;
		level = table.get(13) > exp ? 12 : 13;
		level = table.get(14) > exp ? 13 : 14;
		level = table.get(15) > exp ? 14 : 15;
		level = table.get(16) > exp ? 15 : 16;		
		level = table.get(17) > exp ? 16 : 17;
		level = table.get(18) > exp ? 17 : 18;
		level = table.get(19) > exp ? 18 : 19;
		level = table.get(20) > exp ? 19 : 20;
		level = table.get(21) > exp ? 20 : 21;
		level = table.get(22) > exp ? 21 : 22;
		level = table.get(23) > exp ? 22 : 23;
		level = table.get(24) > exp ? 23 : 24;
		level = table.get(25) > exp ? 24 : 25;
		level = table.get(26) > exp ? 25 : 26;
		level = table.get(27) > exp ? 26 : 27;
		level = table.get(28) > exp ? 27 : 28;
		level = table.get(29) > exp ? 28 : 29;
		level = table.get(30) > exp ? 29 : 30;
		return level;
	}
	
	
}
