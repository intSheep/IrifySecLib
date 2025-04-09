package org.example.common.util;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @description 检测用户输入
 * @author: zpy
 * @Date: 2025/4/9 09:11
 */
@Component
public class CheckUserInput {

    public boolean isHttp(String url){
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public boolean ssrfWhiteList(String url){
        List<String> urlList =new ArrayList<>(Arrays.asList("baidu.com"));
        try{
            URI uri =new URI(url.toLowerCase());
            String host = uri.getHost();
            return urlList.contains(host);
        }catch (URISyntaxException  e){
            System.out.println(e);
            return false;
        }
    }
}
