package com.coins.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.FriendBo;
import com.coins.cloud.bo.UserFriendBo;
import com.coins.cloud.dao.FriendDao;

@Repository
public class FriendDaoMybatisImpl implements FriendDao {

	@Inject
	private FriendMapper friendMapper;

	@Override
	public List<UserFriendBo> searchUser(String account) {
		return friendMapper.searchUser(account);
	}

	@Override
	public String getMyFriends(int userId) {
		return friendMapper.getMyFriends(userId);
	}

	@Override
	public int checkAddRecord(int userId, int targetUserId) {
		return friendMapper.checkAddRecord(userId, targetUserId);
	}

	@Override
	public int addFriendRecord(int userId, String account, int targetUserId) {
		return friendMapper.addFriendRecord(userId, account, targetUserId);
	}

	@Override
	public String getAccountByUserId(int userId) {
		return friendMapper.getAccountByUserId(userId);
	}

	@Override
	public List<FriendBo> getFriendApplicatList(int userId) {
		return friendMapper.getFriendApplicatList(userId);
	}

	@Override
	public int addFriend(int userId, String friendIds) {
		return friendMapper.addFriend(userId, friendIds);
	}

	@Override
	public int updateFriend(int userId, String friendIds) {
		return friendMapper.updateFriend(userId, friendIds);
	}

	@Override
	public int updateRecordStatus(int userId, int targetUserId,int status) {
		return friendMapper.updateRecordStatus(userId, targetUserId, status);
	}

	@Override
	public List<FriendBo> getAllFriend(int userId) {
		return friendMapper.getAllFriend(userId);
	}

	@Override
	public List<FriendBo> getStepRank(int userId, String time) {
		return friendMapper.getStepRank(userId,time);
	}
	
	@Override
	public List<FriendBo> getMyStep(int userId, String time) {
		return friendMapper.getMyStep(userId, time);
	}

	@Override
	public int getStepStarTotal(int userId, String time) {
		return friendMapper.getStepStarTotal(userId, time);
	}

	@Override
	public int checkStar(int userId, String time, int targetUserId) {
		return friendMapper.checkStar(userId, time, targetUserId);
	}

	@Override
	public int starStep(int userId, String time, int targetUserId) {
		return friendMapper.starStep(userId, time, targetUserId);
	}

	@Override
	public int unStarStep(int userId, String time, int targetUserId) {
		return friendMapper.unStarStep(userId, time, targetUserId);
	}

}
