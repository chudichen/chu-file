package com.chudichen.chufile.util;

import com.chudichen.chufile.model.dto.FileItemDTO;
import com.chudichen.chufile.model.enums.FileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Comparator;

/**
 * 文件比较器
 * <p>
 * 文件夹始终比文件排序高
 * 默认按照名称排序
 * 默认排序为牲畜
 * 按名称排序不区分大小写
 *
 * @author chudichen
 * @date 2021-01-22
 */
@NoArgsConstructor
@AllArgsConstructor
public class FileComparator implements Comparator<FileItemDTO> {

    private String sortBy;
    private String order;

    @Override
    public int compare(FileItemDTO o1, FileItemDTO o2) {
        if (sortBy == null) {
            sortBy = "name";
        }

        if (order == null) {
            order = "asc";
        }

        FileTypeEnum o1Type = o1.getType();
        FileTypeEnum o2Type = o2.getType();
        if (o1Type.equals(o2Type)) {
            int result;
            switch (sortBy) {
                case "time":
                    result = o1.getTime().compareTo(o2.getTime());
                    break;
                case "size":
                    result = o1.getSize().compareTo(o2.getSize());
                    break;
                default:
                    result = new NaturalOrderComparator().compare(o1.getName(), o2.getName());
            }
            return "asc".equals(order) ? result : -result;
        }

        return o1Type.equals(FileTypeEnum.FOLDER) ? -1 : 1;
    }
}
