package com.coins.cloud.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.FoodBo;
import com.coins.cloud.dao.sql.FoodProvider;
import com.coins.cloud.vo.FoodVo;

@Mapper
public interface FoodMapper {

	@SelectProvider(type = FoodProvider.class, method = "getFoodList")
	@Results(value = {
			@Result(property = "id", column = "user_food_intake_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "deviceId", column = "user_device_record_bt_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "eatType", column = "food_intake_eat_itype", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "foodName", column = "food_intake_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodBrand", column = "food_intake_brand", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodSize", column = "food_intake_size", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "foodCal", column = "food_intake_cal", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "foodFat", column = "food_intake_fat", javaType = double.class, jdbcType = JdbcType.DECIMAL),
			@Result(property = "status", column = "food_intake_check_itype", javaType = int.class, jdbcType = JdbcType.INTEGER), })
	List<FoodBo> getFoodrList(FoodVo foodVo);

}
