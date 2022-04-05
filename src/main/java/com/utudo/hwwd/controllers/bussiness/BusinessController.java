package com.utudo.hwwd.controllers.bussiness;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.Geometry;
import com.utudo.hwwd.models.searchModel.MapBussinessSearchModel;
import com.utudo.hwwd.service.AnnonceBussinessService;
import com.utudo.hwwd.service.impl.*;
import org.dom4j.rule.Mode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class BusinessController {


    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    ApplyModelServiceImpl applyModelService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    AnnonceBussinessServiceImpl annonceBussinessService;

    @Resource
    DemandeBussinessServiceImpl demandeBussinessService;


    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;

    @Resource
    PaysServiceImpl paysService;


    @RequestMapping({"/","/bussiness"})
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("bussiness/index");
        return mv;
    }



    @RequestMapping(value = "/bussiness/signup")
    public ModelAndView signup(ModelAndView mv,Partner partner){

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Partner partnerOld = partnerService.findUserByEmail(partner.getEmail());
            if (partnerOld!=null){
                mv.addObject("partner",partner);
            }else{
                partnerService.save(partner);
                partner.signupLogin();
                mv.setViewName("redirect:/partner/bussiness");
                return mv;
            }
        }

        mv.addObject("partner",partner);
        mv.setViewName("bussiness/signup");
        return mv;
    }

    @RequestMapping(value = "/bussiness/login")
    public ModelAndView login(ModelAndView mv,Partner partner){

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){

            Partner partnerObj = partnerService.findUserByEmailOrTelephone(partner.getEmail());

            if (partnerObj.login(partner)){
                //to center
                mv.setViewName("partners/bussiness");
                return mv;
            }else{
                partner.setError(0);
                partner.setMessage("账户或密码不对");
            }
        }
        mv.addObject("user",partner);
        mv.setViewName("bussiness/login");
        return mv;
    }


    @RequestMapping(value = "/bussiness/platform")
    public ModelAndView platform(ModelAndView mv,Partner partner){

        mv.setViewName("bussiness/platform");
        return mv;
    }



    @RequestMapping(value = "/bussiness/join/us")
    public ModelAndView joinUs(ModelAndView mv, ApplyModel applyModel){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            applyModel.setType(HwDatas.APPLY_PERSON_NORMAL);
            applyModel.setStatus(HwDatas.APPLY_MESSAGE_STATUS_UNREAD);
            applyModelService.save(applyModel);
            mv.addObject("success",1);
        }
        mv.setViewName("bussiness/joinUs");
        return mv;
    }


    @RequestMapping(value = "/bussiness/student/entreship")
    public ModelAndView studentEntreship(ModelAndView mv,ApplyModel applyModel){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            applyModel.setType(HwDatas.APPLY_PERSON_STUDENT);
            applyModel.setStatus(HwDatas.APPLY_MESSAGE_STATUS_UNREAD);
            applyModelService.save(applyModel);
            mv.addObject("success",1);
        }
        mv.setViewName("bussiness/student");
        return mv;
    }



    @RequestMapping(value = "/bussiness/data/lastInsert")
    public Object lastInsertData(){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getLastedAnnonces();
        if (annonces!=null){
            data.put("annonces",annonces);
            data.put("status",1);
            data.put("types",HwDatas.dataTypes);
            ArrayList<Object> houseType = new ArrayList<>();
            houseType.add(HwDatas.dataHoseTypeOne);
            houseType.add(HwDatas.dataHouseTypeTwo);
            houseType.add(HwDatas.dataHouseTypeThree);
            data.put("houseType",houseType);
            data.put("roomType",HwDatas.dataBussinessHouse);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @RequestMapping(value = "/bussiness/data/lastDemande")
    public Object lastInsertDemande(){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<DemandeBussiness> annonces = demandeBussinessService.getLastedDemande();
        if (annonces!=null){
            data.put("annonces",annonces);
            data.put("status",1);
            data.put("profession",HwDatas.dataProfession);
            return data;
        }
        data.put("status",0);
        return data;

    }


    @RequestMapping(value = "/bussiness/data/platform")
    public Object lastPlatformData(){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getLastedPlatform();
        if (annonces!=null){
            data.put("annonces",annonces);
            data.put("status",1);
            data.put("types",HwDatas.dataTypes);
            ArrayList<Object> houseType = new ArrayList<>();
            houseType.add(HwDatas.dataHoseTypeOne);
            houseType.add(HwDatas.dataHouseTypeTwo);
            houseType.add(HwDatas.dataHouseTypeThree);
            data.put("houseType",houseType);
            data.put("roomType",HwDatas.dataBussinessHouse);
            return data;
        }
        data.put("status",0);
        return data;
    }



    @RequestMapping(value = "/site/bussiness/data/annonce/more")
    public Object moreBussinessAnnonce(){
        HashMap<String,Object> data = new HashMap<>();

        SqlHelper sqlHelper = new SqlHelper("hw_annonce_bussiness");
        sqlHelper.setOrder("id","desc");
        sqlHelper.setSelect("select count(*) ");
        BigInteger count = annonceBussinessService.getAnnonceCountBySql(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(20);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }
        pageHelper.setBaseUrl("/site/bussiness/more");
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<AnnonceBussiness> annnonces = annonceBussinessService.getAnnonceBySql(sqlHelper.toSql());
        data.put("status",1);
        data.put("annonces",annnonces);
        data.put("pageHelper",pageHelper);

        data.put("types",HwDatas.dataTypes);
        ArrayList<Object> houseType = new ArrayList<>();
        houseType.add(HwDatas.dataHoseTypeOne);
        houseType.add(HwDatas.dataHouseTypeTwo);
        houseType.add(HwDatas.dataHouseTypeThree);
        data.put("houseType",houseType);
        data.put("roomType",HwDatas.dataBussinessHouse);

        return data;

    }


    @RequestMapping(value = "/site/bussiness/data/demande/more")
    public Object moreBussinessDemande(){
        HashMap<String,Object> data = new HashMap<>();

        SqlHelper sqlHelper = new SqlHelper("hw_bussiness_demander");
        sqlHelper.setOrder("id","desc");
        sqlHelper.setSelect("select count(*) ");
        BigInteger count = demandeBussinessService.getDemandeCountBySql(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(20);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }
        pageHelper.setBaseUrl("/site/bussiness/more");
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<DemandeBussiness> annnonces = demandeBussinessService.getDemandeBySql(sqlHelper.toSql());
        data.put("status",1);
        data.put("annonces",annnonces);
        data.put("pageHelper",pageHelper);
        data.put("profession",HwDatas.dataProfession);

        return data;

    }



    @RequestMapping(value = "/site/bussiness/more")
    public ModelAndView annoncesBussiness(ModelAndView mv){
        mv.setViewName("bussiness/more");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            mv.addObject("page",page);
        }else{
            mv.addObject("page",0);
        }
        return mv;
    }

    @RequestMapping(value = "/site/demande/more")
    public ModelAndView demandeBussiness(ModelAndView mv){
        mv.setViewName("bussiness/demandeMore");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            mv.addObject("page",page);
        }else{
            mv.addObject("page",0);
        }
        return mv;
    }


    @RequestMapping(value = "/bussiness/house/detail/data")
    public Object dataAnnonceBussinessDetail(){
        HashMap<String,Object> data = new HashMap<>();
        String id = HwTools.request().getParameter("id");
        if (id!=null){
            AnnonceBussiness annonce = annonceBussinessService.findAnnonceById(Long.parseLong(id));
            if (annonce!=null){
                data.put("status",1);
                data.put("annonce",annonce);
            }else{
                data.put("status",0);
            }
        }else{
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping(value = "/site/map/bussiness/annonces")
    public Object mapAnnonces(){
        HashMap<String,Object> data = new HashMap();
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.findAllAnnonces();
        ArrayList<Object> ids = new ArrayList<>();
        HashMap<Long,Object> dataAnnonces = new HashMap<>();

        if (annonces!=null && annonces.size()>0){
            for (int i = 0; i < annonces.size(); i++) {
                ids.add(annonces.get(i).getLatlng());
                dataAnnonces.put(annonces.get(i).getId(),annonces.get(i));
            }
            data.put("annonces",dataAnnonces);
            SqlHelper sqlHelper = new SqlHelper("hw_annonce_lat_lng");
            sqlHelper.addInCondition("id",ids);
            sqlHelper.setLimit("");
            ArrayList<AnnonceLatLng> latlngs = annonceLatLngService.findAnnonceLatLngBySql(sqlHelper.toSql());
            data.put("latlngs",latlngs);

            data.put("types",HwDatas.dataTypes);
            ArrayList<Object> houseType = new ArrayList<>();
            houseType.add(HwDatas.dataHoseTypeOne);
            houseType.add(HwDatas.dataHouseTypeTwo);
            houseType.add(HwDatas.dataHouseTypeThree);
            data.put("houseType",houseType);
            data.put("roomType",HwDatas.dataBussinessHouse);

            data.put("status",1);
            return data;
        }

        data.put("status",0);
        return data;
    }


    @RequestMapping(value = "/site/bussiness/map/search")
    public Object bussinessMapSearch(MapBussinessSearchModel model){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getAnnonceBySql(model.toSql());
        ArrayList<Object> ids = new ArrayList<>();
        data.put("sql",model.toSql());
        if (annonces!=null && annonces.size()>0){
            for (int i = 0; i < annonces.size(); i++) {
                ids.add(annonces.get(i).getLatlng());
            }
            data.put("annonces",annonces);
            SqlHelper sqlHelper = new SqlHelper("hw_annonce_lat_lng");
            sqlHelper.addInCondition("id",ids);
            sqlHelper.setLimit("");
            ArrayList<AnnonceLatLng> latlngs = annonceLatLngService.findAnnonceLatLngBySql(sqlHelper.toSql());
            data.put("latlngs",latlngs);

            data.put("types",HwDatas.dataTypes);
            ArrayList<Object> houseType = new ArrayList<>();
            houseType.add(HwDatas.dataHoseTypeOne);
            houseType.add(HwDatas.dataHouseTypeTwo);
            houseType.add(HwDatas.dataHouseTypeThree);
            data.put("houseType",houseType);
            data.put("roomType",HwDatas.dataBussinessHouse);

            data.put("status",1);
            return data;
        }

        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/bussiness/house/detail/{id}.html")
    public ModelAndView annonceBussinessDetail(@PathVariable("id") long id,ModelAndView mv) throws JsonProcessingException {
        mv.setViewName("bussiness/annonceDetail");
        mv.addObject("id",id);

        AnnonceBussiness annonce = annonceBussinessService.findAnnonceById(id);
        if (annonce!=null){
            ObjectMapper objectMapper = new ObjectMapper();
            if (annonce.getImgsJson() != null) {
                ArrayList<String> imageList = objectMapper.readValue(annonce.getImgsJson(), ArrayList.class);
                if (imageList != null && imageList.size() > 0) {
                    for (int j = 0; j < imageList.size(); j++) {
                        imageList.set(j, "/partners/" + annonce.getPid() + "/" + imageList.get(j));
                    }
                    annonce.setImages(imageList);
                }
            }


            Optional<AnnonceLatLng> dataLatLng = annonceLatLngService.findOneById(annonce.getLatlng());

            if (dataLatLng.isPresent()){
                AnnonceLatLng latLng = dataLatLng.get();
                try{
                    if (latLng.getBboxJson() != null) {
                        ArrayList<Float> bbox = objectMapper.readValue(latLng.getBboxJson(), ArrayList.class);
                        latLng.setBbox(bbox);
                    }

                    if (latLng.getCenterJson()!=null){
                        ArrayList<Float> center = objectMapper.readValue(latLng.getCenterJson(),ArrayList.class);
                        latLng.setCenter(center);
                    }

                    if (latLng.getGeometryJson()!=null){
                        Geometry geometry = objectMapper.readValue(latLng.getGeometryJson(),Geometry.class);
                        latLng.setGeometry(geometry);
                    }

                }catch (JsonProcessingException e){
                    e.printStackTrace();
                }

                mv.addObject("latLng",dataLatLng.get());
            }

            Pays pays = paysService.getPayById(annonce.getPay());
            mv.addObject("pays",pays);
            mv.addObject("annonce",annonce);
        }

        mv.addObject("types",HwDatas.dataTypes);
        ArrayList<Object> houseType = new ArrayList<>();
        houseType.add(HwDatas.dataHoseTypeOne);
        houseType.add(HwDatas.dataHouseTypeTwo);
        houseType.add(HwDatas.dataHouseTypeThree);
        mv.addObject("houseType",houseType);
        mv.addObject("roomType",HwDatas.dataBussinessHouse);

        return mv;
    }


    @RequestMapping(value = "/bussiness/demande/detail/data/{id}")
    public Object dataDemandeBussinessDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        DemandeBussiness annonce = demandeBussinessService.findDemandeById(id);
        if (annonce!=null){
            data.put("profession",HwDatas.dataProfession);
            data.put("annonce",annonce);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }


    @RequestMapping(value = "/bussiness/demande/detail/{id}.html")
    public ModelAndView demandeBussinessDetail(@PathVariable("id") long id, ModelAndView mv){
        mv.setViewName("bussiness/demandeDetail");
        mv.addObject("id",id);
        return mv;
    }

    @RequestMapping(value = "/bussiness/test")
    public ModelAndView test(ModelAndView mv){
        mv.setViewName("partners/sell1");
        return mv;
    }

}
