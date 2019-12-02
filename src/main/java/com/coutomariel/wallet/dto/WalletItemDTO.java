package com.coutomariel.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.coutomariel.wallet.entity.Wallet;

import lombok.Data;

@Data
public class WalletItemDTO {

	private Long id;
	@NotNull
	private Wallet wallet;
	@NotNull(message = "Informe uma data")
	private Date date;
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp = "^(ENTRADA/SAIDA)$", message = "Para o tipo somente aceitos ENTRADA ou SAIDA")
	private String type;
	@NotNull(message = "Informe uma descrição")
	@Length(min = 5, message = "Descrição deve conter no minímo 5 caracteres")
	private String description;
	@NotNull(message = "Informe um valor")
	private BigDecimal value;
}
