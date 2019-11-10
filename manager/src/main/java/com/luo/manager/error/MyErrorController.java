package com.luo.manager.error;

//import org.springframework.boot.autoconfigure.web.BasicErrorController;

//import org.springframework.boot.autoconfigure.web.ErrorAttributes;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//import org.springframework.boot.autoconfigure.web.ErrorViewResolver;

/**
 * 自定义的错无处理:第一种方式
 * boot2:BasicErrorController,ErrorAttributes需要重更新导包
 */
public class MyErrorController extends BasicErrorController {
    //ErrorProperties cannot autowire:disable this inspection
    public MyErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> attrs = super.getErrorAttributes(request, includeStackTrace);
        attrs.remove("timestamp");
        attrs.remove("status");
        attrs.remove("error");
        attrs.remove("exception");
        attrs.remove("path");
        String errorCode = (String) attrs.get("message");
        ErrorEnum errorEnum = ErrorEnum.getByCode(errorCode);
        attrs.put("message", errorEnum.getDesc());
        attrs.put("code", errorEnum.getCode());
        attrs.put("canRetry", errorEnum.isCanRetry());
        return attrs;
    }
}
