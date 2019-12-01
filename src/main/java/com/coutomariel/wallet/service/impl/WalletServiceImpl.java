package com.coutomariel.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coutomariel.wallet.entity.Wallet;
import com.coutomariel.wallet.repository.WalletRepository;
import com.coutomariel.wallet.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService{
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Override
	public Wallet save(Wallet wallet) {
		return walletRepository.save(wallet);
	}
	
	

}
