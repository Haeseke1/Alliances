package me.Haeseke1.Alliances.Main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityTypes;

public class NMSUtil {
	
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
		try{
			List<Map<?,?>> datamap = new ArrayList<Map<?,?>>();
			for(Field f : EntityTypes.class.getDeclaredFields()){
				
				if(f.getType().getSimpleName().equals(Map.class.getSimpleName())){
					f.setAccessible(true);
					datamap.add((Map<?,?>) f.get(null));
				}
			}
			
			if(datamap.get(2).containsKey(id)){
				datamap.get(0).remove(name);
				datamap.get(2).remove(id);
			}
			
			Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			method.setAccessible(true);
			method.invoke(null, customClass, name, id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
