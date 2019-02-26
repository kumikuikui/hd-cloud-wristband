package com.coins.cloud.rest;

import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.coins.cloud.bo.UserBaseBo;
import com.coins.cloud.bo.UserDeviceBo;
import com.coins.cloud.bo.WristbandBo;
import com.coins.cloud.service.WristbandService;
import com.coins.cloud.util.DeviceConfig;
import com.coins.cloud.util.FileUtil;
import com.coins.cloud.util.HttpClientUtil;
import com.coins.cloud.vo.UserBaseVo;
import com.coins.cloud.vo.UserDeviceVo;
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
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = wristbandVo.getUserId();
		String mac = wristbandVo.getMac();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(date);
		//查询绑定设备id
		int bindId = wristbandService.getBandId(userId, mac);
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
		
		//结果 1成功 0失败  数据格式例如 1:1:1:0:1:1:1:1  步数|心率|卡路里|血氧|血压|重量|睡眠|饮水
		String result = "";
		int i = 0;
		//步数上传
		if(!StringUtil.isBlank(wristbandVo.getStep())){
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId).bindId(bindId)
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
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId).bindId(bindId)
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
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId).bindId(bindId)
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
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId).bindId(bindId)
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
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId).bindId(bindId)
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
					.bindId(bindId).configCode(DeviceConfig.con004)
					.value(value).time(time).build();
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
					.bindId(bindId).configCode(DeviceConfig.con005)
					.value(value).time(time).build();
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
		if (!StringUtil.isBlank(wristbandVo.getTotalWater())) {
			String value = wristbandVo.getTotalWater() + "|"
					+ wristbandVo.getDrinkWater();
			UserDeviceVo userDeviceVo = UserDeviceVo.builder().userId(userId)
					.bindId(bindId).configCode(DeviceConfig.con007)
					.value(value).time(time).build();
			int resu = wristbandService.save(userDeviceVo);
			if (resu > 0) {
				i = 1;
			}
			result += "|" + i;
			i = 0;
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
	public BoUtil getList(@QueryParam("pageIndex") Integer pageIndex,@QueryParam("pageSize") Integer pageSize,
			@QueryParam("userId") Integer userId,@QueryParam("mac") String mac,@QueryParam("code") String code) throws ParseException {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		pageIndex = pageIndex == null ? 1 : pageIndex;
		userId = userId == null ? 0 : userId;
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
	    Calendar c=Calendar.getInstance();
	    c.setTime(new Date());
	    int weekday=c.get(Calendar.DAY_OF_WEEK);
	    pageSize = pageSize == null ? 7 : pageSize;
	    int offset = 0;
	    if(pageIndex == 1){
	    	offset = (pageIndex - 1) * pageSize;
	    	if(weekday == 1){//当天是周日
		    	pageSize = 14;//查询这周和上周的数据
		    }else{
		    	pageSize = 7 + weekday - 1;
		    }
	    }else{
	    	pageSize = 7;
	    	if(weekday == 1){//当天是周日
	    		offset = (pageIndex - 1) * pageSize + 7;
		    }else{
		    	offset = (pageIndex - 1) * pageSize + weekday - 1;
		    }
	    }
		log.info("userId: {}, offset: {}, pageSize: {},mac:{},code:{}, ",userId, offset, pageSize,mac,code);
		List<UserDeviceBo> list = wristbandService.getRecordByCode(userId, mac, code, pageIndex, pageSize);
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
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int result = wristbandService.modifyInfo(userBaseVo);
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
}
