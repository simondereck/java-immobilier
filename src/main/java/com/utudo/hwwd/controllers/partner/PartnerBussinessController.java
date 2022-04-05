package com.utudo.hwwd.controllers.partner;


import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.GeocodingFeature;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.service.VilleService;
import com.utudo.hwwd.service.impl.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PartnerBussinessController extends MainController {

    @Resource
    BussinessModelServiceImpl bussinessModelService;

    @Resource
    AnnonceBussinessServiceImpl annonceBussinessService;

    @Resource
    DemandeBussinessServiceImpl demandeBussinessService;

    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;

    @Resource
    PaysServiceImpl paysService;

    @Resource
    VilleServiceImpl villeService;




    @RequestMapping({"/partner/bussiness","/partner"})
    public ModelAndView bussiness(ModelAndView mv,BussinessModel bussinessModel){
        BussinessModel model = bussinessModelService.findModelByUid(HwTools.getCurrentUser().getId());
        if (model==null){
            String method = HwTools.request().getMethod();
            if ("POST".equalsIgnoreCase(method) && model==null){
                bussinessModel.setUid(HwTools.getCurrentUser().getId());
                bussinessModelService.save(bussinessModel);
                mv.setViewName("redirect:/partner/bussiness");
                return mv;
            }
            mv.setViewName("partners/choiceModel");
            return mv;
        }else{
            mv.setViewName("partners/bussiness");
        }
        return mv;
    }


    @RequestMapping(value = "/partner/bussiness/data/sell")
    public Object dataSell(){
        HashMap<String,Object> data = new HashMap<>();
        long uid = HwTools.getCurrentUser().getId();
        SqlHelper sqlHelper = new SqlHelper("hw_annonce_bussiness");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.addAndCondition("pid",uid);
        BigInteger count = annonceBussinessService.getAnnonceCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(10);
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/partner/bussiness");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getAnnonceBySql(sqlHelper.toSql());
        data.put("annonces",annonces);
        if (annonces.size()>0){
            data.put("annonces",annonces);
            data.put("status",1);


            data.put("types",HwDatas.dataTypes);
            ArrayList<Object> houseType = new ArrayList<>();
            houseType.add(HwDatas.dataHoseTypeOne);
            houseType.add(HwDatas.dataHouseTypeTwo);
            houseType.add(HwDatas.dataHouseTypeThree);
            data.put("houseType",houseType);
            data.put("roomType",HwDatas.dataBussinessHouse);

        }else{
            data.put("status",0);
        }
        data.put("pageHelper",pageHelper);
        return data;
    }

    @RequestMapping(value = "/partner/bussiness/data/buy")
    public Object dataBuy(){
        HashMap<String,Object> data = new HashMap<>();

        long uid = HwTools.getCurrentUser().getId();
        SqlHelper sqlHelper = new SqlHelper("hw_bussiness_demander");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.addAndCondition("pid",uid);
        BigInteger count = demandeBussinessService.getDemandeCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(10);
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/partner/bussiness");
        sqlHelper.setSelect("select * ");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }

        sqlHelper.setPageHelper(pageHelper);
        ArrayList<DemandeBussiness> annonces = demandeBussinessService.getDemandeBySql(sqlHelper.toSql());
        if (annonces.size()>0){
            data.put("annonces",annonces);
            data.put("status",1);
            data.put("profession",HwDatas.dataProfession);

        }else{
            data.put("status",0);
        }
        data.put("pageHelper",pageHelper);
        return data;
    }


    @RequestMapping(value = "/partner/bussiness/sell")
    public ModelAndView sellAnnonce(ModelAndView mv, AnnonceBussiness annonceBussiness){
        String method = HwTools.request().getMethod();
        BussinessModel model = bussinessModelService.findModelByUid(HwTools.getCurrentUser().getId());
        if ("POST".equalsIgnoreCase(method)){
            annonceBussiness.setPid(HwTools.getCurrentUser().getId());
            if (annonceBussiness.getImages()!=null&&annonceBussiness.getImages().size()>0){
                ArrayList<String> imgs = (ArrayList<String>) annonceBussiness.getImages();
                HashMap<String,String> imgsUpload = new HashMap<String,String>();
                imgs.forEach(m->{
                    imgsUpload.put(m,m);
                });
                if (annonceBussiness.getCover()==null||annonceBussiness.getCover().equals("")){
                    if (imgs.size()>0){
                        annonceBussiness.setCover(imgs.get(0));
                    }
                }
                annonceBussiness.setImages(new ArrayList<String>(imgsUpload.values()));
                annonceBussiness.setImgsJson(HwTools.toJson(annonceBussiness.getImages()));


            }else{
                annonceBussiness.setStatus(0);
            }
            annonceBussinessService.save(annonceBussiness);
            setLatLng();
            mv.setViewName("redirect:/partner/bussiness");
            return mv;
        }
        mv.addObject("model",model);
        mv.setViewName("partners/sell");
        mv.addObject("houseTypes",HwDatas.dataBussinessHouse);
        return mv;
    }


    public void setLatLng(){
        AnnonceBussiness annonce = annonceBussinessService.getLastInsert(HwTools.getCurrentUser().getId());
        if (annonce!=null){
            Ville ville = villeService.findVilleById(annonce.getLocation());
            MapBoxGeocodingResponse response;
            if (ville!=null){
                response = HwHttpClient.sendGetReqeust(annonce.getAddress()+","+ville.getPostcode());
            }else{
                response = HwHttpClient.sendGetReqeust(annonce.getAddress());
            }

            if (response.getFeatures().size()>0){
                GeocodingFeature geocodingFeature = response.getFeatures().get(0);

                AnnonceLatLng latLngObj = annonceLatLngService.findOnceByPidAndBid(annonce.getPid(),annonce.getId());
                if (latLngObj!=null){
                    latLngObj.setBbox(geocodingFeature.getBbox());
                    latLngObj.setCenter(geocodingFeature.getCenter());
                    latLngObj.setGeometry(geocodingFeature.getGeometry());
                    latLngObj.setBboxJson(HwTools.toJson(latLngObj.getBbox()));
                    latLngObj.setCenterJson(HwTools.toJson(latLngObj.getCenter()));
                    latLngObj.setGeometryJson(HwTools.toJson(latLngObj.getGeometry()));
                    annonceLatLngService.update(latLngObj);
                    annonce.setLatlng(latLngObj.getId());
                }else{
                    AnnonceLatLng annonceLatLng = new AnnonceLatLng();
                    annonceLatLng.setBbox(geocodingFeature.getBbox());
                    annonceLatLng.setBid(annonce.getId());
                    annonceLatLng.setPid(annonce.getPid());
                    annonceLatLng.setCenter(geocodingFeature.getCenter());
                    annonceLatLng.setGeometry(geocodingFeature.getGeometry());
                    annonceLatLngService.save(annonceLatLng);
                    AnnonceLatLng latobj = annonceLatLngService.findOnceByPidAndBid(annonce.getPid(),annonce.getId());
                    annonce.setLatlng(latobj.getId());
                }
                annonceBussinessService.update(annonce);
            }
        }
    }



    @RequestMapping(value = "/partner/bussiness/annonceBussiness/detail/{id}")
    public ModelAndView annonceBussinessDetail(@PathVariable("id") long id,ModelAndView mv){
        mv.addObject("id",id);
        mv.setViewName("partners/annonceBussinessDetail");
        return mv;
    }

    @RequestMapping(value = "/partner/bussiness/demandeBussiness/detail/{id}")
    public ModelAndView demandeBussinessDetail(@PathVariable("id") long id,ModelAndView mv){
        mv.addObject("id",id);
        mv.setViewName("partners/demandeBussinessDetail");
        return mv;
    }


    @RequestMapping(value = "/partner/bussiness/data/demande/bussiness/detail/{id}")
    public Object dataDemandeBussinessDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        long uid = HwTools.getCurrentUser().getId();
        DemandeBussiness annonce = demandeBussinessService.getDemandeByPidAndId(uid,id);
        if (annonce!=null){
            data.put("profession",HwDatas.dataProfession);
            data.put("annonce",annonce);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }



    @RequestMapping(value = "/partner/bussiness/data/annonce/bussiness/detail/{id}")
    public Object dataAnnonceBussinessDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        long uid = HwTools.getCurrentUser().getId();
        AnnonceBussiness annonce = annonceBussinessService.getAnnonceByPidAndId(uid,id);
        if (annonce!=null){
            Ville ville = villeService.getVilleById(annonce.getLocation());
            data.put("ville",ville);
            Pays pays = paysService.getPayById(annonce.getPay());
            data.put("types",HwDatas.dataTypes);
            if (annonce.getTypes()==0){
                data.put("houseType",HwDatas.dataHoseTypeOne);
            }else if (annonce.getTypes()==1){
                data.put("houseType",HwDatas.dataHouseTypeTwo);
            }else if (annonce.getTypes()==2){
                data.put("houseType",HwDatas.dataHouseTypeThree);
            }

            BussinessModel bussinessModel = bussinessModelService.findModelByUid(HwTools.getCurrentUser().getId());
            data.put("model",bussinessModel);
            data.put("roomType",HwDatas.dataBussinessHouse);
            data.put("pays",pays);
            data.put("annonce",annonce);
            data.put("imgUrl","/partners/"+uid+"/");
            data.put("status",1);
        }else{
            data.put("status",0);
        }

        return data;
    }



    @RequestMapping(value = "/partner/bussiness/buy")
    public ModelAndView buyAnnonce(ModelAndView mv, DemandeBussiness demandeBussiness){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            demandeBussinessService.save(demandeBussiness);
            mv.setViewName("redirect:/partner/bussiness");
            return mv;
        }
        mv.setViewName("partners/buy");
        return mv;
    }

    @RequestMapping(value = "/partner/bussiness/annonce/update/{id}/{uid}")
    public ModelAndView updateAnnonceBussiness(@PathVariable("id") long id,@PathVariable("uid") long uid, ModelAndView mv,AnnonceBussiness annonceBussiness){
        mv.addObject("id",id);
        mv.addObject("data-id",uid);
        mv.setViewName("partners/updateAnnonceBussiness");
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonceBussiness.setId(id);
            annonceBussiness.setPid(HwTools.getCurrentUser().getId());
            //update image
            if (annonceBussiness.getImages()!=null&&annonceBussiness.getImages().size()>0){
                ArrayList<String> imgs = (ArrayList<String>) annonceBussiness.getImages();
                HashMap<String,String> imgsUpload = new HashMap<String,String>();
                imgs.forEach(m->{
                    if (m!=null){
                        imgsUpload.put(m,m);
                    }
                });
                if (annonceBussiness.getCover()==null||annonceBussiness.getCover().equals("")){
                    if (imgs.size()>0){
                        annonceBussiness.setCover(imgs.get(0));
                    }
                }
                annonceBussiness.setImages(new ArrayList<String>(imgsUpload.values()));
                annonceBussiness.setImgsJson(HwTools.toJson(annonceBussiness.getImages()));
            }else{
                annonceBussiness.setStatus(0);
            }
            //set location
            Ville ville = villeService.findVilleById(annonceBussiness.getLocation());
            MapBoxGeocodingResponse response;
            if (ville!=null){
                response = HwHttpClient.sendGetReqeust(annonceBussiness.getAddress()+","+ville.getPostcode());
            }else{
                response = HwHttpClient.sendGetReqeust(annonceBussiness.getAddress());
            }
            if (response.getFeatures().size()>0){
                GeocodingFeature geocodingFeature = response.getFeatures().get(0);
                AnnonceLatLng latLngObj = annonceLatLngService.findOnceByPidAndBid(annonceBussiness.getPid(),annonceBussiness.getId());
                if (latLngObj!=null){
                    latLngObj.setBbox(geocodingFeature.getBbox());
                    latLngObj.setCenter(geocodingFeature.getCenter());
                    latLngObj.setGeometry(geocodingFeature.getGeometry());
                    latLngObj.setBboxJson(HwTools.toJson(latLngObj.getBbox()));
                    latLngObj.setCenterJson(HwTools.toJson(latLngObj.getCenter()));
                    latLngObj.setGeometryJson(HwTools.toJson(latLngObj.getGeometry()));
                    annonceLatLngService.update(latLngObj);
                    annonceBussiness.setLatlng(latLngObj.getId());
                }else{
                    AnnonceLatLng annonceLatLng = new AnnonceLatLng();
                    annonceLatLng.setBbox(geocodingFeature.getBbox());
                    annonceLatLng.setBid(annonceBussiness.getId());
                    annonceLatLng.setPid(annonceBussiness.getPid());
                    annonceLatLng.setCenter(geocodingFeature.getCenter());
                    annonceLatLng.setGeometry(geocodingFeature.getGeometry());
                    annonceLatLngService.save(annonceLatLng);
                    AnnonceLatLng latobj = annonceLatLngService.findOnceByPidAndBid(annonceBussiness.getPid(),annonceBussiness.getId());
                    annonceBussiness.setLatlng(latobj.getId());
                }
            }
            if (annonceBussiness.getCtime()==null){
                annonceBussiness.setCtime(annonceBussiness.getUtime());
            }
            annonceBussinessService.update(annonceBussiness);
            mv.setViewName("redirect:/partner/bussiness/annonceBussiness/detail/"+id);
            return mv;
        }
        return mv;
    }


    @RequestMapping(value = "/partner/bussiness/demande/update/{id}/{uid}")
    public ModelAndView updateDemandeBussiness(@PathVariable("id") long id,@PathVariable("uid") long uid, ModelAndView mv,DemandeBussiness demandeBussiness){
        mv.addObject("id",id);
        mv.addObject("data-id",uid);
        mv.setViewName("partners/updateDemandeBussiness");
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            demandeBussiness.setId(id);
            demandeBussiness.setPid(HwTools.getCurrentUser().getId());
            demandeBussinessService.update(demandeBussiness);
            mv.setViewName("redirect:/partner/bussiness/demandeBussiness/detail/"+id);
            return mv;
        }
        return mv;
    }


}
