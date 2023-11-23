package com.zclcs.cloud.lib.security.lite.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SameTokenInvalidException;
import cn.hutool.core.util.StrUtil;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.*;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.exception.RateLimiterException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器
 *
 * @author zclcs
 */
@Slf4j
@Order()
public class BaseExceptionHandler {

    /**
     * 权限码异常
     */
    @ExceptionHandler(RateLimiterException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public BaseRsp<Object> handleNotPermissionException(RateLimiterException e) {
        return RspUtil.message("访问频繁，请稍后再试");
    }

    /**
     * 权限码异常
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseRsp<Object> handleNotPermissionException(NotPermissionException e) {
        log.error("权限校验异常", e);
        return RspUtil.message("没有访问权限，请联系管理员授权");
    }

    /**
     * 角色权限异常
     */
    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseRsp<Object> handleNotRoleException(NotRoleException e) {
        log.error("权限校验异常", e);
        return RspUtil.message("没有访问权限，请联系管理员授权");
    }

    /**
     * 请通过网关访问接口
     */
    @ExceptionHandler(SameTokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseRsp<Object> handleSameTokenInvalidException(SameTokenInvalidException e) {
        log.error("请通过网关访问接口", e);
        return RspUtil.message("请通过网关访问接口");
    }

    /**
     * 认证失败
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public BaseRsp<Object> handleNotLoginException(NotLoginException e) {
        log.error("token过期", e);
        return RspUtil.message("token已过期");
    }

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<String> handleGlobalException(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return RspUtil.message("系统异常");
    }

    @ExceptionHandler(value = MyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleMyException(MyException e) {
        log.error("系统业务异常 ex={}", e.getMessage(), e);
        return RspUtil.message(e.getMessage());
    }

    @ExceptionHandler(value = FieldException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleFieldException(FieldException e) {
        return RspUtil.message(e.getMessage());
    }

    @ExceptionHandler(value = ValidateCodeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleValidateCodeException(ValidateCodeException e) {
        log.error("验证码异常", e);
        return RspUtil.message(e.getLocalizedMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return Response
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseRsp<Object> handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return RspUtil.message(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return Response
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseRsp<Object> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String s = StrUtil.subAfter(path.toString(), ".", true);
            message.append(s).append(violation.getMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return RspUtil.message(message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return Response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseRsp<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(Strings.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return RspUtil.message(message.toString());
    }

    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleFileDownloadException(FileDownloadException e) {
        log.error("FileDownloadException", e);
        return RspUtil.message(e.getMessage());
    }

    @ExceptionHandler(value = FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleFileUploadException(FileUploadException e) {
        log.error("FileUploadException", e);
        return RspUtil.message(e.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        log.error(message, e);
        if (StrUtil.containsIgnoreCase(message, "java.time.LocalDate")) {
            message = "请传入正确的日期格式, 例：2000-02-12";
        } else if (StrUtil.containsIgnoreCase(message, "java.time.LocalDateTime")) {
            message = "请传入正确的日期格式, 例：2000-02-12 10:00:00";
        } else {
            log.error(message, e);
        }
        return RspUtil.message(message);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String message = "该方法不支持" + e.getContentType() + "媒体类型";
        log.error(message);
        return RspUtil.message(message);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseRsp<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = "该方法不支持" + e.getMethod() + "请求方法";
        log.error(message);
        return RspUtil.message(message);
    }

}
