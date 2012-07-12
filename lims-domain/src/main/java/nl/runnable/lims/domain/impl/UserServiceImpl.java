package nl.runnable.lims.domain.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nl.runnable.lims.domain.Group;
import nl.runnable.lims.domain.Password;
import nl.runnable.lims.domain.Role;
import nl.runnable.lims.domain.RoleRepository;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserRepository;
import nl.runnable.lims.domain.UserService;
import nl.runnable.lims.domain.User_;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@ManagedBean
@Transactional
public class UserServiceImpl implements UserService {

	/* Dependencies */

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserRepository userRepository;

	@Inject
	private RoleRepository roleRepository;

	/* Operations */

	@Override
	public boolean userExists(final String email) {
		Assert.hasText(email, "Email cannot be empty.");

		return createUserExistsQuery(email).getSingleResult().equals(1l);
	}

	@Override
	public User createUser(final String email, final Password password, final Set<String> roleNames) {
		Assert.hasText(email, "Email cannot be empty.");
		Assert.notNull(password, "Password cannot be null.");

		final Set<Role> roles = new HashSet<Role>(roleRepository.findByNameIn(roleNames));
		final User user = new User(email, password, roles, Collections.<Group> emptySet());
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	@Override
	public User login(final String email, final Password password) {
		Assert.hasText(email, "Email cannot be empty.");
		Assert.notNull(password, "Password cannot be null.");

		try {
			return createEmailPasswordQuery(email, password).getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	public User getUser(final String email) {
		Assert.hasText(email, "Email cannot be empty.");
		return userRepository.findByEmail(email);
	}

	/* Utility operations */

	private TypedQuery<Long> createUserExistsQuery(final String email) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> query = builder.createQuery(Long.class);
		final Root<User> root = query.from(User.class);
		query.select(builder.count(root)).where(builder.equal(root.get(User_.email), email));
		return entityManager.createQuery(query);
	}

	private TypedQuery<User> createEmailPasswordQuery(final String email, final Password password) {
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		final CriteriaQuery<User> query = builder.createQuery(User.class);
		final Root<User> root = query.from(User.class);
		query.where(builder.and(builder.equal(root.get(User_.email), email),
				builder.equal(root.get(User_.password), password)));
		return entityManager.createQuery(query);
	}

}
