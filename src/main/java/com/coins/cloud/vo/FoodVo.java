package com.coins.cloud.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodVo {
	
	private int offset;
	
	private int pageSize;
	
	private String name;
}
