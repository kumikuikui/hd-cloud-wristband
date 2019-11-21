package com.coins.cloud.service.impl;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coins.cloud.EmailHtml;
import com.coins.cloud.bo.SmsRecord;
import com.coins.cloud.dao.SmsDao;
import com.coins.cloud.service.SmsService;
import com.hlb.cloud.util.RandomUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

	@Inject
	private SmsDao smsRecordDao;

	@Autowired
	private EmailHtml emailHtml;

	@Override
	public int sendCaptchaEmail(String email, int codeType) {
		log.info("email={},codeType={}", email, codeType);
		String code = RandomUtil.createRandCode(4);
		SmsRecord sms = new SmsRecord();
		sms.setCodeType(codeType);
		sms.setAccount(email);
		sms.setVerifyCode(code);
		smsRecordDao.save(sms);
		String content = "Your verification code is:" + code;
		try {
			emailHtml.sendHtmlEmail(email, "vivacity verification code", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(String.valueOf(sms.getSmsId()));
	}

	@Override
	public SmsRecord find(String phoneNo, int codeType) {
		SmsRecord sms = smsRecordDao.find(phoneNo, codeType);
		return sms;
	}

}
