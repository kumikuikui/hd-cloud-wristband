package com.coins.cloud.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Service;

import com.coins.cloud.bo.CalFoodBo;
import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.bo.WristbandBo;
import com.coins.cloud.dao.WristbandDao;
import com.coins.cloud.service.WristbandService;
import com.coins.cloud.util.CommonUtil;
import com.coins.cloud.util.DeviceConfig;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;
import com.hlb.cloud.util.StringUtil;

@Service
public class WristbandServiceImpl implements WristbandService {
	
	@Inject
	private WristbandDao wristbandDao;

	@Override
	public int save(UserDeviceVo userDeviceVo) {
		return wristbandDao.save(userDeviceVo);
	}

	@Override
	public int userBindDevice(int userId, String mac) {
		return wristbandDao.userBindDevice(userId, mac);
	}

	@Override
	public int getBandId(int userId, String mac) {
		Integer bandId = wristbandDao.getBandId(userId, mac);
		return bandId == null ? 0 : bandId.intValue();
	}

	@Override
	public WristbandBo getUserDevice(int userId, String mac) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//int bindId = wristbandDao.getBandId(userId, mac);
		List<UserDeviceBo> list = wristbandDao.getUserDevice(userId);
		WristbandBo wristbandBo = WristbandBo.builder().userId(userId).mac(mac).build();
		//查询目标数据
		List<UserDeviceBo> targetList = wristbandDao.getTarget(userId);
		HashMap<String, String> map = new HashMap<>();
		String targetWeightTime = "";
		for (UserDeviceBo userDeviceBo : targetList) {
			try {
				String time = sdf.format(sdf.parse(userDeviceBo.getTime()));
				userDeviceBo.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put(userDeviceBo.getConfigCode(), userDeviceBo.getValue());
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con004)){
				targetWeightTime = userDeviceBo.getTime();
			}
		}
		//查询用户信息
		UserBaseBo userBaseBo = this.getUserById(userId);
		for (UserDeviceBo userDeviceBo : list) {
			try {
				String time = sdf.format(sdf.parse(userDeviceBo.getTime()));
				userDeviceBo.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con001)){//脚步
				wristbandBo.setStep(userDeviceBo.getValue());
				wristbandBo.setStepTime(userDeviceBo.getTime());
				//计算距离
				double distance = Integer.parseInt(wristbandBo.getStep()) * 0.5 / 1000.0;
				BigDecimal bg = new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP);
				wristbandBo.setDistance(bg.doubleValue());
				wristbandBo.setTargetStep(map.get(userDeviceBo.getConfigCode()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con002)){//卡路里燃烧
				wristbandBo.setCalorie(userDeviceBo.getValue());
				wristbandBo.setCalorieTime(userDeviceBo.getTime());
				wristbandBo.setTargetCalorie(map.get(userDeviceBo.getConfigCode()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con003)){//血压
				wristbandBo.setDiastolic(userDeviceBo.getValue().split("\\|")[0]);
				wristbandBo.setSystolic(userDeviceBo.getValue().split("\\|")[1]);
				wristbandBo.setPressureTime(userDeviceBo.getTime());
				wristbandBo.setPressureWarn(CommonUtil.checkBloodPressure(userDeviceBo.getValue()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con004)){//重量
				wristbandBo.setWeight(userDeviceBo.getValue().split("\\|")[0]);
				wristbandBo.setFat(userDeviceBo.getValue().split("\\|")[1]);
				wristbandBo.setWeightTime(userDeviceBo.getTime());
				//计算BMI(BMI 体重公斤数除以身高米数平方)
				int height = userBaseBo.getHeight();
				if(height > 0){
					double h = height / 100.0;
					double weight =Double.parseDouble(wristbandBo.getWeight());
					double bmi = weight / h / h;
					BigDecimal bg = new BigDecimal(bmi).setScale(2, RoundingMode.HALF_UP);
					wristbandBo.setBmi(String.valueOf(bg.doubleValue()));
					wristbandBo.setBmiWarn(CommonUtil.checkBMI(wristbandBo.getBmi()));
				}
				wristbandBo.setTargetWeight(map.get(userDeviceBo.getConfigCode()));
				wristbandBo.setTargetWeightTime(targetWeightTime);
				//查询设定目标体重时的体重
				String targetCurrWeight = wristbandDao.getWeightNearByTarget(userId, userDeviceBo.getConfigCode(), targetWeightTime);
				wristbandBo.setTargetCurrWeight(targetCurrWeight.split("\\|")[0]);
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con005)){//睡觉
				wristbandBo.setSleepStartTime(userDeviceBo.getValue().split("\\|")[0]);
				wristbandBo.setSleepEndTime(userDeviceBo.getValue().split("\\|")[1]);
				wristbandBo.setTargetSleep(map.get(userDeviceBo.getConfigCode()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con006)){//卡路里摄入量
				wristbandBo.setCalorieIntake(userDeviceBo.getValue());
				wristbandBo.setCalorieIntakeTime(userDeviceBo.getTime());
				wristbandBo.setTargetCalorieIntake(map.get(userDeviceBo.getConfigCode()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con007)){//水
				wristbandBo.setDrinkWater(userDeviceBo.getValue());
				wristbandBo.setTargetWater(map.get(userDeviceBo.getConfigCode()));
				wristbandBo.setDrinkWaterTime(userDeviceBo.getTime());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con008)){//身高
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con009)){//心率
				wristbandBo.setHeart(userDeviceBo.getValue());
				wristbandBo.setHeartTime(userDeviceBo.getTime());
				wristbandBo.setHeartWarn(CommonUtil.checkHeart(userDeviceBo.getValue()));
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con010)){//血氧
				wristbandBo.setBlood(userDeviceBo.getValue());
				wristbandBo.setBloodTime(userDeviceBo.getTime());
			}
		}
		return wristbandBo;
	}

	@Override
	public UserBaseBo getUserById(int userId) {
		UserBaseBo userBaseBo = wristbandDao.getUserById(userId);
		if(userBaseBo != null){
			//查询手环绑定数量
			int bindCount = wristbandDao.getBindCountByUserId(userId);
			userBaseBo.setBindCount(bindCount);
			//查询最新的体重
			String value = wristbandDao.getNewWeight(userId, DeviceConfig.con004);
			if(!StringUtil.isBlank(value)){
				userBaseBo.setWeight(value.split("\\|")[0]);
			}
		}
		return userBaseBo;
	}

	@Override
	public List<UserDeviceBo> getRecordByCode(int userId, String mac, String code,
			String beginTime, String endTime) {
		//int bindId = this.getBandId(userId, mac);
		String temp = code;
		if(code.equals(DeviceConfig.con000_1)){//距离
			code = DeviceConfig.con001;
		}
		int height = 0;
		if(code.equals(DeviceConfig.con000_2)){//BMI
			code = DeviceConfig.con004;
			//查询用户信息
			UserBaseBo userBaseBo = this.getUserById(userId);
			height = userBaseBo.getHeight();
		}
		List<UserDeviceBo> list = wristbandDao.getRecordByCode(userId, code, beginTime, endTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UserDeviceBo userDeviceBo : list) {
			if(temp.equals(DeviceConfig.con000_1)){//距离
				double distance = Integer.parseInt(userDeviceBo.getValue()) * 0.5 / 1000.0;
				BigDecimal bg = new BigDecimal(distance).setScale(1, RoundingMode.HALF_UP);
				userDeviceBo.setValue(String.valueOf(bg.doubleValue()));
			}
			if(temp.equals(DeviceConfig.con000_2)){//BMI
				//计算BMI(BMI 体重公斤数除以身高米数平方)
				if(height > 0){
					double h = height / 100.0;
					double weight =Double.parseDouble(userDeviceBo.getValue().split("\\|")[0]);
					double bmi = weight / h / h;
					BigDecimal bg = new BigDecimal(bmi).setScale(2, RoundingMode.HALF_UP);
					userDeviceBo.setValue(String.valueOf(bg.doubleValue()));
				}
			}
			if(temp.equals(DeviceConfig.con004)){//重量
				userDeviceBo.setValue(userDeviceBo.getValue().split("\\|")[0]);
			}
			if(temp.equals(DeviceConfig.con006)){//卡路里摄入量
				List<CalFoodBo> foodList = wristbandDao.getCalFoodList(userDeviceBo.getUserDeviceId());
				for (CalFoodBo calFoodBo : foodList) {
					try {
						calFoodBo.setCalorieIntakeTime(sdf.format(sdf.parse(calFoodBo.getCalorieIntakeTime())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				userDeviceBo.setFoodList(foodList);
			}
			String time = "";
			try {
				time = sdf.format(sdf.parse(userDeviceBo.getTime()));
				userDeviceBo.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public int modifyInfo(UserBaseVo userBaseVo) {
		return wristbandDao.modifyInfo(userBaseVo);
	}

	@Override
	public int saveTarget(UserDeviceVo userDeviceVo) {
		return wristbandDao.saveTarget(userDeviceVo);
	}

	@Override
	public int updateTarget(UserDeviceVo userDeviceVo) {
		return wristbandDao.updateTarget(userDeviceVo);
	}

	@Override
	public int getTargetExist(int userId,String configCode) {
		return wristbandDao.getTargetExist(userId,configCode);
	}

	@Override
	public WristbandBo getTarget(int userId, String mac) {
//		int bindId = wristbandDao.getBandId(userId, mac);
		WristbandBo wristbandBo = WristbandBo.builder().userId(userId).mac(mac).build();
		//查询目标数据
		List<UserDeviceBo> targetList = wristbandDao.getTarget(userId);
		for (UserDeviceBo userDeviceBo : targetList) {
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con001)){//脚步
				wristbandBo.setTargetStep(userDeviceBo.getValue());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con002)){//卡路里燃烧
				wristbandBo.setTargetCalorie(userDeviceBo.getValue());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con004)){//重量
				wristbandBo.setTargetWeight(userDeviceBo.getValue());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con005)){//睡觉
				wristbandBo.setTargetSleep(userDeviceBo.getValue());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con006)){//卡路里摄入量
				wristbandBo.setTargetCalorieIntake(userDeviceBo.getValue());
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con007)){//水
				wristbandBo.setTargetWater(userDeviceBo.getValue());
			}
		}
		return wristbandBo;
	}

	@Override
	public int regist(UserBaseVo userBaseVo) {
		return wristbandDao.regist(userBaseVo);
	}

	@Override
	public int login(UserBaseVo userBaseVo) {
		Integer userId = wristbandDao.login(userBaseVo);
		return userId == null ? 0 : userId.intValue();
	}

	@Override
	public int existAccount(String account) {
		Integer userId =  wristbandDao.existAccount(account);
		return userId == null ? 0 : userId.intValue();
	}

	@Override
	public List<UserDeviceBo> getTodayInfo(int userId,String configCode,String date) {
		return wristbandDao.getTodayInfo(userId,configCode,date);
	}

	@Override
	public int updateRecord(int userDeviceId, String value,String time) {
		return wristbandDao.updateRecord(userDeviceId, value,time);
	}

	@Override
	public int saveFood(WristbandVo wristbandVo) {
		return wristbandDao.saveFood(wristbandVo);
	}

	@Override
	public int getHeartCountByToday(int userId, String configCode, String date) {
		return wristbandDao.getHeartCountByToday(userId, configCode,date);
	}

	@Override
	public int deleteRecord(int userDeviceId) {
		return wristbandDao.deleteRecord(userDeviceId);
	}

	@Override
	public String getHints(String code, int statusType, int languageType) {
		return wristbandDao.getHints(code, statusType, languageType);
	}

	@Override
	public List<UserDeviceBo> getTarget(int userId) {
		return wristbandDao.getTarget(userId);
	}

	@Override
	public List<UserDeviceBo> getRecordByCodeAndMonth(int userId, String code,
			String beginMonth, String endMonth) {
		String temp = code;
		if(code.equals(DeviceConfig.con000_1)){//距离
			code = DeviceConfig.con001;
		}
		int height = 0;
		if(code.equals(DeviceConfig.con000_2)){//BMI
			code = DeviceConfig.con004;
			//查询用户信息
			UserBaseBo userBaseBo = this.getUserById(userId);
			height = userBaseBo.getHeight();
		}
		SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");
		HashMap<String, String> map = new HashMap<>();
		List<UserDeviceBo> list = wristbandDao.getRecordByCodeAndMonth(userId, code, beginMonth, endMonth);
		for (UserDeviceBo userDeviceBo : list) {
			try {
				String time = monthSdf.format(monthSdf.parse(userDeviceBo.getTime()));
				userDeviceBo.setTime(time);
				userDeviceBo.setValue(userDeviceBo.getValue().split("\\|")[0]);
				if(!map.containsKey(time)){
					map.put(time, "");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, String> retuMap = new HashMap<>();
		for (String key : map.keySet()) {
			double sum = 0.0;
			double avg = 0.0;
			int i = 0;
			for (UserDeviceBo userDeviceBo : list) {
				if(key.equals(userDeviceBo.getTime())){
					i++;
					sum += Double.parseDouble(userDeviceBo.getValue());
				}
			}
			avg = sum / i;
			BigDecimal bg = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);
			retuMap.put(key, String.valueOf(bg.doubleValue()));
		}
		List<UserDeviceBo> recordList = new ArrayList<UserDeviceBo>();
		for (String key : retuMap.keySet()) {
			UserDeviceBo userDevi = UserDeviceBo.builder().time(key).value(retuMap.get(key)).build();
			recordList.add(userDevi);
		}
		for (UserDeviceBo userDeviceBo : recordList) {
			if(temp.equals(DeviceConfig.con000_2)){//BMI
				//计算BMI(BMI 体重公斤数除以身高米数平方)
				if(height > 0){
					double h = height / 100.0;
					double weight =Double.parseDouble(userDeviceBo.getValue());
					double bmi = weight / h / h;
					BigDecimal bg = new BigDecimal(bmi).setScale(2, RoundingMode.HALF_UP);
					userDeviceBo.setValue(String.valueOf(bg.doubleValue()));
				}
			}
		}
		return recordList;
	}
}
