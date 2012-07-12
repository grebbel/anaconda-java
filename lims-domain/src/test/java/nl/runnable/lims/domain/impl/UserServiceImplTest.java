package nl.runnable.lims.domain.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Inject;

import nl.runnable.lims.domain.Password;
import nl.runnable.lims.domain.Role;
import nl.runnable.lims.domain.RoleRepository;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/META-INF/spring/jpa-integration-test-context.xml")
@Transactional
public class UserServiceImplTest {

	@Inject
	private RoleRepository roleRepository;

	@Inject
	private UserService userService;

	@Before
	public void createRolesAndUsers() {
		final Role adminRole = new Role("admin");
		roleRepository.saveAndFlush(adminRole);
		final HashSet<String> roleNames = new HashSet<String>(Arrays.asList("secret"));
		assertNotNull(userService.createUser("user1@host.com", Password.forLiteral("secret"), roleNames));
		assertNotNull(userService.createUser("user2@host.com", Password.forLiteral("secret"), roleNames));
	}

	@Test
	public void testUserExists() {
		assertTrue(userService.userExists("user1@host.com"));
		assertTrue(userService.userExists("user2@host.com"));
		assertFalse(userService.userExists("user3@host.com"));
	}

	@Test
	public void testGetUser() {
		assertNotNull(userService.getUser("user1@host.com"));
		assertNotNull(userService.getUser("user2@host.com"));
		assertNull(userService.getUser("user3@host.com"));
	}

	@Test
	public void testLoginSuccess() {
		final User user1 = userService.login("user1@host.com", Password.forLiteral("secret"));
		assertEquals("user1@host.com", user1.getEmail());
		final User user2 = userService.login("user2@host.com", Password.forLiteral("secret"));
		assertEquals("user2@host.com", user2.getEmail());
	}

	@Test
	public void testLoginFailWithWrongEmail() {
		assertNull(userService.login("does-not-exist@host.com", Password.forLiteral("secret")));
	}

	@Test
	public void testLoginFailWithWrongPassword() {
		assertNull(userService.login("user1@host.com", Password.forLiteral("wrong-password")));
	}

}
