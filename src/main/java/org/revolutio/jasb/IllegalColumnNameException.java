/**
 * 
 */
package org.revolutio.jasb;

/**
 * @author Caio Santesso
 *
 */
public class IllegalColumnNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IllegalColumnNameException() {
	}

	/**
	 * @param message
	 */
	public IllegalColumnNameException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IllegalColumnNameException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IllegalColumnNameException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IllegalColumnNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
