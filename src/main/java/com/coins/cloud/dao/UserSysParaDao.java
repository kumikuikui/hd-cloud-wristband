package com.coins.cloud.dao;

import com.coins.cloud.bo.UserSysParaBo;

public interface UserSysParaDao {

	public UserSysParaBo getUserSysParaByCode(String code);

	public int updateUserSysPara(UserSysParaBo userSysParaBo);
}
