package com.iarchitect.iot.ifttt.controller;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@RestController
//@EnableWebMvc
public class CallBackController extends BaseController {

    private final RestTemplate restTemplate;
    String callBackUrl = "https://maker.ifttt.com/trigger/";
    final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    public CallBackController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(path = "/event/{eventName}/delay/{delay}/key/{apiKey}", method = RequestMethod.GET)
    public String requestCallBack(@PathVariable String eventName,
                                  @PathVariable long delay,
                                  @PathVariable String apiKey,
                                  @RequestParam boolean nightOnly) {
        if (nightOnly) {
            if (isNightTime()) {
                return scheduleCallBack(eventName, delay, apiKey);
            }
        } else {
            return scheduleCallBack(eventName, delay, apiKey);
        }

        return "Nothing scheduled";
    }

    @NotNull
    private String scheduleCallBack(@PathVariable String eventName, @PathVariable long delay, @PathVariable String apiKey) {
        executorService.schedule(invokeIftttWebHook(eventName, apiKey), delay, TimeUnit.MINUTES);
        executorService.shutdown();
        return "Call Back Requested";
    }

    private Runnable invokeIftttWebHook(String eventName, String apiKey) {
        this.callBackUrl = this.callBackUrl + eventName + "/with/key/" + apiKey;
        return () -> {
            this.restTemplate.getForObject(this.callBackUrl, String.class);
        };

    }
}
