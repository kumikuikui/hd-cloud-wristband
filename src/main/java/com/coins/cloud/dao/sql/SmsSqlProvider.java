package com.coins.cloud.dao.sql;

import org.apache.ibatis.jdbc.SQL;

import com.coins.cloud.bo.SmsRecord;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
public class SmsSqlProvider {

	/**
	 * 
	 * @Title: createUserProfile
	 * @param: Sms
	 *             model
	 * @Description: 新增短信记录
	 * @return String
	 */
	public String createSms(final SmsRecord smsRecord) {
		String sql = new SQL() {
			{
				INSERT_INTO("sms_verify_record_br");
				VALUES("verify_rocord_account", "#{account}");
				VALUES("verify_record_verify_code", "#{verifyCode}");
				VALUES("verify_record_code_itype", "#{codeType}");
				VALUES("verify_record_verify_itype", "#{verifyStatus}");
				VALUES("active_flag", "#{activeFlag}");
			}
		}.toString();

		log.info("SmsRecord={},sql={}", smsRecord, sql);
		return sql;
	}

	/**
	 * 
	 * @param sms
	 * @return
	 */
	public String update(final SmsRecord smsRecord) {
		return new SQL() {
			{
				UPDATE("sms_verify_record_br");
				if (smsRecord.getActiveFlag() != null) {
					SET("active_flag = #{activeFlag}");
				}
				if (smsRecord.getVerifyStatus() != 0) {
					SET("verify_record_verify_itype = #{verifyStatus}");
				}
				WHERE("sms_verify_record_br_seq = #{smsId}");
			}
		}.toString();
	}

	/**
	 * 
	 * @Title: getCountByCodeTypeAndRecordAccount
	 * @param: Sms
	 *             model
	 * @Description: 时间查询
	 * @return String
	 */
	public String getCountByCodeTypeAndRecordAccount(final SmsRecord sms) {
		// 一分钟以内
		String sql = "";
		if (sms.getCheckType() == 1) {
			sql = new SQL() {
				{
					SELECT("count(1)");
					FROM("sms_verify_record_br");
					WHERE(" active_flag='y' AND verify_record_code_itype=#{codeType} AND verify_rocord_account = #{account} AND create_Time >= DATE_SUB(NOW(), INTERVAL 1 MINUTE) AND create_time <= NOW() ");
				}
			}.toString();
		} else {
			sql = new SQL() {
				{
					SELECT("count(1)");
					FROM("sms_verify_record_br");
					WHERE(" active_flag='y' AND verify_record_code_itype=#{codeType} AND verify_rocord_account= #{account} AND  to_days(create_time) = to_days(NOW())");
				}
			}.toString();
		}
		log.info("getCountByCodeTypeAndRecordAccount sql={}", sql);
		return sql;
	}
}
