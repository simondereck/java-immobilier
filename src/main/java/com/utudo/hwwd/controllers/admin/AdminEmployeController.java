package com.utudo.hwwd.controllers.admin;

import com.utudo.hwwd.controllers.MainController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AdminEmployeController extends MainController {


    @RequestMapping(value = "/admin/alarm/employe/index")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("alarm/employe");

        return mv;
    }

}
