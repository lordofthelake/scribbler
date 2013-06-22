package it.michelepiccirillo.scribbler;

import java.util.Iterator;
import java.util.Set;

import org.json.JSONObject;


public class BeanMarshaller<C> extends Marshaller<C, JSONObject> {
	private Metamodel<C> metamodel;
	
	public BeanMarshaller(Metamodel<C> metamodel) {
		this.metamodel = metamodel;
	}

	@Override
	public JSONObject marshall(C object) throws Exception {
		JSONObject out = new JSONObject();
		for(String key : metamodel.getProperties()) {
			if(!metamodel.isTransient(key)) {
				Marshaller m = metamodel.getMarshaller(key);
				out.put(key, m.marshall(metamodel.getValue(object, key)));
			}
		}
		
		return out;
	}

	@Override
	public C unmarshall(JSONObject marshalled) throws Exception {
		C obj = metamodel.newInstance();
		Set<String> properties = metamodel.getProperties();
		
		Iterator<String> it = marshalled.keys();
		while(it.hasNext()) {
			String key = it.next();
			
			if(properties.contains(key) && !metamodel.isReadonly(key)) {
				Marshaller m = metamodel.getMarshaller(key);
				metamodel.setValue(obj, key, m.unmarshall(marshalled.get(key)));
			}
		}
		
		return obj;
	}


}
