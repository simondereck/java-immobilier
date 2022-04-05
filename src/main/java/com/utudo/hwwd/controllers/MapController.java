package com.utudo.hwwd.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.Geometry;
import com.utudo.hwwd.models.searchModel.LocationAnnonceSearchModel;
import com.utudo.hwwd.models.searchModel.MapBussinessSearchModel;
import com.utudo.hwwd.service.VilleService;
import com.utudo.hwwd.service.impl.*;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class MapController extends MainController{


    @Resource
    LocationRegionServiceImpl locationRegionService;

    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    UniversiteServiceImpl universiteService;


    @RequestMapping("/location/{ville}/{code}.html")
    public ModelAndView searchVille(@PathVariable("ville") String ville,@PathVariable("code") String codeString,ModelAndView mv) throws JsonProcessingException {
        String delimeter = "-";
        String[] temp;
        temp = ville.split(delimeter);
        if (temp[temp.length-1]!=null){
            long code = Long.parseLong(temp[temp.length-1]);
            LocationRegion region = locationRegionService.findOneByCode(code);
            if (region!=null){
                mv.addObject("region",region);
            }else{
                Ville tempVille = villeService.findOneByCode((int)code);
                if (tempVille!=null){
                    mv.addObject("region",tempVille);
                }
            }


            ArrayList<Object> ids = (ArrayList<Object>) villeService.getIdsByCode((int) code);
            SqlHelper sqlHelper = new SqlHelper("hw_annonce");
            sqlHelper.addInCondition("location",ids);
            sqlHelper.addAndCondition(">","status","0");
            sqlHelper.setSelect("select count(*) ");
            sqlHelper.setOrder("id","desc");
            sqlHelper.setLimit(" ");
            BigInteger count  = annonceService.getCountBySql(sqlHelper.toSql());
            //get count

            PageHelper pageHelper = new PageHelper();
            pageHelper.setCount(Long.parseLong(count+""));
            pageHelper.setLimit(8);
            if (HwTools.request().getParameter("page")!=null){
                int currentPage = Integer.parseInt(HwTools.request().getParameter("page"));
                pageHelper.setCurrentPage(currentPage);
            }else{
                pageHelper.setCurrentPage(0);
            }


            pageHelper.setBaseUrl("/location/"+ville+"/"+codeString + ".html");
            pageHelper.getPages();
            mv.addObject("pageHelper",pageHelper);


            sqlHelper.setSelect("select * ");
            sqlHelper.setPageHelper(pageHelper);
            ArrayList<Annonce> annonces = annonceService.getAnnonceByPage(sqlHelper.toSql());
            ArrayList<Object> lids = new ArrayList<>();
            ArrayList<Object> vids = new ArrayList<>();
            HashMap<String,Ville> villes = new HashMap<>();
            for (int i = 0; i < annonces.size(); i++) {
                ObjectMapper objectMapper = new ObjectMapper();

                if (annonces.get(i).getImgsJson()!=null){
                    ArrayList<String> imageList = objectMapper.readValue(annonces.get(i).getImgsJson(),ArrayList.class);
                    if (imageList!=null&&imageList.size()>0){
                        for (int j = 0; j < imageList.size(); j++) {
                            imageList.set(j,"/partners/"+annonces.get(i).getPid()+"/"+imageList.get(j));
                        }
                        annonces.get(i).setImages(imageList);
                    }
                }
                if (annonces.get(i).getLatlng()!=0){
                    lids.add(annonces.get(i).getLatlng());
                }
                vids.add(annonces.get(i).getLocation());
            }

            if (vids.size()>0){
                villeService.getVillesByIds(vids).forEach(v->{
                    villes.put(v.getId()+"",v);
                });

            }

            mv.addObject("villes",villes);
            ArrayList<AnnonceLatLng> latlngs = null;
            if (lids.size()>0){
                latlngs = annonceLatLngService.getAnnonceLatLngByIds(lids);
            }
            mv.addObject("latlngs",latlngs);
            mv.addObject("annonces",annonces);

        }

        mv.setViewName("map/location");
        return mv;
    }

    @RequestMapping("/location/house/detail/{id}.html")
    public ModelAndView houseDetail(ModelAndView mv,@PathVariable("id") long id){
        mv.setViewName("map/houseDetail");
        Optional<Annonce> data = annonceService.findAnnonceById(id);
        if (data.isPresent()){
            Annonce annonce = data.get();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (annonce.getEnvirJson()!=null){
                    HashMap<Integer,Integer> envirMap = objectMapper.readValue(annonce.getEnvirJson(),HashMap.class);
                    annonce.setEnvir(envirMap);
                }
                if (annonce.getImmsJson()!=null){
                    HashMap<Integer,Integer> baseMap = objectMapper.readValue(annonce.getImmsJson(),HashMap.class);
                    annonce.setBase(baseMap);
                }

                if (annonce.getImgsJson()!=null){
                    ArrayList<String> imageList = objectMapper.readValue(annonce.getImgsJson(),ArrayList.class);
                    if (imageList!=null&&imageList.size()>0){
                        for (int i = 0; i < imageList.size(); i++) {
                            imageList.set(i,"/partners/"+annonce.getPid()+"/"+imageList.get(i));
                        }
                        annonce.setImages(imageList);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if (annonce.getLocation()!=null){
                Ville ville = villeService.findVilleById(annonce.getLocation());
                if (ville!=null){
                    mv.addObject("ville",ville);
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

            mv.addObject("trans", HwDatas.dataTrans);
            mv.addObject("rtypes", HwDatas.dataHouse);
            mv.addObject("energies",HwDatas.dataEnergy);
            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("imms",HwDatas.dataFunctions);
            mv.addObject("current", HwTools.getDay());
            mv.addObject("annonce",annonce);

        }
        return mv;
    }


    @RequestMapping(value = "/site/search")
    public Object searchKeywords(){
        String keyword = HwTools.request().getParameter("keyword");
        //search ville postcode
        HashMap<String,Object> data = new HashMap<>();
        if (keyword!=null){
            SqlHelper sqlHelper = new SqlHelper("hw_ville");
            sqlHelper.addAndCondition("like","name","'%"+keyword+"%'");
            sqlHelper.addOrCondition("like","postcode","'%"+keyword+"%'");
            sqlHelper.setLimit(" limit 5");
            ArrayList<Ville> villes = villeService.getVillesBySql(sqlHelper.toSql());
            data.put("ville",villes);

            SqlHelper sqlHelper2 = new SqlHelper("hw_universite");
            sqlHelper2.addAndCondition("like","name","'%"+keyword+"%'");
            sqlHelper2.addOrCondition("like","postcode","'%"+keyword+"%'");
            sqlHelper2.addOrCondition("like","ville","'%"+keyword+"%'");
            sqlHelper2.setLimit(" limit 5");
            ArrayList<Universite> universites = universiteService.getUniversiteBySql(sqlHelper2.toSql());

            data.put("universites",universites);
        }

        return data;
    }

    @RequestMapping(value = "/site/search/{type}/{id}")
    public ModelAndView searchResults(@PathVariable("type") int type,@PathVariable("id") long id,ModelAndView mv) throws JsonProcessingException {
        mv.setViewName("site/search");
        mv.addObject("type",type);
        mv.addObject("id",id);

        return mv;
    }

    @RequestMapping(value = "/site/search/data/{type}/{id}")
    public Object searchResultsData(@PathVariable("type") int type,@PathVariable("id") long id) {
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(Annonce.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select count(*)");
        Ville ville;
        if (type == 0) {
            ville = villeService.findVilleById(id);
            sqlHelper.addAndCondition("location", ville.getId());
        } else {
            Universite universite = universiteService.getById(id);
            ArrayList<Object> ids = new ArrayList<Object>();
            List<Ville> villes = villeService.findVilleByKeywords(universite.getCity());
            villes.forEach(v -> {
                ids.add(v.getId());
            });
            if (ids.size() > 0)
                sqlHelper.addInCondition("location", ids);
        }
        sqlHelper.setOrder("utime", "desc");

        BigInteger count = annonceService.getCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count + ""));
        pageHelper.setBaseUrl("/site/search/" + type + "/" + id);
        String page = HwTools.request().getParameter("page");
        if (page != null) {
            pageHelper.setCurrentPage(Long.parseLong(page));
        } else {
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setLimit(20);
        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select * ");

        ArrayList<Annonce> annonces = annonceService.getAnnonceByPage(sqlHelper.toSql());
        if (annonces!=null && annonces.size()>0){
            ArrayList<Object> vids = new ArrayList<>();
            HashMap<String, Ville> villes = new HashMap<>();
            try {
                for (int i = 0; i < annonces.size(); i++) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    if (annonces.get(i).getImgsJson() != null) {
                        ArrayList<String> imageList = objectMapper.readValue(annonces.get(i).getImgsJson(), ArrayList.class);
                        if (imageList != null && imageList.size() > 0) {
                            for (int j = 0; j < imageList.size(); j++) {
                                imageList.set(j, "/partners/" + annonces.get(i).getPid() + "/" + imageList.get(j));
                            }
                            annonces.get(i).setImages(imageList);
                        }
                        vids.add(annonces.get(i).getLocation());
                    }
                }

                if (vids.size() > 0) {
                    villeService.getVillesByIds(vids).forEach(v -> {
                        villes.put(v.getId() + "", v);
                    });
                    data.put("villes", villes);

                }

                data.put("status",1);
                data.put("annonces",annonces);
                data.put("pageHelper",pageHelper);

            }catch(Exception e){
                data.put("status",0);
            }
        }


        return data;
    }

    @RequestMapping(value = "/site/map")
    public ModelAndView siteMap(ModelAndView mv){
        mv.setViewName("map/map");

        return mv;
    }



    @RequestMapping(value = "/site/bussiness/map")
    public ModelAndView siteBussinessMap(ModelAndView mv){
        mv.setViewName("map/bussinessMap");
        return mv;
    }



    @RequestMapping(value = "/site/map/annonces")
    public Object mapAnnonces(LocationAnnonceSearchModel model) throws JsonProcessingException {
        HashMap<String,Object> data = new HashMap();

        ArrayList<Ville> villes = villeService.getVillesBySql(model.toSql());
        ArrayList<Object> vids = new ArrayList<>();
        HashMap<Long,Ville> dataVilles = new HashMap<Long, Ville>();
        if (villes!=null && villes.size()>0){
            villes.forEach(v->{
                vids.add(v.getId());
                dataVilles.put(v.getId(),v);
            });

            data.put("villes",dataVilles);

            ArrayList<Annonce> annonces = annonceService.getActiveAnnoncesByLocations(vids);
            ArrayList<Object> ids = new ArrayList<>();
            if (annonces!=null && annonces.size()>0){
                for (int i = 0; i < annonces.size(); i++) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    if (annonces.get(i).getImgsJson()!=null){
                        ArrayList<String> imageList = objectMapper.readValue(annonces.get(i).getImgsJson(),ArrayList.class);
                        if (imageList!=null&&imageList.size()>0){
                            for (int j = 0; j < imageList.size(); j++) {
                                imageList.set(j,"/partners/"+annonces.get(i).getPid()+"/"+imageList.get(j));
                            }
                            annonces.get(i).setImages(imageList);
                        }
                        ids.add(annonces.get(i).getLatlng());
                    }
                }

                SqlHelper sqlHelper = new SqlHelper("hw_annonce_lat_lng");
                sqlHelper.addInCondition("id",ids);
                sqlHelper.setLimit("");
                ArrayList<AnnonceLatLng> latlngs = annonceLatLngService.findAnnonceLatLngBySql(sqlHelper.toSql());
                ArrayList<AnnonceLatLng> annonceLatLngs = new ArrayList<>();
                for (int i = 0; i < annonces.size(); i++) {
                    for (int j = 0; j < latlngs.size(); j++) {
                        if (annonces.get(i).getLatlng()==latlngs.get(j).getId()){
                            latlngs.get(j).setAnnonce(annonces.get(i));
                            annonceLatLngs.add(latlngs.get(j));
                        }
                    }
                }

                data.put("latlngs",annonceLatLngs);
                data.put("status",1);
                return data;
            }

        }
        data.put("status",0);

        return data;
    }




}
