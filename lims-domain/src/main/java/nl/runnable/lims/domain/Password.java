package nl.runnable.lims.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * Represents a password. Passwords are stored using an MD5 hash.
 * 
 * @author Laurens Fridael
 * 
 */
@Embeddable
public class Password implements Serializable {

	private static final long serialVersionUID = -3138948457136318844L;

	/**
	 * Obtains a {@link Password} using the literal password.
	 * <p>
	 * This implementation creates and populates a new Password instance using the literal password's MD5 hash.
	 * 
	 * @param literal
	 * @return
	 */
	public static Password forLiteral(final String literal) {
		Assert.hasText(literal, "Password cannot be empty.");

		final String hash = createMd5Hash(literal);
		return forHash(hash);
	}

	/**
	 * Obtains a {@link Password} for the hash value.
	 * 
	 * @param hash
	 * @return
	 */
	public static Password forHash(final String hash) {
		Assert.hasText(hash, "Password hash cannot be empty.");

		final Password password = new Password();
		password.setHash(hash);
		return password;
	}

	private static String createMd5Hash(final String value) {
		try {
			final byte[] bytesOfMessage = value.getBytes("UTF-8");
			final MessageDigest md = MessageDigest.getInstance("MD5");
			final byte[] digest = md.digest(bytesOfMessage);
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < digest.length; ++i) {
				sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (final UnsupportedEncodingException e) {
			// Should not occur.
			throw new RuntimeException(e);
		} catch (final NoSuchAlgorithmException e) {
			// Should not occur.
			throw new RuntimeException(e);
		}
	}

	@Column(name = "password_hash", nullable = false)
	private String hash;

	protected Password() {
	}

	public String getHash() {
		return hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Password other = (Password) obj;
		if (hash == null) {
			if (other.hash != null) {
				return false;
			}
		} else if (!hash.equals(other.hash)) {
			return false;
		}
		return true;
	}

}
