package com.coins.cloud.vo;

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
public class UserBaseVo {
	//用户Id
	private int userId;
	//名字
	private String name;
	//头像
	private String avatar;
	//身高,cm
	private int height;
	//生日
	private String birthdate;
	//性别，1男2女
	private int genderType;
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
	//帐号
	private String account;
	//密码
	private String password;
	//体重
	private String weight;
	////保险状态，0未认证，1认证中，2认证成功，3认证失败
	private int authType;
}
