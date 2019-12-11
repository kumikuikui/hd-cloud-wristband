package com.coins.cloud.dao;

import java.util.List;

import com.coins.cloud.bo.FoodBo;
import com.coins.cloud.vo.FoodVo;

public interface FoodDao {

	public List<FoodBo> getFoodList(FoodVo foodVo);

}
