package com.coins.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;

public interface WristbandDao {
	
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
	public List<UserDeviceBo> getUserDevice(int userId,int bindId);
	
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
	public List<UserDeviceBo> getRecordByCode(int userId,int bindId,String code,int pageIndex,int pageSize);
	
	/**
	 * 
	* @Title: getBindCountByUserId 
	* @param: 
	* @Description: 获取绑定设备数
	* @return void
	 */
	public int getBindCountByUserId(int userId);
	
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
	public int getTargetExist(int userId,int bindId,String configCode);
	
	/**
	 * 
	* @Title: getTarget 
	* @param: 
	* @Description:查询目标数据
	* @return List<UserDeviceBo>
	 */
	public List<UserDeviceBo> getTarget(int userId,int bindId);
}
