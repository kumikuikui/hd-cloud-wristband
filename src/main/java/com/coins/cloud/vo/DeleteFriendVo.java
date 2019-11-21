package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteFriendVo {
	// 用户Id
	private int userId;
	// 目标用户Id
	private int targetUserId;
}
