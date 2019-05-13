package com.coins.cloud.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserDeviceBo
  * @Description: 设备信息记录
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年2月25日 下午5:08:37
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceBo {
	private int userDeviceId;
	private String configCode;
	private String value;
	private String time;
	private String calsOut;
	private List<CalFoodBo> foodList;
}
