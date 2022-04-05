package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.WebMessage;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.models.ResponseMessage;
import com.utudo.hwwd.service.WebMessageServer;
import com.utudo.hwwd.service.impl.AdminServiceImpl;
import com.utudo.hwwd.service.impl.WebMessageServiceImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;

@RestController
public class WebMessageController extends MainController{


    @Resource
    AdminServiceImpl adminService;

    @Resource
    WebMessageServiceImpl webMessageServer;


    @RequestMapping(value = "/admin/WebMessage/index")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("web/index");
        mv.addObject("admins",adminService.getAdminLists());

        mv.addObject("stage", HwDatas.PERSON_TYPE_ADMIN_STAGE);
        mv.addObject("normal",HwDatas.PERSON_TYPE_ADMIN_NORMAL);
        mv.addObject("manage",HwDatas.PERSON_TYPE_ADMIN_MANAGE);
        mv.addObject("super",HwDatas.PERSON_TYPE_ADMIN_SUPER);



        return mv;
    }

    public Object getMessage(){
        return 123;
    }


    @RequestMapping(value = "/admin/WebMessage/sendMessage",method = RequestMethod.POST)
    public Object sendMessage(){
        try{
            HttpServletRequest request = HwTools.request();
            long toAid = Long.parseLong(request.getParameter("toAid"));
            String message = request.getParameter("message");
            WebMessage webMessage = new WebMessage();
            webMessage.setFromAid(HwTools.getCurrentUser().getId());
            webMessage.setToAid(toAid);
            webMessage.setMessage(message);
            webMessageServer.save(webMessage);
            return new ResponseMessage(1,"send success");
        }catch (Exception e){
            return new ResponseMessage(0,"send failed");
        }

    }


    @RequestMapping(value = "/WebMessage/getAdmins")
    public Object getAdminLists(){
        return adminService.getAdminLists();
    }



    @RequestMapping(value = "/admin/WebMessage/getMesage/{id}")
    public Object getMessagges(@PathVariable("id")long id){
        Person person = HwTools.getCurrentUser();
        ArrayList<WebMessage> webMessages = new ArrayList<WebMessage>();
        webMessages.addAll(webMessageServer.getMessageByAidForm(id,person.getId()));
        return webMessages;
    }

    @RequestMapping(value = "/admin/WebMessage/getCount")
    public Object getWebMessageCount(){
        BigInteger count =  webMessageServer.getMessageCount(HwTools.getCurrentUser().getId());
        HttpSession session = HwTools.session();
        session.setAttribute("unready",count);
        return count;
    }


}
