package com.victor.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HelloController {
    @Autowired
    private Scheduler scheduler;

    @RequestMapping("/user/hello")
    public String hello(){

        return "Hello, world!";
    }

    @RequestMapping("/schedule")
    public String getSchedule() throws SchedulerException {
        List<String> groupNames = scheduler.getTriggerGroupNames();
        for (String groupName: groupNames){
            log.info(groupName);
        }
        return groupNames.toString();
    }
}
