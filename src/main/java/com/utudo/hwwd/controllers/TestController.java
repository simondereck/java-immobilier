package com.utudo.hwwd.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.extModel.GeocodingFeature;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.models.extModel.Village;
import com.utudo.hwwd.models.extModel.Zone;
import com.utudo.hwwd.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.*;


@RestController
@EnableAutoConfiguration
public class TestController extends MainController{

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    UniversiteServiceImpl universiteService;


    @RequestMapping(value = "/matchTest")
    public Object matchTest(){
        Optional<Annonce> annonce	= annonceService.findAnnonceById(1);
        if (annonce.isPresent()){
            Annonce ann = annonce.get();
            HwMatch hwMatch = new HwMatch(ann);
            hwMatch.setNeedsService(needsService);
            hwMatch.matchData();

            return hwMatch.getNeeds();
        }

        return 0;

    }


    @RequestMapping(value = "/testResult")
    public ArrayList<Needs> testResult(){
        Optional<Annonce> annonce	= annonceService.findAnnonceById(1);
        if (annonce.isPresent()){
            Annonce ann = annonce.get();
            HwMatch hwMatch = new HwMatch(ann);
            hwMatch.setNeedsService(needsService);
            hwMatch.matchData();
        }

        return null;

    }

    @Autowired
    private HwEmail mailUtils;

    public void sendMail() throws MessagingException {
        //省略data数据的获取。。。

//        Map<String, Object> data = new HashMap<>();
//        data.put("dsResult", dsResult);
//
//        String[] cc = {"82****80@qq.com"};
//        String[] bcc = {};
//        mailUtils.sendThymeleafMail("今日的日期",
//                "159****3412@163.com", "zha****e6@b****.cn", cc, bcc, data, "mail.html");
    }


    @RequestMapping(value = "/testMail")
    public String testMail(){

//        String templatePath =  "/mail/forgotPassword.html";
        String templatePath =  "/mail/forgot.html";
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("path","1235566");
        try{
            mailUtils.sendThymeleafMail("【海外皇地】忘记密码","513881204@qq.com",data,templatePath);

        }catch (Exception exception){
            logger.error("到底哪里出错了:"+exception.toString());
        }

        return "hello";
    }


    @RequestMapping(value = "/testMail1")
    public String testMail1(){
        String templatePath =  "/mail/forgotPassword.html";
//        String templatePath =  "/mail/test1.html";
        HashMap<String,Object> data = new HashMap<String,Object>();
        data.put("test","这不仅仅是个测试");
        try{
//            mailUtils.sendThymeleafMail("HELLO","funichina@gmail.com",data,templatePath);
//            mailUtils.sendThymeleafMail("HELLO","youjia@huangsfang.com",data,templatePath);
            mailUtils.sendThymeleafMail("HELLO","z513881204@icloud.com",data,templatePath);

        }catch (Exception exception){
            logger.error("到底哪里出错了:"+exception.toString());
        }

        return "hello";
    }


    @RequestMapping(value = "/testRead")
    public Object testJson() throws JsonProcessingException {
        String pathname = "/Library/WebServer/Documents/france.txt";
        String data = HwTools.readJsonFile(pathname);
        logger.error(data);
        ObjectMapper objectMapper = new ObjectMapper();

        List<Village> villages = objectMapper.readValue(data, new TypeReference<List<Village>>(){});
//        List<HashMap<String, Object>> villages = objectMapper.readValue(data,new TypeReference<List<HashMap<String,Object>>>(){});

        String time = HwTools.getTime();
        for (int i = 0; i < villages.size(); i++) {
            Village n = villages.get(i);
            logger.error(n.toString());
            Ville ville = new Ville();

            ville.setCode(Integer.parseInt(n.getCodep().substring(0,n.getCodei().length()-3)));
            ville.setPostcode(n.getCodep());
            ville.setCtime(time);
            ville.setUtime(time);
            ville.setName(n.getName());
            ville.setLabel(n.getLibelle());

            String str = n.getGps();
            if (str!=null&&!str.equals("")){
                String[] latlng;
                String delimeter = ", ";
                latlng = str.split(delimeter);
                ville.setLat(latlng[0]);
                ville.setLng(latlng[1]);
            }else{
                logger.error("就是这里出错了");
            }
            logger.error(ville.toString());
        }

        return "finish";
    }


