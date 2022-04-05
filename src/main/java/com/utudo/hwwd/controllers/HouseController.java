package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.service.VilleService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class HouseController extends MainController{

    @Resource
    VilleService villeService;

    @RequestMapping("/admin/ville/create_ville")
    public ModelAndView create_ville(ModelAndView mv, Ville ville){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            villeService.save(ville);
            mv.setViewName("redirect:/admin/villes");
            return mv;
        }

        mv.setViewName("house/create_ville");
        return mv;
    }


    @RequestMapping("/admin/villes")
    public ModelAndView villes(ModelAndView mv){
        mv.setViewName("house/villes");
        ArrayList<Ville> villeList = (ArrayList<Ville>) villeService.getVilleList();
        mv.addObject("villes",villeList);
        return mv;
    }


    @RequestMapping("/user/location/keywords")
    public List<Ville> keyWords(@RequestParam("keyword") String keyword){
        return villeService.findVilleByKeywords(keyword);
    }




    @RequestMapping("/house/detail/{id}.html")
    public ModelAndView HouseDetail(@PathVariable("id") long id,ModelAndView mv){
        logger.error("错在哪里，错在哪里"+id);
        mv.setViewName("house/houseDetail");
        return mv;
    }
}
