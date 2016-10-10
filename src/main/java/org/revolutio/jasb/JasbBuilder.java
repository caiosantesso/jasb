package org.revolutio.jasb;

public enum JasbBuilder {

	INSTANCE;

	public static JasbBuilder getInstance() {
		return INSTANCE;
	}

	public Jasb build() {
		return JasbImpl.newInstance(this);
	}

}
