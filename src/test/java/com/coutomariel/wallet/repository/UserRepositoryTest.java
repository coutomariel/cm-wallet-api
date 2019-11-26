package com.coutomariel.wallet.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testSave() {
		User user = new User();
		user.setName("João");
		user.setEmail("joao@email.com");
		user.setPassword("123456");
		
		User response = userRepository.save(user);
		
		assertNotNull(response);
	}
}
