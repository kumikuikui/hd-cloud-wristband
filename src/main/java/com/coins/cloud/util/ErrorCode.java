package com.coins.cloud.util;

/**
 * 
  * @ClassName: ErrorCode
  * @Description: 错误码
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年4月11日 上午10:00:23
  *
 */
public class ErrorCode {

	/**
	 * 成功
	 */
	public static final String SUCCESS = "1000000";

	/**
	 * 失败
	 */
	public final static String FAIL = "1000001";
	
	/**
	 * 账号已注册
	 */
	public final static String ACCOUNT_REGISTERED = "1000002";
	
	/**
	 * 账号密码错误
	 */
	public final static String ACCOUNT_PASSWORD_IS_WRONG = "1000003";
	
	/**
	 * 用户已绑定手环
	 */
	public final static String USER_HAS_BOUND_WRISTBAND = "1000004";
	
	
	/**
	 * 手环已被其他用户绑定
	 */
	public final static String WRISTBAND_HAD_BOUND = "1000005";
	
	/**
	 * 已绑定手环设备
	 */
	public final static String WRISTBAND_RING_DEVICE = "1000006";
	
	/**
	 * 保险认证已提交，暂不能修改
	 */
	public final static String INSURANCE_SUBMITTED = "1000007";
	
	/**
	 * 心率添加已达上限
	 */
	public final static String HEART_ADDITION_UPPER_LIMIT = "1000008";
	
	/**
	 * 账号不能为空
	 */
	public final static String ACCOUNT_IS_EMPTY = "1000009";
	
	/**
	 * 邮箱不合法
	 */
	public final static String EMAIL_IS_ERROR = "1000010";
	
	/**
	 * 饮水量不合规
	 */
	public final static String DRINKWATER_IS_ERROR = "1000011";
	
	/**
	 * 心率不合规
	 */
	public final static String HEART_IS_ERROR = "1000012";
	
	/**
	 * 已是好友，不能重复添加
	 */
	public final static String FRIEND_IS_EXIS = "1000013";
	
	/**
	 * 已请求添加好友
	 */
	public final static String ADD_RECORD_IS_EXIS = "1000014";
	
	/**
	 * 已点赞，不能重复
	 */
	public final static String IT_HAD_STAR = "1000015";
	
	/**
	 * 还没点赞，不能取消
	 */
	public final static String IT_HAD_NOT_STAR = "1000016";
	
	/**
	 * 保单号不存在
	 */
	public final static String INSURANCENO_IS_NOT_EXIST = "1000017";
	
	/**
	 * 保单号已被其他用户关联
	 */
	public final static String INSURANCENO_IS_USED = "1000018";

}
