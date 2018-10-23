package cn.edu.nwafu.cie.toxicitypred.common;

import org.apache.ibatis.mapping.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: SungLee
 * @date: 2018-10-15 15:57
 * @description: Spring 控制器增强 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);
    Result resultMap = new Result();

    class BusinessException extends RuntimeException{
        public BusinessException(String message){
            super(message);
        }
    }

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionGet(HttpServletRequest request, Exception e) {
        Result resultMap = new Result();
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            resultMap.error(404, "页面不存在...");
            resultMap.setData(request.getRequestURL());
        } else {
            resultMap.error(500, "服务器端异常...");
            resultMap.setData(request.getRequestURL());
        }
        LOGGER.error("【系统异常】 ", e);
        return resultMap;
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result handleBusinessException(BusinessException e) {
        Result resultMap = new Result();
        LOGGER.error(e.getMessage(), e);
        resultMap.error(500, "服务器端异常...");
        return resultMap;
    }
}
