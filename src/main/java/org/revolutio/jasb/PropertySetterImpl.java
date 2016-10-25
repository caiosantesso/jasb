package org.revolutio.jasb;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

		if (value == null)
			return;

		if (type.equals(LocalDate.class)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			o = LocalDate.parse(value, formatter);
		}

		if (type.equals(Integer.TYPE)) {
			o = (int) Double.parseDouble(value);
		}

		if (type.equals(Integer.class)) {
			o = (int) Double.parseDouble(value);
		}

		if (type.equals(Double.class)) {
			o = Double.parseDouble(value);
		}

		if (type.equals(Double.TYPE)) {
			o = Double.valueOf(value);
		}

		if (type.equals(Float.class)) {
			o = Double.valueOf(value).floatValue();
		}

		if (type.equals(Float.TYPE)) {
			o = Double.valueOf(value).floatValue();
		}

		if (type.equals(Long.class)) {
			o = Double.valueOf(value).longValue();
		}

		if (type.equals(Long.TYPE)) {
			o = Double.valueOf(value).longValue();
		}

		if (type.equals(Boolean.class)) {
			o = Boolean.getBoolean(value);
		}

		if (type.equals(Boolean.TYPE)) {
			o = Boolean.getBoolean(value);
		}

		if (type.equals(Character.TYPE)) {
			o = value.charAt(0);
		}

		if (type.equals(Character.class)) {
			o = Character.valueOf(value.charAt(0));
		}

		if (type.equals(String.class)) {
			o = value;
		}

		if (type.equals(BigInteger.class)) {
			o = new BigInteger("" + Double.valueOf(value).intValue());
		}
		
		if (type.equals(BigDecimal.class)) {
			o = new BigDecimal(value);
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
