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

import com.coutomariel.wallet.dto.UserWalletDTO;
import com.coutomariel.wallet.entity.User;
import com.coutomariel.wallet.entity.UserWallet;
import com.coutomariel.wallet.entity.Wallet;
import com.coutomariel.wallet.response.Response;
import com.coutomariel.wallet.service.UserWalletService;

@RestController
@RequestMapping("user-wallet")
public class UserWalletController {

	@Autowired
	private UserWalletService service;
	
	@PostMapping
	public ResponseEntity<Response<UserWalletDTO>> create (@Valid @RequestBody UserWalletDTO dto, 
			BindingResult result){
		Response<UserWalletDTO> response = new Response<UserWalletDTO>();
		if(result.hasErrors()) {
			result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		UserWallet userWallet = service.save(convertDtoToEntity(dto));
		response.setData(this.convertEntityToDto(userWallet));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	public UserWallet convertDtoToEntity(UserWalletDTO dto) {
		UserWallet userWallet = new UserWallet();

		User user = new User();
		user.setId(dto.getUsers());
		
		Wallet wallet = new Wallet();
		wallet.setId(dto.getWallet());
		
		userWallet.setId(dto.getId());
		userWallet.setUsers(user);
		userWallet.setWallet(wallet);
		
		return userWallet;
	}
	
	public UserWalletDTO convertEntityToDto(UserWallet userWallet) {
		UserWalletDTO dto = new UserWalletDTO();
		
		User user = new User();
		user.setId(dto.getUsers());
		
		Wallet wallet = new Wallet();
		wallet.setId(dto.getWallet());
		
		dto.setId(userWallet.getId());
		dto.setUsers(userWallet.getUsers().getId());
		dto.setWallet(userWallet.getWallet().getId());
		return dto;
	}
}
