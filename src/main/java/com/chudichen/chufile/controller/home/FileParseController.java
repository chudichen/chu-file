package com.chudichen.chufile.controller.home;

import com.chudichen.chufile.model.support.ResultBean;
import com.chudichen.chufile.util.AudioUtil;
import com.chudichen.chufile.util.HttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件解析
 *
 * @author chudichen
 * @date 2021-01-26
 */
@RestController
@RequestMapping("/common")
public class FileParseController {

    /**
     * 获取文件内容，仅限用于txt，md，ini等普通文件
     *
     * @param url 文件路径
     * @return 文件内容
     */
    @GetMapping("/content")
    public ResultBean getContent(String url) {
        return ResultBean.successData(HttpUtil.getTextContent(url));
    }

    /**
     * 获取音频文件信息
     *
     * @param url 文件url
     * @return 音频信息, 标题封面等信息
     * @throws Exception 异常
     */
    @GetMapping("/audio-info")
    public ResultBean getAudioInfo(String url) throws Exception {
        return ResultBean.success(AudioUtil.getAudioInfo(url));
    }
}
