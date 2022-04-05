package com.utudo.hwwd.controllers;


import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwEmail;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.PartnerRdv;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.service.PartnerService;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import com.utudo.hwwd.service.impl.RdvServiceImpl;
import com.utudo.hwwd.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@EnableAutoConfiguration
public class MailController extends MainController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    PartnerServiceImpl partnerService;


    @Autowired
    RdvServiceImpl rdvService;

    @Autowired
    private HwEmail  mailUtils;


    @RequestMapping("/user/forgotPassword")
    public ModelAndView forgotPassword(HttpServletRequest request, ModelAndView mv, User user){
        if ("POST".equalsIgnoreCase(request.getMethod())){
            //do something send email or phone text
            //user.getEmail()
            User userObj = userService.findUserByEmailOrTelephone(user.getEmail());
            if (userObj!=null){
                String token = HwTools.hashPassword(System.currentTimeMillis() + userObj.getId() + "_hwwd");
                userObj.setFtoken(token);
                userService.save(userObj);
                String templatePath =  "mail/forgot.html";
                HashMap<String,Object> data = new HashMap<String,Object>();
                data.put("path","http://www.haiwaihuangdi.com/site/user/resetPassword/"+token);
                try{
                    mailUtils.sendThymeleafMail("重置密码",user.getEmail(),data,templatePath);
                }catch (Exception exception){
                    logger.error("到底哪里出错了:"+exception.toString());
                    //Todo something here send email failed
                }
            }
            mv.setViewName("redirect:/index");
            return mv;
        }
        mv.setViewName("user/forgotPassword");
        return mv;
    }


    @RequestMapping("/partner/forgotPassword")
    public ModelAndView forgotPassword(ModelAndView mv, Partner partner){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Partner partnerObj = partnerService.findUserByEmailOrTelephone(partner.getEmail());
            if (partnerObj!=null){
                String token = HwTools.hashPassword(System.currentTimeMillis() + partnerObj.getId() + "_hwwd");
                partnerObj.setFtoken(token);
                partnerService.save(partnerObj);


                String templatePath =  "mail/forgot.html";
                HashMap<String,Object> data = new HashMap<String,Object>();
                data.put("path","http://www.haiwaihuangdi.com/site/partner/resetPassword/"+token);
                try{
                    mailUtils.sendThymeleafMail("重置密码",partner.getEmail(),data,templatePath);
                }catch (Exception exception){
                    logger.error("到底哪里出错了:"+exception.toString());
                    //Todo something here send email failed
                }

            }
            mv.setViewName("redirect:/index");
            return mv;
        }
        mv.setViewName("partner/forgotPassword");
        return mv;
    }


    @RequestMapping("/partner/admin/house/match/rdv.html")
    public ModelAndView matchRdv(ModelAndView mv, PartnerRdv partnerRdv){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Person person = HwTools.getCurrentUser();
            partnerRdv.setPid(person.getId());
            rdvService.save(partnerRdv);
            //TODO send email

            String templatePath =  "mail/rdv.html";
            HashMap<String,Object> data = new HashMap<String,Object>();
            data.put("rdv",partnerRdv);
            User user = userService.findUserById(partnerRdv.getUid());
            try{
                mailUtils.sendThymeleafMail("Rdv提醒",user.getEmail(),data,templatePath);
            }catch (Exception exception){
                logger.error("到底哪里出错了:"+exception.toString());
                //Todo something here send email failed
            }

            mv.setViewName("redirect:/partner/admin/house/match/detail?id="+partnerRdv.getNid()+"&aid="+partnerRdv.getAid());
            return mv;
        }
        HttpServletRequest request = HwTools.request();
        long aid = Long.parseLong(request.getParameter("aid"));
        long uid = Long.parseLong(request.getParameter("uid"));
        long nid = Long.parseLong(request.getParameter("nid"));
        mv.addObject("aid",aid);
        mv.addObject("uid",uid);
        mv.addObject("nid",nid);
        mv.setViewName("partner/rdv");
        return mv;

    }



    @RequestMapping(value = "/partner/verify")
    public ModelAndView verifyEmail(ModelAndView mv){
        Person person = HwTools.getCurrentUser();
        if (person!=null && person.getType()== HwDatas.PERSON_TYPE_PARTNER){
            mv.setViewName("partners/confirmAccount");
            mv.addObject("email",person.getEmail());
            return mv;
        }
        mv.setViewName("redirect:/partner/login");
        return mv;
    }

    @RequestMapping(value = "/partner/account/sendEmail")
    public boolean partnerSendEmail(){
        Person person = HwTools.getCurrentUser();
        Partner partnerObj = partnerService.findUserByEmailOrTelephone(person.getEmail());
        String token = HwTools.hashPassword(System.currentTimeMillis() + person.getId() + "_hwwd");
        partnerObj.setFtoken(token);
        partnerService.update(partnerObj);
        String templatePath =  "mail/verify.html";
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("path","http://www.haiwaihuangdi.com/site/partner/account/verify/"+partnerObj.getFtoken());
        try{
            mailUtils.sendThymeleafMail("账户确认",partnerObj.getEmail(),data,templatePath);
            return true;
        }catch (Exception exception){
            logger.error("到底哪里出错了:"+exception.toString());
            //Todo something here send email failed
            return false;
        }
    }



    @RequestMapping(value = "/user/verify")
    public ModelAndView userVerifyEmail(ModelAndView mv){
        Person person = HwTools.getCurrentUser();
        if (person!=null && person.getType()== HwDatas.PERSON_TYPE_USER){
            mv.setViewName("users/confirmAccount");
            mv.addObject("email",person.getEmail());
            return mv;
        }
        mv.setViewName("redirect:/user/login");
        return mv;
    }


    @RequestMapping(value = "/user/account/sendEmail")
    public boolean userSendEmail(){
        Person person = HwTools.getCurrentUser();
        User userObj = userService.findUserByEmailOrTelephone(person.getEmail());
        String token = HwTools.hashPassword(System.currentTimeMillis() + person.getId() + "_hwwd");
        userObj.setFtoken(token);
        userService.update(userObj);
        String templatePath =  "mail/verify.html";
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("path","http://www.haiwaihuangdi.com/site/user/account/verify/"+userObj.getFtoken());
        try{
            mailUtils.sendThymeleafMail("账户确认",userObj.getEmail(),data,templatePath);
            return true;
        }catch (Exception exception){
            logger.error("到底哪里出错了:"+exception.toString());
            //Todo something here send email failed
            return false;
        }
    }

}
