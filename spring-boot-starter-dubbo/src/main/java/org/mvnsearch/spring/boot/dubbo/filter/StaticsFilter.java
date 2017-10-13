package org.mvnsearch.spring.boot.dubbo.filter;

import com.alibaba.dubbo.rpc.Filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * statics filter
 *
 * 用于统计 接口和方法的调用次数
 *
 * @author linux_china
 */
public abstract class StaticsFilter implements Filter {
    public static Map<String, AtomicLong> statics = new ConcurrentHashMap<String, AtomicLong>();

    /**
     * 增加 某类的某方法的统计数量
     */
    public static void increase(Class clazz, String methodName) {
        String key = clazz.getCanonicalName() + "." + methodName;
        if (!statics.containsKey(key)) {
            statics.put(key, new AtomicLong(0));
        }
        statics.get(key).incrementAndGet();
    }


    /**
     * 获得某类的某方法的调用次数
     */
    public static long getValue(Class clazz, String methodName) {
        String key = clazz.getCanonicalName() + "." + methodName;
        AtomicLong value = statics.get(key);
        return value != null ? value.get() : 0;
    }
}
