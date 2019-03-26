package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserDeviceVo
  * @Description: 用户设备记录
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年2月25日 下午3:03:55
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceVo {
	private int userDeviceId;
	private int userId;
	private int bindId;
	private String configCode;
	private String value;
	private String remark;
	private String time;
}
