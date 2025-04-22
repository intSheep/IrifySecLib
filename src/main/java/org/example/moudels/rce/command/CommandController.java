package org.example.moudels.rce.command;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description 漏洞场景:命令注入
 * @author: zpy
 * @Date: 2025/4/16
 */
@Slf4j
@Api(value="CommandController",tags = "RCE-远程命令执行")
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/command")
public class CommandController {
    @RequestMapping("")
    public String spel() {
        return "vul/rce/command";
    }

    @RequestMapping("/ProcessBuilder1")
    @ResponseBody
    @ApiOperation(value = "漏洞场景：命令注入-ProcessBuilder1",notes = "命令注入")
    @ApiImplicitParam(name = "payload", value = "命令", required = true, dataType = "String", paramType = "query")
   public R procesBuilder1(@RequestParam("payload") String payload) throws IOException {
        String[] command ={"sh","-c",payload};
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        Process process = pb.start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return R.ok(output.toString());
   }

    @RequestMapping("/ProcessBuilder2")
    @ResponseBody
    @ApiOperation(value = "漏洞场景：命令注入-ProcessBuilder2",notes = "命令注入")
    @ApiImplicitParam(name = "payload", value = "命令", required = true, dataType = "String", paramType = "query")
    public R procesBuilder2(@RequestParam("payload") String payload) throws IOException {
          String[] command = {"sh", "-c", payload};

          ProcessBuilder pb = new ProcessBuilder();
          pb.redirectErrorStream(true);
          pb.command(command);
          Process process = pb.start();
          InputStream inputStream = process.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
          String line;
          StringBuilder output = new StringBuilder();
          while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
          }
          return R.ok(output.toString());
    }

    @RequestMapping("/RuntimeExec")
    @ResponseBody
    @ApiOperation(value = "漏洞场景：命令注入-RuntimeExec",notes = "命令注入")
    @ApiImplicitParam(name = "payload", value = "命令", required = true, dataType = "String", paramType = "query")
    public R runtimeExec(String payload) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        Process proc = Runtime.getRuntime().exec(payload);
        InputStream inputStream = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return R.ok(sb.toString());
    }

    @RequestMapping("/ProcessImpl")
    @ResponseBody
    @ApiOperation(value = "漏洞场景：命令注入-ProcessImpl",notes = "命令注入")
    @ApiImplicitParam(name = "payload", value = "命令", required = true, dataType = "String", paramType = "query")
    public R vul3(String payload) throws Exception {
        // 获取 ProcessImpl 类对象
        Class<?> clazz = Class.forName("java.lang.ProcessImpl");

        // 获取 start 方法
        Method method = clazz.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        method.setAccessible(true);

        Process process = (Process) method.invoke(null, new String[]{payload}, null, null, null, false);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return R.ok(output.toString());
        }
    }

    // 可执行命令白名单
    private static final List<String> ALLOWED_COMMANDS = Arrays.asList("ls", "date");


    @RequestMapping("/safe")
    @ResponseBody
    @ApiOperation( value = "安全代码：命令注入",notes = "命令注入")
    @ApiImplicitParam(name = "payload", value = "命令", required = true, dataType = "String", paramType = "query")
    public R safe(@RequestParam("payload") String payload) throws IOException {
        // 验证命令是否在允许的列表中
        if (!ALLOWED_COMMANDS.contains(payload)) {
            return R.error("不允许执行该命令！");
        }
        String[] cmdArray = { "sh", "-c", payload };
        ProcessBuilder pb = new ProcessBuilder(cmdArray);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        return R.ok(output.toString());
    }
}

