package com.coins.cloud.dao.impl;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.coins.cloud.bo.UserSysParaBo;

@Mapper
public interface UserSysParaMapper {

	@Select(" select sys_para_internal_code as code,sys_para_name as name,sys_para_value as paraValue from user_sys_para_sb where active_flag='y' AND sys_para_internal_code=#{code} ")
	public UserSysParaBo getUserSysParaByCode(String code);

	@Update(" update user_sys_para_sb set sys_para_value =#{paraValue} where sys_para_internal_code=#{code} ")
	public int updateUserSysPara(UserSysParaBo userSysParaBo);
}
