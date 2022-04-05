package com.utudo.hwwd.controllers.partner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.GeocodingFeature;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.service.PartnerService;
import com.utudo.hwwd.service.impl.*;
import org.dom4j.rule.Mode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.*;
import java.util.logging.Logger;

@RestController
@EnableAutoConfiguration
public class PartnerController extends MainController {


    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    AnnonceServiceImpl annonceService;


    @Resource
    AnnonceModelServiceImpl modelService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    NeedsServiceImpl needsService;


    @Resource
    UserServiceImpl userService;


    @Resource
    GarantieServiceImpl garantieService;


    @Resource
    RdvServiceImpl rdvService;


    @Resource
    MatchOptionServiceImpl matchOptionService;

    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;


    @RequestMapping("/partner/test")
    public String test(){
        Partner partner = partnerService.getLastedOne();

        if (partner!=null){
            logger.error(partner.getEmail());
            Person person = new Person(partner);
            person.setType(HwDatas.PERSON_TYPE_PARTNER);

            HttpSession session = HwTools.session();
            session.setAttribute("user", person);
            return partner.toString();
        }

        return HwTools.hashPassword("1234");
    }

    @RequestMapping("/partner/login")
    public ModelAndView login(ModelAndView mv,Partner partner){

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Partner partnerObj = partnerService.findUserByEmailOrTelephone(partner.getEmail());
            if (partnerObj!=null){
                if(partnerObj.login(partner)){
                    mv.setViewName("redirect:/partner/center");
                    return mv;
                }
            }
            partner.setError(0);
            partner.setMessage("账户或密码不正确");
        }
        mv.addObject("partner",partner);

