package com.example.demo.common.async.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AsyncTestService {

    @Autowired
    private AsyncTask asyncTask;

    public void customAsync(){
        for (int i = 0; i < 1000; i++) {
            asyncTask.customAsync(i);
        }
    }

    public void defaultAsync(){
        for (int i = 0; i < 100; i++) {
            asyncTask.defaultAsync(i);
        }
    }
}