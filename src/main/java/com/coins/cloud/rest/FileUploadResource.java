package com.coins.cloud.rest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coins.cloud.WristbandServiceApplication.FileUploadConfig;
import com.coins.cloud.util.ErrorCode;
import com.hlb.cloud.bo.BoUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
  * @ClassName: FileUploadResource
  * @Description: 文件上传
  * @author yaojie yao.jie@hadoop-tech.com
  * @Company hadoop-tech
  * @date 2019年4月1日 下午3:12:50
  *
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadResource {

	@Inject
	private FileUploadConfig fileUploadConfig;

	/**
	 * 
	 * @param 上传文件
	 * @return
	 */
	@RequestMapping(value = "/file", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public BoUtil uploadFile(@RequestParam("uploadFile") MultipartFile file) {
		log.info("###开始上传文件###");
		BoUtil boUtil = BoUtil.getDefaultFalseBo();
		if (file.isEmpty()) {
			boUtil.setMsg("请上传文件");
			return boUtil;
		}

		String folder = "/certificate/";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			String originalFileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 文件上传后的路径
			String filePath = fileUploadConfig.getBasepath() + folder + df.format(System.currentTimeMillis()) + "/";
			// 文件新名称
			String fileName = UUID.randomUUID() + suffixName;

			File dest = new File(filePath + fileName);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}

			file.transferTo(dest);
			String pathUrl = folder + df.format(System.currentTimeMillis()) + "/" + fileName;
			boUtil = BoUtil.getDefaultTrueBo();
			// 返回路径
			boUtil.setData(fileUploadConfig.getDomain()+pathUrl);
			boUtil.setMsg("上传成功");
			log.info("####上传成功:{}", pathUrl);
			return boUtil;
		} catch (IllegalStateException e) {
			boUtil.setMsg("上传失败");
			log.error("####上传失败:{}", e.getMessage());
			return boUtil;
		} catch (Exception e) {
			boUtil.setMsg("上传失败");
			log.error("####上传失败:{}", e.getMessage());
			return boUtil;
		}
	}
}
