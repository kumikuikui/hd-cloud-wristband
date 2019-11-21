package com.coins.cloud.service;

import com.coins.cloud.bo.SmsRecord;

public interface SmsService {

	SmsRecord find(String phoneNo, int codeType);

	int sendCaptchaEmail(String email, int codeType);
}
