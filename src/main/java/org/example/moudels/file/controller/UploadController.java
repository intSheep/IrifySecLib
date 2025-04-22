package org.example.moudels.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.example.common.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 漏洞场景：文件上传
 * @author: zpy
 * @Date: 2025/4/21
 */
@Slf4j
@Api(value = "UploadController",tags = "文件上传")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/upload")
public class UploadController {
    @RequestMapping("")
    public String fileUpload() {
        return "vul/file/upload";
    }

    @Autowired
    private UploadUtil uploadUtil;


    @ApiOperation(value="漏洞场景：任意文件上传", notes = "原生漏洞场景，未做任何限制")
    @RequestMapping("/vul")
    @ResponseBody
    @SneakyThrows
    public R vul(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String res;
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/";
        res =uploadUtil.uploadFile(file, suffix, path);
        return R.ok(res);
    }

    @ApiOperation(value="安全代码：文件上传白名单", notes = "检测文件后缀，做白名单过滤")
    @RequestMapping("/safe")
    @ResponseBody
    @SneakyThrows
    public R safe(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String res;
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        // 后缀白名单检查
        if (!uploadUtil.checkFileSuffixWhiteList(suffix)){
            return R.error("文件后缀不合法");
        }
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file/";
        res =uploadUtil.uploadFile(file, suffix, path);
        return R.ok(res);
    }

}
