package com.coins.cloud.vo;

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
public class WristbandVo {
	//用户Id
	private int userId;
	//手环mac
	private String mac;
	//步数
	private String step;
	//步数时间
	private String stepTime;
	//心率
	private String heart;
	//心率时间
	private String heartTime;
	//卡路里消耗
	private String calorie;
	//卡路里时间
	private String calorieTime;
	//血氧
	private String blood;
	//血氧时间
	private String bloodTime;
	//血压伸张值
	private String diastolic;
	//血压收缩值
	private String systolic;
	//血压时间
	private String pressureTime;
	//体重KG
	private String weight;
	//体脂 百分比
	private String fat;
	//称重时间
	private String weightTime;
	//睡眠开始时间
	private String sleepStartTime;
	//睡眠结束时间
	private String sleepEndTime;
	//饮水量
	private String drinkWater;
	//饮水时间
	private String drinkTime;
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
