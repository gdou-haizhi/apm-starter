package com.jianming.apm.controller;

import com.jianming.apm.aspect.TimerAspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 方法计时controller
 *  可获取方法的执行时间
 * @author jianming
 */
@RestController
public class TimerController {

    @GetMapping("/method/timer")
    public List<String> getTimer(String uniqueName) {
        return TimerAspect.METHOD_EXECUTION_TIME.get(uniqueName);
    }


    @GetMapping("/method/timer/all")
    public Map<String, CopyOnWriteArrayList<String>> getAllTimer(String uniqueName) {
        return TimerAspect.METHOD_EXECUTION_TIME;
    }
}
