package zyh.com.core;

import zyh.com.core.exception.ApiException;

/**
 * @author dingtao
 */
public interface DataCall<T> {

    public void success(T result);

    public void fail(ApiException e);

}
