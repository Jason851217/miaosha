package com.learningcenter.miaosha.dto;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 10:03
 **/
public class Result<T> {

    private int code;
    private String msg;

    private T data;


    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }


    private Result(T data) {
        this.data = data;
        this.code = CodeMsg.SUCCESS.getCode();
        this.msg = CodeMsg.SUCCESS.getMsg();
    }

    private Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }


    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }


    /**
     * 统一管理状态码和消息描述类
     */
    public static class CodeMsg {
        //通用模块
        public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
        public static final CodeMsg SERVER_ERROR= new CodeMsg(500100, "服务器异常");
        public static final CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");

        //登陆模块5002xx
        public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500200, "登录密码不能为空");
        public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500201, "手机号不能为空");
        public static final CodeMsg USER_NOT_EXISTS =new CodeMsg(500202, "用户不存在") ;
        public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500203, "密码错误");
        public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500501, "手机号不存在") ;


        //商品模块5003xx

        // 订单模块5004xx

        // 秒杀模块5005xx
        public static final CodeMsg NO_STOCK =new CodeMsg(500500, "商品已经秒杀完毕") ;
        public static final CodeMsg MIAOSHA_REPEATE = new CodeMsg(500501, "不能重复秒杀") ;
        public static final CodeMsg MIAOSHA_OVER = new CodeMsg(500502, "秒杀结束") ;;
        public static final CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500502, "秒杀结束");


        private CodeMsg(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }


        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public CodeMsg fillArgs(Object... args) {
            int code = this.code;
            String message = String.format(this.msg, args);
            return new CodeMsg(code, message);
        }

        @Override
        public String toString() {
            return "CodeMsg [code=" + code + ", msg=" + msg + "]";
        }

    }
}
