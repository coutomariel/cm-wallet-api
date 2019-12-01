package com.coutomariel.wallet.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class WalletDTO {

	private Long id;
	@NotNull(message = "Defina um nome para carteira")
	@Length(min = 3, message = "O nome precisa conter no mínimo 3 caracteres")
	private String name;
	@NotNull(message = "Insira um valor para carteira.")
	private BigDecimal value;
	 
}