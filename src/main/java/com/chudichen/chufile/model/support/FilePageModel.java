package com.chudichen.chufile.model.support;

import com.chudichen.chufile.model.dto.FileItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
@AllArgsConstructor
public class FilePageModel {

    private Integer totalPage;

    private List<FileItemDTO> fileList;
}
