package com.coins.cloud.dao.impl;

import java.util.Date;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.SmsRecord;
import com.coins.cloud.dao.sql.SmsSqlProvider;

@Mapper
public interface SmsMapper {

	@Select("SELECT sms_verify_record_br_seq , verify_rocord_account, verify_record_verify_code, verify_record_code_itype, verify_record_verify_itype, create_Time "
			+ " FROM sms_verify_record_br where verify_rocord_account = #{account} and verify_record_code_itype = #{codeType} and verify_record_verify_itype = 0 and active_flag='y'  "
			+ " order by sms_verify_record_br_seq desc limit 0,1")
	@Results(value = {
			@Result(property = "smsId", column = "sms_verify_record_br_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "account", column = "verify_rocord_account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "verifyCode", column = "verify_record_verify_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "codeType", column = "verify_record_code_itype", javaType = int.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "verifyStatus", column = "verify_record_verify_itype", javaType = int.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_Time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP), })
	SmsRecord find(@Param("account") String account, @Param("codeType") int codeType);

	@Select("SELECT sms_verify_record_br_seq , verify_rocord_account, verify_record_verify_code, verify_record_code_itype, verify_record_verify_itype, create_Time "
			+ " FROM sms_verify_record_br where verify_rocord_account = #{account} and verify_record_verify_itype = 0 and active_flag='y' order by sms_verify_record_br_seq desc limit 0,1")
	@Results(value = {
			@Result(property = "smsId", column = "sms_verify_record_br_seq", javaType = long.class, jdbcType = JdbcType.BIGINT),
			@Result(property = "account", column = "verify_rocord_account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "verifyCode", column = "verify_record_verify_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "codeType", column = "verify_record_code_itype", javaType = int.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "verifyStatus", column = "verify_record_verify_itype", javaType = int.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createTime", column = "create_Time", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP), })
	SmsRecord findInValidSms(@Param("account") String account);

	@InsertProvider(type = SmsSqlProvider.class, method = "createSms")
	@SelectKey(keyProperty = "smsId", before = false, resultType = int.class, statement = {
			"SELECT LAST_INSERT_ID() AS sms_verify_record_br " })
	int save(SmsRecord smsRecord);

	/**
	 * @Title: update
	 * @Description: 更改短信发送记录
	 * @Table: sms_verify_record_br 短信发送记录表
	 */
	@UpdateProvider(type = SmsSqlProvider.class, method = "update")
	int update(SmsRecord smsRecord);

	/**
	 * @Title: getCountByCodeTypeAndRecordAccount
	 * @Description: 通过类型和 电话号码 查询 发送次数
	 * @Table: sms_verify_record_br 短信发送记录表
	 */
	@SelectProvider(type = SmsSqlProvider.class, method = "getCountByCodeTypeAndRecordAccount")
	int getCountByCodeTypeAndRecordAccount(SmsRecord sms);

}
