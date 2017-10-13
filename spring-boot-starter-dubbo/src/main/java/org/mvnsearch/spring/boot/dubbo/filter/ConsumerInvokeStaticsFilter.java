package org.mvnsearch.spring.boot.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/**
 * 统计 consumer 的接口和方法的调用次数
 * @author linux_china
 */
@Activate(group = Constants.CONSUMER, order = -110000)
public class ConsumerInvokeStaticsFilter extends StaticsFilter {
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        increase(invocation.getClass(), invocation.getMethodName());
        return invoker.invoke(invocation);
    }
}
