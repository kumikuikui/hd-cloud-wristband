package com.coins.cloud.bo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserBaseBo
  * @Description: 设备用户信息
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年2月25日 下午5:45:58
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseBo {
	//用户Id
	private int userId;
	//名字
	private String name;
	//头像
	private String avatar;
	//钱包帐户id
	private String account;
	//钱包余额
	private double balance;
	//身高,cm
	private int height;
	//生日
	private String birthdate;
	//性别，1男2女
	private int genderType;
	//绑定设备数量
	private int bindCount;
}
