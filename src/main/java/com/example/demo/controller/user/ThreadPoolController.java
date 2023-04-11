package com.example.demo.controller.user;

import com.example.demo.common.threadpool.test.ThreadTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/thread/pool")
public class ThreadPoolController {

    @Autowired
    private ThreadTestService threadTestService;
	
    @RequestMapping("/execute")
    public void t1(HttpServletRequest request){
        threadTestService.test();
    }
    @RequestMapping("/submit")
    public void t2(HttpServletRequest request) throws ExecutionException, InterruptedException {
        threadTestService.test2();
    }

}