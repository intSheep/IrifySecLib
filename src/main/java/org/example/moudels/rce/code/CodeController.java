package org.example.moudels.rce.code;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping("/vulGroovy-evaluate")
    @ResponseBody
    @ApiOperation(value = "漏洞常见：RCE-Groovy(使用evaluate方法)",notes = "Groovy代码执行")
    @ApiImplicitParam(name = "payload", value = "Groovy代码", required = true, dataType = "String", paramType = "query")
    public R vulGroovyEvaluate(@ApiParam(name = "payload") String payload) {
        try {
            GroovyShell shell = new GroovyShell();
            Object result = shell.evaluate(payload);
            if (result instanceof Process) {
                Process process = (Process) result;
                String output = getProcessOutput(process);
                return R.ok("[+] Groovy代码执行，结果：" + output);
            } else {
                return R.ok("[+] Groovy代码执行，结果：" + result.toString());
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }

    @GetMapping("/vulGroovy-parse")
    @ResponseBody
    @ApiOperation(value = "漏洞常见：RCE-Groovy(使用parse方法)",notes = "Groovy代码执行")
    public R vulGroovyParse(@ApiParam(name = "payload") String payload) {
        try {
            GroovyShell shell = new GroovyShell();
            Script script = shell.parse(payload);
            Object result = script.run();
            if (result instanceof Process) {
                Process process = (Process) result;
                String output = getProcessOutput(process);
                return R.ok("[+] Groovy代码执行，结果：" + output);
            } else {
                return R.ok("[+] Groovy代码执行，结果：" + result.toString());
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }

    @GetMapping("/vulGroovy-run")
    @ResponseBody
    @ApiOperation(value = "漏洞常见：RCE-Groovy(使用run方法)",notes = "Groovy代码执行")
    public R vulGroovyRun(@ApiParam(name = "payload") String payload) {
        try {
            GroovyShell shell = new GroovyShell();
            Object result = shell.run(payload, "script.groovy", Arrays.asList());
            if (result instanceof Process) {
                Process process = (Process) result;
                String output = getProcessOutput(process);
                return R.ok("[+] Groovy代码执行，结果：" + output);
            } else {
                return R.ok("[+] Groovy代码执行，结果：" + (result != null ? result.toString() : "null"));
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }

    private String getProcessOutput(Process process) {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (Exception e) {
            return "读取输出失败: " + e.getMessage();
        }
        return output.toString();
    }

    @GetMapping("/safeGroovy")
    @ResponseBody
    @ApiOperation(value = "安全代码：Groovy代码执行",notes = "Groovy代码执行")
    @ApiImplicitParam(name = "payload", value = "Groovy代码", required = true, dataType = "String", paramType = "query")
    public R safeGroovy(@ApiParam(name = "payload") String payload) {
       List<String> trustedList = Arrays.asList(
                "\"id\".execute()",
                "\"ls\".execute()",
                "\"whoami\".execute()"
        );
        if (!isTrustedScript(payload, trustedList)) {
            return R.error("非法的脚本输入！");
        }
        try {
            GroovyShell shell = new GroovyShell();
            Object result = shell.evaluate(payload);
            if (result instanceof Process) {
                Process process = (Process) result;
                String output = getProcessOutput(process);
                return R.ok("[+] Groovy代码执行，结果：" + output);
            } else {
                return R.ok("[+] Groovy代码执行，结果：" + result.toString());
            }
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }
    private boolean isTrustedScript(String script, List<String> trustedScripts) {
        return trustedScripts.contains(script);
    }
}
