package com.lk.exception;

import com.lk.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author myq
 * @description 全局异常捕获
 * @create 2019-01-07 11:40
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler
    public Message unKnowException(Exception e){
        if(e instanceof MissingServletRequestParameterException){
            logger.error("请求参数异常", e);
            return Message.error(400, "请求参数异常");
        }


        logger.error("发生了未知异常：", e);
        return Message.error(-1, "系统出现错误，请联系网站管理员");
    }



}
