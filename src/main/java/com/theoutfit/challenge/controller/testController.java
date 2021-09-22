package com.theoutfit.challenge.controller;

import com.theoutfit.challenge.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class testController {
    @Autowired
    OrderService orderService;

    @GetMapping("test")
    public void test() throws ClassNotFoundException, IOException, URISyntaxException {
        orderService.calculateMetrics();
    }
}