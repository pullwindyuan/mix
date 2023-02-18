package com.futuremap.custom.controller;
/**
 * @author futuremap
 *
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.futuremap.custom.annotation.CustomResponse;
import com.futuremap.custom.dto.file.FileSummary;
import com.futuremap.custom.exception.BadRequestException;
import com.futuremap.custom.util.FileUtil;
import com.futuremap.custom.util.MD5Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


/**
 * @author
 */
@RestController()
@Slf4j
@Api(tags = "分片上传\\下载相关服务", description = "分片上传下载相关服务")
public class UploadController {
	
	
    @Value("${prop.upload-folder}")
    private String uploadFolder;

    @Value("${prop.download-url}")
    private String downloadUrl;
    
    @PostMapping("/upload")
    @CustomResponse
    @ApiOperation("直接上传文件")
	public FileSummary uploadChunk(@Valid
			@ApiParam(name = "file", value = "文件", required = true) MultipartFile file
	) {
		log.info("file originName: {}", file.getOriginalFilename());
		FileSummary fileSummary = new FileSummary();
		fileSummary.setFname(file.getOriginalFilename());
		fileSummary.setFsize(file.getSize());
		
		try {
			String md5String = FileUtil.getMD5(file);
			String time = String.valueOf(System.currentTimeMillis());
			String pathString  = md5String + time;
			pathString = MD5Utils.stringToMD5(pathString);
			fileSummary.setKey(pathString);
			fileSummary.setHash(pathString);
			fileSummary.setDownloadUrl(downloadUrl + pathString +"/"+ file.getOriginalFilename());
			byte[] bytes = file.getBytes();
			Path path = Paths.get(FileUtil.generatePath(uploadFolder, file, pathString));
			// 文件写入指定路径
			Files.write(path, bytes);
		} catch (IOException e) {
			log.error("文件导入后端异常",e);
			throw new BadRequestException("文件导入后端异常");
		}
		return fileSummary;
	}
    
    
    
}
