package com.coins.cloud.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.dao.sql.WristbandProvider;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;


@Mapper
public interface WristbandMapper {

	@InsertProvider(type = WristbandProvider.class,method = "save")
	int save(UserDeviceVo userDeviceVo);
	
	@Insert("INSERT INTO user_device_bind_sr "
		   +" (user_device_base_sb_seq,device_bind_address,create_by,create_time,update_by,update_time,active_flag) "
		   +" VALUES (#{userId},#{mac},#{userId},NOW(),#{userId},NOW(),'y')")
	public int userBindDevice(@Param("userId") int userId,@Param("mac") String mac);
	
	@Select("SELECT user_device_bind_sr_seq FROM user_device_bind_sr "
		   +" WHERE user_device_base_sb_seq = #{userId} AND device_bind_address = #{mac} AND active_flag = 'y'")
	public int getBandId(@Param("userId") int userId,@Param("mac") String mac);
	
	@Select("SELECT device_config_internal_code,device_record_value,MAX(create_time) createTime FROM user_device_record_bt"
		   +" WHERE user_device_base_sb_seq = #{userId} AND user_device_bind_sr_seq = #{bindId} AND active_flag = 'y' "
		   +" AND create_time >= curdate() AND create_time < date_add(curdate(),INTERVAL 1 day) "
		   +" GROUP BY device_config_internal_code")
	@Results(value = {
			@Result(property = "configCode", column = "device_config_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "value", column = "device_record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "createTime", javaType = String.class, jdbcType = JdbcType.DATE) 
	})
	public List<UserDeviceBo> getUserDevice(@Param("userId") int userId,@Param("bindId") int bindId);

	@Select("SELECT * FROM user_device_base_sb WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	@Results(value = {
			@Result(property = "userId", column = "user_device_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "device_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "avatar", column = "device_base_avatar_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "account", column = "entity_wallet_account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "balance", column = "entity_wallet_cnt", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "height", column = "device_base_height", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "birthdate", column = "device_base_birthdate", javaType = String.class, jdbcType = JdbcType.DATE),
			@Result(property = "genderType", column = "device_base_gender_itype", javaType = int.class, jdbcType = JdbcType.INTEGER) 
	})
	public UserBaseBo getUserById(@Param("userId") int userId);
	
	@Select("select a.device_record_value,a.create_time "
		   +" from user_device_record_bt a inner join "
		   +" (select DATE_FORMAT(create_time,'%Y-%m-%d') date1,max(create_time) time1 "
		   +" FROM user_device_record_bt "
		   +" where user_device_base_sb_seq = #{userId} AND user_device_bind_sr_seq = #{bindId} "
		   +" AND active_flag = 'y' AND device_config_internal_code = #{code} "
		   +" group by DATE_FORMAT(create_time,'%Y-%m-%d') ORDER BY create_time DESC LIMIT #{pageIndex},#{pageSize}) b "
		   +" on a.create_time = b.time1")
	@Results(value = {
			@Result(property = "value", column = "device_record_value", javaType = String.class, jdbcType = JdbcType.DATE),
			@Result(property = "time", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE) 
	})
	public List<UserDeviceBo> getRecordByCode(@Param("userId") int userId,
			@Param("bindId") int bindId, @Param("code") String code,
			@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 
	* @Title: getBindCountByUserId 
	* @param: 
	* @Description: 获取绑定设备数
	* @return void
	 */
	@Select("SELECT COUNT(1) FROM user_device_bind_sr WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	public int getBindCountByUserId(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: modifyInfo 
	* @param: 
	* @Description: 编辑个人信息
	* @return int
	 */
	@UpdateProvider(type = WristbandProvider.class,method = "update")
	public int modifyInfo(UserBaseVo userBaseVo);
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存用户设备记录
	* @return int
	 */
	@InsertProvider(type = WristbandProvider.class,method = "saveTarget")
	public int saveTarget(UserDeviceVo userDeviceVo);
	
	/**
	 * 
	* @Title: save 
	* @param: 
	* @Description: 保存用户设备记录
	* @return int
	 */
	@InsertProvider(type = WristbandProvider.class,method = "updateTarget")
	public int updateTarget(UserDeviceVo userDeviceVo);
	
	@Select("SELECT COUNT(1) FROM user_device_target_bt WHERE user_device_base_sb_seq = #{userId} "
		  + " AND user_device_bind_sr_seq = #{bindId} AND active_flag = 'y' AND device_config_internal_code = #{configCode}")
	public int getTargetExist(@Param("userId") int userId,@Param("bindId") int bindId,@Param("configCode") String configCode);
	
	@Select("SELECT * FROM user_device_target_bt WHERE user_device_base_sb_seq = #{userId} AND user_device_bind_sr_seq = #{bindId} AND active_flag = 'y'")
	@Results(value = {
			@Result(property = "configCode", column = "device_config_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "value", column = "device_target_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "update_time", javaType = String.class, jdbcType = JdbcType.DATE) })
	public List<UserDeviceBo> getTarget(@Param("userId") int userId,@Param("bindId") int bindId);
}
