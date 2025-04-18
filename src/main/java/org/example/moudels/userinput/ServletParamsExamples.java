package org.example.moudels.userinput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @description Servlet参数注入示例
 * @author: zpy
 * @Date: 2025/4/16
 */
@Slf4j
@Api(value = "ServletParamsExamples", tags = "Servlet参数注入示例")
@Controller
@RequestMapping("/servlet")
public class ServletParamsExamples {

    // 1. 获取请求方法
    @ApiOperation(value = "获取请求方法", notes = "使用request.getMethod()")
    @GetMapping("/method")
    @ResponseBody
    public R getMethod(HttpServletRequest request) {
        return R.ok("请求方法: " + request.getMethod());
    }

    // 2. 获取请求URL
    @ApiOperation(value = "获取请求URL", notes = "使用request.getRequestURL()")
    @GetMapping("/url")
    @ResponseBody
    public R getRequestURL(HttpServletRequest request) {
        return R.ok("请求URL: " + request.getRequestURL());
    }

    // 3. 获取请求URI
    @ApiOperation(value = "获取请求URI", notes = "使用request.getRequestURI()")
    @GetMapping("/uri")
    @ResponseBody
    public R getRequestURI(HttpServletRequest request) {
        return R.ok("请求URI: " + request.getRequestURI());
    }

    // 4. 获取请求协议
    @ApiOperation(value = "获取请求协议", notes = "使用request.getProtocol()")
    @GetMapping("/protocol")
    @ResponseBody
    public R getProtocol(HttpServletRequest request) {
        return R.ok("请求协议: " + request.getProtocol());
    }

    // 5. 获取请求参数
    @ApiOperation(value = "获取请求参数", notes = "使用request.getParameter()")
    @GetMapping("/parameter")
    @ResponseBody
    public R getParameter(HttpServletRequest request) {
        String username = request.getParameter("username");
        return R.ok("获取到的用户名: " + (username != null ? username : "未提供"));
    }

    // 6. 获取多个请求参数
    @ApiOperation(value = "获取多个请求参数", notes = "使用request.getParameterValues()")
    @GetMapping("/parameter-values")
    @ResponseBody
    public R getParameterValues(HttpServletRequest request) {
        String[] hobbies = request.getParameterValues("hobby");
        return R.ok("获取到的爱好: " + (hobbies != null ? String.join(", ", hobbies) : "未提供"));
    }

