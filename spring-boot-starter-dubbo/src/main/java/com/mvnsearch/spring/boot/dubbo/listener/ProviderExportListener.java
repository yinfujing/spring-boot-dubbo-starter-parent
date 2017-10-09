package com.mvnsearch.spring.boot.dubbo.listener;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Exporter;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.listener.ExporterListenerAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * provider export listener
 *
 * provider 暴露的接口和url的记录
 *
 * @author linux_china
 */
@Activate
public class ProviderExportListener extends ExporterListenerAdapter {
    /**
     * 暴露接口
     */
    public static Set<Class> exportedInterfaces = new HashSet<>();
    /**
     * 暴露urls
     */
    public static Set<URL> exportedUrl = new HashSet<>();

    /**
     * 服务暴露触发该方法
     *
     * 对jvm内部的调用协议，不添加url
     */
    public void exported(Exporter<?> exporter) throws RpcException {
        Class<?> anInterface = exporter.getInvoker().getInterface();
        exportedInterfaces.add(anInterface);
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.add(url);
        }
    }

    /**
     * 服务取消暴露触发该方法。
     * 对jvm内部的调用协议，不移除url
     */
    public void unexported(Exporter<?> exporter) {
        exportedInterfaces.remove(exporter.getInvoker().getInterface());
        URL url = exporter.getInvoker().getUrl();
        if (!url.getProtocol().equals("injvm")) {
            exportedUrl.remove(url);
        }
    }
}
