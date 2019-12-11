package com.coins.cloud.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodBo {
	
	private int id;
	
	private int deviceId;
	
	private int eatType;//餐别，1.早餐；2.午餐；3.晚餐
	
	private String foodName;//食品名称
	
	private String foodBrand;//食品品牌
	
	private String foodSize;//食品规格
	
	private  double foodCal;//食品卡路里
	
	private double foodFat;//食品脂肪含量
	
	private int status;//1:审核成功；2.审核失败
	
	private long createBy;
	
	private String activeFlag;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private long updatBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
}
