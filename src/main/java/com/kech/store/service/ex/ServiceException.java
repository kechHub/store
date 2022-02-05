package com.kech.store.service.ex;


/**
 * 业务类异常的基类：throws new ServiceException("业务层产生的未知异常")
 * 根据业务层不同的功能来详细定义具体的异常类型，统一的去继承ServiceException
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
