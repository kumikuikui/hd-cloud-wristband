package com.coins.cloud.dao;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.vo.UserNricVo;

public interface UserNricDao {
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存身份信息
	* @return int
	 */
	public int save(UserNricVo userNricVo);
	
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
	* @Title: getAllUserNric 
	* @param: 
	* @Description: 获取列表
	* @return List<UserNricBo>
	 */
	public List<UserNricBo> getAllUserNric();
	
	/**
	 * 
	* @Title: existByNricNum 
	* @param: 
	* @Description: 验证身份证号是否存在
	* @return int
	 */
	public int existByNricNum(String nric);
}
