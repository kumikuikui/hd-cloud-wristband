package com.coins.cloud.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.FoodBo;
import com.coins.cloud.dao.FoodDao;
import com.coins.cloud.vo.FoodVo;

@Repository
public class FoodDaoMyBatisImpl implements FoodDao {

	@Inject
	private FoodMapper foodMapper;

	@Override
	public List<FoodBo> getFoodList(FoodVo foodVo) {
		return foodMapper.getFoodrList(foodVo);
	}
}
