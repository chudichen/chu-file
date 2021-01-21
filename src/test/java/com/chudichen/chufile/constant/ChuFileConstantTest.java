package com.chudichen.chufile.constant;

import com.chudichen.chufile.BaseTest;
import com.chudichen.chufile.model.constant.ChuFileConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @author chudichen
 * @date 2021-01-21
 */
@Slf4j
public class ChuFileConstantTest extends BaseTest {

    @Test
    public void getValueTest() {
        Long audioMaxFileSizeMb = ChuFileConstant.AUDIO_MAX_FILE_SIZE_MB;
        log.info("audioMaxFileSizeMb: {}", audioMaxFileSizeMb);
        Assert.notNull(audioMaxFileSizeMb, "不能为空");
    }
}
