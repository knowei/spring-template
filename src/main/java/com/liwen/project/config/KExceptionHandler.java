package com.liwen.project.config;

import com.liwen.project.common.response.Response;
import com.liwen.project.exception.BusinessException;
import com.liwen.project.exception.IBusinessException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.stream.Collectors;

@ControllerAdvice
@ConditionalOnProperty(
        prefix = "auth",
        value = {"enableExceptionHandler"},
        matchIfMissing = true
)
public class KExceptionHandler {

    private static String HttpRequestMethodNotSupportedExceptionClass = "org.springframework.web.HttpRequestMethodNotSupportedException";
    private static String MissingServletRequestParameterExceptionClass = "org.springframework.web.bind.MissingServletRequestParameterException";
    private static String DataIntegrityViolationException = "org.springframework.dao.DataIntegrityViolationException";

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, NumberFormatException.class, Exception.class, Throwable.class})
    public Response validateHandler(Throwable ex, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Throwable {

        Response response = Response.buildFailure("未知错误");
        String msg = null;
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException)ex;
            BindingResult bindingResult = mex.getBindingResult();
            msg = (String)bindingResult.getAllErrors().stream().map((a) -> {
                return a.getDefaultMessage();
            }).collect(Collectors.joining(","));
            response.setMsg(msg);
            httpServletResponse.setStatus(400);
            return response;
        } else if (ex instanceof BindException) {
            BindException bindException = (BindException)ex;
            msg = (String)bindException.getAllErrors().stream().map((a) -> {
                return a.getDefaultMessage();
            }).collect(Collectors.joining(","));
            response.setMsg(msg);
            httpServletResponse.setStatus(400);
            return response;
        } else if (ex instanceof BusinessException) {
            response.setCode(((BusinessException)ex).getCode());
            response.setMsg(((BusinessException)ex).getMsg());
            return response;
        } else if (ex instanceof IBusinessException) {
            response.setCode(((IBusinessException)ex).getCode());
            response.setMsg(((IBusinessException)ex).getMsg());
            return response;
        } else if (ex instanceof IllegalArgumentException) {
            response.setMsg("非法数据异常:" + ex.getMessage());
            httpServletResponse.setStatus(405);
            return response;
        } else {
            response.setMsg("抱歉！系统暂时无法完成当前操作，请联系系统管理员");
            this.setCommonExceptionMsg(ex, response);
            return response;
        }

    }


    private void setCommonExceptionMsg(Throwable ex, Response response) {
        if (ex instanceof SQLException) {
            response.setMsg("数据库错误,请联系管理员");
        } else if (ex instanceof NullPointerException) {
            response.setMsg("空指针异常,请联系管理员");
        } else if (ex instanceof NumberFormatException) {
            response.setMsg("参数格式错误,请输入正确的数字类型");
        } else if (DataIntegrityViolationException.equals(ex.getClass().getName())) {
            response.setMsg("数据库操作失败,校验数据完整性失败" + ex.getMessage());
        } else if (ex instanceof IllegalStateException && ex.getStackTrace() != null && ex.getStackTrace().length > 0 && "handleNullValue".equals(ex.getStackTrace()[0].getMethodName())) {
            response.setMsg("数据校验失败,传入的基本数据类型不能为空:" + ex.getMessage());
        } else if (MissingServletRequestParameterExceptionClass.equals(ex.getClass().getName())) {
            response.setMsg("参数没有值:" + ex.getMessage());
        }
    }
}
