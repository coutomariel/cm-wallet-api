package com.coutomariel.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coutomariel.wallet.dto.UserDto;
import com.coutomariel.wallet.entity.User;
import com.coutomariel.wallet.response.Response;
import com.coutomariel.wallet.service.UserService;
import com.coutomariel.wallet.util.Bcrypt;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Response<UserDto>> create(@Valid @RequestBody UserDto userDTO, BindingResult result) {
		Response<UserDto> response = new Response<UserDto>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		User user = userService.save(this.convertDtoToEntity(userDTO));
		response.setData(this.convertEntityToDto(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	private User convertDtoToEntity(UserDto dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(Bcrypt.getHash(dto.getPassword()));
		return user;
	}

	private UserDto convertEntityToDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		return dto;
	}
}
