package com.coins.cloud;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailHtml {

	@Autowired
	private JavaMailSender Sender;

	public void sendHtmlEmail(String To, String Subject, String content) throws Exception {

		MimeMessage simpleMessage = Sender.createMimeMessage();
		MimeMessageHelper MessageHelper = new MimeMessageHelper(simpleMessage, true);

		// 谁去发送邮件
		MessageHelper.setFrom("vivacity@archipelagogrp.com");
		// 发送给谁
		MessageHelper.setTo(To);
		// 主题
		MessageHelper.setSubject(Subject);
		// 发送的文本信息
		MessageHelper.setText(content, true);
		// 开始发送
		Sender.send(simpleMessage);
	}

}
