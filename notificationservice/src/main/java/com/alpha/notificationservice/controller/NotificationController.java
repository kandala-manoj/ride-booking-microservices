package com.alpha.notificationservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @PostMapping("/notification/send")
    public String sendNotification(@RequestBody String message) {

        System.out.println("NOTIFICATION RECEIVED: " + message);

        return "Notification sent: " + message;
    }
}