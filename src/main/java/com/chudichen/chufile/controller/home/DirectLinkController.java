package com.chudichen.chufile.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


/**
 * 直接地址
 *
 * @author chudichen
 * @date 2021-01-20
 */
@Controller
public class DirectLinkController {

    @GetMapping("/direct_link/{driveId}/**")
    public String directLink(@PathVariable("driveId") Integer driveId, final HttpServletRequest request) {
        return "";
    }
}
