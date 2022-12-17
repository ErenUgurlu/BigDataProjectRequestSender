package com.bigdata.sender.Request.Sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/start")
@RequiredArgsConstructor
public class Controller {

    @Value("${number.request}")
    private int numberOfRequest;
    
    @Value("${request.wait.time}")
    private int requestWaitTime;

    @GetMapping
    public String SendData() {
		RequestSender requestSender = new RequestSender();
		requestSender.sendRequest(numberOfRequest,requestWaitTime);
		return "Data Sending is start";
    }
}