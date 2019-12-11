package com.coutomariel.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class WalletItemDTO {

	private Long id;
	@NotNull
	private Long wallet;
	@NotNull(message = "Informe uma data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt-BR", timezone = "Brazil/East")
	private Date date;
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp = "^(ENTRADA|SAÍDA)$", message = "Para o tipo somente aceitos ENTRADA ou SAIDA")
	private String type;
	@NotNull(message = "Informe uma descrição")
	@Length(min = 5, message = "Descrição deve conter no minímo 5 caracteres")
	private String description;
	@NotNull(message = "Informe um valor")
	private BigDecimal value;
}
