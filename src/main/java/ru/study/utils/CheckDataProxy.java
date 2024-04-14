package ru.study.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.study.servises.WriteDataFile;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;

public class CheckDataProxy<T> implements InvocationHandler{
    private WriteDataFile writeDataFile;
    private T o;

    public CheckDataProxy(T o,WriteDataFile writeDataFile) {
        this.o = o;
        this.writeDataFile = writeDataFile;
    }

    public <T> T proxy(T in,WriteDataFile writeDataFile) {
        Class<?> inClass = in.getClass();
        Method[] methods = inClass.getMethods();

        ClassLoader classLoader = in.getClass().getClassLoader();
        Class[] interfaces = in.getClass().getInterfaces();
        T out = (T) Proxy.newProxyInstance(classLoader, interfaces, new CheckDataProxy<>(in,writeDataFile));
        return out;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(o, args);
        if (method.isAnnotationPresent(LogTransformation.class)){
            writeDataFile.write(String.format("Start operation: %s, name: %s, class: %s," +
                            " params: %s, result: %s, End Operation: %s",
                    new Date(System.currentTimeMillis()),method.getName(), method.getDeclaringClass(),
                    Arrays.toString(args), result.toString(),new Date(System.currentTimeMillis())));
        }
        return result;
    }

}
