package com.coins.cloud.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.vo.UserNricVo;

public interface UserNricService {

	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保留身份信息
	* @return int
	 */
	public int save(InputStream inputStream);
	
	/**
	 * 
	* @Title: getUserNricList 
	* @param: 
	* @Description: 获取列表
	* @return List<UserNricBo>
	 */
	public List<UserNricBo> getUserNricList(int pageIndex,int pageSize);
	
	/**
	 * 
	* @Title: getTotal 
	* @param: 
	* @Description: 获取总数
	* @return int
	 */
	public int getTotal();
	
	/**
	 * 
	* @Title: getUserNricDetail 
	* @param: 
	* @Description: 详情
	* @return UserNricBo
	 */
	public UserNricBo getUserNricDetail(int id);
	
	/**
	 * 
	* @Title: updateUserNric 
	* @param: 
	* @Description: 修改身份信息
	* @return int
	 */
	int updateUserNric(UserNricVo userNricVo);
	
	/**
	 * 
	* @Title: reportUserNric 
	* @param: 
	* @Description: 导出报表
	* @return void
	 */
	File reportUserNric(String csvFilePath, String fileName);
}
