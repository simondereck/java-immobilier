package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.neuf.NeufModel;
import com.utudo.hwwd.service.impl.neuf.NeufServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class NeufController extends MainController{

    @Resource
    NeufServiceImpl neufService;

    @RequestMapping(value = "/neuf/houseType")
    public Object getNeufHouseType(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("types",HwDatas.dataNeufHouseTypes);
        data.put("status",1);
        return data;
    }

    @RequestMapping(value = "/neuf/data/index")
    public Object indexData(){
        HashMap<String,Object> data = new HashMap<>();

        return data;
    }

    @RequestMapping(value = "/neuf/index")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("neuf/frontend/index");
        return mv;
    }


    @RequestMapping(value = "/neuf/data/houses")
    public Object dataHouses(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(NeufModel.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select count(*)");
        BigInteger count = neufService.getCountBySql(sqlHelper.toSql());
        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/neuf/data/houses");
        pageHelper.setLimit(15);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }

        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<NeufModel> items = neufService.getNeufBySql(sqlHelper.toSql());

        if (items!=null){
            data.put("pageHelper",pageHelper);
            data.put("items",items);
            data.put("status",1);
        }else{
            data.put("status",0);
        }

        return data;
    }

    @RequestMapping(value = "/neuf/houses")
    public ModelAndView houses(ModelAndView mv){
        mv.setViewName("neuf/frontend/houses");

        return mv;
    }
}
