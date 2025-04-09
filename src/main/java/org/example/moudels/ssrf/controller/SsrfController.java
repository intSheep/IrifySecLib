package org.example.moudels.ssrf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.CheckUserInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URI;

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

    @ApiOperation(value="漏洞场景：RestTemplate delete SSRF",notes="使用RestTemplate的delete方法发起请求，未做任何限制，可删除内网资源")
    @GetMapping("/vul/delete")
    @ResponseBody()
    @ApiImplicitParam(name="url",value="请求参数",dataType = "String",paramType = "query",dataTypeClass = String.class)
    public String vulDelete(@ApiParam(name="url",value="请求参数",required = true)@RequestParam String url){
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(url);
            return "DELETE request sent successfully to: " + url;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value="漏洞场景：RestTemplate exchange SSRF",notes="使用RestTemplate的exchange方法发起请求，支持自定义请求方法和请求头，未做任何限制")
    @GetMapping("/vul/exchange")
    @ResponseBody()
    @ApiImplicitParam(name="url",value="请求参数",dataType = "String",paramType = "query",dataTypeClass = String.class)
    public String vulExchange(
            @ApiParam(name="url",value="请求参数",required = true)@RequestParam String url,
            @ApiParam(name="method",value="请求方法(GET,POST,PUT,DELETE等)",required = true)@RequestParam String method){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("X-Forwarded-For", "127.0.0.1");
            HttpEntity<String> entity = new HttpEntity<>("{\"test\":\"data\"}", headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                new URI(url),
                HttpMethod.valueOf(method),
                entity,
                String.class
            );
            
            return "Response Status: " + response.getStatusCode() + "\n" +
                   "Response Headers: " + response.getHeaders() + "\n" +
                   "Response Body: " + response.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value="漏洞场景：RestTemplate getForEntity with variables SSRF",notes="使用RestTemplate的getForEntity方法发起请求，支持URI变量，未做任何限制")
    @GetMapping("/vul/getForEntity")
    @ResponseBody()
    @ApiImplicitParam(name="url",value="请求参数，支持{0}、{1}等占位符",dataType = "String",paramType = "query",dataTypeClass = String.class)
    public String vulGetForEntity(
            @ApiParam(name="url",value="请求参数，支持{0}、{1}等占位符",required = true)@RequestParam String url,
            @ApiParam(name="var1",value="URI变量1",required = false)@RequestParam(required = false) String var1,
            @ApiParam(name="var2",value="URI变量2",required = false)@RequestParam(required = false) String var2){
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            
            if (var1 != null && var2 != null) {
                response = restTemplate.getForEntity(url, String.class, var1, var2);
            } else if (var1 != null) {
                response = restTemplate.getForEntity(url, String.class, var1);
            } else {
                response = restTemplate.getForEntity(url, String.class);
            }
            
            return "Response Status: " + response.getStatusCode() + "\n" +
                   "Response Headers: " + response.getHeaders() + "\n" +
                   "Response Body: " + response.getBody();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Autowired
    private CheckUserInput checkUserInput;
}
