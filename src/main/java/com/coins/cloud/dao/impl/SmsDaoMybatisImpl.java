package com.coins.cloud.dao.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.SmsRecord;
import com.coins.cloud.dao.SmsDao;

@Repository
public class SmsDaoMybatisImpl implements SmsDao {

	@Inject
	private SmsMapper smsMapper;

	@Override
	public int save(SmsRecord smsRecord) {
		smsRecord.setActiveFlag("y");
		return smsMapper.save(smsRecord);
	}

	@Override
	public SmsRecord find(String account, int codeType) {
		return smsMapper.find(account, codeType);
	}

	@Override
	public int update(SmsRecord smsRecord) {
		return smsMapper.update(smsRecord);
	}

	@Override
	public int getCountByCodeTypeAndRecordAccount(SmsRecord smsRecord) {
		try {
			return smsMapper.getCountByCodeTypeAndRecordAccount(smsRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public SmsRecord find(String account) {
		return smsMapper.findInValidSms(account);
	}

}
