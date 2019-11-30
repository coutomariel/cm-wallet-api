package com.coutomariel.wallet.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	private Long id;
	@Length(min = 3, max = 30, message = "O nom precisa ter entre 3 e 30 caracteres.")
	private String name;
	@Email(message = "Email inválido")
	private String email;
	@NotNull
	@Length(min = 6, message = "A senha deve ter no minímo 6 caracteres.")
	private String password;
}
