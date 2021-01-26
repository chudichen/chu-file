package com.chudichen.chufile.controller.home;

import com.chudichen.chufile.context.DriveContext;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import com.chudichen.chufile.service.base.AbstractBaseFileService;
import com.chudichen.chufile.service.impl.LocalServiceImpl;
import com.chudichen.chufile.util.FileUtil;
import com.chudichen.chufile.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 本地存储的controller
 *
 * @author chudichen
 * @date 2021-01-20
 */
@Controller
public class LocalController {

    private final DriveContext driveContext;

    public LocalController(DriveContext driveContext) {
        this.driveContext = driveContext;
    }

    /**
     * 本地存储下载指定文件
     *
     * @param driveId 驱动器id
     * @param request 请求
     * @return 文件
     */
    @ResponseBody
    @GetMapping("/file/{driveId}/**")
    public ResponseEntity<Object> downAttachment(@PathVariable("driveId") Integer driveId, final HttpServletRequest request) {
        String path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        String bestMatchPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        AntPathMatcher apm = new AntPathMatcher();
        String filePath = apm.extractPathWithinPattern(bestMatchPattern, path);
        LocalServiceImpl localService = (LocalServiceImpl) driveContext.get(driveId);
        return FileUtil.export(new File(StringUtils.removeDuplicateSeparator(localService.getFilePath() + ChuFileConstant.PATH_SEPARATOR + filePath)));
    }
}
