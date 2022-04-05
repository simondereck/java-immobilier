package com.utudo.hwwd.controllers.admin;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Cobber;
import com.utudo.hwwd.models.CobberShop;
import com.utudo.hwwd.service.impl.CobberServiceImpl;
import com.utudo.hwwd.service.impl.CobberShopServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;

@RestController
public class AdminCobberController extends MainController {

    @Resource
    CobberServiceImpl cobberService;

    @Resource
    CobberShopServiceImpl shopService;


    @RequestMapping(value = "/admin/cobber/lists")
    public ModelAndView cobberList(ModelAndView mv){
        mv.setViewName("cobber/cobberLists");

        SqlHelper sqlHelper = new SqlHelper("hw_cobber");
        sqlHelper.setSelect("select count(*) ");
        BigInteger count = cobberService.getCobberCount(sqlHelper.toSql());

        String page = HwTools.request().getParameter("page");
        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(20);
        pageHelper.setCount(Long.parseLong(count+""));
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setBaseUrl("/admin/cobber/lists");
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<Cobber> cobbers = cobberService.getCobbersBySql(sqlHelper.toSql());

        mv.addObject("cobbers",cobbers);
        mv.addObject("pageHelper",pageHelper);

        return mv;
    }


    @RequestMapping(value = "/admin/cobber/shop")
    public ModelAndView cobberShop(ModelAndView mv){

        SqlHelper sqlHelper = new SqlHelper("hw_cobber_shop");
        sqlHelper.setSelect("select count(*) ");

        BigInteger count = shopService.getCobberShopCount(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(15);
        pageHelper.setBaseUrl("/admin/cobber/shop");
        pageHelper.setCount(Long.parseLong(count+""));
        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select * ");
        shopService.getCobbersShopBySql(sqlHelper.toSql());
        mv.addObject("pageHelper",pageHelper);

        mv.setViewName("cobber/cobberShop");

        return mv;
    }


    @RequestMapping(value = "/admin/cobber/detail/{id}")
    public ModelAndView cobberDetail(ModelAndView mv,@PathVariable("id") long id){
        mv.setViewName("cobber/cobberDetail");
        Cobber cobber = cobberService.findCobberById(id);
        CobberShop shop = shopService.findCobberByUid(id);
        if (shop!=null){
            shop.setDescription(HtmlUtils.htmlUnescape(shop.getDescription()));
        }
        mv.addObject("types", HwDatas.dataRecommand);
        mv.addObject("cobber",cobber);
        mv.addObject("shop",shop);
        return mv;
    }

    @RequestMapping(value = "/admin/cobber/update/{id}")
    public ModelAndView cobberUpdate(ModelAndView mv,@PathVariable("id") long id,Cobber cobberObj){
        Cobber cobber = cobberService.findCobberById(id);
        String method =  HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            if (!cobber.getPassword().equals(cobberObj.getPassword())){
                cobber.setPassword(HwTools.hashPassword(cobberObj.getPassword()));
            }
            cobber.setTelephone(cobberObj.getTelephone());
            cobber.setEmail(cobberObj.getEmail());
            cobber.setPrenom(cobberObj.getPrenom());
            cobber.setNom(cobberObj.getNom());
            cobber.setType(cobberObj.getType());
            cobber.setStatus(cobberObj.getiStatus());
            cobberService.update(cobber);
            mv.setViewName("redirect:/admin/cobber/detail/"+cobber.getId());
            return mv;
        }
        mv.setViewName("cobber/cobberUpdate");
        mv.addObject("cobber",cobber);
        return mv;
    }


    @RequestMapping(value = "/admin/cobber/shop/update/{id}")
    public ModelAndView cobberShopUpdate(ModelAndView mv,@PathVariable("id") long id,CobberShop cobberShop){
        CobberShop shop = shopService.findCobberById(id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            shop.setAddress(cobberShop.getAddress());
            String temp = HtmlUtils.htmlEscape(cobberShop.getDescription());
            shop.setDescription(temp);
            shop.setEmail(cobberShop.getEmail());
            shop.setTelephone(cobberShop.getTelephone());
            shop.setName(cobberShop.getName());
            shop.setType(cobberShop.getType());
            shop.setStatus(cobberShop.getiStatus());
            shopService.update(shop);
            mv.setViewName("redirect:/admin/cobber/detail/"+shop.getCid());
            return mv;
        }
        shop.setDescription(HtmlUtils.htmlUnescape(shop.getDescription()));
        mv.addObject("types",HwDatas.dataRecommand);
        mv.setViewName("cobber/cobberShopUpdate");
        mv.addObject("shop",shop);
        return mv;
    }

    @RequestMapping(value = "/admin/cobber/login/{id}")
    public ModelAndView cobberLogin(ModelAndView mv,@PathVariable("id") long id){
        mv.setViewName("redirect:/coo/admin/login/"+id);
        return mv;
    }


}
