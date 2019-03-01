package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: IndustryInfoVo
  * @Description: 手环目标
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2018年12月29日 下午2:29:07
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WristbandTargetVo {
	//用户Id
	private int userId;
	//手环mac
	private String mac;
	//步数
	private String targetStep;
	//卡路里消耗
	private String targetCalorie;
	//体重KG
	private String targetWeight;
	//睡眠
	private String targetSleep;
	//卡路里摄入
	private String targetCalorieIntake;
	//饮水量
	private String targetWater;
}
