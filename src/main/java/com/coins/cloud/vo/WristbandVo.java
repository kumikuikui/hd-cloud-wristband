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
	//卡路里
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
	//血压时间戳
	private String pressureTime;
	
	
}