        mv.setViewName("partner/login");
        return mv;
    }

    @RequestMapping("/partner/info")
    public Object getPartner(){
        Person person = HwTools.getCurrentUser();
        return person;
    }


    @RequestMapping({"/partner/center"})
    public ModelAndView centers(ModelAndView mv){
        mv.setViewName("redirect:/partner/center/0.html");
        mv.addObject("user",HwTools.getCurrentUser());
        return mv;

    }

    @RequestMapping("/partner/center/{id}.html")
    public ModelAndView center(@PathVariable("id") Integer id,ModelAndView mv){
        List<Annonce> annonces;
        if (id<=0||id>=4){
            id=0;
            annonces = annonceService.getAnnonceListsByPid(HwTools.getCurrentUser().getId());
        }else{
            annonces = annonceService.getAnnonceListsByStatus(id,HwTools.getCurrentUser().getId());
        }

        mv.setViewName("partner/center");
        mv.addObject("user",HwTools.getCurrentUser());
        mv.addObject("annonces",annonces);
        return mv;
    }


    @RequestMapping("/partner/signup")
    public ModelAndView signup(ModelAndView mv,Partner partner){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Partner partnerOld = partnerService.findUserByTelephone(partner.getTelephone());
            if (partnerOld!=null){
                mv.addObject("partner",partner);
            }else{
                partnerService.save(partner);
                partner.signupLogin();
                mv.setViewName("redirect:/partner/center");
                return mv;
            }
        }
        mv.setViewName("partner/signup");
        return mv;
    }



    @RequestMapping("/partner/signup/email")
    public ModelAndView signupEmail(ModelAndView mv,Partner partner){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Partner partnerOld = partnerService.findUserByEmail(partner.getEmail());
            if (partnerOld!=null){
                mv.addObject("partner",partner);
            }else{
                partnerService.save(partner);
                partner.signupLogin();
                mv.setViewName("redirect:/partner/center");
                return mv;
            }
        }
        mv.setViewName("partner/signupEmail");
        return mv;
    }



    @RequestMapping("/partner/confrim")
    public ModelAndView cofirmId(ModelAndView mv){
        mv.setViewName("partner/confirmAccount");
        mv.addObject("email","z513881204@icloud.com");
        return mv;
    }

    @RequestMapping(value = "/site/partner/resetPassword/{token}")
    public ModelAndView resetPassword(@PathVariable("token") String token,ModelAndView mv, Partner partner){
        Partner partnerObj = partnerService.findUserByToken(token);
        String method =  HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            partnerObj.setFtoken("");
            partnerObj.setPassword(HwTools.hashPassword(partner.getPassword()));
            partnerObj.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
            partnerService.update(partnerObj);
            mv.setViewName("partner/login");
            return mv;
        }
        mv.addObject("partner",partnerObj);
        mv.setViewName("partner/resetPassword");
        return mv;
    }




    @RequestMapping(value = "/partner/admin/addSource")
    public ModelAndView addSource(ModelAndView mv, Annonce annonce){
        AnnonceModel model = modelService.findModelByUid(HwTools.getCurrentUser().getId());
        if (model!=null){
            String method = HwTools.request().getMethod();
            if ("POST".equalsIgnoreCase(method)){
                annonce.setPid(HwTools.getCurrentUser().getId());
                if (annonce.getImages()!=null&&annonce.getImages().size()>0){
                    ArrayList<String> imgs = (ArrayList<String>) annonce.getImages();
                    ArrayList<String> imgsUpload = new ArrayList<>();
                    imgs.forEach(m->{
                        if(m!=null){
                            imgsUpload.add(m);
                        }
                    });
                    if (annonce.getCover()==null||annonce.getCover().equals("")){
                        if (imgsUpload.size()>0){
                            annonce.setCover(imgsUpload.get(0));
                        }
                    }
                    annonce.setImages(imgsUpload);
                }else{
                    annonce.setStatus(0);
                }
                annonceService.save(annonce);
                mv.setViewName("redirect:/partner/setLatlng");
                return mv;
            }
            mv.setViewName("partner/addSource");
            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("rtypes", HwDatas.dataHouse);
            mv.addObject("imms",HwDatas.dataFunctions);
            mv.addObject("trans",HwDatas.dataTrans);
            mv.addObject("model",model);
        }else{
            mv.setViewName("redirect:/partner/addType");
        }

        return mv;
    }

    @RequestMapping("/partner/addType")
    public ModelAndView addType(ModelAndView mv,AnnonceModel annonceModel){
        if ("POST".equalsIgnoreCase(HwTools.request().getMethod())){
            annonceModel.setUid(HwTools.getCurrentUser().getId());
            modelService.save(annonceModel);
            mv.setViewName("redirect:/partner/admin/addSource");
            return  mv;
        }
        mv.setViewName("partner/choiceType");
        return mv;
    }


    @RequestMapping("/partner/setLatlng")
    public ModelAndView setLatlng(ModelAndView mv){
        Annonce annonce = annonceService.getLastInsert(HwTools.getCurrentUser().getId());
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
        }
        mv.setViewName("redirect:/partner/center");
        return mv;
    }




    @RequestMapping(value = "/partner/admin/message")
    public ModelAndView message(ModelAndView mv){


        mv.setViewName("partner/message");
        SqlHelper sqlHelper = new SqlHelper("hw_rdv");
        sqlHelper.addAndCondition("pid",HwTools.getCurrentUser().getId());
        //select count(*)
        sqlHelper.setSelect("select count(*)");
        sqlHelper.setOrder("id","desc");

        BigInteger count = rdvService.getRdvCountBySql(sqlHelper.toSql());


        logger.error("count rdv:"+count);
        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(15);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setBaseUrl("/partner/admin/message");
        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select *");
        ArrayList<PartnerRdv> rdvs = rdvService.getRdvBySql(sqlHelper.toSql());
        HashMap<String,User> users = new HashMap<>();
        HashMap<String,Needs> needs = new HashMap<>();
        if (rdvs.size()>0){
            ArrayList<Object> uids = new ArrayList<>();
            ArrayList<Object> nids = new ArrayList<>();
            rdvs.forEach(r->{
                uids.add(r.getUid());
                nids.add(r.getNid());
            });
            needsService.findNeedsByNids(nids).forEach(n->{
                needs.put(n.getId()+"",n);
            });
            userService.getUsersByIds(uids).forEach(u->{
                users.put(u.getId()+"",u);
            });
        }
        mv.addObject("users",users);
        mv.addObject("needs",needs);
        mv.addObject("pageHelper",pageHelper);
        mv.addObject("rdvs",rdvs);
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/match/{id}")
    public ModelAndView houseMatch(@PathVariable("id") Integer id,ModelAndView mv){
        mv.setViewName("partner/houseMatch");

        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        if (annonce!=null){
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


        }

        return mv;
    }

    @RequestMapping(value = "/partner/admin/set")
    public ModelAndView set(ModelAndView mv,Partner partner){
        Person person = HwTools.getCurrentUser();
        Partner partnerObj = partnerService.findPartnerById(person.getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            if (!partner.getPassword().equals(partnerObj.getPassword())){
                partnerObj.setPassword(HwTools.hashPassword(partnerObj.getPassword()));
            }
            if (!partner.getCover().equals(partnerObj.getCover())){
                partnerObj.setCover(partner.getCover());
            }
            partnerObj.setPrenom(partner.getPrenom());
            partnerObj.setNom(partner.getNom());
            partnerService.update(partnerObj);
        }
        mv.setViewName("partner/set");
        mv.addObject("user",partnerObj);
        return mv;
    }



    @RequestMapping(value = "/partner/admin/house/detail/{id}")
    public ModelAndView detailHouse(@PathVariable("id") Integer id, ModelAndView mv,Annonce annonce){
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Annonce annonceObj = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
            annonce.setPid(HwTools.getCurrentUser().getId());
            annonce.setId(id);
            if (annonce.getImages()!=null&&annonce.getImages().size()>0){
                ArrayList<String> imgs = (ArrayList<String>) annonce.getImages();
                ArrayList<String> imgsUpload = new ArrayList<>();
                imgs.forEach(m->{
                    if(m!=null){
                        imgsUpload.add(m);
                    }
                });
                if (annonce.getCover()==null||annonce.getCover().equals("")){
                    if (imgsUpload.size()>0){
                        annonce.setCover(imgsUpload.get(0));
                    }
                }
                annonce.setImages(imgsUpload);
            }else{
                annonce.setStatus(0);
            }
            if (annonceObj.getCtime()!=null){
                annonce.setCtime(annonceObj.getCtime());
            }else{
                annonce.setCtime(annonceObj.getUtime());
            }

            annonce.setEnvirJson(HwTools.toJson(annonce.getEnvir()));
            annonce.setImgsJson(HwTools.toJson(annonce.getImages()));
            annonce.setPrice(annonce.getCharges()+annonce.getLoyer());
            annonce.setImmsJson(HwTools.toJson(annonce.getBase()));
            if (annonceObj.getStatus()!=null){
                annonce.setStatus(annonceObj.getStatus());
            }else{
                annonce.setStatus(3);
            }
            annonceService.update(annonce);
        }
        mv.addObject("id",id);
        mv.setViewName("partner/detailAnnonce");
        return mv;
    }


    @RequestMapping("/partner/admin/house/detail/data/{id}")
    public Object houseDetailData(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        try{
            AnnonceModel model = modelService.findModelByUid(HwTools.getCurrentUser().getId());
            Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
            if(annonce!=null){
                if(annonce.getLocation()!=null){
                    Ville ville = villeService.findVilleById(annonce.getLocation());
                    if (ville!=null){
                        data.put("ville",ville);
                    }
                }
                data.put("trans",HwDatas.dataTrans);
                data.put("rtypes", HwDatas.dataHouse);
                data.put("bases",HwDatas.dataBaseFunctions);
                data.put("imms",HwDatas.dataFunctions);
            }
            data.put("meubles",HwDatas.dataMeubles);
            data.put("annonce",annonce);
            data.put("model",model);
            data.put("status",1);
        }catch(Exception e){
            e.printStackTrace();
            data.put("status",0);
        }
        return data;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/meubles/{id}")
    public ModelAndView updateMeubles(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setFurniture(annonceObj.getFurniture());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
            return mv;
        }
        mv.setViewName("partner/updateMeubles");
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/images/{id}")
    public ModelAndView updateImages(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
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
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
            return mv;
        }
        mv.setViewName("partner/updateImages");
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/address/{id}")
    public ModelAndView updateAddress(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setAddress(annonceObj.getAddress());
            annonce.setLocation(annonceObj.getLocation());

            Ville ville = villeService.findVilleById(annonce.getLocation());
            MapBoxGeocodingResponse response;
            if (ville!=null){
                response = HwHttpClient.sendGetReqeust(annonce.getAddress()+","+ville.getPostcode());
            }else{
                response = HwHttpClient.sendGetReqeust(annonce.getAddress());
            }
            logger.error(response.getFeatures().toString());
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

            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            if (annonce.getLocation()!=null){
                Ville ville = villeService.findVilleById(annonce.getLocation());
                if (ville!=null){
                    mv.addObject("ville",ville);
                }
            }
            mv.addObject("annonce",annonce);
            mv.setViewName("partner/updateAddress");
        }

        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/sdate/{id}")
    public ModelAndView updateSdate(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setSdate(annonceObj.getSdate());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateSdata");
            mv.addObject("annonce",annonce);
        }
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/rtype/{id}")
    public ModelAndView updateRtype(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setRtype(annonceObj.getRtype());
            annonce.setSize(annonceObj.getSize());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateRtype");
            mv.addObject("annonce",annonce);
            mv.addObject("rtypes", HwDatas.dataHouse);
        }
        return mv;
    }


    @RequestMapping(value = "/partner/admin/house/detail/update/price/{id}")
    public ModelAndView updatePrice(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setLoyer(annonceObj.getLoyer());
            annonce.setCharges(annonceObj.getCharges());
            annonce.setPrice(annonceObj.getLoyer()+annonceObj.getCharges());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updatePrice");
            mv.addObject("annonce",annonce);
        }

        return mv;
    }


    @RequestMapping(value = "/partner/admin/house/detail/update/description/{id}")
    public ModelAndView updateDescription(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            logger.error(annonceObj.toString());
            annonce.setDescription(annonceObj.getDescription());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateDescription");
            mv.addObject("annonce",annonce);
        }
        return mv;
    }


    @RequestMapping(value = "/partner/admin/house/detail/update/energy/{id}")
    public ModelAndView updateEnergy(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setEnergy(annonceObj.getEnergy());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateEnergy");
            mv.addObject("annonce",annonce);
        }
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/trans/{id}")
    public ModelAndView updateTrans(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setTrans(annonceObj.getTrans());
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateTrans");
            mv.addObject("annonce",annonce);
            mv.addObject("trans",HwDatas.dataTrans);
        }
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/bases/{id}")
    public ModelAndView updateBases(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setImmsJson(HwTools.toJson(annonceObj.getBase()));
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateBases");
            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("annonce",annonce);
        }

        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/detail/update/envir/{id}")
    public ModelAndView updateEnvir(@PathVariable("id") Integer id,ModelAndView mv,Annonce annonceObj){
        Annonce annonce = annonceService.getAnnonceByPidAndId(id,HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            annonce.setEnvirJson(HwTools.toJson(annonceObj.getEnvir()));
            annonceService.update(annonce);
            mv.setViewName("redirect:/partner/admin/house/detail/"+id);
        }else{
            mv.setViewName("partner/updateEnvir");
            mv.addObject("imms",HwDatas.dataFunctions);
            mv.addObject("annonce",annonce);
        }

        return mv;
    }

    @RequestMapping(value = "/partner/admin/contact")
    public ModelAndView contact(ModelAndView mv){
        mv.setViewName("partner/contact");

        return mv;
    }


    @RequestMapping(value="/partner/admin/uploadImage")
    public List uploadImage(@RequestParam("uploadFiles") MultipartFile[] files){
        logger.error("上传图片的数量"+files.length);

        List<Map<String,Object>> root=new ArrayList<Map<String,Object>>();

        for (MultipartFile file : files) {    //循环保存文件

            Map<String,Object> result=new HashMap<String, Object>();//一个文件上传的结果
            String result_msg="";//上传结果信息

            if (file.getSize() / 1000 > 3000){
                result_msg="图片大小不能超过3M";
            }
            else{
                //判断上传文件格式
                String fileType = file.getContentType();
                if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                    // 要上传的目标文件存放的绝对路径
                    final String localPath = HwDatas.PARTNER_FILE_PATH + HwTools.getCurrentUser().getId();
                    //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                    //获取文件名
                    String fileName = file.getOriginalFilename();
                    result.put("fileName",fileName);
                    //获取文件后缀名
                    String suffixName = fileType.split("/")[1];
                    //重新生成文件名
                    String fileNameNew =  HwTools.hashPassword(fileName+HwTools.getTimeMillis()) +"."+ suffixName;

                    if (FileUtils.upload(file, localPath, fileNameNew)) {
                        //文件存放的相对路径(一般存放在数据库用于img标签的src)
                        String relativePath = fileNameNew;
                        result.put("relativePath",relativePath);//前端根据是否存在该字段来判断上传是否成功
                        result_msg="图片上传成功";
                    }
                    else{
                        result_msg="图片上传失败";
                    }
                }
                else{
                    result_msg="图片格式不正确";
                }
            }
            result.put("result_msg",result_msg);
            root.add(result);
        }
        String root_json = HwTools.toJson(root);
        return root;
    }



    @RequestMapping(value = "/partner/admin/house/match/dislike")
    public ModelAndView matchDislike(@RequestParam("id") long id,@RequestParam("aid") long aid,ModelAndView mv){
        Person person = HwTools.getCurrentUser();
        MatchOption matchOption = matchOptionService.findOneIfExist(aid,person.getId(),id);
        matchOption.setAid(aid);
        matchOption.setNid(id);
        matchOption.setPid(person.getId());
        matchOption.setStatus(0);
        matchOptionService.save(matchOption);
        mv.setViewName("redirect:/partner/admin/house/match/"+aid);
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/match/like")
    public ModelAndView matchLike(@RequestParam("id") long id,@RequestParam("aid") long aid,ModelAndView mv)
    {
        Person person = HwTools.getCurrentUser();
        MatchOption matchOption = matchOptionService.findOneIfExist(aid,person.getId(),id);
        matchOption.setAid(aid);
        matchOption.setNid(id);
        matchOption.setPid(person.getId());
        matchOption.setStatus(1);
        matchOptionService.save(matchOption);
        mv.setViewName("redirect:/partner/admin/house/match/"+aid);
        return mv;
    }

    @RequestMapping(value = "/partner/admin/house/match/detail")
    public ModelAndView matchDetail(@RequestParam("id") long id,@RequestParam("aid") long aid, ModelAndView mv) throws JsonProcessingException {

        mv.setViewName("partner/matchDetail");
        mv.addObject("id",id);
        mv.addObject("aid",aid);
        mv.addObject("rtypes",HwDatas.dataHouse);
        mv.addObject("trans",HwDatas.dataTrans);
        mv.addObject("bases",HwDatas.dataBaseFunctions);
        mv.addObject("imms",HwDatas.dataFunctions);
        mv.addObject("garanties",HwDatas.dataGaranties);
        mv.addObject("professions",HwDatas.dataProfession);
        mv.addObject("payMethods",HwDatas.dataPayMethods);
        Optional<Needs> value = needsService.findNeedsById(id);
        if (value.isPresent()){
            Needs needs =  value.get();
            User user = userService.findUserById(needs.getUid());
            mv.addObject("person",user);
            Optional<Garantie> optionGarantie = garantieService.findGarantieByNidAndUid(needs.getId(),user.getId());
            optionGarantie.ifPresent(garantie -> mv.addObject("garantie", garantie));
            Ville ville = villeService.findVilleById(needs.getLocation());
            mv.addObject("ville",ville);
            ObjectMapper objectMapper = new ObjectMapper();

            if (needs.getEnvirJson()!=null){
                HashMap<Integer,Integer> envirMap = objectMapper.readValue(needs.getEnvirJson(),HashMap.class);
                needs.setEnvir(envirMap);
            }
            if (needs.getImmsJson()!=null){
                HashMap<Integer,Integer> baseMap = objectMapper.readValue(needs.getImmsJson(),HashMap.class);
                needs.setBase(baseMap);
            }
            mv.addObject("needs",needs);
        }

        return mv;
    }

    @RequestMapping(value = "/site/partner/account/verify/{token}")
    public ModelAndView accountVerify(@PathVariable("token") String token, ModelAndView mv){
        Partner partner = partnerService.findUserByToken(token);
        partner.setFtoken("");
        partner.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
        partnerService.update(partner);
        partner.signupLogin();
        mv.setViewName("redirect:/partner/center");
        return mv;
    }


    @RequestMapping(value = "/site/partner/check/email")
    public HashMap<String, Object> checkEmail(){
        HttpServletRequest request = HwTools.request();
        String email = request.getParameter("email");
        HashMap<String,Object> result = new HashMap<>();
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result.put("message","* 这不是一个有效的email");
            result.put("status",0);
            return result;
        }

        Partner partner = partnerService.findUserByEmail(email);
        if (partner!=null){
            result.put("message","* 该邮箱已经被注册过");
            result.put("status",0);
            return result;
        }

        result.put("status",1);
        return result;
    }


    @RequestMapping(value = "/site/partner/check/telephone")
    public HashMap<String,Object> checkTelephone(){
        HttpServletRequest request = HwTools.request();
        String telephone = request.getParameter("telephone");
        HashMap<String,Object> result = new HashMap<>();
        Partner partner = partnerService.findUserByTelephone(telephone);
        if (partner!=null){
            result.put("message","* 该手机号码已经被注册过了");
            result.put("status",0);
            return result;
        }

        result.put("status",1);
        return result;
    }

    @RequestMapping(value = "/partner/profile")
    public ModelAndView addProfile(ModelAndView mv,Partner partner){
        Partner partnerObj = partnerService.findPartnerById(HwTools.getCurrentUser().getId());
        mv.addObject("partner",partnerObj);
        mv.setViewName("partners/profile");
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            if (partner.getEmail()==null && partner.getTelephone()==null){
                return mv;
            }
            Partner partnerOld;
            if (partner.getEmail()!=null){
                partnerOld = partnerService.findUserByEmail(partner.getEmail());
                if (partnerOld!=null){
                    return mv;
                }else{
                    partnerObj.setEmail(partner.getEmail());
                }
                partnerObj.setStatus(HwDatas.PERSON_TYPE_USER_BLOCK);
            }

            if (partner.getTelephone()!=null){
                partnerOld = partnerService.findUserByTelephone(partner.getTelephone());
                if (partnerOld!=null){
                    return mv;
                }else{
                    partnerObj.setTelephone(partner.getTelephone());
                }
                partnerObj.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
            }

            partnerService.update(partnerObj);
            partnerObj.signupLogin();
            mv.setViewName("redirect:/partner/center");
            return mv;
        }
        return mv;
    }

    @RequestMapping(value = "/partner/user/needs/{nid}/{uid}")
    public ModelAndView userNeedsDetail(@PathVariable("nid") long nid,@PathVariable("uid") long uid,ModelAndView mv){
        mv.setViewName("partners/userNeedsDetail");

        Needs needs = needsService.findNeedsByUidAndId(uid,nid);
        Garantie garantie = garantieService.findGarantieByUid(uid);
        if (needs!=null && garantie!=null){
            Ville ville = villeService.findVilleById(needs.getLocation());
            ObjectMapper objectMapper = new ObjectMapper();

            if (needs.getImmsJson()!=null){

                HashMap<Integer,Integer> baseMap = null;
                try {
                    baseMap = objectMapper.readValue(needs.getImmsJson(), HashMap.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                needs.setBase(baseMap);
            }

            if (needs.getEnvirJson()!=null){

                HashMap<Integer,Integer> envirMap = null;
                try {
                    envirMap = objectMapper.readValue(needs.getEnvirJson(), HashMap.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                needs.setEnvir(envirMap);
            }
            mv.addObject("needs",needs);
            mv.addObject("ville",ville);
            mv.addObject("rtypes",HwDatas.dataHouse);
            mv.addObject("trans",HwDatas.dataTrans);
            mv.addObject("garantie",garantie);
            mv.addObject("garanties",HwDatas.dataGaranties);
            mv.addObject("bases",HwDatas.dataBaseFunctions);
            mv.addObject("imms",HwDatas.dataFunctions);
            mv.addObject("professions",HwDatas.dataProfession);
            mv.addObject("payMethods",HwDatas.dataPayMethods);
        }else{
            mv.setViewName("redirect:/partner/center");
            return mv;
        }
        return mv;
    }

    @RequestMapping(value = "/partner/rdv/delete/{id}.html")
    public ModelAndView deleteRdv(@PathVariable("id") long id, ModelAndView mv){
        PartnerRdv rdv = rdvService.getRdvByPidAndId(HwTools.getCurrentUser().getId(),id);
        if (rdv!=null){
            rdvService.delete(rdv);
        }
        mv.setViewName("redirect://partner/admin/message");
        return mv;
    }

    @RequestMapping(value = "/partner/rdv/detail/{id}.html")
    public ModelAndView detailRdv(@PathVariable("id") long id,ModelAndView mv){
        PartnerRdv rdv = rdvService.getRdvByPidAndId(HwTools.getCurrentUser().getId(),id);
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            //do something
            //then go to center
            mv.setViewName("redirect:/partner/admin/message");
            return mv;
        }
        mv.addObject("rdv",rdv);
        mv.setViewName("partners/detailRdv");
        return mv;
    }



    @RequestMapping(value = "/partner/detail/data")
    public Object partnerDetailData(){
        return partnerService.findPartnerById(HwTools.getCurrentUser().getId());
    }

}
