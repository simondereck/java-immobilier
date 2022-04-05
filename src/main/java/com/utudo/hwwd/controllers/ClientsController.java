package com.utudo.hwwd.controllers;


import com.utudo.hwwd.helpers.WebSocketServer;
import com.utudo.hwwd.models.models.ResponseMessage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientsController {

    @RequestMapping("/push/{sToken}")
    public ResponseMessage pushToWeb(@RequestParam String message, @PathVariable String sToken) {
//        WebSocketServer.se(message,sToken);
        return new ResponseMessage(1,"sounds great");
    }




}
