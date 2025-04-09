package org.example.moudels.rce.code;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 代码注入
 * @author: zpy
 * @Date: 2025/4/9 09:27
 */
@Slf4j
@Api(value="CodeController",tags = "RCE-代码注入")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/code")
public class CodeController {
    @RequestMapping("")
    public String code() {
        return "vul/rce/code";
    }
}
