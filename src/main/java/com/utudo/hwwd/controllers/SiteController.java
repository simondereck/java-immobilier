package com.utudo.hwwd.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.CodeSms;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.residenceModel.Residence;
import com.utudo.hwwd.models.searchModel.MapSearchModel;
import com.utudo.hwwd.service.impl.*;
import com.utudo.hwwd.service.impl.residence.ResidenceServiceImpl;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
@EnableAutoConfiguration
public class SiteController extends MainController {

    @Resource
    AnnonceServiceImpl annonceService;


    @Resource
    UserServiceImpl userService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    PartnerServiceImpl partnerService;


    @Resource
    PageEditorServiceImpl pageEditorService;


    @Resource
    ResidenceServiceImpl residenceService;


    @RequestMapping("/index/data")
    public Object indexData(){
        HashMap<String,Object> data = new HashMap<>();

        try{
            ArrayList<Annonce> annonces = annonceService.getLastedAnnonces();
            ArrayList<Object> lids = new ArrayList<>();
            ArrayList<Object> uids = new ArrayList<>();
            HashMap<String, Ville> villes = new HashMap<String, Ville>();
            HashMap<String, User> users = new HashMap<>();
            if (annonces.size()>0){
                ObjectMapper objectMapper = new ObjectMapper();
                for (int i = 0; i < annonces.size(); i++) {
                    if (annonces.get(i).getImgsJson()!=null){
                        ArrayList<String> imageList = objectMapper.readValue(annonces.get(i).getImgsJson(),ArrayList.class);
                        if (imageList!=null&&imageList.size()>0){
                            for (int j = 0; j < imageList.size(); j++) {
                                imageList.set(j,"/partners/"+annonces.get(i).getPid()+"/"+imageList.get(j));
                            }
                            annonces.get(i).setImages(imageList);
                        }
                    }
                    lids.add(annonces.get(i).getLocation());
                }
            }


            ArrayList<Needs> needs = needsService.findLastedNeeds();
            data.put("needs",needs);

            needs.forEach(n->{
                lids.add(n.getLocation());
                uids.add(n.getUid());
            });

            ArrayList<Residence> residences = residenceService.findLastedResidences();
            if (residences!=null){
                residences.forEach(r->{
                    lids.add(r.getVille());
                });
            }

            villeService.getVillesByIds(lids).forEach(v->{
                villes.put(v.getId()+"",v);
            });

            userService.getUsersByIds(uids).forEach(u->{
                users.put(u.getId()+"",u);
            });


            ArrayList<Partner> partners = partnerService.getLastedPartners();

            for (int i = 0; i < partners.size(); i++) {
                Annonce annonce = annonceService.findOneByPid(partners.get(i).getId());
                if (annonce!=null)
                    partners.get(i).setAnnonce(annonce);
            }



            data.put("status",1);
            data.put("partners",partners);
            data.put("users",users);
            data.put("villes",villes);
            data.put("lastedAnnonces",annonces);
            data.put("rtypes",HwDatas.dataHouse);
            data.put("residences",residences);

        }catch (Exception e){
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping({"/index","/site"})
    public ModelAndView index(ModelAndView mv) throws JsonProcessingException {
        mv.setViewName("site/index");

        return mv;
    }



    @RequestMapping("/test")
    public String test(ModelAndView mv){
        mv.setViewName("site/index");
        String email = "513881204@163.com";
//        String telephone = "0656759257";
        User user = userService.findUserByEmailOrTelephone(email);
//        User user = userService.findUserByTelephone(telephone);
        if (user!=null){
            logger.error(user.getEmail());
//            user.setFtoken(HwTools.hashPassword(user.getEmail()));
//            userService.update(user);
            Person person = new Person(user);

            HttpSession session = HwTools.session();
            session.setAttribute("user", person);
//            Person testUser = HwTools.getCurrentUser();
//            return testUser.toString();

            return user.toString();
        }

        return HwTools.hashPassword("1234");
    }





    @RequestMapping("/site/about")
    public ModelAndView about(ModelAndView mv){
        mv.setViewName("site/about");
        return mv;
    }


    @RequestMapping("/site/fr")
    public ModelAndView fr(){
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/site/zh")
    public ModelAndView zh(){
        return new ModelAndView("redirect:/home");
    }


    @RequestMapping("/home")
    public ModelAndView home(ModelAndView mv){
        mv.setViewName("site/home");
        return mv;
    }

//    @RequestMapping("/error")
//    public ModelAndView errors(ModelAndView mv){
//        mv.setViewName("site/error");
//        return mv;
//    }


    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mv){
        mv.setViewName("site/login");
        return mv;
    }

    @RequestMapping("/signup")
    public ModelAndView signup(ModelAndView mv){
        mv.setViewName("site/signup");
        return mv;
    }

    @RequestMapping("/site/logout")
    public ModelAndView logout(){
        HwTools.session().removeAttribute("user");
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/site/getCode")
    public Object getCode(@RequestParam("telephone") String telephone,@RequestParam("prix") String prix){
        CodeSms sms;
        sms = (CodeSms) HwTools.session().getAttribute("code");
        if (sms!=null){
            if (sms.getTelephone().equals(telephone)){
                return sms;
            }
        }

        sms = new CodeSms();
        String telephoneNo = prix + telephone;
        String code = HwSMS.generateCode();
        String[] telephoneNos = {telephoneNo};
        String[] params = {code,"20"};
        sms.setCode(code);
        sms.setPrix(prix);
        sms.setTelephone(telephone);
        sms.setCtime(HwTools.getTime());
        sms.setUtime(HwTools.getTime());
        HwTools.session().setAttribute("code",sms);
        HwSMS.sendSms(telephoneNos,params);
        return sms;
    }


    @RequestMapping(value = "/site/new/more/data")
    public Object newMoreData(MapSearchModel model) throws JsonProcessingException {

        HashMap<String,Object> data = model.moreToSql(annonceService,"/site/new/more/data");

        if(Integer.parseInt(data.get("status")+"")==1){
            ArrayList<Object> ids = new ArrayList<>();
            HashMap<String,Ville> villes = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<Annonce> annonces = (ArrayList<Annonce>) data.get("annonces");
            for (int i = 0; i < annonces.size(); i++) {
                if (annonces.get(i).getImgsJson()!=null){
                    ArrayList<String> imageList = objectMapper.readValue(annonces.get(i).getImgsJson(),ArrayList.class);
                    if (imageList!=null&&imageList.size()>0){
                        for (int j = 0; j < imageList.size(); j++) {
                            imageList.set(j,"/partners/"+annonces.get(i).getPid()+"/"+imageList.get(j));
                        }
                        annonces.get(i).setImages(imageList);
                    }
                }
                ids.add(annonces.get(i).getLocation());
            }


            villeService.getVillesByIds(ids).forEach(v->{
                villes.put(v.getId()+"",v);
            });

            data.put("villes",villes);
        }

        return data;
    }



    @RequestMapping(value = "/site/partner/more/data")
    public Object partnerMoreData(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);
        SqlHelper sqlHelper = new SqlHelper("hw_partner");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.addAndCondition("status",HwDatas.PERSON_TYPE_USER_ACTIVE);
        BigInteger count = partnerService.getPartnerCount(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setBaseUrl("/site/partner/more/data");
        pageHelper.setLimit(20);
        sqlHelper.setSelect("select * ");
        sqlHelper.setOrder("id","desc");
        sqlHelper.setPageHelper(pageHelper);

        ArrayList<Partner> partners = partnerService.getPartnersBySql(sqlHelper.toSql());
        if (partners!=null && partners.size()>0){
            for (int i = 0; i < partners.size(); i++) {
                Annonce annonce = annonceService.findOneByPid(partners.get(i).getId());
                if (annonce!=null){
                    partners.get(i).setAnnonce(annonce);
                }
            }
            data.put("status",1);
            data.put("partners",partners);
            data.put("pageHelper",pageHelper);
            return data;
        }
        data.put("status",0);

        return data;
    }


    @RequestMapping(value = "/site/needs/more/data")
    public Object needsMoreData(){
        HashMap<String,Object> data = new HashMap<>();

        SqlHelper sqlHelper = new SqlHelper("hw_needs");
        sqlHelper.setSelect("select count(*) ");

        BigInteger count = needsService.getCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(20);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/site/needs/more/data");

        sqlHelper.setSelect("select * ");
        sqlHelper.setOrder("id","desc");
        sqlHelper.setPageHelper(pageHelper);

        ArrayList<Needs> needs = needsService.matchNeedsBySql(sqlHelper.toSql());
        if (needs!=null&&needs.size()>0){
            ArrayList<Object> lids = new ArrayList<>();
            ArrayList<Object> uids = new ArrayList<>();
            HashMap<Long,Object> villes = new HashMap<>();
            HashMap<Long,Object> users = new HashMap<>();

            needs.forEach(n->{
                lids.add(n.getLocation());
                uids.add(n.getUid());
            });

            villeService.getVillesByIds(lids).forEach(v->{
                villes.put(v.getId(),v);
            });

            userService.getUsersByIds(uids).forEach(u->{
                users.put(u.getId(),u);
            });

            data.put("status",1);
            data.put("users",users);
            data.put("villes",villes);
            data.put("needs",needs);
            data.put("rtypes", HwDatas.dataHouse);
            data.put("pageHelper",pageHelper);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/site/new/more")
    public ModelAndView newMore(ModelAndView mv){
        mv.setViewName("site/more");
        return mv;
    }


    @RequestMapping(value = "/site/partner/more")
    public ModelAndView partnerMore(ModelAndView mv){
        mv.setViewName("site/partnerMore");

        return mv;
    }

    @RequestMapping(value = "/site/needs/more")
    public ModelAndView needsMore(ModelAndView mv){
        mv.setViewName("site/needsMore");

        return mv;
    }

    @RequestMapping(value = "/site/partner/center/data")
    public Object partnerCetnerData(){
        HashMap<String,Object> data = new HashMap<>();
        String id = HwTools.request().getParameter("id");

        data.put("status",1);
        return data;
    }

    @RequestMapping(value = "/site/partner/center/{id}")
    public ModelAndView partnerCetner(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("site/partnerCenter");

        return mv;
    }

    @RequestMapping(value = "/site/client")
    public ModelAndView clients(ModelAndView mv){
        mv.setViewName("site/serviceClient");

        return mv;
    }

    @RequestMapping(value = "/site/admin")
    public ModelAndView logoutUser(ModelAndView mv){
        Person admin = (Person) HwTools.session().getAttribute("admin");
        if (admin!=null){
            HwTools.session().setAttribute("user",admin);
            HwTools.session().removeAttribute("admin");
            mv.setViewName("redirect:/admin");
        }else{
            mv.setViewName("redirect:/");
        }
        return mv;
    }

    @RequestMapping(value = "/site/page/data/{id}")
    public Object sitePageData(@PathVariable("id") int id,ModelAndView mv){
        PageEditor pageEditor = pageEditorService.findByTypeAndStatus(id,2);
        HashMap<String,Object> data = new HashMap<>();
        if(pageEditor!=null){
            String temp = HtmlUtils.htmlUnescape(pageEditor.getDescription());
            pageEditor.setDescription(temp);
            data.put("page",pageEditor);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return pageEditor;
    }

    @RequestMapping(value = "/site/page/{id}")
    public ModelAndView sitePageEditor(@PathVariable("id") int id,ModelAndView mv){
        mv.setViewName("site/page");
        mv.addObject("id",id);
        return mv;
    }

}
