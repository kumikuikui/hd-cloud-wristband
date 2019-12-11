package com.coins.cloud.dao.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import com.coins.cloud.vo.FoodVo;

public class FoodProvider {

	public String getFoodList(FoodVo foodVo) {
		String sql = new SQL() {
			{
				SELECT("user_food_intake_bt_seq,user_device_record_bt_seq,food_intake_eat_itype,food_intake_name,food_intake_brand,food_intake_size,food_intake_cal,food_intake_fat,food_intake_check_itype");
				FROM("user_food_intake_bt");
				WHERE("active_flag = 'y' AND food_intake_check_itype=1 ");
				if (StringUtils.isNotBlank(foodVo.getName())) {
					AND();
					WHERE("food_intake_name = #{name}");
				}
				ORDER_BY("user_food_intake_bt_seq desc limit #{offset},#{pageSize}");
			}
		}.toString();
		return sql;
	}

}
