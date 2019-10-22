package com.coins.cloud.rest;

import javax.inject.Inject;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coins.cloud.service.UserSysParaService;
import com.hlb.cloud.bo.BoUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: UserSysParaResource
 * @Description: 用户常量管理
 * @author ShengHao shenghaohao@hadoop-tech.com
 * @Company initbay
 * @date 2019年10月22日 上午11:29:41
 *
 */

@RefreshScope
@RestController
@RequestMapping("syspara")
public class UserSysParaResource {

	@Inject
	private UserSysParaService userSysParaService;

	@ApiOperation(httpMethod = "GET", value = "list", notes = "list")
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getUserSysPara() {
		BoUtil bo = BoUtil.getDefaultTrueBo();
		bo.setData(userSysParaService.getUserSysPara());
		return bo;
	}

}
