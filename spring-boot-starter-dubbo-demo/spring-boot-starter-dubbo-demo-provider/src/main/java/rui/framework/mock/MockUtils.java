package rui.framework.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.apache.commons.lang3.RandomUtils.*;

public class MockUtils {
    private final static Logger logger = LoggerFactory.getLogger(MockUtils.class);


    /**
     * mock 一个对象，自动填充内部变量
     *
     * @param t   类型
     * @param <T> 泛型
     * @return 通过此类型实例化的对象
     */
    public static <T> T mock(Class<T> t) {
        try {
            T basicType =checkClassType(t);
            if(basicType !=null){
                return basicType;
            }
            T finalInstance = t.newInstance();
            getMethods(t).stream()
                    .filter(method -> method.getName().contains("set"))
                    .forEach(method -> {
                        Class<?> paramType = method.getParameterTypes()[0];
                        mockParam(finalInstance, method, paramType);
                    });
            return finalInstance;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("模拟对象出错，错误原因为{}", e);
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    private static  <T>T checkClassType(Class<T> t){
        if(t == Date.class){
            return (T) new Date();
        }else if(t== String.class) {
            return (T) UUID.randomUUID().toString();
        }else if(t.getName().equals("[B")) {
            return (T) nextBytes(100);
        }else{
            return null;
        }
    }

    private static <T> List<Method> getMethods(Class<T> t) {
        List<Method> methods = new ArrayList<>();
        Class clazz = t;
        while (clazz != Object.class) {
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    /**
     * 模拟参数， 后期继续维护
     *
     * @param instance  实例对象
     * @param method    方法
     * @param paramType 参数类型
     * @param <T>       泛型
     */
    private static <T> void mockParam(T instance, Method method, Class<?> paramType) {
        try {
            if (!mockBasicParam(instance, method, paramType) && !mockGeneralParam(instance, method, paramType)) {
                logger.warn("非默认mock 类型，需要调用者手动mock，此方法为 {}", method);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟 基本数据类型
     *
     * @param instance  实例对象
     * @param method    方法
     * @param paramType 参数
     * @param <T>       泛型
     * @return true 为击中基本类型 ，false 为非基本数据类型
     */
    private static <T> boolean mockBasicParam(T instance, Method method, Class<?> paramType) throws InvocationTargetException, IllegalAccessException {
        if (paramType.getName().equals("boolean")) {
            method.invoke(instance, nextBoolean());
            return true;
        } else if (paramType.getName().equals("long")) {
            method.invoke(instance, nextLong());
            return true;
        } else if (paramType.getName().equals("int")) {
            method.invoke(instance, nextInt());
            return true;
        } else if (paramType.getName().equals("[B")) {
            method.invoke(instance, (Object) nextBytes(1024));
            return true;
        }else if(paramType.getName().equals("[[B")){
            int random=nextInt(1,10);
            List<byte[]> bytes=new ArrayList<>(random);
            for (int i = 0; i < random; i++) {
                bytes.add(nextBytes(1024));
            }
            byte[][] bytes1= bytes.toArray(new byte[1][1]);
            method.invoke(instance, (Object) bytes1);
            return true;
        }
        return false;
    }

    /**
     * 模拟 常见数据类型
     *
     * @param instance  实例对象
     * @param method    方法
     * @param paramType 参数
     * @param <T>       泛型
     * @return true 为击中 ，false 为未击中
     */
    @SuppressWarnings("unchecked")
    private static <T> boolean mockGeneralParam(T instance, Method method, Class<?> paramType) throws InvocationTargetException, IllegalAccessException {
        if (paramType == String.class) {
            method.invoke(instance, UUID.randomUUID().toString());
            return true;
        } else if (paramType == Integer.class) {
            method.invoke(instance, nextInt());
            return true;
        } else if (paramType == Boolean.class) {
            method.invoke(instance, nextBoolean());
            return true;
        } else if (paramType == Date.class) {
            method.invoke(instance, new Date());
            return true;
        } else if (paramType == List.class) {
            Class<?> clazz = (Class<?>) ((ParameterizedTypeImpl)(method.getGenericParameterTypes()[0])).getActualTypeArguments()[0];
            int size = new Random().nextInt(100);
            List list = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                list.add(mock(clazz));
            }
            method.invoke(instance, list);
            return true;
        }
        return false;
    }

}
