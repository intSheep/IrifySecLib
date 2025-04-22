package org.example.common.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description 系统配置
 * @author: zpy
 * @Date: 2025/4/21
 */
@Component
@Data
public class SysConstant {
    @Value("/tmp/upload")
    private String uploadFolder;

    @Value("${folder.static:/tmp/static}")
    private String staticFolder;

}
