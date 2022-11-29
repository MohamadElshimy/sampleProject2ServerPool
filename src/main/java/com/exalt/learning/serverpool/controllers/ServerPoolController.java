package com.exalt.learning.serverpool.controllers;

import com.exalt.learning.serverpool.entities.Request;
import com.exalt.learning.serverpool.services.ServerPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerPoolController {

    @Autowired
    ServerPoolService serverPoolService;

    @GetMapping("/servers")
    public String requestServersState() {
        return serverPoolService.requestServersState();
    }

    @PostMapping("/servers")
    public String requestServerCapacity(@RequestBody Request request) throws InterruptedException {
        return serverPoolService.requestServerCapacity(request);
    }



}
