package com.coins.cloud.rest;

import java.util.regex.Matcher;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.service.SmsService;
import com.coins.cloud.vo.SmsVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.RegexUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: EmailResource
 * @Description: 发送邮箱验证码
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company initbay
 * @date 2019年10月18日 下午2:42:27
 *
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("email")
public class EmailResource {

	@Inject
	private SmsService smsService;
	 

	/**
	 * 
	 * @Title: sendEmail
	 * @param:
	 * @Description: 发送验证码
	 * @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "send", notes = "send")
	@ResponseBody
	@RequestMapping(value = "send", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil sendEmail(final @RequestBody SmsVo smsVo) {
		BoUtil bo = BoUtil.getDefaultFalseBo();
		String email = smsVo.getEmail();
		log.info("email={}", email);

		if (StringUtils.isBlank(email)) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("请输入邮箱");
			return bo;
		}
		if (smsVo.getType() <= 0) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("发送邮件类型错误");
			return bo;
		}
		if(email.length()>100) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("邮箱长度100位以内");
			return bo;
		}
		// 邮箱格式验证
		Matcher matcher = RegexUtil.pattern_email.matcher(email);
		if (!matcher.find()) {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("邮箱格式错误");
			return bo;
		}
 
		int captchaId = smsService.sendCaptchaEmail(email, smsVo.getType());
		log.info("captchaId={}", captchaId);
		if (captchaId > 0) {
			bo = BoUtil.getDefaultTrueBo();
			return bo;
		} else {
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("发送邮箱失败!");
			return bo;
		}
	}

}
