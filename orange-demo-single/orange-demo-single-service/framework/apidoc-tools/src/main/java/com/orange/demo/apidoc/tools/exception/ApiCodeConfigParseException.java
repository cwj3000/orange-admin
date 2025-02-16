package com.orange.demo.apidoc.tools.exception;

/**
 * 解析接口信息配置对象中的异常。
 *
 * @author Jerry
 * @date 2020-09-24
 */
public class ApiCodeConfigParseException extends RuntimeException {

    /**
     * 构造函数。
     */
    public ApiCodeConfigParseException() {

    }

    /**
     * 构造函数。
     *
     * @param msg 错误信息。
     */
    public ApiCodeConfigParseException(String msg) {
        super(msg);
    }

}
