package org.example.moudels.userinput;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * @description Spring MVC参数绑定全面示例
 * @author: zpy
 * @Date: 2025/4/16
 */
@Slf4j
@Api(value = "ParameterBindingExamples", tags = "Spring MVC参数绑定示例")
@Controller
@RequestMapping("/param-binding")
public class ParameterBindingExamples {

    // 1. @RequestParam基本用法
    @ApiOperation(value = "RequestParam基本用法", notes = "从请求参数中获取值")
    @GetMapping("/request-param/basic")
    @ResponseBody
    public R requestParamBasic(@RequestParam String username) {
        return R.ok("获取到的用户名: " + username);
    }

    // 2. @PathVariable基本用法
    @ApiOperation(value = "PathVariable基本用法", notes = "从URL路径中获取变量")
    @GetMapping("/path-variable/{id}")
    @ResponseBody
    public R pathVariableBasic(@PathVariable Integer id) {
        return R.ok("获取到的ID: " + id);
    }

    // 3. @RequestHeader基本用法
    @ApiOperation(value = "RequestHeader基本用法", notes = "获取请求头")
    @GetMapping("/request-header/basic")
    @ResponseBody
    public R requestHeaderBasic(@RequestHeader("User-Agent") String userAgent) {
        return R.ok("获取到的User-Agent: " + userAgent);
    }

    // 4. @CookieValue基本用法
    @ApiOperation(value = "CookieValue基本用法", notes = "获取Cookie值")
    @GetMapping("/cookie-value/basic")
    @ResponseBody
    public R cookieValueBasic(@CookieValue(value = "sessionId", required = false) String sessionId) {
        return R.ok("获取到的sessionId: " + (sessionId != null ? sessionId : "未提供"));
    }

    // 5. @RequestBody基本用法
    @ApiOperation(value = "RequestBody基本用法", notes = "获取JSON请求体并绑定到对象")
    @PostMapping("/request-body/basic")
    @ResponseBody
    public R requestBodyBasic(@RequestBody User user) {
        return R.ok("获取到的用户: " + user);
    }

    // 6. @RequestPart基本用法
    @ApiOperation(value = "RequestPart基本用法", notes = "处理文件上传")
    @PostMapping("/request-part")
    @ResponseBody
    public R requestPart(@RequestPart("file") MultipartFile file) {
        return R.ok("上传的文件: " + (file != null ? file.getOriginalFilename() : "未上传"));
    }

    // 7. @MatrixVariable基本用法
    @ApiOperation(value = "MatrixVariable基本用法", notes = "获取URL路径中的矩阵变量")
    @GetMapping("/matrix/{path}")
    @ResponseBody
    public R matrixVariable(@MatrixVariable(value = "param", required = false) String param) {
        return R.ok("矩阵变量param: " + (param != null ? param : "未提供"));
    }

    // 8. @SessionAttribute基本用法
    @ApiOperation(value = "SessionAttribute基本用法", notes = "获取会话属性")
    @GetMapping("/session-attribute")
    @ResponseBody
    public R sessionAttribute(@SessionAttribute(value = "userProfile", required = false) User user) {
        return R.ok("会话用户: " + (user != null ? user : "未登录"));
    }

    // 9. @RequestAttribute基本用法
    @ApiOperation(value = "RequestAttribute基本用法", notes = "获取请求属性")
    @GetMapping("/request-attribute")
    @ResponseBody
    public R requestAttribute(@RequestAttribute(value = "startTime", required = false) Long startTime) {
        return R.ok("请求开始时间: " + (startTime != null ? startTime : "未设置"));
    }

    // 10. @ModelAttribute基本用法
    @ApiOperation(value = "ModelAttribute基本用法", notes = "表单数据绑定到对象")
    @PostMapping("/model-attribute")
    @ResponseBody
    public R modelAttribute(@ModelAttribute User user) {
        return R.ok("通过模型属性获取的用户: " + user);
    }

    // 用户类用于请求绑定示例
    public static class User {
        private String username;
        private String email;
        private Integer age;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
} 