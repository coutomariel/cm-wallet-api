package com.coutomariel.wallet.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.coutomariel.wallet.dto.UserDTO;
import com.coutomariel.wallet.entity.User;
import com.coutomariel.wallet.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	private static final String EMAIL = "test@email.com";
	private static final String NAME = "jonh";
	private static final String PASSWORD = "123456";

	private static final String URL = "/users";

	@MockBean
	UserService userService;

	@Autowired
	MockMvc mvc;

	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(userService.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayLoad())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}

	public User getMockUser() {
		User user = new User();
		user.setEmail(EMAIL);
		user.setName(NAME);
		user.setPassword(PASSWORD);
		return user;
	}

	public String getJsonPayLoad() throws JsonProcessingException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(EMAIL);
		userDTO.setName(NAME);
		userDTO.setPassword(PASSWORD);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(userDTO);
	}
}
