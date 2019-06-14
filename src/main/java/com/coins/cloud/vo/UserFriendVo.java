package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserFriendVo
  * @Description: 好友
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年6月13日 上午10:50:22
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendVo {
	//用户Id
	private int userId;
	//目标用户Id
	private int targetUserId;
	//1、同意 2、拒绝
	private int operType;
	//时间
	private String time;
}
