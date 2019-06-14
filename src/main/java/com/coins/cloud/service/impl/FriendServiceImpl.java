package com.coins.cloud.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.coins.cloud.bo.FriendBo;
import com.coins.cloud.bo.UserFriendBo;
import com.coins.cloud.dao.FriendDao;
import com.coins.cloud.service.FriendService;
import com.hlb.cloud.util.StringUtil;

@Service
public class FriendServiceImpl implements FriendService {
	
	@Inject
	private FriendDao friendDao;

	@Override
	public List<UserFriendBo> searchUser(String account,int userId) {
		List<UserFriendBo> list = friendDao.searchUser(account);
		//查询好友主键Id
		String friendIds = friendDao.getMyFriends(userId);
		if(!StringUtil.isBlank(friendIds)){
			friendIds = "|" + friendIds + "|";
			for (UserFriendBo userFriendBo : list) {
				String str = "|" + userFriendBo.getUserId() + "|";
				if(friendIds.contains(str)){//在好友列表里
					userFriendBo.setFriend(true);
				}
			}
		}	
		return list;
	}

	@Override
	public String getMyFriends(int userId) {
		return friendDao.getMyFriends(userId);
	}

	@Override
	public int checkAddRecord(int userId, int targetUserId) {
		return friendDao.checkAddRecord(userId, targetUserId);
	}

	@Override
	public int addFriendRecord(int userId, String account, int targetUserId) {
		return friendDao.addFriendRecord(userId, account, targetUserId);
	}

	@Override
	public String getAccountByUserId(int userId) {
		return friendDao.getAccountByUserId(userId);
	}

	@Override
	public List<FriendBo> getFriendApplicatList(int userId) {
		return friendDao.getFriendApplicatList(userId);
	}

	@Override
	public int addFriend(int userId, String friendIds) {
		return friendDao.addFriend(userId, friendIds);
	}

	@Override
	public int updateFriend(int userId, String friendIds) {
		return friendDao.updateFriend(userId, friendIds);
	}

	@Override
	public int updateRecordStatus(int userId, int targetUserId, int status) {
		return friendDao.updateRecordStatus(userId, targetUserId, status);
	}

	@Override
	public List<FriendBo> getAllFriend(int userId) {
		return friendDao.getAllFriend(userId);
	}

	@Override
	public List<FriendBo> getStepRank(int userId, String time) {
		return friendDao.getStepRank(userId, time);
	}

	@Override
	public int getStepStarTotal(int userId, String time) {
		return friendDao.getStepStarTotal(userId, time);
	}

	@Override
	public int checkStar(int userId, String time, int targetUserId) {
		return friendDao.checkStar(userId, time, targetUserId);
	}

	@Override
	public int starStep(int userId, String time, int targetUserId) {
		return friendDao.starStep(userId, time, targetUserId);
	}

	@Override
	public int unStarStep(int userId, String time, int targetUserId) {
		return friendDao.unStarStep(userId, time, targetUserId);
	}
}
