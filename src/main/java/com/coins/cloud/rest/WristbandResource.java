package com.coins.cloud.rest;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.WristbandServiceApplication.RSAConfig;
import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.bo.WristbandBo;
import com.coins.cloud.service.WristbandService;
import com.coins.cloud.util.AESUtil;
import com.coins.cloud.util.DeviceConfig;
import com.coins.cloud.util.ErrorCode;
import com.coins.cloud.util.FileUtil;
import com.coins.cloud.util.HttpClientUtil;
import com.coins.cloud.vo.RequestVo;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
import com.coins.cloud.vo.WristbandTargetVo;
import com.coins.cloud.vo.WristbandVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.StringUtil;

@RefreshScope
@RestController
@RequestMapping("/wristband")
@Slf4j
public class WristbandResource {
	
	private final String domain = "http://54.169.80.178:3100/";
	
	@Inject
	private RSAConfig rSAConfig;
	
	@Inject
	private WristbandService wristbandService;

	/**
	 * 
	* @Title: addBank 
	* @param: 
	* @Description: 手环数据上链
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "upload", notes = "upload")
	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil uploadWritband(final @RequestBody WristbandVo wristbandVo){
		String url = domain + "bcsgw/rest/v1/transaction/invocation";
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		//结果 1成功 0失败  数据格式例如 1:1:1:0:1  步数|心率|卡路里|血氧|血压
		String result = "";
		int i = 0;
		//步数上链
		if(!StringUtil.isBlank(wristbandVo.getStep())){
			String entity = getEntity("step", wristbandVo.getMac(),
					wristbandVo.getStep(), wristbandVo.getStepTime());
			String responeJson = HttpClientUtil.executeByPost(url, entity);
			log.info(" responeJson : {} ",responeJson);
			JSONObject jsonObject = JSONObject.fromObject(responeJson);
			boolean flag = jsonObject.containsKey("returnCode");
			if(flag){
				if("Success".equals(jsonObject.get("returnCode").toString())){//上链成功
					i = 1;
				}
			}
			result += i;
			i = 0;
		}else{
			result += 0;
		}
		// 心率上链
		if (!StringUtil.isBlank(wristbandVo.getHeart())) {
			String entity = getEntity("heart", wristbandVo.getMac(),
					wristbandVo.getHeart(), wristbandVo.getHeartTime());
			String responeJson = HttpClientUtil.executeByPost(url, entity);
			log.info(" responeJson : {} ",responeJson);
			JSONObject jsonObject = JSONObject.fromObject(responeJson);
			boolean flag = jsonObject.containsKey("returnCode");
			if(flag){
				if("Success".equals(jsonObject.get("returnCode").toString())){//上链成功
					i = 1;
				}
			}
			result += "|" + i;
			i = 0;
		}else{
			result += "|0";
		}
		
		// 卡路里上链
		if (!StringUtil.isBlank(wristbandVo.getCalorie())) {
			String entity = getEntity("heart", wristbandVo.getMac(),
					wristbandVo.getCalorie(), wristbandVo.getCalorieTime());
			String responeJson = HttpClientUtil.executeByPost(url, entity);
			log.info(" responeJson : {} ",responeJson);
			JSONObject jsonObject = JSONObject.fromObject(responeJson);
			boolean flag = jsonObject.containsKey("returnCode");
			if (flag) {
				if ("Success".equals(jsonObject.get("returnCode").toString())) {//上链成功
					i = 1;
				}
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 血氧上链
		if (!StringUtil.isBlank(wristbandVo.getBlood())) {
			String entity = getEntity("blood", wristbandVo.getMac(),
					wristbandVo.getBlood(), wristbandVo.getBloodTime());
			String responeJson = HttpClientUtil.executeByPost(url, entity);
			log.info(" responeJson : {} ",responeJson);
			JSONObject jsonObject = JSONObject.fromObject(responeJson);
			boolean flag = jsonObject.containsKey("returnCode");
			if (flag) {
				if ("Success".equals(jsonObject.get("returnCode").toString())) {//上链成功
					i = 1;
				}
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 血压上链
		if (!StringUtil.isBlank(wristbandVo.getDiastolic()) && !StringUtil.isBlank(wristbandVo.getSystolic())) {
			String entity = getEntity("pressure", wristbandVo.getMac(),
					wristbandVo.getDiastolic(),wristbandVo.getSystolic(), wristbandVo.getPressureTime());
			String responeJson = HttpClientUtil.executeByPost(url, entity);
			log.info(" responeJson : {} ",responeJson);
			JSONObject jsonObject = JSONObject.fromObject(responeJson);
			boolean flag = jsonObject.containsKey("returnCode");
			if (flag) {
				if ("Success".equals(jsonObject.get("returnCode").toString())) {// 上链成功
					i = 1;
				}
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		boUtil.setData(result);
		return boUtil;
	}
	
	
	private String getEntity(String tname,String mac,String value,String time){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("\"" + mac + "\",");
		sb.append("\"" + tname + "\",");
		sb.append("\"" + FileUtil.getIncrNum() + "\",");
		sb.append("\"" + value + "\",");
		sb.append("\"" + time + "\",");
		sb.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "samchannel");
		jsonObject.put("chaincode", "visitTrace1");
		jsonObject.put("method", "initwristbank");
		jsonObject.put("args", sb.toString());
		jsonObject.put("chaincodeVer", "v1");
		return jsonObject.toString();
	}
	
	private String getEntity(String tname,String mac,String param1,String param2,String time){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("\"" + mac + "\",");
		sb.append("\"" + tname + "\",");
		sb.append("\"" + FileUtil.getIncrNum() + "\",");
		sb.append("\"" + param1 + "\",");
		sb.append("\"" + param2 + "\",");
		sb.append("\"" + time + "\",");
		sb.append("]");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("channel", "samchannel");
		jsonObject.put("chaincode", "visitTrace1");
		jsonObject.put("method", "initwristbank");
		jsonObject.put("args", sb.toString());
		jsonObject.put("chaincodeVer", "v1");
		return jsonObject.toString();
	}
	
	/**
	 * 
	* @Title: uploadDevice 
	* @param: 
	* @Description: 保存手环信息
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "save", notes = "save")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil uploadDevice(final @RequestBody WristbandVo wristbandVo){
		log.info(" wristbandVo : {} ",wristbandVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = wristbandVo.getUserId();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		//查询绑定设备id
		/*int bindId = wristbandService.getBandId(userId, mac);
		if(bindId == 0){//绑定设备
			int result = wristbandService.userBindDevice(userId, mac);
			if(result > 0){
				//查询绑定设备id
				bindId = wristbandService.getBandId(userId, mac);
			}else{
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setMsg("未查询到绑定的设备");
				return boUtil;
			}
		}
		log.info(" bindId : {} ",bindId);*/
		//结果 1成功 0失败  数据格式例如 1:1:1:0:1:1:1:1  步数|心率|卡路里消耗|血氧|血压|重量|睡眠|饮水|卡路里摄入
		String result = "";
		int i = 0;
		//步数上传
		if(!StringUtil.isBlank(wristbandVo.getStep())){
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con001).value(wristbandVo.getStep())
					.time(wristbandVo.getStepTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if(resu > 0){
				i = 1;
			}
			result += i;
			i = 0;
		}else{
			result += 0;
		}
		// 心率上传
		if (!StringUtil.isBlank(wristbandVo.getHeart())) {
			//查询今日心率添加次数
			int count = wristbandService.getHeartCountByToday(userId, DeviceConfig.con009,wristbandVo.getHeartTime());
			if(count >= 5){
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.HEART_ADDITION_UPPER_LIMIT);
				boUtil.setMsg("Heart rate has reached the upper limit");
				return boUtil;
			}
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con009).value(wristbandVo.getHeart())
					.time(wristbandVo.getHeartTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if(resu > 0){
				i = 1;
			}
			result += "|" + i;
			i = 0;
		}else{
			result += "|0";
		}
		
		// 消耗卡路里上传
		if (!StringUtil.isBlank(wristbandVo.getCalorie())) {
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con002).value(wristbandVo.getCalorie())
					.time(wristbandVo.getCalorieTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if(resu > 0){
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 血氧上传
		if (!StringUtil.isBlank(wristbandVo.getBlood())) {
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con010).value(wristbandVo.getBlood())
					.time(wristbandVo.getBloodTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if(resu > 0){
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 血压上传
		if (!StringUtil.isBlank(wristbandVo.getDiastolic()) && !StringUtil.isBlank(wristbandVo.getSystolic())) {
			String value = wristbandVo.getDiastolic() + "|" + wristbandVo.getSystolic();
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con003).value(value)
					.time(wristbandVo.getPressureTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if(resu > 0){
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 重量上传
		if (!StringUtil.isBlank(wristbandVo.getWeight())
				&& !StringUtil.isBlank(wristbandVo.getFat())) {
			String value = wristbandVo.getWeight() + "|"
					+ wristbandVo.getFat();
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con004)
					.value(value).time(wristbandVo.getWeightTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if (resu > 0) {
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 睡眠上传
		if (!StringUtil.isBlank(wristbandVo.getSleepStartTime())
				&& !StringUtil.isBlank(wristbandVo.getSleepEndTime())) {
			String value = wristbandVo.getSleepStartTime() + "|"
					+ wristbandVo.getSleepEndTime();
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con005)
					.value(value).time(wristbandVo.getSleepEndTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if (resu > 0) {
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 饮水量上传
		if (!StringUtil.isBlank(wristbandVo.getDrinkWater())) {
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.configCode(DeviceConfig.con007)
					.value(wristbandVo.getDrinkWater()).time(wristbandVo.getDrinkTime()).build();
			int resu = wristbandService.save(userDeviceVo);
			if (resu > 0) {
				i = 1;
			}
			result += "|" + i;
			i = 0;
		} else {
			result += "|0";
		}
		// 卡路里摄入量上传
		if (!StringUtil.isBlank(wristbandVo.getCalorieIntake())) {
			//今日是否有摄入卡路里
			int resu = 0;
			int userDeviceId = 0;
			List<UserDeviceBo> calIntakeList = wristbandService.getTodayInfo(userId, DeviceConfig.con006,wristbandVo.getCalorieIntakeTime());
			log.info(" calIntakeList : {} ",calIntakeList);
			if(calIntakeList == null || calIntakeList.isEmpty()){
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con006)
						.value(wristbandVo.getCalorieIntake())
						.time(wristbandVo.getCalorieIntakeTime()).build();
				resu = wristbandService.save(userDeviceVo);
				userDeviceId = userDeviceVo.getUserDeviceId();
				log.info(" userDeviceId : {} ",userDeviceId);
			}else{
				UserDeviceBo userDeviceBo = calIntakeList.get(0);
				userDeviceId = userDeviceBo.getUserDeviceId();
				//更新卡路里摄入量
				double calIntakeTotal = Double.parseDouble(userDeviceBo
						.getValue())
						+ Double.parseDouble(wristbandVo.getCalorieIntake());
				resu = wristbandService.updateCalIntake(userDeviceBo.getUserDeviceId(), String.valueOf(calIntakeTotal));
			}
			if (resu > 0) {
				i = 1;
			}
			result += "|" + i;
			i = 0;
			//保存食物
			wristbandVo.setUserDeviceId(userDeviceId);
			wristbandService.saveFood(wristbandVo);
		} else {
			result += "|0";
		}
		boUtil.setData(result);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: index 
	* @param: 
	* @Description: 首页
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "index", notes = "index")
	@ResponseBody
	@RequestMapping(value = "index", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil index(@QueryParam("userId") Integer userId,@QueryParam("mac") String mac){
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		WristbandBo wristbandBo = wristbandService.getUserDevice(userId, mac);
		boUtil.setData(wristbandBo);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: getList 
	* @param: 
	* @Description: 获取列表
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "list", notes = "list")
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getList(@QueryParam("userId") Integer userId,@QueryParam("mac") String mac,@QueryParam("code") String code,
			@QueryParam("beginTime") String beginTime,@QueryParam("endTime") String endTime) throws ParseException {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		userId = userId == null ? 0 : userId;
//		pageIndex = pageIndex == null ? 1 : pageIndex;
//		if (pageIndex <= 0) {
//			pageIndex = 1;
//		}
//	    Calendar c=Calendar.getInstance();
//	    c.setTime(new Date());
//	    int weekday=c.get(Calendar.DAY_OF_WEEK);
//	    pageSize = pageSize == null ? 7 : pageSize;
//	    int offset = 0;
//	    if(pageIndex == 1){
//	    	offset = (pageIndex - 1) * pageSize;
//	    	if(weekday == 1){//当天是周日
//		    	pageSize = 14;//查询这周和上周的数据
//		    }else{
//		    	pageSize = 7 + weekday - 1;
//		    }
//	    }else{
//	    	pageSize = 7;
//	    	if(weekday == 1){//当天是周日
//	    		offset = (pageIndex - 1) * pageSize + 7;
//		    }else{
//		    	offset = (pageIndex - 1) * pageSize + weekday - 1;
//		    }
//	    }
		beginTime = beginTime + " 00:00:00";
		endTime = endTime + " 23:59:59";
		log.info("userId: {}, beginTime: {}, endTime: {},mac:{},code:{}, ",userId, beginTime, endTime,mac,code);
		List<UserDeviceBo> list = wristbandService.getRecordByCode(userId, mac, code, beginTime, endTime);
		boUtil.setData(list);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: info 
	* @param: 
	* @Description: 获取个人信息
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "info", notes = "info")
	@ResponseBody
	@RequestMapping(value = "info", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil info(@QueryParam("userId") Integer userId){
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		UserBaseBo userBaseBo = wristbandService.getUserById(userId);
		boUtil.setData(userBaseBo);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: modifyInfo 
	* @param: 
	* @Description: 编辑个人资料 
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "PUT", value = "modify", notes = "modify")
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public BoUtil modifyInfo(final @RequestBody UserBaseVo userBaseVo) throws Exception {
		log.info(" userBaseVo : {} ",userBaseVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		//保险号不为空，更新保险认证
		if(!StringUtil.isBlank(userBaseVo.getInsuranceNo())){
			//查询认证状态
			int authType = wristbandService.getUserById(userBaseVo.getUserId()).getAuthType();
			if(authType == 1 || authType == 2){//保险状态，0未认证，1认证中，2认证成功，3认证失败
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.INSURANCE_SUBMITTED);
				boUtil.setMsg("Insurance has been submitted and cannot be modified");
				return boUtil;
			}
			userBaseVo.setAuthType(1);
		}
		if(!StringUtil.isBlank(userBaseVo.getWeight())){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date);
			String value = userBaseVo.getWeight() + "|0";//重量 + 体脂(默认为0)
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userBaseVo.getUserId())
					.configCode(DeviceConfig.con004)
					.value(value).time(time).build();
			int resu = wristbandService.save(userDeviceVo);
			log.info(" 上传体重结果resu : {} ",resu);
		}
		int result = wristbandService.modifyInfo(userBaseVo);
		
		// 异步初始化目标默认值
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		threadPool.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				log.info("===============异步初始化目标默认值开始:{} ==========================");
				//注册完善信息后，目标数据给予默认值
				if (!StringUtil.isBlank(userBaseVo.getName())
						&& !StringUtil.isBlank(userBaseVo.getBirthdate())
						&& !StringUtil.isBlank(userBaseVo.getWeight())
						&& userBaseVo.getHeight() > 0 && userBaseVo.getGenderType() > 0) {
					//查询是否有目标数据
					List<UserDeviceBo> targetList = wristbandService.getTarget(userBaseVo.getUserId());
					if(targetList == null || targetList.isEmpty()){
						UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userBaseVo.getUserId()).build();
						//默认值步数 8000步
						userDeviceVo.setConfigCode(DeviceConfig.con001);
						userDeviceVo.setValue("8000");
						wristbandService.saveTarget(userDeviceVo);
						//默认值饮水量 8杯
						userDeviceVo.setConfigCode(DeviceConfig.con007);
						userDeviceVo.setValue("8");
						wristbandService.saveTarget(userDeviceVo);
						//默认值睡眠 8h
						userDeviceVo.setConfigCode(DeviceConfig.con005);
						userDeviceVo.setValue("8");
						wristbandService.saveTarget(userDeviceVo);
						//默认值体重
						//男性：(身高cm－80)×70﹪=标准体重, 女性：(身高cm－70)×60﹪=标准体重
						int targetWeight = 0;
						if(userBaseVo.getGenderType() == 1){//1男2女
							targetWeight = (int) ((userBaseVo.getHeight() - 80) * 0.7);
						}
						if(userBaseVo.getGenderType() == 2){//1男2女
							targetWeight = (int) ((userBaseVo.getHeight() - 70) * 0.6);
						}
						userDeviceVo.setConfigCode(DeviceConfig.con004);
						userDeviceVo.setValue(String.valueOf(targetWeight));
						wristbandService.saveTarget(userDeviceVo);
						//默认值卡路里摄入量
						int targetIntakeCal = 0;
						Calendar cal = Calendar.getInstance();
						Calendar bir = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						bir.setTime(sdf.parse(userBaseVo.getBirthdate()));
						int age = cal.get(Calendar.YEAR) - bir.get(Calendar.YEAR);
						if(userBaseVo.getGenderType() == 1){//1男2女
							targetIntakeCal = (int) (67	+ 13.71 * Double.parseDouble(userBaseVo.getWeight())
									+ 5 * userBaseVo.getHeight() - 6.9 * age);
						}
						if(userBaseVo.getGenderType() == 2){//1男2女
							targetIntakeCal = (int) (661 + 9.6 * Double.parseDouble(userBaseVo.getWeight())
									+ 1.72 * userBaseVo.getHeight() - 4.7 * age);
						}
						userDeviceVo.setConfigCode(DeviceConfig.con006);
						userDeviceVo.setValue(String.valueOf(targetIntakeCal));
						wristbandService.saveTarget(userDeviceVo);
					}
				}else{
					log.info(" 编辑资料不初始化 ");
				}
				log.info("===============异步初始化目标默认值结束:{} ==========================");
				return null;
			}
		});
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
	
	
	/**
	 * 
	* @Title: getTargetInfo 
	* @param: 
	* @Description: 查询目标数据
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "target/info", notes = "target/info")
	@ResponseBody
	@RequestMapping(value = "target/info", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getTargetInfo(@QueryParam("userId") Integer userId,@QueryParam("mac") String mac){
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		WristbandBo wristbandBo = wristbandService.getTarget(userId, mac);
		boUtil.setData(wristbandBo);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: modifyInfo 
	* @param: 
	* @Description: 编辑目标数据
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "PUT", value = "target/modify", notes = "target/modify")
	@ResponseBody
	@RequestMapping(value = "target/modify", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public BoUtil modifyInfo(final @RequestBody WristbandTargetVo wristbandTargetVo) throws Exception {
		log.info(" wristbandTargetVo : {} ",wristbandTargetVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = wristbandTargetVo.getUserId();
		// 查询绑定设备id
		/*int bindId = wristbandService.getBandId(userId,
				wristbandTargetVo.getMac());
		if (bindId == 0) {// 绑定设备
			int result = wristbandService.userBindDevice(userId,
					wristbandTargetVo.getMac());
			if (result > 0) {
				// 查询绑定设备id
				bindId = wristbandService.getBandId(userId,
						wristbandTargetVo.getMac());
			} else {
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setMsg("未查询到绑定的设备");
				return boUtil;
			}
		}*/
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetStep())){//步数
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con001);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con001)
						.value(wristbandTargetVo.getTargetStep()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con001)
						.value(wristbandTargetVo.getTargetStep()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetCalorie())){//卡路里消耗
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con002);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con002)
						.value(wristbandTargetVo.getTargetCalorie()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con002)
						.value(wristbandTargetVo.getTargetCalorie()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetWeight())){//体重KG
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con004);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con004)
						.value(wristbandTargetVo.getTargetWeight()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con004)
						.value(wristbandTargetVo.getTargetWeight()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetSleep())){//睡眠
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con005);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con005)
						.value(wristbandTargetVo.getTargetSleep()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con005)
						.value(wristbandTargetVo.getTargetSleep()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetCalorieIntake())){//卡路里摄入
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con006);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con006)
						.value(wristbandTargetVo.getTargetCalorieIntake()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con006)
						.value(wristbandTargetVo.getTargetCalorieIntake()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		if(!StringUtil.isBlank(wristbandTargetVo.getTargetWater())){//饮水量
			//是否存在目标数据
			int exist = wristbandService.getTargetExist(userId,DeviceConfig.con007);
			if(exist > 0){//存在，更新数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con007)
						.value(wristbandTargetVo.getTargetWater()).build();
				wristbandService.updateTarget(userDeviceVo);
			}else{//不存在，新增数据
				UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
						.configCode(DeviceConfig.con007)
						.value(wristbandTargetVo.getTargetWater()).build();
				wristbandService.saveTarget(userDeviceVo);
			}
		}
		return boUtil;
	}
	
	/**
	 * 
	* @Title: regist 
	* @param: 
	* @Description: 注册
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "regist", notes = "regist")
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil regist(final @RequestBody RequestVo requestVo) throws Exception {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		String regJson = AESUtil.decrypt(requestVo.getSign(), rSAConfig.getAeskey());
		log.info("##########regJson:{}", regJson);
		JSONObject regObj = JSONObject.fromObject(regJson);
		UserBaseVo userBaseVo = (UserBaseVo) JSONObject.toBean(regObj, UserBaseVo.class);
		
		int userId = wristbandService.existAccount(userBaseVo.getAccount());
		if(userId > 0){
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.ACCOUNT_REGISTERED);
			boUtil.setMsg("Account registered");
			return boUtil;
		}
		int result = wristbandService.regist(userBaseVo);
		if(result > 0){
			boUtil.setData(userBaseVo.getUserId());
			return boUtil;
		}else{
			boUtil.setCode(ErrorCode.FAIL);
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: login 
	* @param: 
	* @Description: 登录
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "login", notes = "login")
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil login(final @RequestBody RequestVo requestVo) throws Exception {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		String regJson = AESUtil.decrypt(requestVo.getSign(), rSAConfig.getAeskey());
		log.info("##########regJson:{}", regJson);
		JSONObject regObj = JSONObject.fromObject(regJson);
		UserBaseVo userBaseVo = (UserBaseVo) JSONObject.toBean(regObj, UserBaseVo.class);
		int userId = wristbandService.login(userBaseVo);
		if(userId > 0){
			boUtil.setData(userId);
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.ACCOUNT_PASSWORD_IS_WRONG);
			boUtil.setMsg("Account password is wrong");
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: bindWristband 
	* @param: 
	* @Description: 绑定手环
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "bind", notes = "bind")
	@ResponseBody
	@RequestMapping(value = "/bind", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil bindWristband(final @RequestBody WristbandTargetVo wristbandTargetVo) throws Exception {
		log.info(" wristbandTargetVo : {} ",wristbandTargetVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = wristbandTargetVo.getUserId();
		// 查询用户是否已绑定手环
		int userBind = wristbandService.getBandId(userId,"");
		if(userBind > 0){
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.USER_HAS_BOUND_WRISTBAND);
			boUtil.setMsg("用户已绑定手环");
			return boUtil;
		}
		// 查询手环是否已被其他用户绑定
		int wristbandBind = wristbandService.getBandId(0,wristbandTargetVo.getMac());
		if(wristbandBind > 0){
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.WRISTBAND_HAD_BOUND);
			boUtil.setMsg("手环已被其他用户绑定");
			return boUtil;
		}
		log.info(" userBind : {},wristbandBind : {} ",userBind,wristbandBind);
		if (userBind == 0 && wristbandBind == 0) {// 绑定设备
			int result = wristbandService.userBindDevice(userId,
					wristbandTargetVo.getMac());
			if (result > 0) {
				return boUtil;
			} else {
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.FAIL);
				return boUtil;
			}
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.WRISTBAND_RING_DEVICE);
			boUtil.setMsg("已绑定手环设备");
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: getTodayHeart 
	* @param: 
	* @Description: 查询今日心率数据
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "heart/today", notes = "heart/today")
	@ResponseBody
	@RequestMapping(value = "heart/today", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getTodayHeart(@QueryParam("userId") Integer userId,@QueryParam("date") String date){
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		userId = userId == null ? 0 : userId;
		List<UserDeviceBo> list = wristbandService.getTodayInfo(userId, DeviceConfig.con009,date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UserDeviceBo userDeviceBo : list) {
			String time = "";
			try {
				time = sdf.format(sdf.parse(userDeviceBo.getTime()));
				userDeviceBo.setTime(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		boUtil.setData(list);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: deleteTodayHeart 
	* @param: 
	* @Description: 删除心率
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "DELETE", value = "heart/delete", notes = "heart/delete")
	@ResponseBody
	@RequestMapping(value = "heart/delete", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/*")
	public BoUtil deleteTodayHeart(@QueryParam("userDeviceId") Integer userDeviceId){
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		userDeviceId = userDeviceId == null ? 0 : userDeviceId;
		int result = wristbandService.deleteRecord(userDeviceId);
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.FAIL);
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: getHints 
	* @param: statusType 状态，1正常，2偏低，3偏高
	* @param: languageType 语种,1英文，2中文，3泰文，4，印度文
	* @Description: 查询系统提示语
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "hints", notes = "hints")
	@ResponseBody
	@RequestMapping(value = "hints", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getHints(@QueryParam("code") String code,
			@QueryParam("statusType") Integer statusType,
			@QueryParam("languageType") Integer languageType) {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		statusType = statusType == null ? 0 : statusType;
		languageType = languageType == null ? 0 : languageType;
		String content = wristbandService.getHints(code, statusType, languageType);
		boUtil.setData(content);
		return boUtil;
	}
}
