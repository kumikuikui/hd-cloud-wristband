package com.coins.cloud.util;

import com.hlb.cloud.util.StringUtil;

/**
 * 
  * @ClassName: CommonUtil
  * @Description: 工具类
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年4月17日 下午2:43:31
  *
 */
public class CommonUtil {
	
	/**
	 * 
	* @Title: checkHeart 
	* @param: 
	* @Description: 检测心率是否正常
	* @return int 0:无 1:偏低 2:正常 3:偏高
	 */
	public static int checkHeart(String value) {
		//范围
		int[] array = {60,100};
		int result = 0;
		if(StringUtil.isBlank(value)){
			return result;
		}else{
			int heart = Integer.parseInt(value);
			if(heart < array[0]){//偏低
				result = 1;
			}else if(heart >= array[0] && heart <= array[1]){//正常
				result = 2;
			}else if(heart > array[1]){//偏高
				result = 3;
			}
		}
		return result;
	}
	
	/**
	 * 
	* @Title: checkBMI 
	* @param: 
	* @Description: 检测BMI数值
	* @return int
	 */
	public static int checkBMI(String value) {
		/*
		 * 分类 BMI 范围 偏瘦 <= 18.4, 正常 18.5 ~ 23.9 ,过重 24.0 ~ 27.9 ,肥胖 >= 28.0
		 */
		// 范围
		double[][] array = { { 18.4 }, { 18.5, 23.9 }, { 24.0, 27.9 }, { 28.0 } };
		int result = 0;
		if (StringUtil.isBlank(value)) {
			return result;
		} else {
			double heart = Double.parseDouble(value);
			if (heart <= array[0][0]) {// 偏瘦
				result = 1;
			} else if (heart >= array[1][0] && heart <= array[1][1]) {// 正常
				result = 2;
			} else if (heart >= array[2][0] && heart <= array[2][1]) {// 过重
				result = 3;
			} else if (heart >= array[3][0]) {// 肥胖
				result = 4;
			}
		}
		return result;
	}
	
	/**
	 * 
	* @Title: checkBloodPressure 
	* @param: 
	* @Description: 检测血压
	* @return int
	 */
	public static int checkBloodPressure(String value) {
	      /*分类	血压               范围(收缩压(mmHg) 舒张压(mmHg))
			低血压      		<=89	 <=59
			理想血压			90~120   60~80
			正常血压  			121~130  81~85
			正常高值  			131～139	 86～89
			轻度高血压(1级)   140～159	 90～99
			中度高血压(2级)   160～179	 100～109
			重度高血压(3级)	≥180	 ≥110
		 */
		int result = 0;
		if (StringUtil.isBlank(value)) {
			return result;
		} else {
			// 范围
			int[][] diastolicArray = { { 59 }, { 60, 80 }, { 81, 85 }, { 86, 89 },
					{ 90, 99 }, { 100, 109 }, { 110 } };
			int[][] systolicArray = { { 89 }, { 90, 120 }, { 121, 130 },{ 131, 139 }, 
					{ 140, 159 }, { 160, 179 }, { 180 } };
			int diastolicResult = 0;
			int systolicResult = 0;
			//血压伸张值
			int diastolic = Integer.parseInt(value.split("\\|")[0]);
			if (diastolic <= diastolicArray[0][0]) {// 低血压
				diastolicResult = 1;
			}else if (diastolic >= diastolicArray[1][0] && diastolic <= diastolicArray[1][1]) {// 理想血压
				diastolicResult = 2;
			}else if (diastolic >= diastolicArray[2][0] && diastolic <= diastolicArray[2][1]) {// 正常血压
				diastolicResult = 3;
			}else if (diastolic >= diastolicArray[3][0] && diastolic <= diastolicArray[3][1]) {// 正常高值
				diastolicResult = 4;
			}else if (diastolic >= diastolicArray[4][0] && diastolic <= diastolicArray[4][1]) {// 轻度高血压(1级)
				diastolicResult = 5;
			}else if (diastolic >= diastolicArray[5][0] && diastolic <= diastolicArray[5][1]) {// 中度高血压(2级)
				diastolicResult = 6;
			}else if (diastolic >= diastolicArray[6][0]) {// 重度高血压(3级)
				diastolicResult = 7;
			}
			
			//血压收缩值
			int systolic = Integer.parseInt(value.split("\\|")[1]);
			if (systolic <= systolicArray[0][0]) {// 低血压
				systolicResult = 1;
			}else if (systolic >= systolicArray[1][0] && systolic <= systolicArray[1][1]) {// 理想血压
				systolicResult = 2;
			}else if (systolic >= systolicArray[2][0] && systolic <= systolicArray[2][1]) {// 正常血压
				systolicResult = 3;
			}else if (systolic >= systolicArray[3][0] && systolic <= systolicArray[3][1]) {// 正常高值
				systolicResult = 4;
			}else if (systolic >= systolicArray[4][0] && systolic <= systolicArray[4][1]) {// 轻度高血压(1级)
				systolicResult = 5;
			}else if (systolic >= systolicArray[5][0] && systolic <= systolicArray[5][1]) {// 中度高血压(2级)
				systolicResult = 6;
			}else if (systolic >= systolicArray[6][0]) {// 重度高血压(3级)
				systolicResult = 7;
			}
			
			if(diastolicResult == 1 || systolicResult == 1){
				result = 1;// 低血压
			}else{//哪个值更大，说明更危险，提示危险值给用户
				result = diastolicResult > systolicResult ? diastolicResult : systolicResult;
			}
		}
		return result;
	}
	
}
