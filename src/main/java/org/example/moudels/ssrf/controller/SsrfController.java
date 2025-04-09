package org.example.moudels.ssrf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.CheckUserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description SSRF-请求伪造漏洞
 * @author: zpy
 * @Date: 2025/4/6 20:40
 */
@Slf4j
@Api(value = "SsrfController",tags = "SSRF-服务端请求伪造")
@Controller
@CrossOrigin("*")
@RequestMapping("/ssrf")
public class SsrfController {
    @RequestMapping("")
    public String fileUpload(){
        return "vul/ssrf/ssrf";
    }

    @ApiOperation(value="漏洞场景：服务端请求伪造",notes="原生漏洞场景，未做任何限制，可调用URLConnection发起任意请求，探测内网服务、读取文件")
    @GetMapping("/vul")
    @ResponseBody()
    @ApiImplicitParam(name="url",value="请求参数",dataType = "String",paramType = "query",dataTypeClass = String.class)
    public String   vul(@ApiParam(name="url",value="请求参数",required = true)@RequestParam String url){
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String content;
            StringBuilder html =new StringBuilder();
            html.append("<pre>");
            while ((content = bufferedReader.readLine())!=null){
                html.append(content).append("\n");
            }
            html.append("</pre>");
            bufferedReader.close();
            return html.toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @ApiOperation(value="安全代码：请求白名单过滤",notes = "判断协议，对url白名单进行过滤")
    @GetMapping("/safe")
    @ResponseBody()
    @ApiImplicitParam(name="url",value="请求参数",dataType = "String",paramType = "query",dataTypeClass = String.class)
    public String safe(@ApiParam(name="url",value="请求参数",required = true)@RequestParam String url){
        if (!checkUserInput.isHttp(url)){
            return "<h1>检测到不是http协议</h1>";
        }else if (!checkUserInput.ssrfWhiteList(url)){
            return "<h1>检测到不是白名单</h1>";
        }else {
            try {
                URL u = new URL(url);
                URLConnection conn = u.openConnection();    // 这里以URLConnection作为演示
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String content;
                StringBuilder html = new StringBuilder();
                html.append("<pre>");
                while ((content = reader.readLine()) != null) {
                    html.append(content).append("\n");
                }
                html.append("</pre>");
                reader.close();
                return html.toString();
            }catch (Exception e){
                return e.getMessage();
            }
        }

    }
    @Autowired
    private CheckUserInput checkUserInput;
}
