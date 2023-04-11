package com.example.demo.controller;

import com.example.demo.common.async.test.AsyncTestService;
import com.example.demo.common.async.test.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncTestService asyncTestService;
	
    @RequestMapping("/custom")
    public void t1(HttpServletRequest request){
        asyncTestService.customAsync();
    }

    @RequestMapping("/default")
    public void t2(HttpServletRequest request){
        asyncTestService.defaultAsync();
    }

    @RequestMapping("/threads")
    public List<String> t3(HttpServletRequest request, @RequestParam(value = "name") String name){
        Thread[] allThreads = ThreadLocalUtil.getAllThreads(name);
        List<String> collect = Arrays.stream(allThreads).map((t) -> t.getName()).collect(Collectors.toList());
        return collect;
    }

    @RequestMapping("/thread/local")
    public Map<String, Map<String, Object>> t3(@RequestParam(value = "name") String name){
        Map<String, Map<String, Object>> result = new HashMap<>();
        Thread[] allThreads = ThreadLocalUtil.getAllThreads(name);
        for (Thread thread : allThreads) {
            Map<WeakReference<ThreadLocal>, ThreadLocalUtil.ThreadLocalValue> threadLocalMap = ThreadLocalUtil.getThreadLocalMap(thread);
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<WeakReference<ThreadLocal>, ThreadLocalUtil.ThreadLocalValue> entry : threadLocalMap.entrySet()) {
                WeakReference<ThreadLocal> key = entry.getKey();
                Object value = entry.getValue().getValue();
                map.put(key.toString(), value!=null?value.toString():null);
            }
            result.put(thread.getName(), map);
        }
        return result;
    }

    @RequestMapping("/thread/local/reset")
    public void t4(@RequestParam(value = "name") String name) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Map<String, Object>> result = new HashMap<>();
        Thread[] allThreads = ThreadLocalUtil.getAllThreads(name);
        for (Thread thread : allThreads) {
            Map<WeakReference<ThreadLocal>, ThreadLocalUtil.ThreadLocalValue> threadLocalMap = ThreadLocalUtil.getThreadLocalMap(thread);
            ThreadLocalUtil.resetThreadLocals(threadLocalMap);
        }
    }

}