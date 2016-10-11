package org.revolutio.jasb;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class PropertySetterImpl implements PropertySetter {

	private PropertySetterImpl() {

	}

	static PropertySetter newInstance() {
		return new PropertySetterImpl();
	}

	@Override
	public <T> void set(Field field, T t, String value) {
		Class<?> type = field.getType();
		Object o = null;

		if (type.equals(LocalDate.class)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			o = LocalDate.parse(value, formatter);
		}

		if (type.equals(Integer.TYPE)) {
			o = (int) Double.parseDouble(value);
		}

		if (type.equals(Double.TYPE)) {
			o = Double.parseDouble(value);
		}

		if (type.equals(String.class)) {
			o = value;
		}
		
		if (!field.isAccessible())
			field.setAccessible(true);
		try {
			field.set(t, o);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new JasbUnderlyingApiException(e);
		}

	}

}
