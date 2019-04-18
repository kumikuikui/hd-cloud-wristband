package com.coins.cloud.service;

import java.util.List;

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.bo.WristbandBo;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;

public interface WristbandService {

	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存用户设备记录
	* @return int
	 */
	public int save(UserDeviceVo userDeviceVo);
	
	/**
	 * 
	* @Title: userBindDevice 
	* @param: 
	* @Description: 绑定设备
	* @return int
	 */
	public int userBindDevice(int userId,String mac);
	
	/**
	 * 
	* @Title: getBandId 
	* @param: 
	* @Description: 查询绑定id
	* @return int
	 */
	public int getBandId(int userId,String mac);
	
	/**
	 * 
	* @Title: getUserDevice 
	* @param: 
	* @Description: 查询设备信息记录
	* @return List<UserDeviceBo>
	 */
	public WristbandBo getUserDevice(int userId,String mac);
	
	/**
	 * 
	* @Title: getUserById 
	* @param: 
	* @Description: 查询用户
	* @return UserBaseBo
	 */
	public UserBaseBo getUserById(int userId);
	
	/**
	 * 
	* @Title: getRecordByCode 
	* @param: 
	* @Description: 查询列表
	* @return List<String>
	 */
	public List<UserDeviceBo> getRecordByCode(int userId,String mac,String code,String beginTime, String endTime);
	
	/**
	 * 
	* @Title: modifyInfo 
	* @param: 
	* @Description: 编辑个人信息
	* @return int
	 */
	public int modifyInfo(UserBaseVo userBaseVo);
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存目标数据
	* @return int
	 */
	public int saveTarget(UserDeviceVo userDeviceVo);
	
	/**
	 * 
	* @Title: updateTarget 
	* @param: 
	* @Description: 修改目标数据
	* @return int
	 */
	public int updateTarget(UserDeviceVo userDeviceVo);
	
	/**
	 * 
	* @Title: getTargetExist 
	* @param: 
	* @Description: 查询目标是否存在
	* @return int
	 */
	public int getTargetExist(int userId,String configCode);
	
	/**
	 * 
	* @Title: getTarget 
	* @param: 
	* @Description:查询目标数据
	* @return List<UserDeviceBo>
	 */
	public WristbandBo getTarget(int userId,String mac);
	
	/**
	 * 
	* @Title: regist 
	* @param: 
	* @Description: 注册
	* @return int
	 */
	int regist(UserBaseVo userBaseVo);

	/**
	 * 
	* @Title: login 
	* @param: 
	* @Description: 登录
	* @return int
	 */
	int login(UserBaseVo userBaseVo);
	
	/**
	 * 
	* @Title: existAccount 
	* @param: 
	* @Description: 验证账号是否已注册
	* @return int
	 */
	int existAccount(String account);
	
	/**
	 * 
	* @Title: getCalIntakeByToday 
	* @param: 
	* @Description: 查询今日某个内码值
	* @return UserDeviceBo
	 */
	List<UserDeviceBo> getTodayInfo(int userId,String configCode);
	
	/**
	 * 
	* @Title: updateCalIntake 
	* @param: 
	* @Description: 更新卡路里摄入量
	* @return int
	 */
	int updateCalIntake(int userDeviceId,String value);
	
	/**
	 * 
	* @Title: saveFood 
	* @param: 
	* @Description: 保存食物
	* @return int
	 */
	int saveFood(WristbandVo wristbandVo);
	
	/**
	 * 
	* @Title: getHeartCountByToday 
	* @param: 
	* @Description: 查询今日心率添加数量
	* @return int
	 */
	int getHeartCountByToday(int userId,String configCode);
	
	/**
	 * 
	* @Title: deleteRecord 
	* @param: 
	* @Description: 删除记录
	* @return int
	 */
	int deleteRecord(int userDeviceId);
	
}
