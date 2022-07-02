package com.tzl.exceptionHander;

import com.tzl.result.Result;
import com.tzl.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result error(Exception e){
		log.error(ExceptionUtil.getMessage(e));
		return Result.error();
	}

	@ExceptionHandler(GuliException.class)
	@ResponseBody
	public Result error(GuliException e){
		log.error(ExceptionUtil.getMessage(e));
		return Result.error().code(e.getCode()).message(e.getMessage());
	}
}
