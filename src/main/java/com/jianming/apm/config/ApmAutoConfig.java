package com.jianming.apm.config;

import com.jianming.apm.aspect.TimerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * apm 配置类
 * @author jianming
 */
@EnableAspectJAutoProxy
@Configuration
public class ApmAutoConfig {

    @Bean
    public TimerAspect timerAspect() {
        return new TimerAspect();
    }

}
