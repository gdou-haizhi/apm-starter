package com.jianming.apm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * apm 配置类
 * @author jianming
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan(value = "com.jianming.apm",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Component.class})},
        useDefaultFilters = false)
public class ApmAutoConfig {

}
