package nl.runnable.lims.webapp.helpers;

import java.util.Date;

import javax.annotation.ManagedBean;

/**
 * Helper for obtaining date/time information.
 * 
 * @author Laurens Fridael
 * 
 */
@ManagedBean
public class TimeHelper {

	private static ThreadLocal<Long> currentTime = new ThreadLocal<Long>();

	/**
	 * Obtains the current time. Returns the system time if the time has not been set explcitly using
	 * {@link #setCurrentTime(long)}.
	 * 
	 * @return The current time in milliseconds.
	 */
	public static long getCurrentTime() {
		Long time = currentTime.get();
		if (time == null) {
			time = new Date().getTime();
		}
		return time;
	}

	/**
	 * Sets the current time.
	 * 
	 * @param time
	 *            UNIX time in milliseconds.
	 */
	public static void setCurrentTime(final long time) {
		currentTime.set(time);
	}

	/**
	 * Clears the current time. Should be called after every request.
	 */
	public static void clearCurrentTime() {
		currentTime.remove();
	}

}
