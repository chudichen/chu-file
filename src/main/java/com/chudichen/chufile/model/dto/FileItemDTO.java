package com.chudichen.chufile.model.dto;

import com.chudichen.chufile.model.enums.FileTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件对象
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Data
public class FileItemDTO implements Serializable {

    private String name;
    private Date time;
    private Long size;
    private FileTypeEnum type;
    private String path;
    private String url;
}
