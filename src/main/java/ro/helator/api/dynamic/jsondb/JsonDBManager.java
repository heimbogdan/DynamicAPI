package ro.helator.api.dynamic.jsondb;

import io.jsondb.JsonDBTemplate;
import java.util.List;

public class JsonDBManager {

	private JsonDBManager(){}

	private static String dbFilesLocation = "DynamicAPI/JsonDB";
	private static String baseScanPackage = "ro.helator.api.dynamic.dto";
	
	private static JsonDBTemplate jsonDBTemplate;
	
	static {
		jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
	}
	
	public static <T> void insert(T entity){
		checkCollection(entity.getClass());
		
		jsonDBTemplate.insert(entity);
	}
	
	public static <T> void remove(T entity){
		checkCollection(entity.getClass());
		
		jsonDBTemplate.remove(entity, entity.getClass());
	}
	
	public static <T> void save(T entity){
		checkCollection(entity.getClass());
		
		jsonDBTemplate.save(entity, entity.getClass());
	}
	
	public static <T> void update(T entity){
		checkCollection(entity.getClass());
		
		jsonDBTemplate.upsert(entity);
	}
	
	public static <T> List<T> find(String jxQuery, Class<T> clazz){
		checkCollection(clazz);
		
		List<T> entities = jsonDBTemplate.find(jxQuery, clazz);
		return entities;
	}
	
	public static <T> T findById(String id, Class<T> clazz){
		checkCollection(clazz);
		
		T entity = jsonDBTemplate.findById(id, clazz);
		return entity;
	}
	
	private static <T> void checkCollection(Class<T> clazz){
		if(!jsonDBTemplate.collectionExists(clazz)){
			jsonDBTemplate.createCollection(clazz);
		}
	}
	
	public static String formatJxQuery(String jxQuery, Object...objects ){
		int size = objects.length;
		for(int i = 0; i < size; i++){
			if(null == objects[i]){
				objects[i] = "*";
			} else if(objects[i] instanceof String){
				objects[i] = "'" + objects[i] + "'";
			}
		}
		return String.format(jxQuery, objects);
	}
}
