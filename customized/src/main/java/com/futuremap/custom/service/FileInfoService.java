/**
 * 
 */
package com.futuremap.custom.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.futuremap.custom.entity.file.FileInfo;


/**
 */
@Service
public interface FileInfoService extends IService<FileInfo>{

	/**
	 * @param fileInfo
	 */
	void addFileInfo(FileInfo fileInfo);




}