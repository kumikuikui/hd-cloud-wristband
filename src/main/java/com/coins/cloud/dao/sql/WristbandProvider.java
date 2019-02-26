package com.coins.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;

public class WristbandProvider {

	public String save(UserDeviceVo userDeviceVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_device_record_bt");
				VALUES("user_device_base_sb_seq", "#{userId}");
				VALUES("user_device_bind_sr_seq", "#{bindId}");
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
				SET("update_time = now()");
				WHERE("user_device_base_sb_seq = #{userId}");
			}
		}.toString();
	}
}
