package com.coins.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserNricVo
  * @Description: 身份证信息
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年2月11日 下午3:47:03
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNricBo {
	//用户Id
	private int userId;
	//身份证号
	private String nric;
	//名字
	private String name;
	//
	private String oldIc;
	//生日
	private String birthDate;
	//出生地
	private String birthPlace;
	//性别
	private String gender;
	//地址1
	private String address1;
	//地址2
	private String address2;
	//地址3
	private String address3;
	//邮编
	private String postcode;
	//城市
	private String city;
	//洲
	private String state;
	//种族
	private String race;
	//宗教
	private String religion;
	//国籍
	private String citizenship;
	//签证日期
	private String issueDate;
	//起源
	private String emorigin;
	//编码
	private String handcode;
	//大马卡（马来西亚人民现用的身份证）图像路径
	private String mimageUrl;
	//数字照片路径
	private String cameraUrl;
	//指标状态
	private String indicatorStatus;
}
