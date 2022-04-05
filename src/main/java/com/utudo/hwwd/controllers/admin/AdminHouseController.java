package com.utudo.hwwd.controllers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.GeocodingFeature;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.models.searchModel.AnnonceBussinessSearchModel;
import com.utudo.hwwd.models.searchModel.PaysSearchModel;
import com.utudo.hwwd.service.AnnonceLatLngService;
import com.utudo.hwwd.service.UserService;
import com.utudo.hwwd.service.impl.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class AdminHouseController extends MainController {


    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    AnnonceLatLngService annonceLatLngService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    UserServiceImpl userService;

    @Resource
    GarantieServiceImpl garantieService;


    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    MatchOptionServiceImpl matchOptionService;


    @Resource
    PaysServiceImpl paysService;


    @Resource
    AnnonceModelServiceImpl annonceModelService;

    @RequestMapping("/admin/annonce/detail/{id}")
    public ModelAndView annonceDetail(@PathVariable("id") long id,ModelAndView mv){
        String method = HwTools.request().getMethod();
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if (annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
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
                    ArrayList<String> imageList = (ArrayList<String>) objectMapper.readValue(annonce.getImgsJson(),ArrayList.class);
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

            Ville ville = villeService.findVilleById(annonce.getLocation());
            if (ville!=null){
                mv.addObject("ville",ville);
            }

            mv.addObject("trans", HwDatas.dataTrans);
            mv.addObject("rtypes", HwDatas.dataHouse);

            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("imms",HwDatas.dataFunctions);

            mv.addObject("annonce",annonceOptional.get());
        }


        mv.setViewName("admin/annonceDetail");

        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/images/{id}")
    public ModelAndView updateHouseImage(@PathVariable("id") long id, ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id, HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            if (annonceObj.getImages().size()>0){
                ArrayList<String> imgs = (ArrayList<String>) annonceObj.getImages();
                HashMap<String,String> imgsUpload = new HashMap<String,String>();
                imgs.forEach(m->{
                    imgsUpload.put(m,m);
                });

                annonce.setImages(new ArrayList<String>(imgsUpload.values()));
                annonce.setImgsJson(HwTools.toJson(annonce.getImages()));
            }
            annonceService.update(annonce);
            mv.setViewName("redirect:/admin/annonce/detail/"+id);
            return mv;
        }
        mv.setViewName("adminHouse/updateImages");
        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/address/{id}")
    public ModelAndView updateAddress(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        String method = HwTools.request().getMethod();

        if (annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setAddress(annonceObj.getAddress());
                annonce.setLocation(annonceObj.getLocation());

                Ville ville = villeService.findVilleById(annonce.getLocation());
                MapBoxGeocodingResponse response;
                if (ville!=null){
                    response = HwHttpClient.sendGetReqeust(annonce.getAddress()+","+ville.getPostcode());
                }else{
                    response = HwHttpClient.sendGetReqeust(annonce.getAddress());
                }
                if (response.getFeatures().size()>0){
                    GeocodingFeature geocodingFeature = response.getFeatures().get(0);
                    AnnonceLatLng latLngObj = annonceLatLngService.findOnceByPidAndAid(annonce.getPid(),annonce.getId());
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
                        annonceLatLng.setAid(annonce.getId());
                        annonceLatLng.setPid(annonce.getPid());
                        annonceLatLng.setCenter(geocodingFeature.getCenter());
                        annonceLatLng.setGeometry(geocodingFeature.getGeometry());
                        annonceLatLngService.save(annonceLatLng);
                        AnnonceLatLng latobj = annonceLatLngService.findOnceByPidAndAid(annonce.getPid(),annonce.getId());
                        annonce.setLatlng(latobj.getId());
                    }
                    annonceService.update(annonce);
                }

                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                if (annonce.getLocation()!=null){
                    Ville ville = villeService.findVilleById(annonce.getLocation());
                    if (ville!=null){
                        mv.addObject("ville",ville);
                    }
                }
                mv.addObject("annonce",annonce);
                mv.setViewName("adminHouse/updateAddress");
            }

        }else{
            mv.setViewName("redirect:/admin");
        }

        return mv;
    }

    @RequestMapping(value = "/admin/house/detail/update/sdate/{id}")
    public ModelAndView updateSdate(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if (annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setSdate(annonceObj.getSdate());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateSdata");
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }

        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/rtype/{id}")
    public ModelAndView updateRtype(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setRtype(annonceObj.getRtype());
                annonce.setSize(annonceObj.getSize());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateRtype");
                mv.addObject("annonce",annonce);
                mv.addObject("rtypes", HwDatas.dataHouse);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }

        return mv;
    }



    @RequestMapping(value = "/admin/house/detail/update/price/{id}")
    public ModelAndView updatePrice(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setLoyer(annonceObj.getLoyer());
                annonce.setCharges(annonceObj.getCharges());
                annonce.setPrice(annonceObj.getLoyer()+annonceObj.getCharges());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updatePrice");
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }

        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/description/{id}")
    public ModelAndView updateDescription(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                logger.error(annonceObj.toString());
                annonce.setDescription(annonceObj.getDescription());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateDescription");
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/energy/{id}")
    public ModelAndView updateEnergy(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setEnergy(annonceObj.getEnergy());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateEnergy");
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }

    @RequestMapping(value = "/admin/house/detail/update/trans/{id}")
    public ModelAndView updateTrans(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setTrans(annonceObj.getTrans());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateTrans");
                mv.addObject("annonce",annonce);
                mv.addObject("trans",HwDatas.dataTrans);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }

    @RequestMapping(value = "/admin/house/detail/update/transation/{id}")
    public ModelAndView updateTransation(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setTransation(annonceObj.getTransation());
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateTrans");
                mv.addObject("annonce",annonce);
                mv.addObject("trans",HwDatas.dataTrans);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }


    @RequestMapping(value = "/admin/house/detail/update/bases/{id}")
    public ModelAndView updateBases(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setImmsJson(HwTools.toJson(annonceObj.getBase()));
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateBases");
                mv.addObject("bases",HwDatas.dataBaseFunctions);
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }

    @RequestMapping(value = "/admin/house/detail/update/envir/{id}")
    public ModelAndView updateEnvir(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if(annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
                annonce.setEnvirJson(HwTools.toJson(annonceObj.getEnvir()));
                annonceService.update(annonce);
                mv.setViewName("redirect:/admin/annonce/detail/"+id);
            }else{
                mv.setViewName("adminHouse/updateEnvir");
                mv.addObject("imms",HwDatas.dataFunctions);
                mv.addObject("annonce",annonce);
            }
        }else{
            mv.setViewName("redirect:/admin");
        }

        return mv;
    }


    @RequestMapping(value = "/admin/annonce/detail/houseMatch/{id}")
    public ModelAndView adminHouseMatch(@PathVariable("id") long id,ModelAndView mv) throws JsonProcessingException {
        mv.setViewName("adminHouse/houseMatch");
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if (annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            Partner partner = partnerService.findPartnerById(annonce.getPid());
            mv.addObject("partner",partner);
            mv.addObject("annonce",annonce);
            HwMatch hwMatch = new HwMatch(annonce);
            hwMatch.setNeedsService(needsService);
            hwMatch.matchData();
            mv.addObject("trans",HwDatas.dataTrans);
            mv.addObject("rtypes",HwDatas.dataHouse);
            mv.addObject("dataMatches",hwMatch.getNeeds());
            ArrayList<Object> ids = new ArrayList<>();
            ArrayList<Object> uids = new ArrayList<>();
            if (hwMatch.getNeeds().size()>0){
                hwMatch.getNeeds().forEach(n->{
                    ids.add(n.getLocation());
                    uids.add(n.getUid());
                });
            }

            if (ids.size()>0){
                HashMap<String,Ville> locations = new HashMap<>();
                villeService.getVillesByIds(ids).forEach(v->{
                    locations.put(v.getId()+"",v);
                });
                mv.addObject("locations",locations);
                HashMap<String,MatchOption> matches = new HashMap<>();
                matchOptionService.getMatchOptions(annonce.getId(), annonce.getPid()).forEach(m->{
                    matches.put(m.getNid()+"",m);
                });
                mv.addObject("matches",matches);

            }

            if (uids.size()>0){
                HashMap<String,User> users = new HashMap<>();
                userService.getUsersByIds(uids).forEach(u->{
                    users.put(u.getId()+"",u);
                });

                mv.addObject("users",users);
            }

        }else{
            mv.setViewName("redirect:/admin");
        }


        return mv;
    }


    @RequestMapping(value = "/admin/data/annonces")
    public Object annonces(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper("hw_annonce");
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.setLimit(" ");
        BigInteger count  = annonceService.getCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(10);
        if (HwTools.request().getParameter("page")!=null){
            int currentPage = Integer.parseInt(HwTools.request().getParameter("page"));
            pageHelper.setCurrentPage(currentPage);
        }else{
            pageHelper.setCurrentPage(0);
        }

        pageHelper.setBaseUrl("/admin/data/annonces");
        pageHelper.getPages();
        data.put("pageHelper",pageHelper);

        sqlHelper.setSelect("select * ");
        sqlHelper.setOrder("id","desc");
        sqlHelper.setPageHelper(pageHelper);
        String sql = sqlHelper.toSql();
        ArrayList<Annonce> annonces = annonceService.getAnnonceByPage(sql);
        data.put("annonces",annonces);


        if (annonces.size()>0){
            ArrayList<Object> vids = new ArrayList<>();
            ArrayList<Object> pids = new ArrayList<>();
            annonces.forEach(a->{
                if (a.getLocation()!=null){
                    vids.add(a.getLocation());
                    pids.add(a.getPid());
                }
            });
            HashMap<Long,Ville> dataVille = new HashMap<Long, Ville>();
            villeService.getVillesByIds(vids).forEach(v->{
                dataVille.put(v.getId(),v);
            });
            HashMap<Long,Partner> dataPartner = new HashMap<>();
            partnerService.getPartnersByIds(pids).forEach(p->{
                dataPartner.put(p.getId(),p);
            });
            data.put("villes",dataVille);
            data.put("partners",dataPartner);
            data.put("status",1);
        }else{
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping(value = "/admin/house/status/update/{id}/{sid}")
    public Object updateStatus(@PathVariable("id") long id,@PathVariable("sid") int sid){
        HashMap<String,Object> data = new HashMap<>();
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        if (!checkIsStageAdmin() && annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            if (sid==4){
                annonceService.delete(id);
            }else{
                annonce.setStatus(sid);
                annonceService.update(annonce);
            }

            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/pays/data/lists")
    public Object paysDataLists(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper("hw_pays");
        sqlHelper.setSelect("select count(*)");
        BigInteger count = paysService.getPaysCount(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(15);

        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setBaseUrl("/admin/pays/data/lists");
        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<Pays> pays = paysService.getPaysBySql(sqlHelper.toSql());
        if (pays!=null && pays.size()>0){
            data.put("status",1);
            data.put("pays",pays);
            data.put("pageHelper",pageHelper);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/pays/lists")
    public ModelAndView paysLists(ModelAndView mv){
        mv.setViewName("adminHouse/paysLists");
        return mv;
    }


    @RequestMapping(value = "/admin/search/pays")
    public Object adminSearchBussinessAnnonces(Pays pays){
        HashMap<String,Object> data = new HashMap<>();
        PaysSearchModel model = new PaysSearchModel(pays);
        ArrayList<Pays> paysObjs = paysService.getPaysBySql(model.getSql());
        if (paysObjs!=null && paysObjs.size()>0){
            data.put("pays",paysObjs);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }

    @RequestMapping(value = "/admin/pays/update/{id}")
    public ModelAndView paysUpdate(@PathVariable("id") long id,ModelAndView mv,Pays pays){
        Pays payObj = paysService.findPayById(id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            payObj.setZname(pays.getZname());
            payObj.setName(pays.getName());
            payObj.setCode(pays.getCode());
            payObj.setPremission(pays.getPremission());
            paysService.update(payObj);
            mv.setViewName("redirect:/admin/pays/lists");
            return mv;
        }
        mv.addObject("pays",payObj);
        mv.setViewName("adminHouse/updatePays");
        return mv;
    }

    @RequestMapping(value = "/admin/annonce/partners/lists/data")
    public Object partnerIdentityListsData(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(AnnonceModel.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select count(*) ");
        BigInteger count = annonceModelService.getAnnonceModelCount(sqlHelper.toSql());
        PageHelper pageHelper = new PageHelper();
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setLimit(15);
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/admin/annonce/partners/lists/data");
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<AnnonceModel> models = annonceModelService.getAnnonceModelListsBySql(sqlHelper.toSql());
        if (models!=null && models.size()>0){
            ArrayList<Object> ids = new ArrayList<>();
            models.forEach(m->{
                ids.add(m.getUid());
            });

            ArrayList<Partner> partners = partnerService.getPartnersByIds(ids);
            data.put("partners",partners);
            data.put("status",1);
            data.put("pageHelper",pageHelper);
            data.put("models",models);

        }else{
            data.put("status",0);
        }

        data.put("count",count);
        return data;
    }

    @RequestMapping(value = "/admin/annonce/partners/lists")
    public ModelAndView partnerIdentityLists(ModelAndView mv){
        mv.setViewName("adminHouse/partnerLists");

        return mv;
    }


    @RequestMapping(value = "/admin/partner/annnonce/set/{id}")
    public ModelAndView partnerUpdateAnnonceModel(@PathVariable("id") long id, ModelAndView mv,AnnonceModel annonceModel){
        mv.setViewName("adminHouse/setPartner");
        mv.addObject("id",id);
        String method = HwTools.request().getMethod();
        AnnonceModel model = annonceModelService.findModelById(id);
        mv.addObject("model",model);
        mv.addObject("id",id);
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            model.setType(annonceModel.getType());
            annonceModelService.update(model);
            mv.setViewName("redirect:/admin/annonce/partners/lists");
            return mv;
        }

        return mv;
    }
}
