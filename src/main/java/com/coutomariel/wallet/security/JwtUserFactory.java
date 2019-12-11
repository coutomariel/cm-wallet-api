package com.coutomariel.wallet.security;

import com.coutomariel.wallet.entity.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtUserFactory {

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword());
	}
	
}
