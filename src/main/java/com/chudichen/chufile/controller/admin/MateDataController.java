package com.chudichen.chufile.controller.admin;

import com.alibaba.fastjson.JSON;
import com.chudichen.chufile.context.StorageStrategyContext;
import com.chudichen.chufile.model.entity.StorageConfig;
import com.chudichen.chufile.model.enums.StorageStrategyEnum;
import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统元数据Controller
 *
 * @author chudichen
 * @date 2021-01-28
 */
@RestController
@RequestMapping("/admin")
public class MateDataController {

    /**
     * 返回支持的存储引擎
     *
     * @return 存储引擎
     */
    @GetMapping("/support-strategy")
    public ResultBean supportStrategy() {
        StorageStrategyEnum[] values = StorageStrategyEnum.values();
        return ResultBean.successData(values);
    }

    /**
     * 获取指定存储策略的表单域
     *
     * @param   storageStrategy 存储策略
     * @return  所有表单域
     */
    @GetMapping("/strategy-form")
    public ResultBean getFormByStorageType(StorageStrategyEnum storageType) {
        AbstractBaseFileService storageTypeService = StorageStrategyContext.getStorageStrategyService(storageType);
        List<StorageConfig> storageConfigList = storageTypeService.storageStrategyConfigList();
        return ResultBean.success(storageConfigList);
    }
}
