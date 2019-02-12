package com.coins.cloud.rest;

import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.service.UserNricService;
import com.coins.cloud.vo.UserNricVo;
import com.hlb.cloud.bo.BoUtil;
import com.hlb.cloud.util.CSVUtils;

@RefreshScope
@RestController
@RequestMapping("/nric")
public class UserNricResource {
	
	@Inject
	private UserNricService userNricService;
	
	@Autowired
	private HttpServletResponse response;
	
	/**
	 * 
	* @Title: batchImportNric 
	* @param: 
	* @Description: 导入身份证
	* @return BoUtil
	 */
	@ResponseBody
	@RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json; charset=UTF-8", consumes = MediaType.MULTIPART_FORM_DATA)
	public BoUtil batchImportNric(@RequestParam("uploadFile") MultipartFile file) throws IOException {
		BoUtil bo = BoUtil.getDefaultTrueBo();
		// 验证合法输入
		if (file.isEmpty()) {
			bo.setMsg("请上传需要导入的文件");
			return bo;
		}
		int total = userNricService.save(file.getInputStream());
		bo.setData(total);
		return bo;
	}
	
	/**
	 * 
	* @Title: getUserNricList 
	* @param: 
	* @Description: 获取身份列表
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "list", notes = "list")
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getUserNricList(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 10 : pageSize;
		int offset = (page - 1) * pageSize;
		BoUtil bo = BoUtil.getDefaultTrueBo();
		int total = userNricService.getTotal();
		List<UserNricBo> list = userNricService.getUserNricList(offset, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalItems", total);
		bo.setData(map);
		return bo;
	}
	
	/**
	 * 
	* @Title: getUserNricDetail 
	* @param: 
	* @Description: 详情
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "GET", value = "detail", notes = "detail")
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "application/json", consumes = "application/*")
	public BoUtil getUserNricDetail(@QueryParam("id") Integer id) {
		id = id == null ? 0 : id;
		BoUtil bo = BoUtil.getDefaultTrueBo();
		UserNricBo userNricBo = userNricService.getUserNricDetail(id);
		bo.setData(userNricBo);
		return bo;
	}
	
	/**
	 * 
	* @Title: updateUserNric 
	* @param: 
	* @Description: 修改身份信息
	* @return BoUtil
	 */
	@ApiOperation(httpMethod = "PUT", value = "modify", notes = "modify")
	@ResponseBody
	@RequestMapping(value = "/modify", method = RequestMethod.PUT, produces = "application/json", consumes = "application/*")
	public BoUtil updateUserNric(final @RequestBody UserNricVo userNricVo) {
		BoUtil bo = BoUtil.getDefaultTrueBo();
		int result = userNricService.updateUserNric(userNricVo);
		if(result > 0){
			return bo;
		}else{
			bo = BoUtil.getDefaultFalseBo();
			bo.setMsg("update faild");
			return bo;
		}
	}
	
	/**
	 * 
	* @Title: downloadUserNricList 
	* @param: 
	* @Description: 导出报表
	* @return void
	 */
	@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadUserNricList() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			String fileName = "USER_NRIC_RECORD" + date + ".csv";
			String csvFilePath = "/tmp/";
			File file = userNricService.reportUserNric(csvFilePath,fileName);
			CSVUtils.exportFile(response, file.getPath(), file.getName());
			CSVUtils.deleteFile(file.getParent(), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

}
