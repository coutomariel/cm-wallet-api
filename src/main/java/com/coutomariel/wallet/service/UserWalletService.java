package com.coutomariel.wallet.service;

import java.util.Optional;

import com.coutomariel.wallet.entity.UserWallet;

public interface UserWalletService {
	UserWallet save(UserWallet userWallet);
	
	Optional<UserWallet> findByUsersIdAndWalletId(Long user, Long wallet);
}
