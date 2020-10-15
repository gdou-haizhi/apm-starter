package com.jianming.apm.annotation;

import java.lang.annotation.*;

/**
 * 方法执行计时器注解
 *   标注在方法上，可以通过该方法的 （全类名#方法名-参数类型-参数类型-...） 获取到该方法的执行时间
 *   如 localhost:8080/timer/class?uniqueName=com.jianming.monthscore.controller.UserLoginController-hello-java.lang.String
 * @author jianming
 * @create 2020-10-02-21:06
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Timer {
}