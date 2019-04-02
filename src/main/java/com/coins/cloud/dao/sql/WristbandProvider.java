package com.coins.cloud.dao.sql;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandVo;
import com.hlb.cloud.util.StringUtil;

public class WristbandProvider {

	public String regist(UserBaseVo userBaseVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_device_base_sb");
				VALUES("device_base_account", "#{account}");
				VALUES("device_base_password", "#{password}");
				VALUES("create_by", "10000");
				VALUES("create_time", "now()");
				VALUES("update_by", "10000");
				VALUES("update_time", "now()");
				VALUES("active_flag", "'y'");
			}
		}.toString();
		return sql;
	}
	
	public String save(UserDeviceVo userDeviceVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_device_record_bt");
				VALUES("user_device_base_sb_seq", "#{userId}");
				VALUES("device_config_internal_code", "#{configCode}");
				VALUES("device_record_value", "#{value}");
				VALUES("device_record_remark", "#{remark}");
				VALUES("create_by", "#{userId}");
				VALUES("create_time", "#{time}");
				VALUES("update_by", "#{userId}");
				VALUES("update_time", "#{time}");
				VALUES("active_flag", "'y'");
			}
		}.toString();
		return sql;
	}
	
	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑
	 * @return String
	 */
	public String update(UserBaseVo userBaseVo) {
		return new SQL() {
			{
				UPDATE("user_device_base_sb");
				if (StringUtils.isNotBlank(userBaseVo.getName())) {
					SET("device_base_name = #{name}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getAvatar())) {
					SET("device_base_avatar_url = #{avatar}");
				}
				if (userBaseVo.getHeight() > 0) {
					SET("device_base_height = #{height}");
				}
				if(userBaseVo.getGenderType() > 0){
					SET("device_base_gender_itype = #{genderType}");
				}
				if (userBaseVo.getBirthdate() != null) {
					SET("device_base_birthdate = #{birthdate}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getFirstName())) {
					SET("device_base_first_name = #{firstName}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getLastName())) {
					SET("device_base_last_name = #{lastName}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getInsuranceNo())) {
					SET("device_base_insurance_no = #{insuranceNo}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getInsuranceName())) {
					SET("device_base_insurance_name = #{insuranceName}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getIdcard())) {
					SET("device_base_idcard = #{idcard}");
				}
				if (StringUtils.isNotBlank(userBaseVo.getIdcardUrl())) {
					SET("device_base_idcard_urls = #{idcardUrl}");
				}
				SET("update_time = now()");
				WHERE("user_device_base_sb_seq = #{userId}");
			}
		}.toString();
	}
	
	public String saveTarget(UserDeviceVo userDeviceVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_device_target_bt");
				VALUES("user_device_base_sb_seq", "#{userId}");
				VALUES("device_config_internal_code", "#{configCode}");
				VALUES("device_target_value", "#{value}");
				VALUES("device_target_remark", "#{remark}");
				VALUES("create_by", "#{userId}");
				VALUES("create_time", "now()");
				VALUES("update_by", "#{userId}");
				VALUES("update_time", "now()");
				VALUES("active_flag", "'y'");
			}
		}.toString();
		return sql;
	}
	
	/**
	 * 
	 * @Title: update
	 * @param:
	 * @Description: 编辑
	 * @return String
	 */
	public String updateTarget(UserDeviceVo userDeviceVo) {
		return new SQL() {
			{
				UPDATE("user_device_target_bt ");
				SET("device_target_value = #{value}");
				SET("update_time = now()");
				WHERE("user_device_base_sb_seq = #{userId} and device_config_internal_code = #{configCode}");
			}
		}.toString();
	}
	
	public String saveFood(WristbandVo wristbandVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_food_intake_bt");
				VALUES("user_device_record_bt_seq", "#{userDeviceId}");
				VALUES("food_intake_eat_itype", "#{eatType}");
				VALUES("food_intake_name", "#{foodName}");
				VALUES("food_intake_brand", "#{foodBrand}");
				VALUES("food_intake_size", "#{foodSize}");
				VALUES("food_intake_cal", "#{calorieIntake}");
				VALUES("food_intake_fat", "#{foodFat}");
				VALUES("create_by", "#{userId}");
				VALUES("create_time", "#{calorieIntakeTime}");
				VALUES("update_by", "#{userId}");
				VALUES("update_time", "#{calorieIntakeTime}");
				VALUES("active_flag", "'y'");
			}
		}.toString();
		return sql;
	}
	
	public String getBandId(Map<String,Object> map){
		String mac = map.get("mac").toString();
		int userId = Integer.parseInt(map.get("userId").toString());
		String sql = new SQL() {
			{
				SELECT("user_device_bind_sr_seq");
				FROM("user_device_bind_sr");
				WHERE("active_flag = 'y'");
				if(userId > 0){
					AND();
					WHERE(" user_device_base_sb_seq = " + userId);
				}
				if(!StringUtil.isBlank(mac)){
					AND();
					WHERE(" device_bind_address = '" + mac + "'");
				}
			}
		}.toString();
		return sql;
	}
}