    // 7. 获取所有参数名
    @ApiOperation(value = "获取所有参数名", notes = "使用request.getParameterNames()")
    @GetMapping("/parameter-names")
    @ResponseBody
    public R getParameterNames(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            params.append(paramName).append("\n");
        }
        return R.ok("所有参数名:\n" + params);
    }

    // 8. 获取请求头
    @ApiOperation(value = "获取请求头", notes = "使用request.getHeader()")
    @GetMapping("/header")
    @ResponseBody
    public R getHeader(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return R.ok("User-Agent: " + (userAgent != null ? userAgent : "未提供"));
    }

    // 9. 获取所有请求头名
    @ApiOperation(value = "获取所有请求头名", notes = "使用request.getHeaderNames()")
    @GetMapping("/header-names")
    @ResponseBody
    public R getHeaderNames(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            headers.append(headerNames.nextElement()).append("\n");
        }
        return R.ok("所有请求头名:\n" + headers);
    }

    // 10. 获取请求头值
    @ApiOperation(value = "获取请求头值", notes = "使用request.getHeaders()")
    @GetMapping("/headers")
    @ResponseBody
    public R getHeaders(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        Enumeration<String> headerValues = request.getHeaders("Accept");
        while (headerValues.hasMoreElements()) {
            headers.append(headerValues.nextElement()).append("\n");
        }
        return R.ok("Accept头值:\n" + headers);
    }

    // 11. 获取请求属性
    @ApiOperation(value = "获取请求属性", notes = "使用request.getAttribute()")
    @GetMapping("/attribute")
    @ResponseBody
    public R getAttribute(HttpServletRequest request) {
        Object attribute = request.getAttribute("requestTime");
        return R.ok("请求属性: " + (attribute != null ? attribute : "未设置"));
    }

    // 12. 获取所有请求属性名
    @ApiOperation(value = "获取所有请求属性名", notes = "使用request.getAttributeNames()")
    @GetMapping("/attribute-names")
    @ResponseBody
    public R getAttributeNames(HttpServletRequest request) {
        StringBuilder attributes = new StringBuilder();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            attributes.append(attributeNames.nextElement()).append("\n");
        }
        return R.ok("所有请求属性名:\n" + attributes);
    }

    // 13. 获取Cookie
    @ApiOperation(value = "获取Cookie", notes = "使用request.getCookies()")
    @GetMapping("/cookies")
    @ResponseBody
    public R getCookies(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        StringBuilder cookieInfo = new StringBuilder();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                cookieInfo.append(cookie.getName()).append(": ").append(cookie.getValue()).append("\n");
            }
        }
        return R.ok("所有Cookie:\n" + cookieInfo);
    }

    // 14. 获取请求字符编码
    @ApiOperation(value = "获取请求字符编码", notes = "使用request.getCharacterEncoding()")
    @GetMapping("/character-encoding")
    @ResponseBody
    public R getCharacterEncoding(HttpServletRequest request) {
        return R.ok("字符编码: " + request.getCharacterEncoding());
    }

    // 15. 获取请求内容类型
    @ApiOperation(value = "获取请求内容类型", notes = "使用request.getContentType()")
    @GetMapping("/content-type")
    @ResponseBody
    public R getContentType(HttpServletRequest request) {
        return R.ok("内容类型: " + request.getContentType());
    }

    // 16. 获取请求内容长度
    @ApiOperation(value = "获取请求内容长度", notes = "使用request.getContentLength()")
    @GetMapping("/content-length")
    @ResponseBody
    public R getContentLength(HttpServletRequest request) {
        return R.ok("内容长度: " + request.getContentLength());
    }

    // 17. 获取请求本地化信息
    @ApiOperation(value = "获取请求本地化信息", notes = "使用request.getLocale()")
    @GetMapping("/locale")
    @ResponseBody
    public R getLocale(HttpServletRequest request) {
        return R.ok("本地化信息: " + request.getLocale());
    }

    // 18. 获取请求上下文路径
    @ApiOperation(value = "获取请求上下文路径", notes = "使用request.getContextPath()")
    @GetMapping("/context-path")
    @ResponseBody
    public R getContextPath(HttpServletRequest request) {
        return R.ok("上下文路径: " + request.getContextPath());
    }

    // 19. 获取请求查询字符串
    @ApiOperation(value = "获取请求查询字符串", notes = "使用request.getQueryString()")
    @GetMapping("/query-string")
    @ResponseBody
    public R getQueryString(HttpServletRequest request) {
        return R.ok("查询字符串: " + request.getQueryString());
    }

    // 20. 获取请求远程地址
    @ApiOperation(value = "获取请求远程地址", notes = "使用request.getRemoteAddr()")
    @GetMapping("/remote-addr")
    @ResponseBody
    public R getRemoteAddr(HttpServletRequest request) {
        return R.ok("远程地址: " + request.getRemoteAddr());
    }

    // 21. 获取请求远程主机
    @ApiOperation(value = "获取请求远程主机", notes = "使用request.getRemoteHost()")
    @GetMapping("/remote-host")
    @ResponseBody
    public R getRemoteHost(HttpServletRequest request) {
        return R.ok("远程主机: " + request.getRemoteHost());
    }

    // 22. 获取请求远程端口
    @ApiOperation(value = "获取请求远程端口", notes = "使用request.getRemotePort()")
    @GetMapping("/remote-port")
    @ResponseBody
    public R getRemotePort(HttpServletRequest request) {
        return R.ok("远程端口: " + request.getRemotePort());
    }

    // 23. 获取请求本地地址
    @ApiOperation(value = "获取请求本地地址", notes = "使用request.getLocalAddr()")
    @GetMapping("/local-addr")
    @ResponseBody
    public R getLocalAddr(HttpServletRequest request) {
        return R.ok("本地地址: " + request.getLocalAddr());
    }

    // 24. 获取请求本地主机
    @ApiOperation(value = "获取请求本地主机", notes = "使用request.getLocalName()")
    @GetMapping("/local-name")
    @ResponseBody
    public R getLocalName(HttpServletRequest request) {
        return R.ok("本地主机: " + request.getLocalName());
    }

    // 25. 获取请求本地端口
    @ApiOperation(value = "获取请求本地端口", notes = "使用request.getLocalPort()")
    @GetMapping("/local-port")
    @ResponseBody
    public R getLocalPort(HttpServletRequest request) {
        return R.ok("本地端口: " + request.getLocalPort());
    }

    // 26. 获取请求方案
    @ApiOperation(value = "获取请求方案", notes = "使用request.getScheme()")
    @GetMapping("/scheme")
    @ResponseBody
    public R getScheme(HttpServletRequest request) {
        return R.ok("请求方案: " + request.getScheme());
    }

    // 27. 获取请求服务器名称
    @ApiOperation(value = "获取请求服务器名称", notes = "使用request.getServerName()")
    @GetMapping("/server-name")
    @ResponseBody
    public R getServerName(HttpServletRequest request) {
        return R.ok("服务器名称: " + request.getServerName());
    }

    // 28. 获取请求服务器端口
    @ApiOperation(value = "获取请求服务器端口", notes = "使用request.getServerPort()")
    @GetMapping("/server-port")
    @ResponseBody
    public R getServerPort(HttpServletRequest request) {
        return R.ok("服务器端口: " + request.getServerPort());
    }

    // 29. 获取请求路径信息
    @ApiOperation(value = "获取请求路径信息", notes = "使用request.getPathInfo()")
    @GetMapping("/path-info")
    @ResponseBody
    public R getPathInfo(HttpServletRequest request) {
        return R.ok("路径信息: " + request.getPathInfo());
    }

    // 30. 获取请求路径翻译
    @ApiOperation(value = "获取请求路径翻译", notes = "使用request.getPathTranslated()")
    @GetMapping("/path-translated")
    @ResponseBody
    public R getPathTranslated(HttpServletRequest request) {
        return R.ok("路径翻译: " + request.getPathTranslated());
    }

    // 31. 获取请求Servlet路径
    @ApiOperation(value = "获取请求Servlet路径", notes = "使用request.getServletPath()")
    @GetMapping("/servlet-path")
    @ResponseBody
    public R getServletPath(HttpServletRequest request) {
        return R.ok("Servlet路径: " + request.getServletPath());
    }

    // 32. 获取请求认证类型
    @ApiOperation(value = "获取请求认证类型", notes = "使用request.getAuthType()")
    @GetMapping("/auth-type")
    @ResponseBody
    public R getAuthType(HttpServletRequest request) {
        return R.ok("认证类型: " + request.getAuthType());
    }

    // 33. 获取请求远程用户
    @ApiOperation(value = "获取请求远程用户", notes = "使用request.getRemoteUser()")
    @GetMapping("/remote-user")
    @ResponseBody
    public R getRemoteUser(HttpServletRequest request) {
        return R.ok("远程用户: " + request.getRemoteUser());
    }

    // 34. 获取请求用户主体
    @ApiOperation(value = "获取请求用户主体", notes = "使用request.getUserPrincipal()")
    @GetMapping("/user-principal")
    @ResponseBody
    public R getUserPrincipal(HttpServletRequest request) {
        return R.ok("用户主体: " + (request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "未认证"));
    }

    // 35. 获取请求是否安全
    @ApiOperation(value = "获取请求是否安全", notes = "使用request.isSecure()")
    @GetMapping("/is-secure")
    @ResponseBody
    public R isSecure(HttpServletRequest request) {
        return R.ok("是否安全: " + request.isSecure());
    }

    // 36. 获取请求是否异步
    @ApiOperation(value = "获取请求是否异步", notes = "使用request.isAsyncStarted()")
    @GetMapping("/is-async")
    @ResponseBody
    public R isAsyncStarted(HttpServletRequest request) {
        return R.ok("是否异步: " + request.isAsyncStarted());
    }

    // 37. 获取请求是否在SSL会话中
    @ApiOperation(value = "获取请求是否在SSL会话中", notes = "使用request.isRequestedSessionIdFromCookie()")
    @GetMapping("/is-ssl-session")
    @ResponseBody
    public R isRequestedSessionIdFromCookie(HttpServletRequest request) {
        return R.ok("是否在SSL会话中: " + request.isRequestedSessionIdFromCookie());
    }

    // 38. 获取请求是否在URL中
    @ApiOperation(value = "获取请求是否在URL中", notes = "使用request.isRequestedSessionIdFromURL()")
    @GetMapping("/is-url-session")
    @ResponseBody
    public R isRequestedSessionIdFromURL(HttpServletRequest request) {
        return R.ok("是否在URL中: " + request.isRequestedSessionIdFromURL());
    }

    // 39. 获取请求是否有效
    @ApiOperation(value = "获取请求是否有效", notes = "使用request.isRequestedSessionIdValid()")
    @GetMapping("/is-valid-session")
    @ResponseBody
    public R isRequestedSessionIdValid(HttpServletRequest request) {
        return R.ok("是否有效: " + request.isRequestedSessionIdValid());
    }

    // 40. 获取请求是否在Cookie中
    @ApiOperation(value = "获取请求是否在Cookie中", notes = "使用request.isRequestedSessionIdFromCookie()")
    @GetMapping("/is-cookie-session")
    @ResponseBody
    public R isRequestedSessionIdFromCookie2(HttpServletRequest request) {
        return R.ok("是否在Cookie中: " + request.isRequestedSessionIdFromCookie());
    }
} 