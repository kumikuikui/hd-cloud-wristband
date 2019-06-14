package com.coins.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
  * @ClassName: UserFriendBo
  * @Description: 用户好友
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年6月13日 上午10:21:42
  *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendBo {
	//用户Id
	private int userId;
	//名字
	private String name;
	//头像
	private String avatar;
	//用户账号
	private String account;
	//是否为好友
	private boolean isFriend;
}
