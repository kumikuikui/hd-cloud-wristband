package com.coins.cloud.service;

import java.util.List;

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.bo.WristbandBo;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;

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
	public List<UserDeviceBo> getRecordByCode(int userId,String mac,String code,int pageIndex,int pageSize);
	
	/**
	 * 
	* @Title: modifyInfo 
	* @param: 
	* @Description: 编辑个人信息
	* @return int
	 */
	public int modifyInfo(UserBaseVo userBaseVo);

}