    @RequestMapping(value = "/test/annonce")
    public Object testSms(){
        ArrayList<Annonce> annonces = annonceService.getAllAnnonces();
        logger.error("看看的啊："+annonces.toString());
        for (int i = 0; i < annonces.size(); i++) {
            Annonce anno = new Annonce();
            anno.setEnvirJson(annonces.get(i).getEnvirJson());
            anno.setImgsJson(annonces.get(i).getImgsJson());
            anno.setAddress(annonces.get(i).getAddress());
            anno.setDescription(annonces.get(i).getDescription());
            anno.setCharges(annonces.get(i).getCharges());
            anno.setEnergy(annonces.get(i).getEnergy());
            anno.setPid(annonces.get(i).getPid());
            anno.setCtime(annonces.get(i).getCtime());
            anno.setUtime(annonces.get(i).getUtime());
            anno.setLoyer(annonces.get(i).getLoyer());
            anno.setImmsJson(annonces.get(i).getImmsJson());
            anno.setPrice(annonces.get(i).getPrice());
            anno.setLocation(annonces.get(i).getLocation());
            anno.setSdate(annonces.get(i).getSdate());
            anno.setSize(annonces.get(i).getSize());
            anno.setRtype(annonces.get(i).getRtype());
            anno.setTrans(annonces.get(i).getTrans());
            anno.setStatus(annonces.get(i).getStatus());
            annonceService.update(anno);

        }
        return annonces;
    }


    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;

    @RequestMapping(value = "/test/map")
    public Object map(){
        //map
        ArrayList<Annonce> annonces = annonceService.getAllAnnonces();
        ArrayList<MapBoxGeocodingResponse> responses = new ArrayList<>();
        annonces.forEach(a->{
            MapBoxGeocodingResponse response;
            Ville ville = villeService.findVilleById(a.getLocation());
            if(ville!=null){
                response = HwHttpClient.sendGetReqeust(a.getAddress()+","+ville.getPostcode());
            }else {
                response = HwHttpClient.sendGetReqeust(a.getAddress());
            }

            if (response.getFeatures().size()>0){
                GeocodingFeature geocodingFeature = response.getFeatures().get(0);
                logger.error("都是啥"+geocodingFeature.toString());
                if (a.getLatlng()!=0){
                    Optional<AnnonceLatLng> annonceLatLngOptional = annonceLatLngService.findOneById(a.getLatlng());
                    if (annonceLatLngOptional.isPresent()){
                        AnnonceLatLng annonceLatLng = annonceLatLngOptional.get();
                        annonceLatLng.setBbox(geocodingFeature.getBbox());
                        annonceLatLng.setAid(a.getId());
                        annonceLatLng.setPid(a.getPid());
                        annonceLatLng.setCenter(geocodingFeature.getCenter());
                        annonceLatLng.setGeometry(geocodingFeature.getGeometry());
                        annonceLatLngService.update(annonceLatLng);
                    }else{
                        AnnonceLatLng annonceLatLng = new AnnonceLatLng();
                        annonceLatLng.setBbox(geocodingFeature.getBbox());
                        annonceLatLng.setAid(a.getId());
                        annonceLatLng.setPid(a.getPid());
                        annonceLatLng.setCenter(geocodingFeature.getCenter());
                        annonceLatLng.setGeometry(geocodingFeature.getGeometry());
                        annonceLatLngService.save(annonceLatLng);
                        AnnonceLatLng latLngObj = annonceLatLngService.findOnceByPidAndAid(a.getPid(),a.getId());
                        a.setLatlng(latLngObj.getId());
                        annonceService.update(a);
                    }
                }
            }
        });
        return 123;
    }


    @RequestMapping(value = "/test/delete/needs")
    public String  deleteNeeds(){
        ArrayList<Needs>  needs = needsService.findAllNeddsByUid(1);
        needs.forEach(n->{
            needsService.delete(n.getId());
        });
        return "213";
    }



    List<String> prix = new ArrayList<String>(){{
        add("A");
        add("B");
        add("C");
        add("E");
        add("F");
        add("G");
        add("H");
        add("I");
        add("J");
        add("K");
        add("L");
        add("M");
        add("N");
        add("O");
        add("P");
        add("Q");
        add("R");
        add("S");
        add("T");
        add("U");
        add("V");
        add("W");
        add("X");
        add("Y");
        add("Z");
    }};

    ArrayList<String> suffix = new ArrayList<>(){{
        add("@qq.com");
        add("@163.com");
        add("@gmail.com");
        add("@126.com");
        add("@icloud.com");
        add("@souhu.com");
        add("@yahoo.com");
    }};

    @Resource
    PartnerServiceImpl partnerService;

