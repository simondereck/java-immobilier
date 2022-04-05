package com.utudo.hwwd.controllers;


import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.WebSocketServer;
import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;


@RestController
public class WebSocketController extends MainController{

    @Autowired
    WebSocketServer webSocketServer;

    @Resource
    AdminServiceImpl adminService;



    @RequestMapping(value = "/admin/websocket/count")
    public Object count(){
        return webSocketServer.webSocketSets.size();
    }

    @RequestMapping(value = "/admin/websocket/index")
    public Object index(){
        webSocketServer.webSocketSets.forEach(w->{
            try {
                w.session.getBasicRemote().sendText("xxxx hello yea");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return 56789;
    }


    @RequestMapping(value = "/admin/websocket/userLists")
    public Object userLists(){
        HashMap<String, Person> data = new HashMap<>();
        webSocketServer.webSocketSets.forEach(w->{
            data.put(w.session.getId()+"",w.person);
        });
        return data;
    }


    @RequestMapping("/admin/websocket/sendMessage/")
    public Object sendMessage(){
        HwTools.request().getParameter("message");
        HwTools.request().getParameter("aid");
        HwTools.request().getParameter("token");
        return 123;
    }


    @RequestMapping("/admin/clients")
    public ModelAndView clients(ModelAndView mv){
        mv.setViewName("admin/clients");
        Admin admin = adminService.findAdminById(HwTools.getCurrentUser().getId());
        if (admin.getToken()!=null){
            String  token = HwTools.hashPassword(HwTools.getCurrentUser().getId()+"_hwwd_admin");
            admin.setToken(token);
            adminService.update(admin);
            mv.addObject("token",token);
        }else{
            mv.addObject("token",admin.getToken());
        }

        mv.addObject("count", webSocketServer.webSocketSets.size());
        return mv;
    }


}
