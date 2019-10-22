package com.coins.cloud.dao.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.UserSysParaBo;
import com.coins.cloud.dao.UserSysParaDao;

@Repository
public class UserSysParaDaoMyBatisImpl implements UserSysParaDao {

	@Inject
	private UserSysParaMapper userSysParaMapper;

	@Override
	public UserSysParaBo getUserSysParaByCode(String code) {
		return userSysParaMapper.getUserSysParaByCode(code);
	}

	@Override
	public int updateUserSysPara(UserSysParaBo userSysParaBo) {
		return userSysParaMapper.updateUserSysPara(userSysParaBo);
	}

}
