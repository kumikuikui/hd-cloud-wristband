package com.coins.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.dao.WristbandDao;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;

@Repository
public class WristbandDaoMybatisImpl implements WristbandDao {

	@Inject
	private WristbandMapper wristbandMapper;

	@Override
	public int save(UserDeviceVo userDeviceVo) {
		return wristbandMapper.save(userDeviceVo);
	}

	@Override
	public int userBindDevice(int userId, String mac) {
		return wristbandMapper.userBindDevice(userId, mac);
	}

	@Override
	public int getBandId(int userId, String mac) {
		return wristbandMapper.getBandId(userId, mac);
	}

	@Override
	public List<UserDeviceBo> getUserDevice(int userId, int bindId) {
		return wristbandMapper.getUserDevice(userId, bindId);
	}

	@Override
	public UserBaseBo getUserById(int userId) {
		return wristbandMapper.getUserById(userId);
	}

	@Override
	public List<UserDeviceBo> getRecordByCode(int userId, int bindId, String code,
			int pageIndex, int pageSize) {
		return wristbandMapper.getRecordByCode(userId, bindId, code, pageIndex,
				pageSize);
	}

	@Override
	public int getBindCountByUserId(int userId) {
		return wristbandMapper.getBindCountByUserId(userId);
	}

	@Override
	public int modifyInfo(UserBaseVo userBaseVo) {
		return wristbandMapper.modifyInfo(userBaseVo);
	}

	@Override
	public int saveTarget(UserDeviceVo userDeviceVo) {
		return wristbandMapper.saveTarget(userDeviceVo);
	}

	@Override
	public int updateTarget(UserDeviceVo userDeviceVo) {
		return wristbandMapper.updateTarget(userDeviceVo);
	}

	@Override
	public int getTargetExist(int userId, int bindId) {
		return wristbandMapper.getTargetExist(userId, bindId);
	}

	@Override
	public List<UserDeviceBo> getTarget(int userId, int bindId) {
		return wristbandMapper.getTarget(userId, bindId);
	}

}
