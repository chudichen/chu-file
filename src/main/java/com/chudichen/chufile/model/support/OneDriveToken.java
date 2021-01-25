package com.chudichen.chufile.model.support;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
public class OneDriveToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "refresh_token")
    private String refreshToken;
}
