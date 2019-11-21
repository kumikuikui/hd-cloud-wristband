package com.coins.cloud.dao;

import com.coins.cloud.bo.SmsRecord;

public interface SmsDao {

	/**
	 * 
	 * @Title: isUniversalCode
	 * @param:
	 * @Description: 万能验证码
	 * @return boolean
	 */
	int save(SmsRecord smsRecord);

	/**
	 * 
	 * @param smsRecord
	 * @return
	 */
	int update(SmsRecord smsRecord);

	/**
	 * 根据账号查询验证码
	 * 
	 * @param account
	 * @return
	 */
	SmsRecord find(String account, int codeType);

	/**
	 * 
	 * @param account
	 * @return
	 */
	SmsRecord find(String account);

	/**
	 * 
	 * @param smsRecord
	 * @return
	 */
	int getCountByCodeTypeAndRecordAccount(SmsRecord smsRecord);

}
