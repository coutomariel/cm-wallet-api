package com.coutomariel.wallet.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.coutomariel.wallet.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

	private static final String EMAIL = "email@email.com";

	@Autowired
	UserRepository userRepository;

	@Before
	public void setUp() {
		User user = new User();
		user.setName("Set Up user");
		user.setEmail(EMAIL);
		user.setPassword("123456");

		userRepository.save(user);
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void testSave() {
		User user = new User();
		user.setName("João");
		user.setEmail("joao@email.com");
		user.setPassword("123456");

		User response = userRepository.save(user);

		assertNotNull(response);
	}

	@Test
	public void testFindByEmail() {
		Optional<User> user = userRepository.findByEmailEquals(EMAIL);
		assertTrue(user.isPresent());
		assertEquals(EMAIL, user.get().getEmail());
	}
}
