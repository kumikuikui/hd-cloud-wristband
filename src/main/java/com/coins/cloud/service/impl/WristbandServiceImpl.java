package com.coins.cloud.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.coins.cloud.util.DeviceConfig;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;

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
		return wristbandDao.getBandId(userId, mac);
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
				double distance = Integer.parseInt(wristbandBo.getStep()) * 0.5;
				wristbandBo.setDistance(distance);
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
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con004)){//重量
				wristbandBo.setWeight(userDeviceBo.getValue().split("\\|")[0]);
				wristbandBo.setFat(userDeviceBo.getValue().split("\\|")[1]);
				//计算BMI(BMI 体重公斤数除以身高米数平方)
				int height = userBaseBo.getHeight();
				if(height > 0){
					double h = height / 100.0;
					double weight =Double.parseDouble(wristbandBo.getWeight());
					double bmi = weight / h / h;
					BigDecimal bg = new BigDecimal(bmi).setScale(2, RoundingMode.HALF_UP);
					wristbandBo.setBmi(String.valueOf(bg.doubleValue()));
				}
				wristbandBo.setTargetWeight(map.get(userDeviceBo.getConfigCode()));
				wristbandBo.setTargetWeightTime(targetWeightTime);
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
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con008)){//身高
			}
			if(userDeviceBo.getConfigCode().equals(DeviceConfig.con009)){//心率
				wristbandBo.setHeart(userDeviceBo.getValue());
				wristbandBo.setHeartTime(userDeviceBo.getTime());
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
			int bindCount = wristbandDao.getBindCountByUserId(userId);
			userBaseBo.setBindCount(bindCount);
		}
		return userBaseBo;
	}

	@Override
	public List<UserDeviceBo> getRecordByCode(int userId, String mac, String code,
			int pageIndex, int pageSize) {
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
		List<UserDeviceBo> list = wristbandDao.getRecordByCode(userId, code, pageIndex, pageSize);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UserDeviceBo userDeviceBo : list) {
			if(temp.equals(DeviceConfig.con000_1)){//距离
				double distance = Integer.parseInt(userDeviceBo.getValue()) * 0.5;
				userDeviceBo.setValue(String.valueOf(distance));
			}
			if(temp.equals(DeviceConfig.con000_1)){//BMI
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
	public UserDeviceBo getCalIntakeByToday(int userId,String configCode) {
		return wristbandDao.getCalIntakeByToday(userId,configCode);
	}

	@Override
	public int updateCalIntake(int userDeviceId, String value) {
		return wristbandDao.updateCalIntake(userDeviceId, value);
	}

	@Override
	public int saveFood(WristbandVo wristbandVo) {
		return wristbandDao.saveFood(wristbandVo);
	}
}
