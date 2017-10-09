package com.mvnsearch.spring.boot.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * provider invoke statics filter
 *
 * 统计 provider的接口和方法的调用次数
 *
 * @author linux_china
 */
@Activate(group = Constants.PROVIDER)
public class ProviderInvokeStaticsFilter extends StaticsFilter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        increase(invoker.getInterface(), invocation.getMethodName());
        return invoker.invoke(invocation);
    }
}
