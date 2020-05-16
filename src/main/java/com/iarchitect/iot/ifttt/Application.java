package com.iarchitect.iot.ifttt;

import com.iarchitect.iot.ifttt.controller.CallBackController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.iarchitect.iot.ifttt.controller.PingController;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "com.iarchitect.iot.ifttt.controller")
@Import({ PingController.class, CallBackController.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
