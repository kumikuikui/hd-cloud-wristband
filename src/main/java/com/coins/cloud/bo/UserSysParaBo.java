package com.coins.cloud.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * 
 * @ClassName: UserSysParaBo
 * @Description: 系统表
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company initbay
 * @date 2019年10月22日 上午11:04:52
 *
 */
@SuppressWarnings("deprecation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSysParaBo {

	private String id;
	
	private String code;
	
	private String name;
	
	private String paraValue;
}
