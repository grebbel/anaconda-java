package nl.runnable.lims;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method can be audited. Typically used to track the use of key operations. An example would be to log an
 * application's activity.
 * 
 * @author Laurens Fridael
 * 
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
	/**
	 * Specifies the message or message key for logging the audited method.
	 * 
	 * @return
	 */
	String value() default "";
}
