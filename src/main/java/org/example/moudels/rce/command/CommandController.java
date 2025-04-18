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
}
