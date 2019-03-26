package com.coins.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: IndustryInfoVo
  * @Description: 手环mac值
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2018年12月29日 下午2:29:07
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalFoodBo {
	//卡路里摄入量
	private String calorieIntake;
	//卡路里摄入时间
	private String calorieIntakeTime;
	//用户设备记录主键id
	private int userDeviceId;
	//食物名称
	private String foodName;
	//食物品牌
	private String foodBrand;
	//食物规格
	private String foodSize;
	//食物脂肪含量
	private String foodFat;
	//餐别，1早餐，2中餐，3晚餐，4其它
	private int eatType;
}
