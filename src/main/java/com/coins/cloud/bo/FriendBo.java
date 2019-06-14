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
public class FriendBo {
	//用户Id
	private int friendUserId;
	//名字
	private String name;
	//头像
	private String avatar;
	//用户账号
	private String account;
	//添加状态，1、待处理 2、已添加 3、拒绝
	private int  friendType;
	//步数
	private String step;
	//点赞人数
	private int starNum;
	//是否点赞
	private boolean isStar;
	//时间
	private String time;
}
