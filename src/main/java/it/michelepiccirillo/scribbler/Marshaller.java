package it.michelepiccirillo.scribbler;

import java.lang.reflect.Type;


public abstract class Marshaller<I, O> {
	public static <C> Marshaller<C, ?> of(Type clazz) {
		return null;
	}
	
	public abstract O marshall(I object) throws Exception;
	
	public abstract I unmarshall(O marshalled) throws Exception;
}
