package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.LocationRegion;
import com.utudo.hwwd.service.impl.LocationRegionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class RegionController {


    @Resource
    LocationRegionServiceImpl locationRegionService;

    @RequestMapping(value = "/region/add")
    public ModelAndView setLocation(ModelAndView mv, LocationRegion locationRegion){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            LocationRegion region = new LocationRegion();
            region.setCode(locationRegion.getCode());
            region.setName(locationRegion.getName());
            region.setLat(locationRegion.getLat());
            region.setLng(locationRegion.getLng());
            locationRegionService.save(region);
            mv.setViewName("redirect:/region/lists");
            return mv;
        }

        mv.setViewName("region/add");
        return mv;
    }


    @RequestMapping(value = "/region/lists")
    public ModelAndView regionLists(ModelAndView mv){
        mv.setViewName("region/lists");

        return mv;
    }
}
