package it.michelepiccirillo.scribbler;

import it.michelepiccirillo.mirror.beans.BeanInfo;
import it.michelepiccirillo.mirror.beans.IntrospectionException;
import it.michelepiccirillo.mirror.beans.Introspector;
import it.michelepiccirillo.mirror.beans.PropertyDescriptor;
import it.michelepiccirillo.scribbler.annotations.MarshalledBy;
import it.michelepiccirillo.scribbler.annotations.Transient;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanMetamodel<T> implements Metamodel<T> {
	private Class<T> type;
	private Map<String, PropertyDescriptor> descriptors;
	
	public BeanMetamodel(Class<T> type) {
		this.type = type;
		try {
			descriptors = new HashMap<String, PropertyDescriptor>();
			
			BeanInfo beanInfo = Introspector.getBeanInfo(type, Object.class);
			PropertyDescriptor[] descs = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd : descs) {
				descriptors.put(pd.getName(), pd);
			}
			
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public Set<String> getProperties() {
		return descriptors.keySet();
	}

	public T newInstance() {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating " + type.getName(), e);
		}
	}

	public void setValue(T obj, String property, Object value) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			pd.getWriteMethod().invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException("Error setting property '" + property + "'", e);
		}
		
	}

	public Object getValue(T obj, String property) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			return pd.getReadMethod().invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("Error setting property '" + property + "'", e);
		}
	}

	public  <M extends Marshaller<T, ?>> M getMarshaller(String property) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			Method m = pd.getReadMethod();
			MarshalledBy mb = m.getAnnotation(MarshalledBy.class);
			if(m != null) {
				return (M) mb.value().newInstance();
			}
			
			Type t = m.getReturnType();
			return (M) Marshaller.of(t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Type getType(String property) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			return pd.getPropertyType();
		} catch (Exception e) {
			throw new RuntimeException("Can't read type for property '" + property + "'");
		}
	}

	public boolean isReadonly(String property) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			return pd.getWriteMethod() == null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isTransient(String property) {
		try {
			PropertyDescriptor pd = descriptors.get(property);
			Method m = pd.getReadMethod();
			return m.isAnnotationPresent(Transient.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
