package com.coins.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRecord {

	/**
	 * 验证码ID
	 */
	private long smsId;

	/**
	 * 账号：手机
	 */
	private String account;

	/**
	 * 验证码
	 */
	private String verifyCode;

	/**
	 * 类型
	 */
	private int codeType;

	/**
	 * 状态:1已验证 0未验证
	 */
	private int verifyStatus;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 激活状态
	 */
	private String activeFlag;

	// checkType 为查询的时间区间类型 1为查询一分钟内 2为查询一天内
	private int checkType;
}
