package me.Haeseke1.Alliances.Buildings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

	
	public static void Save(Object o, File f){
		try{
			if(!f.exists()){
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.flush();
			oos.close();
			}catch( Exception e){
				e.printStackTrace();
			}
			
		
	}
	
	
	public static Object Load(File f){
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			Object result = ois.readObject();
			ois.close();
			return result;
		}catch(Exception e){
			return null;
		}
	}
	
}
