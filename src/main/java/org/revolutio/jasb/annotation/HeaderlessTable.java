/**
 * 
 */
package org.revolutio.jasb.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A classe anotada torna-se uma planilha sem cabeçalhos.
 * <p>
 * Os campos precisam ser anotados com {@link Column} para serem vinculados.
 * 
 * @author Caio Santesso
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HeaderlessTable {

	/**
	 * Especifica o nome da planilha a ser procurado. Caso vazio o nome exato da
	 * classe anotada será utilizado em seu lugar.
	 * 
	 * @return
	 */
	String value() default "";
	
}
