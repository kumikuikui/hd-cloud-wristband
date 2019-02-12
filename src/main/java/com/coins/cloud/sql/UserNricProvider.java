package com.coins.cloud.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.coins.cloud.vo.UserNricVo;

public class UserNricProvider {

	public String save(UserNricVo userNricVo) {
		String sql = new SQL() {
			{
				INSERT_INTO("user_user_base_sb");
				VALUES("user_base_nric", "#{nric}");
				VALUES("user_base_name", "#{name}");
				VALUES("user_base_old_ic", "#{oldIc}");
				VALUES("user_base_birth_date", "#{birthDate}");
				VALUES("user_base_birth_place", "#{birthPlace}");
				VALUES("user_base_gender", "#{gender}");
				VALUES("user_base_address1", "#{address1}");
				VALUES("user_base_address2", "#{address2}");
				VALUES("user_base_address3", "#{address3}");
				VALUES("user_base_postcode", "#{postcode}");
				VALUES("user_base_city", "#{city}");
				VALUES("user_base_state", "#{state}");
				VALUES("user_base_race", "#{race}");
				VALUES("user_base_religion", "#{religion}");
				VALUES("user_base_citizenship", "#{citizenship}");
				VALUES("user_base_issue_date", "#{issueDate}");
				VALUES("user_base_emorigin", "#{emorigin}");
				VALUES("user_base_handcode", "#{handcode}");
				VALUES("user_base_mimage_url", "#{mimageUrl}");
				VALUES("user_base_camera_url", "#{cameraUrl}");
				VALUES("user_base_indicator_status", "#{indicatorStatus}");
				VALUES("create_by", "10000");
				VALUES("create_time", "now()");
				VALUES("update_by", "10000");
				VALUES("update_time", "now()");
				VALUES("active_flag", "'y'");
			}
		}.toString();
		return sql;
	}
	
	public String update(UserNricVo userNricVo) {
		String sql = new SQL() {
			{
				UPDATE("user_user_base_sb");
				SET("user_base_nric = #{nric}");
				SET("user_base_name = #{name}");
				SET("user_base_old_ic = #{oldIc}");
				SET("user_base_birth_place = #{birthPlace}");
				SET("user_base_gender = #{gender}");
				SET("user_base_address1 = #{address1}");
				SET("user_base_address2 = #{address2}");
				SET("user_base_address3 = #{address3}");
				SET("user_base_postcode = #{postcode}");
				SET("user_base_city = #{city}");
				SET("user_base_state = #{state}");
				SET("user_base_race = #{race}");
				SET("user_base_religion = #{religion}");
				SET("user_base_citizenship = #{citizenship}");
				SET("user_base_emorigin = #{emorigin}");
				SET("user_base_handcode = #{handcode}");
				SET("user_base_indicator_status = #{indicatorStatus}");
				SET("update_time = now()");
				WHERE("user_user_base_sb_seq = #{userId}");
			}
		}.toString();
		return sql;
	}

}
