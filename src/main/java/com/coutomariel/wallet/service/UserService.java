package com.coutomariel.wallet.service;

import java.util.Optional;

import com.coutomariel.wallet.entity.User;

public interface UserService {

	User save(User user);
	
	Optional<User> findByEmail(String string);

}
