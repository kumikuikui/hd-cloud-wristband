package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: RequestVo
  * @Description: 加密参数
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年3月26日 上午9:53:46
  *
 */
@SuppressWarnings("deprecation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestVo {

	private String sign;

	private int appType;
}
