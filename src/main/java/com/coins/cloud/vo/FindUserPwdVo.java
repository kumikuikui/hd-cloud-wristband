package com.coins.cloud.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FindUserPwdVo {

	// 邮箱 手机号码
	private String email;

	// 验证码
	private String captcha;

	// 密码
	private String password;
}
