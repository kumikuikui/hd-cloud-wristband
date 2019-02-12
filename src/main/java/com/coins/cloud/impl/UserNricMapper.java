package com.coins.cloud.impl;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.sql.UserNricProvider;
import com.coins.cloud.vo.UserNricVo;


@Mapper
public interface UserNricMapper {

	@InsertProvider(type = UserNricProvider.class,method = "save")
	int save(UserNricVo userNricVo);
	
	@Select("SELECT * FROM user_user_base_sb WHERE active_flag = 'y' ORDER BY create_time DESC LIMIT #{pageIndex},#{pageSize}")
	@Results(value = {
			@Result(property = "userId",column = "user_user_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "nric",column = "user_base_nric", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "name",column = "user_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "oldIc",column = "user_base_old_ic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "birthDate",column = "user_base_birth_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "birthPlace",column = "user_base_birth_place", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "gender",column = "user_base_gender", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address1",column = "user_base_address1", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address2",column = "user_base_address2", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address3",column = "user_base_address3", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postcode",column = "user_base_postcode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "city",column = "user_base_city", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "state",column = "user_base_state", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "race",column = "user_base_race", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "religion",column = "user_base_religion", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "citizenship",column = "user_base_citizenship", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "issueDate",column = "user_base_issue_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "emorigin",column = "user_base_emorigin", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "handcode",column = "user_base_handcode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "mimageUrl",column = "user_base_mimage_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "cameraUrl",column = "user_base_camera_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "indicatorStatus",column = "user_base_indicator_status", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	public List<UserNricBo> getUserNricList(@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize);

	@Select("SELECT COUNT(1) FROM user_user_base_sb WHERE active_flag = 'y'")
	public int getTotal();
	
	@Select("SELECT * FROM user_user_base_sb WHERE active_flag = 'y' AND user_user_base_sb_seq = #{id}")
	@Results(value = {
			@Result(property = "userId",column = "user_user_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "nric",column = "user_base_nric", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "name",column = "user_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "oldIc",column = "user_base_old_ic", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "birthDate",column = "user_base_birth_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "birthPlace",column = "user_base_birth_place", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "gender",column = "user_base_gender", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address1",column = "user_base_address1", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address2",column = "user_base_address2", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "address3",column = "user_base_address3", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "postcode",column = "user_base_postcode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "city",column = "user_base_city", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "state",column = "user_base_state", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "race",column = "user_base_race", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "religion",column = "user_base_religion", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "citizenship",column = "user_base_citizenship", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "issueDate",column = "user_base_issue_date", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "emorigin",column = "user_base_emorigin", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "handcode",column = "user_base_handcode", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "mimageUrl",column = "user_base_mimage_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "cameraUrl",column = "user_base_camera_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "indicatorStatus",column = "user_base_indicator_status", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	public UserNricBo getUserNricDetail(@Param("id") int id);
	
	@UpdateProvider(type = UserNricProvider.class,method = "update")
	int updateUserNric(UserNricVo userNricVo);
}
