package com.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.InvokerListenerAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * dubbo client invoker listener
 *
 * 消费者 可用接口和使用的url的记录
 *
 * @author linux_china
 */
@Activate
public class ConsumerSubscribeListener extends InvokerListenerAdapter {
    public static Set<Class> subscribedInterfaces = new HashSet<>();
    public static Map<String, HashSet<Object>> connections = new HashMap<>();

    /**
     *  记录 调用接口名称的信息和调用url的记录
     */
    @Override
    public void referred(Invoker<?> invoker) throws RpcException {
        Class<?> subscribeInterface = invoker.getInterface();
        subscribedInterfaces.add(subscribeInterface);
        String subscribeInterfaceCanonicalName = subscribeInterface.getCanonicalName();
        if (!connections.containsKey(subscribeInterfaceCanonicalName)) {
            connections.put(subscribeInterfaceCanonicalName, new HashSet<>());
        }
        connections.get(subscribeInterfaceCanonicalName).add(invoker.getUrl().toString());
    }

    /**
     *  销毁 调用这的接口名称和调用url的记录
     */
    @Override
    public void destroyed(Invoker<?> invoker) {
        Class<?> subscribedInterface = invoker.getInterface();
        subscribedInterfaces.remove(subscribedInterface);
        String subscribedInterfaceCanonicalName = subscribedInterface.getCanonicalName();
        if (connections.containsKey(subscribedInterfaceCanonicalName)) {
            connections.get(subscribedInterfaceCanonicalName).remove(invoker.getUrl().toString());
        }
    }
}
