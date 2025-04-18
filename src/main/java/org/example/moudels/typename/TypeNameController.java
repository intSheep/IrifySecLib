package org.example.moudels.typename;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.*;
import org.springframework.web.cors.*;

/**
 * @description Irify TypeName用例
 * @author: zpy
 * @Date: 2025/4/14
 */
@Slf4j
@Api(value = "TypeNameController",tags = "Irify TypeName用例")
@Controller
@RequestMapping("/typename")
public class TypeNameController {

    @GetMapping("/simple")
    public ResponseEntity<Object> simple(@RequestParam(name = "id") String id) {
        Object anyJSON = JSON.parse(id);
        return ResponseEntity.ok(anyJSON);
    }

    @GetMapping("/importAll")
    public void importAll(@ApiParam(name="url",value="请求参数",required = true)@RequestParam String url){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(url);
    }
}
