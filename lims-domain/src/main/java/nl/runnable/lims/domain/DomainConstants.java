package nl.runnable.lims.domain;

/**
 * Contains constants specific to the domain.
 * 
 * @author Laurens Fridael
 * 
 */
public class DomainConstants {

	/**
	 * The maximum difference for which two given RN values are still considered equal.
	 * <p>
	 * This value is based on comparing a given RN value from the 7500 System Software (read from the Excel sheet as a
	 * <code>double</code> value) with the value stored in a MySQL FLOAT field.
	 */
	public static double MAXIMUM_RN_DIFFERENCE = 0.0000000000001;

	/**
	 * The maximum difference for which values that map to a SQL FLOAT column are still considered equal.
	 * <p>
	 * This is currently considered equal to {@value #MAXIMUM_RN_DIFFERENCE}.
	 */
	public static double MAX_FLOAT_DIFFERENCE = 0.0000000000001;

	/**
	 * Indicates the maximum length of an external token.
	 * 
	 * @see Analysis#setExternalId(String)
	 */
	public static final int EXTERNAL_ID_MAX_LENGTH = 255;

	/**
	 * Constructor made private to prevent instantiation and subclassing.
	 */
	private DomainConstants() {
	}

}
