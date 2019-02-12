package com.coins.cloud.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.dao.UserNricDao;
import com.coins.cloud.vo.UserNricVo;

@Repository
public class UserNricDaoMybatisImpl implements UserNricDao {
	
	@Inject
	private UserNricMapper userNricMapper;

	@Override
	public int save(UserNricVo userNricVo) {
		return userNricMapper.save(userNricVo);
	}

	@Override
	public List<UserNricBo> getUserNricList(int pageIndex, int pageSize) {
		return userNricMapper.getUserNricList(pageIndex, pageSize);
	}

	@Override
	public int getTotal() {
		return userNricMapper.getTotal();
	}

	@Override
	public UserNricBo getUserNricDetail(int id) {
		return userNricMapper.getUserNricDetail(id);
	}

	@Override
	public int updateUserNric(UserNricVo userNricVo) {
		return userNricMapper.updateUserNric(userNricVo);
	}

}
