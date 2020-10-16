package com.jianming.apm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jianming
 * @create 2020-10-02-21:05
 */
@Aspect
@Component
public class TimerAspect {

    public static Map<String, CopyOnWriteArrayList<String>> METHOD_EXECUTION_TIME = new ConcurrentHashMap<>();

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final static int TIME_UNIT = 1000;

    @Pointcut("@annotation(com.jianming.apm.annotation.Timer)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void timerStart(JoinPoint joinPoint) {
        String uniqueName = getUniqueName(joinPoint);
        long nano = System.nanoTime();
        startTime.set(nano);
        System.out.println(uniqueName + " 计时开始......");
    }

    @AfterReturning("pointCut()")
    public void timerEnd(JoinPoint joinPoint) {
        String uniqueName = getUniqueName(joinPoint);
        CopyOnWriteArrayList<String> list = METHOD_EXECUTION_TIME.get(uniqueName);
        if(list == null) {
            synchronized (this) {
                list = METHOD_EXECUTION_TIME.get(uniqueName);
                if(list == null) {
                    list = new CopyOnWriteArrayList<>();
                    METHOD_EXECUTION_TIME.put(uniqueName,list);
                }
            }
        }
        list.add(getTime());
        System.out.println(uniqueName + " 计时结束......");
    }


    /**
     * 获取方法唯一名字
     *   （全类名#方法名-参数类型-参数类型-...）
     * @param joinPoint
     * @return
     */
    private String getUniqueName(JoinPoint joinPoint) {
        StringBuilder builder = new StringBuilder();
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        String className = declaringType.getName();
        String methodName = joinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        builder.append(className).append("#").append(methodName);
        for(Class param : parameterTypes) {
            builder.append("-").append(param.getName());
        }
        return builder.toString();
    }

    /**
     * 获取执行时间
     * @return
     */
    private String getTime() {
        long end = System.nanoTime();
        Long start = startTime.get();
        // 移除此次记录，防止内存泄漏
        startTime.remove();
        long time = end - start;
        // 1ms = 1000000ns    1s = 1000ms  1μs = 1000ms
        if(time < TIME_UNIT) {
            return time + "ns";
        }else if(time < TIME_UNIT * TIME_UNIT) {
            return time/TIME_UNIT + "μs";
        }else if (time < TIME_UNIT * TIME_UNIT * TIME_UNIT){
            return time/TIME_UNIT/TIME_UNIT + "ms";
        }else {
            return time/TIME_UNIT/TIME_UNIT/TIME_UNIT + "s";
        }

    }

}
