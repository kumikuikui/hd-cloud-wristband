package com.coins.cloud.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.CalFoodBo;
import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.dao.sql.WristbandProvider;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;


@Mapper
public interface WristbandMapper {

	@InsertProvider(type = WristbandProvider.class,method = "save")
	@SelectKey(keyProperty = "userDeviceId", before = false, resultType = int.class, statement = {
	"SELECT LAST_INSERT_ID() AS user_device_record_bt_seq " })
	int save(UserDeviceVo userDeviceVo);
	
	@Insert("INSERT INTO user_device_bind_sr "
		   +" (user_device_base_sb_seq,device_bind_address,create_by,create_time,update_by,update_time,active_flag) "
		   +" VALUES (#{userId},#{mac},#{userId},NOW(),#{userId},NOW(),'y')")
	public int userBindDevice(@Param("userId") int userId,@Param("mac") String mac);
	
	@SelectProvider(type = WristbandProvider.class,method = "getBandId")
	public Integer getBandId(@Param("userId") int userId,@Param("mac") String mac);
	
	@Select("SELECT x.device_config_internal_code,x.createTime, "
		   +" case when y.device_config_internal_code = 'con009' then floor(avg(y.device_record_value)) else max(y.device_record_value) end record_value "
		   +" FROM (SELECT device_config_internal_code,MAX(create_time) createTime,device_record_value "
		   +" FROM user_device_record_bt "
		   +" WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y' "
		   +" GROUP BY device_config_internal_code ) x "
		   +" INNER JOIN user_device_record_bt y "
		   +" ON x.device_config_internal_code = y.device_config_internal_code "
		   +" AND y.user_device_base_sb_seq = #{userId} AND y.active_flag = 'y' "
		   +" AND case when x.device_config_internal_code = 'con009' then DATE_FORMAT(x.createTime,'%Y-%m-%d') "
		   +" else x.createTime end = case when y.device_config_internal_code = 'con009' then DATE_FORMAT(y.create_time,'%Y-%m-%d') else  y.create_time end "
		   +" group by x.device_config_internal_code,x.createTime")
	@Results(value = {
			@Result(property = "configCode", column = "device_config_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "value", column = "record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "createTime", javaType = String.class, jdbcType = JdbcType.DATE) 
	})
	public List<UserDeviceBo> getUserDevice(@Param("userId") int userId);

