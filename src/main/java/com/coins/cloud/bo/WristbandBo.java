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
public class WristbandBo {
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
	//体重测量时间
	private String weightTime;
	//睡眠开始时间
	private String sleepStartTime;
	//睡眠结束时间
	private String sleepEndTime;
	//饮水量
	private String drinkWater;
	//饮水时间
	private String drinkWaterTime;
	//BMI
	private String bmi;
	//距离M
	private double distance;
	//卡路里摄入量
	private String calorieIntake;
	//卡路里摄入时间
	private String calorieIntakeTime;
	//步数
	private String targetStep;
	//卡路里消耗
	private String targetCalorie;
	//体重KG
	private String targetWeight;
	//目标重量设定时间
	private String targetWeightTime;
	//睡眠
	private String targetSleep;
	//卡路里摄入
	private String targetCalorieIntake;
	//饮水量
	private String targetWater;
	//心率警告 1:偏低 2:正常 3:偏高
	private int heartWarn;
	//血压警告 1:低血压,2:理想血压,3:正常血压,4:正常高值,5:轻度高血压(1级),6:中度高血压(2级),7:重度高血压(3级)
	private int pressureWarn;
	//BMI警告 1:偏瘦, 2:正常,3:过重,4:肥胖 
	private int bmiWarn;

}
