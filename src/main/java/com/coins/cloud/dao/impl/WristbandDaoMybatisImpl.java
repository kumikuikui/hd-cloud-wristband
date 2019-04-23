package com.coins.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.CalFoodBo;
import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.dao.WristbandDao;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;

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
	public Integer getBandId(int userId, String mac) {
		return wristbandMapper.getBandId(userId, mac);
	}

	@Override
	public List<UserDeviceBo> getUserDevice(int userId) {
		return wristbandMapper.getUserDevice(userId);
	}

	@Override
	public UserBaseBo getUserById(int userId) {
		return wristbandMapper.getUserById(userId);
	}

	@Override
	public List<UserDeviceBo> getRecordByCode(int userId, String code,
			String beginTime, String endTime) {
		return wristbandMapper.getRecordByCode(userId, code, beginTime,
				endTime);
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
	public int getTargetExist(int userId,String configCode) {
		return wristbandMapper.getTargetExist(userId,configCode);
	}

	@Override
	public List<UserDeviceBo> getTarget(int userId) {
		return wristbandMapper.getTarget(userId);
	}

	@Override
	public int regist(UserBaseVo userBaseVo) {
		return wristbandMapper.regist(userBaseVo);
	}

	@Override
	public Integer login(UserBaseVo userBaseVo) {
		return wristbandMapper.login(userBaseVo);
	}

	@Override
	public Integer existAccount(String account) {
		return wristbandMapper.existAccount(account);
	}

	@Override
	public List<UserDeviceBo> getTodayInfo(int userId, String configCode) {
		return wristbandMapper.getTodayInfo(userId, configCode);
	}

	@Override
	public int updateCalIntake(int userDeviceId, String value) {
		return wristbandMapper.updateCalIntake(userDeviceId, value);
	}

	@Override
	public int saveFood(WristbandVo wristbandVo) {
		return wristbandMapper.saveFood(wristbandVo);
	}

	@Override
	public List<CalFoodBo> getCalFoodList(int userDeviceId) {
		return wristbandMapper.getCalFoodList(userDeviceId);
	}

	@Override
	public String getNewWeight(int userId, String configCode) {
		return wristbandMapper.getNewWeight(userId, configCode);
	}

	@Override
	public int getHeartCountByToday(int userId, String configCode) {
		return wristbandMapper.getHeartCountByToday(userId, configCode);
	}

	@Override
	public int deleteRecord(int userDeviceId) {
		return wristbandMapper.deleteRecord(userDeviceId);
	}

	@Override
	public String getHints(String code, int statusType, int languageType) {
		return wristbandMapper.getHints(code, statusType, languageType);
	}

}
