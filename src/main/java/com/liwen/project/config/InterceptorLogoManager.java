package com.liwen.project.config;

import com.liwen.project.common.utils.ThreadLocals;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 *
 */
@Component
public class InterceptorLogoManager {

    private static Logger logger = LoggerFactory.getLogger(InterceptorLogoManager.class);


    public InterceptorLogoManager() {
    }

    public String getUserCode(HttpServletRequest request) {
        String token = this.getAccessToken(request);
        ThreadLocals.put("accessToken", token);
        this.getAndSetValue(request, "applicationCode");
        this.getAndSetValue(request, "tenantCode");
        this.getAndSetValue(request, "openauth");
        return token;
    }

    private String getAccessToken(HttpServletRequest request) {
        String cipherText = request.getHeader("token");
        if (StringUtils.isBlank(cipherText)) {
            cipherText = request.getParameter("token");
        }

        /*if (StringUtils.isBlank(cipherText)) {
            cipherText = request.getHeader(this.authToken.getAuthCookieName());
        }

        if (StringUtils.isBlank(cipherText)) {
            cipherText = request.getHeader("token");
        }*/

        if (StringUtils.isBlank(cipherText)) {
            Cookie cookie = WebUtils.getCookie(request, "token");
            if (cookie != null) {
                cipherText = cookie.getValue();
            }
        }

        return cipherText;
    }

    public String getAndSetValue(HttpServletRequest request, String header) {
        String value = request.getHeader(header);
        if (StringUtils.isNoneBlank(new CharSequence[]{value})) {
            ThreadLocals.put(header, value);
        }

        return value;
    }

    public void getAndSetTraceId(HttpServletRequest request) {
        String traceId = request.getHeader("traceId");
        if (StringUtils.isBlank(traceId)) {
            traceId = request.getHeader("traceid");
        }

        if (StringUtils.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
        }

        ThreadLocals.put("traceId", traceId);
        MDC.put("traceId", traceId);
    }
}
