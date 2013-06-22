package it.michelepiccirillo.scribbler;

import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class MapMarshaller<T, M extends Map<String, T>> extends Marshaller<M, JSONObject> {
	
	private final Class<T> type;
	private final Class<M> mapType;
	
	public MapMarshaller(Class<T> type, Class<M> mapType) {
		this.type = type;
		this.mapType = mapType;
	}

	@Override
	public JSONObject marshall(M map) throws Exception {
		JSONObject obj = new JSONObject();
		Marshaller<T, ?> marshaller = Marshaller.of(type);
		
		for(Map.Entry<String, T> entry : map.entrySet()) {
			T value = entry.getValue();
			if(value != null)
				obj.put(entry.getKey(), marshaller.marshall(value));
		}
		
		return obj;		
	}

	@Override
	public M unmarshall(JSONObject marshalled) throws Exception {
		M map = mapType.newInstance();
		Iterator<String> it = marshalled.keys();
		Marshaller<T, Object> marshaller = (Marshaller<T, Object>) Marshaller.of(type);
		
		while(it.hasNext()) {
			String key = it.next();
			map.put(key, marshaller.unmarshall(marshalled.get(key)));
		}
		
		return map;		
	}
	
	
}
