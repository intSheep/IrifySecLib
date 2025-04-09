package org.example.moudels.rce.code;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 代码注入
 * @author: zpy
 * @Date: 2025/4/9 09:27
 */
@Slf4j
@Api(value="CodeController",tags = "RCE-远程命令执行")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/code")
public class CodeController {
    @RequestMapping("")
    public String code() {
        return "vul/rce/code";
    }

    @GetMapping("/vulGroovy")
    @ResponseBody
    @ApiOperation(value = "漏洞常见：RCE-Groovy",notes = "")
    public R vulGroovy() {

    }

}
