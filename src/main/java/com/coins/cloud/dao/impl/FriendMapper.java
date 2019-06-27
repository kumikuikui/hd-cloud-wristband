package com.coins.cloud.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.coins.cloud.bo.FriendBo;
import com.coins.cloud.bo.UserFriendBo;


@Mapper
public interface FriendMapper {

	/**
	 * 
	* @Title: searchUser 
	* @param: 
	* @Description: 搜索用户
	* @return List<UserFriendBo>
	 */
	@Select("SELECT * FROM user_device_base_sb WHERE device_base_account = #{account} AND active_flag = 'y' ")
	@Results(value = {
			@Result(property = "userId", column = "user_device_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "device_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "avatar", column = "device_base_avatar_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "account", column = "device_base_account", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	public List<UserFriendBo> searchUser(@Param("account") String account);
	
	/**
	 * 
	* @Title: getMyFriends 
	* @param: 
	* @Description: 获取我的好友
	* @return String
	 */
	@Select("SELECT device_base_sb_seqs FROM user_friend_relation_bt WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	public String getMyFriends(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: checkAddRecord 
	* @param: 
	* @Description: 查询是否有添加记录
	* @return int
	 */
	@Select("SELECT count(1) FROM user_add_friend_bt WHERE active_flag = 'y' "
			+ "AND user_device_base_sb_to = #{userId} AND user_device_base_sb_seq = #{targetUserId} AND add_friend_itype = 1")
	public int checkAddRecord(@Param("userId") int userId,@Param("targetUserId") int targetUserId);
	
	/**
	 * 
	* @Title: addFriendRecord 
	* @param: 
	* @Description: 添加好友申请记录
	* @return int
	 */
	@Insert("INSERT INTO user_add_friend_bt "
			+" (user_device_base_sb_seq,user_device_base_sb_to,device_base_account,add_friend_itype,"
			+" create_by,create_time,update_by,update_time,active_flag) "
			+" VALUES (#{targetUserId},#{userId},#{account},1,#{userId},NOW(),#{userId},NOW(),'y')")
	public int addFriendRecord(@Param("userId") int userId,
			@Param("account") String account,
			@Param("targetUserId") int targetUserId);
	
	/**
	 * 
	* @Title: getAccountByUserId 
	* @param: 
	* @Description: 查询用户账号
	* @return String
	 */
	@Select("SELECT device_base_account FROM user_device_base_sb WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y'")
	public String getAccountByUserId(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: getFriendApplicatList 
	* @param: 
	* @Description: 好友申请列表
	* @return List<FriendBo>
	 */
	@Select("SELECT * FROM user_add_friend_bt WHERE user_device_base_sb_seq = #{userId} AND active_flag = 'y' "
			+ "ORDER BY create_time DESC")
	@Results(value = {
			@Result(property = "friendUserId", column = "user_device_base_sb_to", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "account", column = "device_base_account", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "friendType", column = "add_friend_itype", javaType = int.class, jdbcType = JdbcType.INTEGER)
	})
	public List<FriendBo> getFriendApplicatList(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: addFriend 
	* @param: 
	* @Description: 添加好友
	* @return int
	 */
	@Insert("INSERT INTO user_friend_relation_bt "
			+" (user_device_base_sb_seq,device_base_sb_seqs,create_by,create_time,update_by,update_time,active_flag) "
			+" VALUES (#{userId},#{friendIds},#{userId},NOW(),#{userId},NOW(),'y')")
	public int addFriend(@Param("userId") int userId,@Param("friendIds") String friendIds);
	
	/**
	 * 
	* @Title: updateFriend 
	* @param: 
	* @Description: 更新好友
	* @return int
	 */
	@Update("UPDATE user_friend_relation_bt SET device_base_sb_seqs = #{friendIds},update_time = NOW() "
			+ "WHERE user_device_base_sb_seq = #{userId}")
	public int updateFriend(@Param("userId") int userId,@Param("friendIds") String friendIds);
	
	/**
	 * 
	* @Title: updateRecordStatus 
	* @param: 
	* @Description: 更新好友请求状态
	* @return int
	 */
	@Update("UPDATE user_add_friend_bt SET add_friend_itype = #{status},update_time = NOW() "
			+ "WHERE user_device_base_sb_seq = #{userId} AND user_device_base_sb_to = #{targetUserId}")
	public int updateRecordStatus(@Param("userId") int userId,
			@Param("targetUserId") int targetUserId, @Param("status") int status);
	
	/**
	 * 
	* @Title: getAllFriend 
	* @param: 
	* @Description: 获取我的所有好友
	* @return List<FriendBo>
	 */
	@Select("SELECT * FROM user_device_base_sb "
		   +" WHERE locate(CONCAT(user_device_base_sb_seq,','),( "
		   +" SELECT CONCAT(REPLACE(device_base_sb_seqs,'|',','),',')  FROM user_friend_relation_bt WHERE user_device_base_sb_seq = #{userId}))")
	@Results(value = {
			@Result(property = "friendUserId", column = "user_device_base_sb_seq", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "device_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "avatar", column = "device_base_avatar_url", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	public List<FriendBo> getAllFriend(@Param("userId") int userId);
	
	/**
	 * 
	* @Title: getStepRank 
	* @param: 
	* @Description: 获取步数排行榜
	* @return List<FriendBo>
	 */
	@Select("select z.device_record_value,x.seqs,y.device_base_name,y.device_base_avatar_url,z.create_time "
		   +" from (select substring_index(substring_index(concat(a.device_base_sb_seqs,'|',a.user_device_base_sb_seq),'|',b.ids+1),'|',-1) seqs "
		   +" from user_friend_relation_bt a "
		   +" join aux b on b.ids < (length(concat(a.device_base_sb_seqs,'|',a.user_device_base_sb_seq)) - length(replace(concat(a.device_base_sb_seqs,'|',a.user_device_base_sb_seq),'|',''))+1) "
		   +" where a.user_device_base_sb_seq = #{userId}) x "
		   +" inner join user_device_base_sb y "
		   +" on x.seqs= y.user_device_base_sb_seq "
		   +" and y.active_flag = 'y' "
		   +" inner join user_device_record_bt z "
		   +" on x.seqs = z.user_device_base_sb_seq "
		   +" and DATE_FORMAT(z.create_time,'%Y-%m-%d') = #{time} "
		   +" and z.device_config_internal_code = 'con001' "
		   +" order by z.device_record_value DESC")
	@Results(value = {
			@Result(property = "friendUserId", column = "seqs", javaType = int.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "name", column = "device_base_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "avatar", column = "device_base_avatar_url", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "time", column = "create_time", javaType = String.class, jdbcType = JdbcType.DATE),
			@Result(property = "step", column = "device_record_value", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	public List<FriendBo> getStepRank(@Param("userId") int userId,@Param("time") String time);
	
	/**
	 * 
	* @Title: getStepStarTotal 
	* @param: 
	* @Description: 获取步数点赞总数
	* @return int
	 */
	@Select("SELECT COUNT(1) FROM user_like_record_bt WHERE active_flag = 'y' "
			+" AND user_device_base_sb_to = #{userId} AND  like_record_date = #{time}")
	public int getStepStarTotal(@Param("userId") int userId,@Param("time") String time);
	
	/**
	 * 
	* @Title: checkStar 
	* @param: 
	* @Description: 是否点赞
	* @return int
	 */
	@Select("SELECT COUNT(1) FROM user_like_record_bt WHERE active_flag = 'y' AND user_device_base_sb_to = #{targetUserId} "
			+" AND  like_record_date = #{time} AND user_device_base_sb_seq = #{userId}")
	public int checkStar(@Param("userId") int userId,
			@Param("time") String time, @Param("targetUserId") int targetUserId);
	
	/**
	 * 
	* @Title: starStep 
	* @param: 
	* @Description: 点赞步数
	* @return int
	 */
	@Insert("INSERT INTO user_like_record_bt "
		   +" (user_device_base_sb_seq,user_device_base_sb_to,like_record_date,like_record_itype,create_by,"
		   +" create_time,update_by,update_time,active_flag) "
		   +" VALUES (#{userId},#{targetUserId},#{time},1,#{userId},NOW(),#{userId},NOW(),'y')")
	public int starStep(@Param("userId") int userId,
			@Param("time") String time, @Param("targetUserId") int targetUserId);
	
	/**
	 * 
	* @Title: unStarStep 
	* @param: 
	* @Description: 取消点赞
	* @return int
	 */
	@Delete("DELETE FROM user_like_record_bt WHERE active_flag = 'y' AND user_device_base_sb_to = #{targetUserId} "
		   +" AND like_record_date = #{time} AND user_device_base_sb_seq = #{userId}")
	public int unStarStep(@Param("userId") int userId,
			@Param("time") String time, @Param("targetUserId") int targetUserId);
}
