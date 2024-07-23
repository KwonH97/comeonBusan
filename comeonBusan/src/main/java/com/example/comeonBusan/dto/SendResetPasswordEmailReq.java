package com.example.comeonBusan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendResetPasswordEmailReq {

	 private String email;
	 private String username;
}