    @RequestMapping("/test/partners/create")
    public Object createPartners(){
        Random rand = new Random();
        String nom = "Ye";
        String prenom = "Liang";
        String password = "123456";
        for (int i = 0; i < 10; i++) {
            Partner partner = new Partner();
            int sex = rand.nextInt(2);
            partner.setSex(sex);
            partner.setNom(nom + i);
            partner.setPrenom(prenom + i);
            partner.setPassword(password);
            partner.setTelephone("0662984065" + i);
            int sizeLength = rand.nextInt(9);
            StringBuilder builderEmail = new StringBuilder();
            for (int j = 0; j < sizeLength; j++) {
                String item = "";
                int skip = rand.nextInt(2);
                if (skip == 0) {
                    if (j != 0) {
                        item = rand.nextInt(10) + "";
                    }
                } else {
                    item = prix.get(rand.nextInt(prix.size()));
                }

                builderEmail.append(item);
            }
            builderEmail.append(suffix.get(rand.nextInt(suffix.size())));
            partner.setEmail(builderEmail.toString());
            partnerService.save(partner);
        }
        return 456;
    }

//    @RequestMapping(value = "/test/ecole")
    public Object universite(ModelAndView mv) throws JsonProcessingException {
        String pathname = "/Library/WebServer/Documents/data.json";
        String data = HwTools.readJsonFile(pathname);
        ObjectMapper objectMapper = new ObjectMapper();

        List<HashMap<String, Object>> ecoles = objectMapper.readValue(data, new TypeReference<List<HashMap<String,Object>>>(){});

        ArrayList<Universite> unviersites = new ArrayList<>();
        ecoles.forEach(e->{
            HashMap<String,Object> fields = (HashMap<String, Object>) e.get("fields");

            String city = (String) fields.get("acad");

            String name = (String) fields.get("lib_uai");

            String professs = (String) fields.get("groupe_cnu");

            String postcode = (String) fields.get("code_commune");

            String ville  = (String) fields.get("commune");

            List<Double> latlng = (List<Double>) fields.get("geolocalisation");

            Universite unviersite = new Universite();
            unviersite.setCity(city);
            unviersite.setName(name);
            unviersite.setProfession(professs);
            unviersite.setPostcode(postcode);
            unviersite.setVille(ville);
            unviersite.setLat(latlng.get(0));
            unviersite.setLng(latlng.get(1));
            universiteService.save(unviersite);
            unviersites.add(unviersite);

        });

        return unviersites.get(0).toString();
    }

    @Resource
    UserFileUploadHelper uploadHelper;

    @RequestMapping(value = "/test/file")
    public Object upload(@RequestParam("file") MultipartFile file) throws Exception {
//         uploadHelper.uploadFile(file);
        String type = HwTools.request().getParameter("type");
        logger.error("xxxxx"+type);
        uploadHelper.uploadFile(file);
        return uploadHelper.uploadDir;
    }


    @RequestMapping(value = "/test/image")
    public ModelAndView images(ModelAndView mv){
        mv.setViewName("test/test");

        return mv;
    }


    @Resource
    PaysServiceImpl paysService;

    @RequestMapping(value = "/test/pays")
    public Object pays() throws JsonProcessingException {
        String pathname = "/Library/WebServer/Documents/pays.json";
        String data = HwTools.readJsonFile(pathname);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Zone> pays = objectMapper.readValue(data, new TypeReference<List<Zone>>() {});

//        pays.forEach(z->{
//            String zname = z.getName();
//            z.getCountrys().forEach(p->{
//                Pays py = new Pays();
//                py.setCode(p.getNumber());
//                py.setName(p.getName());
//                py.setZname(zname);
//                paysService.save(py);
//            });
//        });

        return pays;
//        List<Village> villages = objectMapper.readValue(data, new TypeReference<List<Village>>(){});
//        villages.forEach(n->{
//            logger.error(n.toString());
//            Ville ville = new Ville();
//            ville.setCode(Integer.parseInt(n.getCodep().substring(0,2)));
//            ville.setPostcode(n.getCodep());
//            ville.setName(n.getName());
//            ville.setLabel(n.getLibelle());
//            String str = n.getGps();
//            if (str!=null&&!str.equals("")){
//                String[] latlng;
//                String delimeter = ", ";
//                latlng = str.split(delimeter);
//                ville.setLat(latlng[0]);
//                ville.setLng(latlng[1]);
//            }else{
//                logger.error("就是这里出错了");
//            }
//            villeService.save(ville);
//
//        });

//        return 123;
    }



}
