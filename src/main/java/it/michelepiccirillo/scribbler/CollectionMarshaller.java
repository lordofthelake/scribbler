package it.michelepiccirillo.scribbler;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

public class CollectionMarshaller<T, C extends Collection<T>> extends Marshaller<C, JSONArray> {

	private final Class<T> type;
	private final Class<C> collectionType;
	
	public CollectionMarshaller(Class<T> type, Class<C> collectionType) {
		this.type = type;
		this.collectionType = collectionType;
	}

	@Override
	public JSONArray marshall(C object) throws Exception {
		JSONArray array = new JSONArray();
		Marshaller<T, ?> marshaller = Marshaller.of(type);

		for(T val : object) {
			array.put(val == null ? JSONObject.NULL : marshaller.marshall(val));
		}
		
		return array;
	}

	@Override
	public C unmarshall(JSONArray marshalled) throws Exception {
		C collection = collectionType.newInstance();
		Marshaller<T, Object> marshaller = (Marshaller<T, Object>) Marshaller.of(type);
		
		int length = marshalled.length();
		for(int i = 0; i < length; ++i) {
			Object value = marshalled.get(i);
			collection.add(value == JSONObject.NULL ? null : marshaller.unmarshall(value));
		}
		
		return collection;
	}

}
