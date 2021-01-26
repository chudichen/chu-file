package com.chudichen.chufile.controller.onedrive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chudichen
 * @date 2021-01-26
 */
@Controller
@RequestMapping("/onedrive")
public class OneDriveCallbackController {

    @GetMapping("/callback")
    public String oneDriveCallback() {
        return "callback";
    }

    @GetMapping("/china-callback")
    public String oneDriveChinaCallback() {
        return "callback";
    }
}
