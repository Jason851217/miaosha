package com.learningcenter.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.learningcenter.miaosha.dto.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice  //Controller层面的通知
@ResponseBody  // 添加在类上，省去了在每个方法上面都加上这个注解
public class GlobalExceptionHandler {

	@ExceptionHandler(value=Exception.class) // @ExceptionHandle可以写多个，对不同的异常进行处理
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
		e.printStackTrace();
		if(e instanceof GlobalException) {
			GlobalException ex = (GlobalException)e;
			return Result.error(ex.getCm());
		}else if(e instanceof BindException) {  //BindResult 异常，验证异常
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors(); // 获取所有校验错误信息
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(Result.CodeMsg.BIND_ERROR.fillArgs(msg));
		}else {
			return Result.error(Result.CodeMsg.SERVER_ERROR);
		}
	}
}
