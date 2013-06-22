package it.michelepiccirillo.scribbler;

import java.lang.reflect.Type;
import java.util.Set;

public class BeanMetamodel<T> implements Metamodel<T> {
	private Class<T> type;
	
	public BeanMetamodel(Class<T> type) {
		this.type = type;
	}

	public Set<String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public T newInstance() {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating " + type.getName(), e);
		}
	}

	public void setValue(T obj, String property, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object getValue(T obj, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	public Marshaller<?, ?> getMarshaller(String property) {
		// TODO Auto-generated method stub
		return null;
	}

	public Type getType(String property) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isReadonly(String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isTransient(String property) {
		// TODO Auto-generated method stub
		return false;
	}

}
