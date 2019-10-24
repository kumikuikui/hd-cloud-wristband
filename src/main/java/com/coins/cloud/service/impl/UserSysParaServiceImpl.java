package com.coins.cloud.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.coins.cloud.bo.UserSysParaBo;
import com.coins.cloud.dao.UserSysParaDao;
import com.coins.cloud.service.UserSysParaService;
import com.coins.cloud.util.ConstantUtil;

@Service
public class UserSysParaServiceImpl implements UserSysParaService {

	@Inject
	private UserSysParaDao userSysParaDao;

	@Override
	public Map<String, Object> getUserSysPara() {
		Map<String, Object> map = new HashMap<>();
		map.put("tpaName", "");
		map.put("hotline", "");
		map.put("phone", "");
		map.put("email", "");
		map.put("domain", "");
		UserSysParaBo sysParaBo = userSysParaDao.getUserSysParaByCode(ConstantUtil.TPA_NAME);
		if (sysParaBo != null) {
			map.put("tpaName", sysParaBo.getParaValue());
		}
		UserSysParaBo userSysParaBo = userSysParaDao.getUserSysParaByCode(ConstantUtil.HOTLINE_NUMBER);
		if (userSysParaBo != null) {
			map.put("hotline", userSysParaBo.getParaValue());
		}
		UserSysParaBo phoneBo = userSysParaDao.getUserSysParaByCode(ConstantUtil.PHONE);
		if (phoneBo != null) {
			map.put("phone", phoneBo.getParaValue());
		}
		UserSysParaBo  emailBo= userSysParaDao.getUserSysParaByCode(ConstantUtil.EMAIL);
		if (emailBo != null) {
			map.put("email", emailBo.getParaValue());
		}
		UserSysParaBo domainSysBo = userSysParaDao.getUserSysParaByCode(ConstantUtil.DOMAIN);
		if (domainSysBo != null) {
			map.put("domain", domainSysBo.getParaValue());
		}
		return map;
	}

}
