package com.example.Luna.api.controller;

import com.example.Luna.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final MessageProducer messageProducer;

    @Autowired
    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello World";
        messageProducer.sendMessage(message);
        return "Message sent: " + message;
    }


}