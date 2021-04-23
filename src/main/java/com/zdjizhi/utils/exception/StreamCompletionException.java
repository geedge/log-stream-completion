package com.zdjizhi.utils.exception;

/**
 * @author qidaijie
 * @Package com.zdjizhi.utils.exception
 * @Description:
 * @date 2021/3/2510:14
 */
public class StreamCompletionException extends RuntimeException {

    public StreamCompletionException(Exception e) {
        super(e);
    }

    public StreamCompletionException(String e) {
        super(e);
    }
}
