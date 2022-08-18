package com.example.demo.controller.test1;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@RestController
public class InitBinderTestController {

    @InitBinder
    public void InitBinder(WebDataBinder binder){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class,dateEditor);
    }

    /**
     * http://localhost/it1?date=2020-01-02 12:02:00
     */
    @RequestMapping(value="/it1",method= RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getFormatData(Date date) throws ParseException {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name", "zhangsan");
        map.put("age", 22);
        map.put("date",date);
        return map;
    }


}
