package com.coutomariel.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coutomariel.wallet.entity.UserWallet;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long>{

}
