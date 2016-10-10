package org.revolutio.jasb;

import java.lang.reflect.Field;

public interface PropertySetter {

	<T> void set(Field field, T t, String value);

	
	
}
