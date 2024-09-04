package com.liwen.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liwen.project.common.response.Response;
import com.liwen.project.common.response.SingleResponse;
import com.liwen.project.common.utils.DateUtils;
import com.liwen.project.common.utils.ThreadLocals;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.swagger.web.SwaggerResource;

@ControllerAdvice
public class KResponseBodyAdvice implements ResponseBodyAdvice, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(KResponseBodyAdvice.class);
    private static ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 拦截请求，将返回给前端的数据进行封装
     * @param o
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ServletServerHttpRequest ssrequest = (ServletServerHttpRequest) request;
        log.info("{}", ssrequest.getServletRequest().getRequestURI());
        log.info("{}", ssrequest.getServletRequest().getRequestURL());
        String requestURI = ssrequest.getServletRequest().getRequestURI();
        if (requestURI.indexOf("swagger") > 0 || requestURI.indexOf("api-docs")> 0) {
            return o;
        }

        if (o == null) {
            return new Response();

        } else if (o instanceof Response) {
            this.setTimeStampAndTraceId(o);
            return o;
        } else if (!(o instanceof ResponseEntity)) {
            SingleResponse singleResponse = new SingleResponse();
            singleResponse.setData(o);
            if (o instanceof String) {
                try {
                    this.setTimeStampAndTraceId(singleResponse);
                    return objectMapper.writeValueAsString(singleResponse);
                } catch (Throwable var11) {
                    return "{\"code\": \"0\",\"msg\": null,\"data\":\"" + o + "\"}";
                }
            } else {
                this.setTimeStampAndTraceId(singleResponse);
                return singleResponse;
            }
        }

        return null;
    }


    private void setTimeStampAndTraceId(Object o) {
        if (o instanceof Response) {
            Response response = (Response)o;

            try {
                if (StringUtils.isBlank(response.getTraceId())) {
                    response.setTraceId((String) ThreadLocals.get("traceId"));
                }
            } catch (Throwable var4) {
            }

            if (StringUtils.isBlank(response.getTimestamp())) {
                response.setTimestamp(DateUtils.getCurrentTimeString());
            }
        }

    }
}
