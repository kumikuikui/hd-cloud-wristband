package com.coins.cloud.service;

import java.util.List;

import com.coins.cloud.bo.FriendBo;
import com.coins.cloud.bo.UserFriendBo;

public interface FriendService {

	/**
	 * 
	* @Title: searchUser 
	* @param: 
	* @Description: 搜索用户
	* @return List<UserFriendBo>
	 */
	public List<UserFriendBo> searchUser(String account,int userId);
	
	/**
	 * 
	* @Title: getMyFriends 
	* @param: 
	* @Description: 获取我的好友
	* @return String
	 */
	public String getMyFriends(int userId);
	
	/**
	 * 
	* @Title: checkAddRecord 
	* @param: 
	* @Description: 查询是否有添加记录
	* @return int
	 */
	public int checkAddRecord(int userId,int targetUserId);
	
	/**
	 * 
	* @Title: addFriendRecord 
	* @param: 
	* @Description: 添加好友申请记录
	* @return int
	 */
	public int addFriendRecord(int userId,String account,int targetUserId);
	
	/**
	 * 
	* @Title: getAccountByUserId 
	* @param: 
	* @Description: 查询用户账号
	* @return String
	 */
	public String getAccountByUserId(int userId);
	
	/**
	 * 
	* @Title: getFriendApplicatList 
	* @param: 
	* @Description: 好友申请列表
	* @return List<FriendBo>
	 */
	public List<FriendBo> getFriendApplicatList(int userId);
	
	/**
	 * 
	* @Title: addFriend 
	* @param: 
	* @Description: 添加好友
	* @return int
	 */
	public int addFriend(int userId,String friendIds);
	
	/**
	 * 
	* @Title: updateFriend 
	* @param: 
	* @Description: 更新好友
	* @return int
	 */
	public int updateFriend(int userId,String friendIds);
	
	/**
	 * 
	* @Title: updateRecordStatus 
	* @param: 
	* @Description: 更新好友请求状态
	* @return int
	 */
	public int updateRecordStatus(int userId,int targetUserId,int status);
	
	/**
	 * 
	* @Title: getAllFriend 
	* @param: 
	* @Description: 获取我的所有好友
	* @return List<FriendBo>
	 */
	public List<FriendBo> getAllFriend(int userId);
	
	/**
	 * 
	* @Title: getStepRank 
	* @param: 
	* @Description: 获取步数排行榜
	* @return List<FriendBo>
	 */
	public List<FriendBo> getStepRank(int userId, String time);
	
	/**
	 * 
	* @Title: getStepStarTotal 
	* @param: 
	* @Description: 获取步数点赞总数
	* @return int
	 */
	public int getStepStarTotal(int userId,String time);
	
	/**
	 * 
	* @Title: checkStar 
	* @param: 
	* @Description: 是否点赞
	* @return int
	 */
	public int checkStar(int userId,String time,int targetUserId);
	
	/**
	 * 
	* @Title: starStep 
	* @param: 
	* @Description: 点赞步数
	* @return int
	 */
	public int starStep(int userId,String time,int targetUserId);
	
	/**
	 * 
	* @Title: unStarStep 
	* @param: 
	* @Description: 取消点赞
	* @return int
	 */
	public int unStarStep(int userId,String time,int targetUserId);
	
}
