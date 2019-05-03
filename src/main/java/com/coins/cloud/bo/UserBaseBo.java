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
	private String walletAccount;
	//钱包余额
	private double balance;
	//身高,cm
	private double height;
	//体重
	private String weight;
	//生日
	private String birthdate;
	//性别，1男2女
	private int genderType;
	//绑定设备数量
	private int bindCount;
	//First name
	private String firstName;
	//Last name
	private String lastName;
	//保险号
	private String insuranceNo;
	//保险公司的名字
	private String insuranceName;
	//身份识别号
	private String idcard;
	//身份识别图片,多张图用|分隔
	private String idcardUrl;
	//认证状态0未认证，1认证中，2认证成功，3认证失败
	private int authType;
}
