package com.coins.cloud.rest;

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.bo.FoodBo;
import com.coins.cloud.dao.FoodDao;
import com.coins.cloud.vo.FoodVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.StringUtil;

@RefreshScope
@RestController
@RequestMapping("/thirdfood")
@Slf4j
public class ThirdFoodResource {

	private final String APP_ID = "e959e1b9";
	private final String APP_KEY = "dfca6483ff7e49d9f3b405ca3dc20ee5";
	private final String URL = "https://api.edamam.com/api/food-database/parser";

	@Inject
	private FoodDao foodDao;
	
	/**
	 * 
	 * @Title: searchFood
	 * @param:
	 * @Description: 第三方食品库搜索
	 * @return BoUtil
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(httpMethod = "GET", value = "search", notes = "search")
	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil searchFood(@QueryParam("ingr") String ingr)
			throws UnsupportedEncodingException {
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		if (StringUtil.isBlank(ingr)) {
			return boUtil;
		}
		log.info(" ingr : {} ",ingr);
		String encodeIngr = URLEncoder.encode(ingr, "UTF-8");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String path = URL + "?ingr=" + encodeIngr + "&app_id=" + APP_ID + "&app_key=" + APP_KEY;
		String json = "";
		try {
			HttpGet httpget = new HttpGet(path);
			log.info(" path : {} ",path);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				log.info(" response code : {} ", response.getStatusLine().getStatusCode());
				if (entity != null) {
					String resultBody = EntityUtils.toString(entity);
					log.info(" resultBody : {} ",resultBody);
					JSONObject ob=JSONObject.fromObject(resultBody);
					JSONArray parsed = ob.getJSONArray("parsed");
					log.info(" parsed : {} ",parsed);
					log.info(" parsed size : {} ",parsed.size());
					if(parsed.size() > 0){
						JSONObject parsedNum1 = parsed.getJSONObject(0);
						log.info(" parsedNum1 : {} ",parsedNum1);
						if(parsedNum1 != null){
							JSONObject food = parsedNum1.getJSONObject("food");
							if(food != null){
								JSONObject nutrients = food.getJSONObject("nutrients");
								json = nutrients.toString();
							}
						}
						
					}
					
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FoodVo foodVo=FoodVo.builder().name(ingr).offset(0).pageSize(10000).build();
		List<FoodBo> foodBos= foodDao.getFoodList(foodVo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("thirdfood", json);
		map.put("food", foodBos);
		boUtil.setData(map);
		return boUtil;
	}
}
