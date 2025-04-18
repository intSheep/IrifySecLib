package org.example.moudels.userinput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @description Session参数注入示例
 * @author: zpy
 * @Date: 2025/4/16
 */
@Slf4j
@Api(value = "SessionExamples", tags = "Session参数注入示例")
@Controller
@RequestMapping("/session")
public class SessionExamples {

    // 1. 获取会话ID
    @ApiOperation(value = "获取会话ID", notes = "使用session.getId()")
    @GetMapping("/id")
    @ResponseBody
    public R getSessionId(HttpSession session) {
        return R.ok("会话ID: " + session.getId());
    }

    // 2. 获取会话创建时间
    @ApiOperation(value = "获取会话创建时间", notes = "使用session.getCreationTime()")
    @GetMapping("/creation-time")
    @ResponseBody
    public R getCreationTime(HttpSession session) {
        return R.ok("会话创建时间: " + session.getCreationTime());
    }

    // 3. 获取会话最后访问时间
    @ApiOperation(value = "获取会话最后访问时间", notes = "使用session.getLastAccessedTime()")
    @GetMapping("/last-accessed-time")
    @ResponseBody
    public R getLastAccessedTime(HttpSession session) {
        return R.ok("会话最后访问时间: " + session.getLastAccessedTime());
    }

    // 4. 获取会话最大不活动间隔
    @ApiOperation(value = "获取会话最大不活动间隔", notes = "使用session.getMaxInactiveInterval()")
    @GetMapping("/max-inactive-interval")
    @ResponseBody
    public R getMaxInactiveInterval(HttpSession session) {
        return R.ok("会话最大不活动间隔: " + session.getMaxInactiveInterval() + "秒");
    }

    // 5. 获取会话属性
    @ApiOperation(value = "获取会话属性", notes = "使用session.getAttribute()")
    @GetMapping("/attribute")
    @ResponseBody
    public R getSessionAttribute(HttpSession session) {
        Object attribute = session.getAttribute("user");
        return R.ok("会话属性: " + (attribute != null ? attribute : "未设置"));
    }

    // 6. 获取所有会话属性名
    @ApiOperation(value = "获取所有会话属性名", notes = "使用session.getAttributeNames()")
    @GetMapping("/attribute-names")
    @ResponseBody
    public R getSessionAttributeNames(HttpSession session) {
        StringBuilder attributes = new StringBuilder();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            attributes.append(attributeNames.nextElement()).append("\n");
        }
        return R.ok("所有会话属性名:\n" + attributes);
    }

    // 7. 检查会话是否为新会话
    @ApiOperation(value = "检查会话是否为新会话", notes = "使用session.isNew()")
    @GetMapping("/is-new")
    @ResponseBody
    public R isNew(HttpSession session) {
        return R.ok("是否为新会话: " + session.isNew());
    }

    // 8. 获取会话Servlet上下文
    @ApiOperation(value = "获取会话Servlet上下文", notes = "使用session.getServletContext()")
    @GetMapping("/servlet-context")
    @ResponseBody
    public R getServletContext(HttpSession session) {
        return R.ok("Servlet上下文: " + session.getServletContext().getServerInfo());
    }

    // 9. 设置会话属性
    @ApiOperation(value = "设置会话属性", notes = "使用session.setAttribute()")
    @GetMapping("/set-attribute")
    @ResponseBody
    public R setAttribute(HttpSession session) {
        session.setAttribute("testKey", "testValue");
        return R.ok("已设置会话属性: testKey = testValue");
    }

    // 10. 移除会话属性
    @ApiOperation(value = "移除会话属性", notes = "使用session.removeAttribute()")
    @GetMapping("/remove-attribute")
    @ResponseBody
    public R removeAttribute(HttpSession session) {
        session.removeAttribute("testKey");
        return R.ok("已移除会话属性: testKey");
    }

    // 11. 使会话失效
    @ApiOperation(value = "使会话失效", notes = "使用session.invalidate()")
    @GetMapping("/invalidate")
    @ResponseBody
    public R invalidate(HttpSession session) {
        session.invalidate();
        return R.ok("会话已失效");
    }

    // 12. 设置会话最大不活动间隔
    @ApiOperation(value = "设置会话最大不活动间隔", notes = "使用session.setMaxInactiveInterval()")
    @GetMapping("/set-max-inactive-interval")
    @ResponseBody
    public R setMaxInactiveInterval(HttpSession session) {
        session.setMaxInactiveInterval(1800); // 30分钟
        return R.ok("已设置会话最大不活动间隔为30分钟");
    }
} 