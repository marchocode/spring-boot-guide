package xyz.chaobei.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public class FormController {

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        webDataBinder.addCustomFormatter(new DateFormatter());
    }


    @GetMapping("/form/date")
    @ResponseBody
    public String dateArgument(@RequestParam("date") Date date) {

        log.info("转换date={}", date);
        return "success";
    }


}
