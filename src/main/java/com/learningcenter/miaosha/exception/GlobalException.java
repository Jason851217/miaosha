package com.learningcenter.miaosha.exception;

import com.learningcenter.miaosha.dto.Result;
import com.sun.tools.javac.jvm.Code;

/**
 * 定义一个全局异常
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Result.CodeMsg cm;
	
	public GlobalException(Result.CodeMsg cm) {
		super(cm.toString());
		this.cm = cm;
	}

	public Result.CodeMsg getCm() {
		return cm;
	}

}
