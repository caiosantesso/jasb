package org.revolutio.jasb.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Caio Santesso
 *
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {

	String value();
}
