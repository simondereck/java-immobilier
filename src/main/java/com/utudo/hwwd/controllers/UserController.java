package com.utudo.hwwd.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.service.impl.GarantieServiceImpl;
import com.utudo.hwwd.service.impl.NeedsServiceImpl;
import com.utudo.hwwd.service.impl.UserServiceImpl;

import com.utudo.hwwd.service.impl.VilleServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController extends MainController {

    @Resource
    UserServiceImpl userService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    GarantieServiceImpl garantieService;

    @RequestMapping({"/user/center","/user"})
    public ModelAndView center(ModelAndView mv){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUid(person.getId());
        Garantie garantie = garantieService.findGarantieByUid(person.getId());
        if (needs!=null && garantie!=null){
            if (needs.getLocation()!=null){
                Ville ville = villeService.findVilleById(needs.getLocation());
                mv.addObject("ville",ville);
            }
            ObjectMapper objectMapper = new ObjectMapper();

            if (needs.getImmsJson()!=null){

                HashMap<Integer,Integer> baseMap = null;
                try {
                    baseMap = objectMapper.readValue(needs.getImmsJson(), HashMap.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                needs.setBase(baseMap);
            }

            if (needs.getEnvirJson()!=null){

                HashMap<Integer,Integer> envirMap = null;
                try {
                    envirMap = objectMapper.readValue(needs.getEnvirJson(), HashMap.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                needs.setEnvir(envirMap);
            }
            mv.addObject("needs",needs);
            mv.addObject("rtypes",HwDatas.dataHouse);
            mv.addObject("trans",HwDatas.dataTrans);
            mv.addObject("garantie",garantie);
            mv.addObject("garanties",HwDatas.dataGaranties);
            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("imms",HwDatas.dataFunctions);
            mv.addObject("professions",HwDatas.dataProfession);
            mv.addObject("payMethods",HwDatas.dataPayMethods);

        }else{
            mv.setViewName("redirect:/user/needs");
            return mv;
        }
        mv.setViewName("user/center");

        return mv;
    }

    @RequestMapping("/user/signup")
    public ModelAndView signup(ModelAndView mv,User user){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            User userObj = userService.findUserByTelephone(user.getTelephone());
            if (userObj!=null){
                mv.addObject("user",user);
            }else{
                userService.save(user);
                //Todo should check from here login is right or not
                user.signupLogin();
                mv.setViewName("redirect:/user/needs");
                return mv;
            }
        }
        mv.setViewName("user/signup");
        return mv;
    }

    @RequestMapping("/user/signup/email")
    public ModelAndView signupEmail(ModelAndView mv,User user){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            User userObj = userService.findUserByEmail(user.getEmail());
            if (userObj!=null){
                mv.addObject("user",user);
            }else{
                userService.save(user);
                //Todo should check from here login is right or not
                user.signupLogin();
                mv.setViewName("redirect:/user/needs");
                return mv;
            }
        }
        mv.setViewName("user/signupEmail");
        return mv;
    }





    @RequestMapping("/user/needs")
    public ModelAndView needs(ModelAndView mv, Needs needs, Garantie garantie){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Person person = HwTools.getCurrentUser();
            needs.setUid(person.getId());
            needsService.save(needs);

            Needs userNeeds = needsService.findNeedsByUid(person.getId());
            if (userNeeds!=null){
                garantie.setNid(userNeeds.getId());
                garantie.setUid(person.getId());
                garantieService.save(garantie);
                mv.setViewName("redirect:/user/center");
                return mv;
            }
            //go to some where
        }
        mv.setViewName("user/needs");
        mv.addObject("bases",HwDatas.dataBaseFunctions);
        mv.addObject("rtype", HwDatas.dataHouse);
        mv.addObject("trans",HwDatas.dataTrans);
        mv.addObject("imms",HwDatas.dataFunctions);
        mv.addObject("garanties",HwDatas.dataGaranties);
        mv.addObject("professions",HwDatas.dataProfession);
        return mv;

    }




    @RequestMapping("/user/test")
    public String userTest(){
        Person person = HwTools.getCurrentUser();
        Needs userNeeds = needsService.findNeedsByUid(person.getId());
        Garantie garantie = garantieService.findGarantieByUid(person.getId());
        if (userNeeds!=null){
            garantie.setNid(userNeeds.getId());
            garantie.setUid(person.getId());
            garantieService.save(garantie);
        }
        logger.error("就看对不对吧："+userNeeds.toString()+garantie.toString());
        return userNeeds.toString() + garantie.toString();
    }




    @RequestMapping(value = "/site/user/resetPassword/{token}")
    public ModelAndView resetPassword(@PathVariable("token") String token,ModelAndView mv,User user){
        //save the reset password
        User userObj = userService.findUserByToken(token);
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            userObj.setFtoken("");
            userObj.setPassword(HwTools.hashPassword(user.getPassword()));
            userObj.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
            userService.update(userObj);
            mv.setViewName("redirect:/user/login");
            return mv;
        }

        mv.addObject("user",userObj);
        mv.setViewName("user/resetPassword");
        return mv;
    }


    @RequestMapping("/user/checkUserExist")
    public String checkEmailAndTel(@RequestParam("email") String email){
        User user = userService.findUserByEmailOrTelephone(email);
        if (user!=null){
            return "false";
        }
        return "true";
    }

    @RequestMapping("/user/pay")
    public ModelAndView pay(ModelAndView mv){
        mv.setViewName("user/pay");
        return mv;
    }

    @RequestMapping("/user/set")
    public ModelAndView set(ModelAndView mv,User user){
        Person person = HwTools.getCurrentUser();
        User userObj = userService.findUserById(person.getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            if (!user.getPassword().equals(userObj.getPassword())){
                userObj.setPassword(HwTools.hashPassword(user.getPassword()));
            }
            userObj.setPrenom(user.getPrenom());
            userObj.setNom(user.getNom());
            userService.update(userObj);
        }
        mv.setViewName("user/set");
        mv.addObject("user",userObj);

        return mv;
    }

    @RequestMapping("/user/login")
    public ModelAndView login(ModelAndView mv, User user){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            User userObj = userService.findUserByEmailOrTelephone(user.getEmail());
            if (userObj!=null){
                if(userObj.login(user)){
                    mv.setViewName("redirect:/user/center");
                    return mv;
                }
            }
            user.setStatus(0);
            user.setMessage("账户或密码不正确");
        }
        mv.addObject("user",user);
        mv.setViewName("user/login");
        return mv;
    }


    @RequestMapping("/ip")
    public String index(HttpServletRequest request) {
        String ip=request.getRemoteAddr();
        String host = request.getRemoteHost();

//        Visitor visitor=repository.findByIp(ip);
//        if(visitor==null){
//            visitor=new Visitor();
//            visitor.setIp(ip);
//            visitor.setTimes(1);
//        }else {
//            visitor.setTimes(visitor.getTimes()+1);
//        }
//        repository.save(visitor);
        return "I have been seen ip :" + ip + " et remote host:"+host;
//        return "I have been seen ip "+visitor.getIp()+" "+visitor.getTimes()+" times.";
    }




    @RequestMapping(value = "/site/user/account/verify/{token}")
    public ModelAndView accountVerify(@PathVariable("token") String token, ModelAndView mv){
        User user = userService.findUserByToken(token);
        user.setFtoken("");
        user.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
        userService.update(user);
        user.signupLogin();
        mv.setViewName("redirect:/user/center");
        return mv;
    }


    @RequestMapping(value = "/site/user/check/email")
    public HashMap<String, Object> checkEmail(){
        HttpServletRequest request = HwTools.request();
        String email = request.getParameter("email");
        HashMap<String,Object> result = new HashMap<>();
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result.put("message","* 这不是一个有效的email");
            result.put("status",0);
            return result;
        }

        User user = userService.findUserByEmail(email);
        if (user!=null){
            result.put("message","* 该邮箱已经被注册过");
            result.put("status",0);
            return result;
        }

        result.put("status",1);
        return result;
    }



    @RequestMapping(value = "/site/user/check/telephone")
    public HashMap<String,Object> checkTelephone(){
        HttpServletRequest request = HwTools.request();
        String telephone = request.getParameter("telephone");
        HashMap<String,Object> result = new HashMap<>();
        User user = userService.findUserByTelephone(telephone);
        if (user!=null){
            result.put("message","* 该手机号码已经被注册过了");
            result.put("status",0);
            return result;
        }

        result.put("status",1);
        return result;
    }


    @RequestMapping(value = "/user/profile")
    public ModelAndView addProfile(ModelAndView mv,User user){
        User userObj = userService.findUserById(HwTools.getCurrentUser().getId());
        if (userObj!=null){
            if (userObj.getEmail()==null||userObj.getEmail().equals("")){
                userObj.setEmail(null);
            }
            if (userObj.getTelephone()==null||userObj.getTelephone().equals("")){
                userObj.setTelephone(null);
            }
        }
        mv.addObject("user",userObj);
        String method = HwTools.request().getMethod();
        mv.setViewName("users/profile");
        if ("POST".equalsIgnoreCase(method)){
            if (user.getTelephone()==null && user.getEmail()==null){
                return mv;
            }

            User userOld;
            if (user.getEmail()!=null){
                userOld = userService.findUserByEmail(user.getEmail());
                if (userOld==null){
                    userObj.setEmail(user.getEmail());
                }else{
                    return mv;
                }
            }

            if (user.getTelephone()!=null){
                userOld = userService.findUserByTelephone(user.getTelephone());
                if (userOld==null){
                    userObj.setTelephone(user.getTelephone());
                }else{
                    return mv;
                }
            }

            userObj.setStatus(HwDatas.PERSON_TYPE_USER_BLOCK);
            userService.update(userObj);
            userObj.signupLogin();
            mv.setViewName("redirect:/user/center");
            return mv;
        }
        return mv;
    }


}
