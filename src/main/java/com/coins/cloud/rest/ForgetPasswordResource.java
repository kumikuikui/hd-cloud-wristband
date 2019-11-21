package com.coins.cloud.rest;

import javax.inject.Inject;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.bo.SmsRecord;
import com.coins.cloud.service.SmsService;
import com.coins.cloud.service.WristbandService;
import com.coins.cloud.vo.FindUserPwdVo;
import com.coins.cloud.vo.UserBaseVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/forget")
public class ForgetPasswordResource {

	@Inject
	private WristbandService wristbandService;

	@Inject
	private SmsService smsService;

	/**
	 * 
	 * @Title: findpwd
	 * @param:
	 * @Description: 找回密码
	 * @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "password", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil findpwd(final @RequestBody FindUserPwdVo payload) throws Exception {
		log.info("##########payload:{}", payload);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		if (StringUtil.isBlank(payload.getEmail())) {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("Email is empty");
			return boUtil;
		}
		if (StringUtil.isBlank(payload.getCaptcha())) {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("captcha is empty");
			return boUtil;
		}

		if (StringUtil.isBlank(payload.getPassword())) {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("password is empty");
			return boUtil;
		}
		int userId = wristbandService.existAccount(payload.getEmail());
		if (userId <= 0) {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("Account does not exist");
			return boUtil;
		}
		SmsRecord sms = smsService.find(payload.getEmail(), 4);
		if (sms == null || !sms.getVerifyCode().equals(payload.getCaptcha())) {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("Verification code error");
			return boUtil;
		}
		UserBaseVo userBaseVo = UserBaseVo.builder().userId(userId).password(payload.getPassword()).build();
		int result = wristbandService.modifyInfo(userBaseVo);
		if (result > 0) {
			boUtil.setMsg("Password reset complete");
		} else {
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setMsg("Password modification failed");
		}
		return boUtil;
	}

}
