package com.chinasws.web.controller;

import com.alibaba.fastjson.JSON;
import com.chinasws.web.entity.Application;
import com.chinasws.web.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping(value = "submit")
    public void submit(String app,String result, String pid, String tid) throws Exception {
        Application application = JSON.parseObject(app, Application.class);
        appService.submit(application,result, pid, tid);
    }


    @PostMapping(value = "/getDaiBanTasks")
    public Map getDaiBanTasks(String userCd,
                              @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                              @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        int start = pageIndex * pageSize;
        return appService.getTasks(userCd, start, pageSize);
    }


    @GetMapping(value = "/getAppByPid")
    public Map getAppByPid(String pid) {
        return appService.getAppByPid(pid);
    }
}
