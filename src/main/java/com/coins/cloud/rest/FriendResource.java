package com.coins.cloud.rest;

import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.bo.FriendBo;
import com.coins.cloud.bo.UserFriendBo;
import com.coins.cloud.service.FriendService;
import com.coins.cloud.util.ErrorCode;
import com.coins.cloud.vo.UserFriendVo;
import com.coins.cloud.vo.WristbandTargetVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.StringUtil;

@RefreshScope
@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendResource {
	
	@Inject
	private FriendService friendService;
	
	/**
	 * 
	* @Title: searchUser 
	* @param: 
	* @Description: 搜索用户
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "search", notes = "search")
	@ResponseBody
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil searchUser(@QueryParam("account") String account,
			@QueryParam("userId") Integer userId) throws ParseException {
		log.info(" account : {},userId : {} ",account,userId);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<UserFriendBo> list = friendService.searchUser(account, userId);
		boUtil.setData(list);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: addFriend 
	* @param: 
	* @Description: 添加好友
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "add", notes = "add")
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil addFriend(final @RequestBody UserFriendVo userFriendVo) throws Exception {
		log.info(" userFriendVo : {} ",userFriendVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = userFriendVo.getUserId();
		int targetUserId = userFriendVo.getTargetUserId();
		//查询我的好友列表
		String friendIds = friendService.getMyFriends(userId);
		if(!StringUtil.isBlank(friendIds)){
			String targetUser = "|" + targetUserId + "|";
			friendIds = "|" + friendIds + "|";
			if(friendIds.contains(targetUser)){
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.FRIEND_IS_EXIS);
				boUtil.setMsg("Already a friend, can't be added repeatedly");
				return boUtil;
			}
		}
		//查询是否已申请好友请求
		int count = friendService.checkAddRecord(userId, targetUserId);
		if(count > 0){
			boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.ADD_RECORD_IS_EXIS);
			boUtil.setMsg("Friend add request already exists");
			return boUtil;
		}
		//查询邮箱账号
		String account = friendService.getAccountByUserId(userId);
		//添加好友申请
		int result = friendService.addFriendRecord(userId, account, targetUserId);
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: getFriendApplicatList 
	* @param: 
	* @Description: 我的好友申请记录
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "record", notes = "record")
	@ResponseBody
	@RequestMapping(value = "record", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getFriendApplicatList(@QueryParam("userId") Integer userId) throws ParseException {
		log.info(" userId : {} ",userId);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<FriendBo> list = friendService.getFriendApplicatList(userId);
		boUtil.setData(list);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: acceptFriend 
	* @param: 
	* @Description: 审核 1、同意添加 2、拒绝添加
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "POST", value = "check", notes = "check")
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public BoUtil acceptFriend(final @RequestBody UserFriendVo userFriendVo) throws Exception {
		log.info(" userFriendVo : {} ",userFriendVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = userFriendVo.getUserId();
		int targetUserId = userFriendVo.getTargetUserId();
		int operType = userFriendVo.getOperType();//1、同意 2、拒绝
		int status = 2;//状态， 2、已添加 3、拒绝
		if(operType == 1){//1、同意
			//查询我的好友列表
			String friendIds = friendService.getMyFriends(userId);
			if(!StringUtil.isBlank(friendIds)){
				String targetUser = "|" + targetUserId + "|";
				String friendIdsStr = "|" + friendIds + "|";
				if(friendIdsStr.contains(targetUser)){
					boUtil = BoUtil.getDefaultFalseBo();
					boUtil.setCode(ErrorCode.FRIEND_IS_EXIS);
					boUtil.setMsg("Already a friend, can't be added repeatedly");
					return boUtil;
				}
				//更新我的好友列表
				friendService.updateFriend(userId, friendIds + "|" + targetUserId);
			}else{
				//新增好友
				friendService.addFriend(userId, String.valueOf(targetUserId));
			}
			//查询目标用户的好友列表
			String targetFriendIds = friendService.getMyFriends(targetUserId);
			if(!StringUtil.isBlank(targetFriendIds)){
				String userStr = "|" + userId + "|";
				String friendIdsStr = "|" + targetFriendIds + "|";
				if(!friendIdsStr.contains(userStr)){
					//更新目标用户的好友列表
					friendService.updateFriend(targetUserId, targetFriendIds + "|" + userId);
				}
			}else{
				//新增目标用户好友
				friendService.addFriend(targetUserId, String.valueOf(userId));
			}
			status = 2;
		}else{//2、拒绝
			status = 3;
		}
		int result = friendService.updateRecordStatus(userId, targetUserId, status);
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
	
	/**
	 * 
	* @Title: getAllFriends 
	* @param: 
	* @Description: 获取我的所有好友
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "all", notes = "all")
	@ResponseBody
	@RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getAllFriends(@QueryParam("userId") Integer userId) throws ParseException {
		log.info(" userId : {} ",userId);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<FriendBo> list = friendService.getAllFriend(userId);
		boUtil.setData(list);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: getRank 
	* @param: 
	* @Description: 获取步数排行榜
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "step/rank", notes = "step/rank")
	@ResponseBody
	@RequestMapping(value = "step/rank", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getRank(@QueryParam("userId") Integer userId,@QueryParam("time") String time) throws ParseException {
		log.info(" userId : {} ",userId);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		List<FriendBo> list = friendService.getStepRank(userId,time);
		int i = 0;
		int myStep = 0;
		Map<String,Object> map = new HashedMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (FriendBo friendBo : list) {
			friendBo.setTime(sdf.format(sdf.parse(friendBo.getTime())));
			//获取步数点赞数量
			int starTotal = friendService.getStepStarTotal(friendBo.getFriendUserId(), time);
			friendBo.setStarNum(starTotal);
			//验证是否点赞步数
			int count = friendService.checkStar(userId, time, friendBo.getFriendUserId());
			friendBo.setStar(count > 0 ? true : false);
			i++;//计数，获取自己的排名
			if(friendBo.getFriendUserId() == userId){
				map.put("rank", i);
				map.put("star", starTotal);
				map.put("myStep", friendBo.getStep());
				myStep = Integer.parseInt(friendBo.getStep());
			}
		}
		if(i > 1){//不是排第一名
			//i-1-1  -1是下标从0开始，-1是获取上一名的步数
			int stepDiff = Integer.parseInt(list.get(i-1-1).getStep()) - myStep;
			map.put("stepDiff", stepDiff);
		}else{//排第一名,距离上一名步数相差为0
			map.put("stepDiff", 0);
		}
		map.put("list", list);
		boUtil.setData(map);
		return boUtil;
	}
	
	/**
	 * 
	* @Title: starStep 
	* @param: 
	* @Description: 点赞步数 1、点赞 2、取消点赞
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "PUT", value = "step/star", notes = "step/star")
	@ResponseBody
	@RequestMapping(value = "step/star", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public BoUtil starStep(final @RequestBody UserFriendVo userFriendVo) throws Exception {
		log.info(" userFriendVo : {} ",userFriendVo);
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		int userId = userFriendVo.getUserId();
		int targetUserId = userFriendVo.getTargetUserId();
		int operType = userFriendVo.getOperType();// 1、点赞 2、取消点赞
		String time = userFriendVo.getTime();
		int result = 0;
		//验证是否点赞
		int count = friendService.checkStar(userId, time, targetUserId);
		if(operType == 1){//1、点赞
			if(count > 0){
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.IT_HAD_STAR);
				boUtil.setMsg("Liked, can't repeat the like");
				return boUtil;
			}
			//点赞
			result = friendService.starStep(userId, time, targetUserId);
		}else{//取消点赞
			if(count == 0){
				boUtil = BoUtil.getDefaultFalseBo();
				boUtil.setCode(ErrorCode.IT_HAD_NOT_STAR);
				boUtil.setMsg("Still not like, can't cancel");
				return boUtil;
			}
			result = friendService.unStarStep(userId, time, targetUserId);
		}
		if(result > 0){
			return boUtil;
		}else{
			boUtil = BoUtil.getDefaultFalseBo();
			return boUtil;
		}
	}
}
