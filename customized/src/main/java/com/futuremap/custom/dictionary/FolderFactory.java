package com.futuremap.custom.dictionary;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 使用分组字典来操作文件夹、分组、分类、目录、菜单树等的工厂类
 * @author dell
 */
@Data
@Accessors(chain = true)
public class FolderFactory implements Serializable {
    private static final long serialVersionUID = 1L;
}
