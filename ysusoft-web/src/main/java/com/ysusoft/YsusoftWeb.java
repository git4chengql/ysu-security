package com.ysusoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chengql
 * @create 2018-01-02 下午4:41
 **/
@SpringBootApplication
@Controller
public class YsusoftWeb {
    public static void main(String[] args) {
        SpringApplication.run(YsusoftWeb.class,args);
    }

    @GetMapping("/")
    public String index(){
        return "login.html";
    }
}
