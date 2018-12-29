package com.coins.cloud.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.util.FileUtil;
import com.coins.cloud.util.HttpClientUtil;
import com.coins.cloud.vo.WristbandVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.StringUtil;

@RefreshScope
@RestController
@RequestMapping("/wristband")
@Slf4j
public class WristbandResource {
	
	private final String domain = "http://54.169.80.178:3100/";

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
}
