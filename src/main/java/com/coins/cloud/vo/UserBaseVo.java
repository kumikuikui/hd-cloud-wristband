package com.coins.cloud.vo;

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
}
