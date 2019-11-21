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
public class SmsVo { 

	// 1 手机注册 2 邮箱注册  3 手机找回密码  4 邮箱找回密码 
	private int type;
	
	private String email;
}
