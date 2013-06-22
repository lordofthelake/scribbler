package it.michelepiccirillo.scribbler;

import java.lang.reflect.Type;
import java.util.Set;

public interface Metamodel<T> {

	public Set<String> getProperties();
	
	public T newInstance();
	
	public void setValue(T obj, String property, Object value);
	
	public Object getValue(T obj, String property);
	
	public Marshaller<?, ?> getMarshaller(String property);
		
	public Type getType(String property);
	
	public boolean isReadonly(String property);
	
	public boolean isTransient(String property);
}
