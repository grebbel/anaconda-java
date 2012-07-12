package nl.runnable.lims.domain;

import java.util.Set;

public interface UserService {

	/**
	 * Tests if the user with the given e-mail exists.
	 * 
	 * @param email
	 * @return True if the user exists, false if not.
	 */
	public boolean userExists(String email);

	/**
	 * Creates a {@link User} using the provided credentials. Clients are expected to first test for existing e-mails
	 * using {@link #userExists(String)}.
	 * 
	 * @param email
	 * @param password
	 * @return The new {@link User}.
	 */
	public User createUser(String email, Password password, Set<String> role);

	/**
	 * Attempts to login using the specified credentials.
	 * 
	 * @param email
	 * @param password
	 * @return The matching {@link User} or null if the user could not be authenticated.
	 */
	public User login(String email, Password password);

	public User getUser(String email);

}
