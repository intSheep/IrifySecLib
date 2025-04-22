package org.example.moudels.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.constant.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description 漏洞场景：任意文件读取
 * @author: zpy
 * @Date: 2025/4/21
 */
@Slf4j
@Api
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/file/read")
public class ReadController {
    @RequestMapping("")
    public String fileRead() {
        return "vul/file/read";
    }

    @ApiOperation(value = "漏洞场景：任意文件读取", notes = "原生漏洞场景，未做任何限制")
    @RequestMapping("/vul")
    @ResponseBody
    public String vul(String filePath) {
        try {
            // 读取文件内容
            Path path = new File(filePath).toPath();
            StringBuilder content = new StringBuilder();
            Files.lines(path).forEach(line -> content.append(line).append("\n"));
            return "<pre>" + content.toString() + "</pre>";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Autowired
    private SysConstant sysConstant;

    @ApiOperation(value = "安全代码：文件读取白名单", notes = "检测文件路径，做白名单过滤")
    @RequestMapping("/safe")
    @ResponseBody
    public String safe(@RequestParam("fileName") String fileName) throws IOException {
        String baseDir = sysConstant.getUploadFolder();
        Path filePath = Paths.get(baseDir, fileName).normalize();
        // 确保文件路径在允许的目录中
        if (!filePath.startsWith(Paths.get(baseDir))) {
            return "访问被拒绝：文件路径不合法";
        }
        File file = filePath.toFile();
        if (file.exists() && file.isFile()) {
            return new String(Files.readAllBytes(file.toPath()));
        } else {
            return "文件不存在或路径不正确：" + fileName;
        }
    }
}