	@Select("SELECT * FROM user_device_base_sb WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	@Results(value = {
			@Result(property = "userId", column = "user_device_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "device_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "avatar", column = "device_base_avatar_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "walletAccount", column = "entity_wallet_account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "balance", column = "entity_wallet_cnt", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "height", column = "device_base_height", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "birthdate", column = "device_base_birthdate", javaType = String.class, jdbcType = JdbcType.DATE),
			@Result(property = "genderType", column = "device_base_gender_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "firstName", column = "device_base_first_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "lastName", column = "device_base_last_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "insuranceNo", column = "device_base_insurance_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "insuranceName", column = "device_base_insurance_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "idcard", column = "device_base_idcard", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "idcardUrl", column = "device_base_idcard_urls", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "authType", column = "device_base_auth_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "registTime", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE)
	})
	public UserBaseBo getUserById(@Param("userId") int userId);
	
	@Select("select case when a.device_config_internal_code = 'con009' then x1 else a.device_record_value end record_value,"
		   +" a.create_time,a.user_device_record_bt_seq "
		   +" from user_device_record_bt a inner join "
		   +" (select DATE_FORMAT(create_time,'%Y-%m-%d') date1,max(create_time) time1,floor(avg(device_record_value)) x1 "
		   +" FROM user_device_record_bt "
		   +" where user_device_base_sb_seq = #{userId} "
		   +" AND active_flag = 'y' AND device_config_internal_code = #{code} "
		   +" AND create_time >= #{beginTime} AND create_time <= #{endTime} "
		   +" group by DATE_FORMAT(create_time,'%Y-%m-%d')) b "
		   +" on a.create_time = b.time1 and a.device_config_internal_code = #{code} and a.user_device_base_sb_seq = #{userId} ")
	@Results(value = {
			@Result(property = "userDeviceId", column = "user_device_record_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "value", column = "record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE) 
	})
	public List<UserDeviceBo> getRecordByCode(@Param("userId") int userId,
			@Param("code") String code,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
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
		  + " AND active_flag = 'y' AND device_config_internal_code = #{configCode}")
	public int getTargetExist(@Param("userId") int userId,@Param("configCode") String configCode);
	
	@Select("SELECT * FROM user_device_target_bt WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	@Results(value = {
			@Result(property = "configCode", column = "device_config_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "value", column = "device_target_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "update_time", javaType = String.class, jdbcType = JdbcType.DATE) })
	public List<UserDeviceBo> getTarget(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: regist 
	* @param: 
	* @Description: 注册
	* @return int
	 */
	@InsertProvider(type = WristbandProvider.class,method = "regist")
	@SelectKey(keyProperty = "userId", before = false, resultType = int.class, statement = {
	"SELECT LAST_INSERT_ID() AS user_device_base_sb_seq " })
	public int regist(UserBaseVo userBaseVo);
	
	/**
	 * 
	* @Title: login 
	* @param: 
	* @Description: 登录
	* @return int
	 */
	@Select("SELECT user_device_base_sb_seq FROM user_device_base_sb "
			+" WHERE device_base_account = #{account} AND device_base_password = #{password} AND active_flag = 'y'")
	Integer login(UserBaseVo userBaseVo);
	
	/**
	 * 
	* @Title: existAccount 
	* @param: 
	* @Description: 验证账号是否已注册
	* @return int
	 */
	@Select("SELECT user_device_base_sb_seq FROM user_device_base_sb WHERE device_base_account = #{account} AND active_flag = 'y'")
	Integer existAccount(@Param("account") String account);
	
	/**
	 * 
	* @Title: getCalIntakeByToday 
	* @param: 
	* @Description: 查询今日某个内码值
	* @return UserDeviceBo
	 */
	@Select("SELECT * FROM user_device_record_bt WHERE user_device_base_sb_seq = #{userId} "
			+" AND device_config_internal_code = #{configCode} "
			+" AND DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_FORMAT(#{date},'%Y-%m-%d') ORDER BY create_time DESC")
	@Results(value = {
			@Result(property = "userDeviceId", column = "user_device_record_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "value", column = "device_record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "update_time", javaType = String.class, jdbcType = JdbcType.DATE) })
	List<UserDeviceBo> getTodayInfo(@Param("userId") int userId,
			@Param("configCode") String configCode,@Param("date") String date);
	
	/**
	 * 
	* @Title: updateRecord 
	* @param: 
	* @Description: 更新记录
	* @return int
	 */
	@Update("UPDATE user_device_record_bt SET device_record_value = #{value},create_time = #{time},update_time = #{time} WHERE user_device_record_bt_seq = #{userDeviceId}")
	int updateRecord(@Param("userDeviceId") int userDeviceId,@Param("value") String value,@Param("time") String time);
	
	/**
	 * 
	* @Title: saveFood 
	* @param: 
	* @Description: 保存食物
	* @return int
	 */
	@InsertProvider(type = WristbandProvider.class,method = "saveFood")
	int saveFood(WristbandVo wristbandVo);
	
	/**
	 * 
	* @Title: getCalFoodList 
	* @param: 
	* @Description: 获取食物记录
	* @return List<CalFoodBo>
	 */
	@Select("SELECT * FROM user_food_intake_bt WHERE user_device_record_bt_seq = #{userDeviceId} "
			+ "AND active_flag = 'y' ORDER BY food_intake_eat_itype ASC")
	@Results(value = {
			@Result(property = "userDeviceId", column = "user_device_record_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "eatType", column = "food_intake_eat_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "foodName", column = "food_intake_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodBrand", column = "food_intake_brand", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodSize", column = "food_intake_size", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "calorieIntake", column = "food_intake_cal", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodFat", column = "food_intake_fat", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "calorieIntakeTime", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE) })
	List<CalFoodBo> getCalFoodList(@Param("userDeviceId") int userDeviceId);
	
	/**
	 * 
	* @Title: getNewWeight 
	* @param: 
	* @Description: 查询用户最新的重量
	* @return String
	 */
	@Select("SELECT device_record_value FROM user_device_record_bt "
		   +" WHERE active_flag = 'y' AND user_device_base_sb_seq = #{userId} AND device_config_internal_code = #{configCode} "
		   +" ORDER BY create_time DESC LIMIT 1")
	String getNewWeight(@Param("userId") int userId,@Param("configCode") String configCode);
	
	/**
	 * 
	* @Title: getHeartCountByToday 
	* @param: 
	* @Description: 查询今日心率添加数量
	* @return int
	 */
	@Select("SELECT COUNT(1) FROM user_device_record_bt "
		   +" WHERE active_flag = 'y' AND user_device_base_sb_seq = #{userId} AND device_config_internal_code = #{configCode} "
		   +" AND DATE_FORMAT(create_time,'%Y-%m-%d') = DATE_FORMAT(#{date},'%Y-%m-%d')")
	int getHeartCountByToday(@Param("userId") int userId,
			@Param("configCode") String configCode,
			@Param("date") String date);
	
	/**
	 * 
	* @Title: deleteRecord 
	* @param: 
	* @Description: 删除记录
	* @return int
	 */
	@Delete("DELETE FROM user_device_record_bt WHERE user_device_record_bt_seq = #{userDeviceId}")
	int deleteRecord(@Param("userDeviceId") int userDeviceId);
	
	/**
	 * 
	* @Title: getHints 
	* @param: 
	* @Description: 获取系统提示语
	* @return String
	 */
	@Select("SELECT sys_warning_content FROM user_sys_warning_sb WHERE active_flag = 'y' "
		   +" AND device_config_internal_code = #{code} AND sys_warning_status_itype = #{statusType} "
		   +" AND sys_warning_language_itype = #{languageType} ORDER BY user_sys_warning_sb_seq DESC LIMIT 1")
	String getHints(@Param("code") String code,
			@Param("statusType") int statusType,
			@Param("languageType") int languageType);
	
	/**
	 * 
	* @Title: getWeghtNearByTarget 
	* @param: 
	* @Description: 查询设定目标体重时的体重
	* @return String
	 */
	@Select("SELECT device_record_value FROM user_device_record_bt "
		   +" WHERE user_device_base_sb_seq = #{userId} AND device_config_internal_code = #{configCode} "
		   +" AND create_time <= #{time} ORDER BY create_time LIMIT 1")
	String getWeightNearByTarget(@Param("userId") int userId,
			@Param("configCode") String configCode, @Param("time") String time);
	
	/**
	 * 
	* @Title: getRecordByCodeAndMonth 
	* @param: 
	* @Description: 按code和月份分组查询
	* @return List<UserDeviceBo>
	 */
	@Select("SELECT device_record_value,create_time,device_config_internal_code FROM user_device_record_bt "
		   +" WHERE user_device_base_sb_seq = #{userId} AND device_config_internal_code = #{code} "
		   +" AND active_flag = 'y' AND create_time >= #{beginMonth} AND create_time < #{endMonth} ")
	@Results(value = {
			@Result(property = "configCode", column = "device_config_internal_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "value", column = "device_record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE) 
	})
	public List<UserDeviceBo> getRecordByCodeAndMonth(@Param("userId") int userId,
			@Param("code") String code, @Param("beginMonth") String beginMonth,
			@Param("endMonth") String endMonth);
}
