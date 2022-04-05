package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.*;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.models.ResponseMessage;
import com.utudo.hwwd.service.AnnonceService;
import com.utudo.hwwd.service.impl.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@EnableAutoConfiguration
public class UserNeedsController extends MainController{


    @Resource
    GarantieServiceImpl garantieService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    RdvServiceImpl rdvService;

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    DocumentsServiceImpl documentsService;

    @RequestMapping(value = "/user/needs/update/garantie/{id}")
    public ModelAndView updateGarantie(@PathVariable("id") Integer id, ModelAndView mv, Garantie garantieObj){
        Person person = HwTools.getCurrentUser();
        Garantie garantie = garantieService.findGarantieByIdAndUid(id,person.getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            garantie.loadFromObj(garantieObj);
            garantieService.update(garantie);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        mv.addObject("garanties", HwDatas.dataGaranties);
        mv.addObject("professions",HwDatas.dataProfession);
        mv.addObject("garantie",garantie);
        mv.addObject("payMethods",HwDatas.dataPayMethods);
        mv.setViewName("user/updateGarantie");

        return mv;
    }


    @RequestMapping("/user/needs/update/envir/{id}")
    public ModelAndView updateEnvir(@PathVariable("id") Integer id, ModelAndView mv, Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setEnvirJson(HwTools.toJson(needsObj.getEnvir()));
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        mv.addObject("trans",HwDatas.dataTrans);
        mv.addObject("needs",needs);
        mv.addObject("imms",HwDatas.dataFunctions);

        mv.setViewName("user/updateEnvir");

        return mv;
    }


    @RequestMapping("/user/needs/update/location/{id}")
    public ModelAndView updateLocation(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setLocation(needsObj.getLocation());
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        if (needs.getLocation()!=null){
            Ville ville = villeService.findVilleById(needs.getLocation());
            mv.addObject("ville",ville);
        }

        mv.addObject("needs",needs);
        mv.setViewName("user/updateLocation");

        return mv;
    }

    @RequestMapping("/user/needs/update/budget/{id}")
    public ModelAndView updateBudget(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setBudget(needsObj.getBudget());
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }

        mv.addObject("needs",needs);
        mv.setViewName("user/updateBudget");
        return mv;
    }

    @RequestMapping("/user/needs/update/sdate/{id}")
    public ModelAndView updateSdate(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setSdate(needsObj.getSdate());
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }

        mv.addObject("needs",needs);
        mv.setViewName("user/updateSdate");
        return mv;
    }


    @RequestMapping("/user/needs/update/rtype/{id}")
    public ModelAndView updateRtype(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setRtype(needsObj.getRtype());
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        mv.addObject("rtype",HwDatas.dataHouse);
        mv.addObject("needs",needs);
        mv.setViewName("user/updateRtype");

        return mv;
    }



    @RequestMapping("/user/needs/update/trans/{id}")
    public ModelAndView updateTrans(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setTrans(needsObj.getTrans());
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        mv.addObject("trans",HwDatas.dataTrans);
        mv.addObject("needs",needs);
        mv.setViewName("user/updateTrans");

        return mv;
    }

    @RequestMapping("/user/needs/update/base/{id}")
    public ModelAndView updateImms(@PathVariable("id") Integer id,ModelAndView mv,Needs needsObj){
        Person person = HwTools.getCurrentUser();
        Needs needs = needsService.findNeedsByUidAndId(person.getId(),id);

        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            needs.setImmsJson(HwTools.toJson(needsObj.getBase()));
            needsService.update(needs);
            mv.setViewName("redirect:/user/center/");
            return  mv;
        }
        mv.addObject("trans",HwDatas.dataTrans);
        mv.addObject("needs",needs);
        mv.addObject("bases",HwDatas.dataBaseFunctions);
        mv.setViewName("user/updateImms");

        return mv;
    }


    @RequestMapping("/user/documents")
    public ModelAndView updateDocuments(ModelAndView mv){
        long uid = HwTools.getCurrentUser().getId();
        Documents documents = documentsService.findOneByUid(uid);
        if (documents!=null){
            mv.addObject("documents",documents);
        }else{

            Documents documentsNew = new Documents();
            documentsNew.setUid(uid);

            Needs needs = needsService.findNeedsByUid(uid);
            Optional<Garantie> optionalGarantie = garantieService.findGarantieByNidAndUid(needs.getId(),uid);

            if (optionalGarantie.isPresent()){
                Garantie garantie = optionalGarantie.get();
                documentsNew.setType(garantie.getGarantie());
            }
            documentsService.save(documentsNew);
            mv.addObject("documents",documentsNew);
        }

        mv.setViewName("users/documents");

        return mv;
    }

    @RequestMapping("/user/message")
    public ModelAndView userMessage(ModelAndView mv){
        mv.setViewName("users/message");

        SqlHelper sqlHelper = new SqlHelper("hw_rdv");
        sqlHelper.setSelect("select count(*)");
        sqlHelper.addAndCondition("uid",HwTools.getCurrentUser().getId());
        sqlHelper.setOrder("id","desc");
        BigInteger count = rdvService.getRdvCountBySql(sqlHelper.toSql());
        logger.error("count："+count);
        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setLimit(15);
        pageHelper.setBaseUrl("/user/message");
        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);
        ArrayList<PartnerRdv> rdvs = rdvService.getRdvBySql(sqlHelper.toSql());
        HashMap<String, Partner> partners = new HashMap<>();
        HashMap<String,Annonce> annonces = new HashMap<>();
        if (rdvs.size()>0){
            ArrayList<Object> pids = new ArrayList<>();
            ArrayList<Object> aids = new ArrayList<>();
            rdvs.forEach(r->{
                pids.add(r.getPid());
                aids.add(r.getAid());
            });
            annonceService.getAnnoncesByIds(aids).forEach(a->{
                annonces.put(a.getId()+"",a);
            });

            partnerService.getPartnersByIds(pids).forEach(p->{
                partners.put(p.getId()+"",p);
            });

        }

        mv.addObject("partners",partners);
        mv.addObject("annonces",annonces);


        mv.addObject("pageHelper",pageHelper);
        mv.addObject("rdvs",rdvs);


        return mv;
    }


    @RequestMapping(value = "/user/uplaod/file")
    public HashMap<String,Object> userUploadFile(@RequestParam("file") MultipartFile file,@RequestParam("type") String type){
        long uid = HwTools.getCurrentUser().getId();
        HashMap<String,Object> result = new HashMap<>();
        String fileType = file.getContentType();
        final String localPath = HwDatas.USER_FILE_PATH + uid;
        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileType.split("/")[1];
        //重新生成文件名
        String fileNameNew =  HwTools.hashPassword(fileName+HwTools.getTimeMillis()) +"."+ suffixName;
        try{
            if (FileUtils.upload(file, localPath, fileNameNew)) {
                //文件存放的相对路径(一般存放在数据库用于img标签的src)
                String relativePath = fileNameNew;
                result.put("path",relativePath);//前端根据是否存在该字段来判断上传是否成功
                result.put("message","图片上传成功");
                result.put("status",1);
            }else{
                result.put("message","图片上传失败");
                result.put("status",0);
            }
        }catch(Exception e){
            result.put("message","图片上传失败");
            result.put("status",0);
        }
        result.put("uid",uid);
        return result;
    }


    @RequestMapping(value = "/user/documents/data")
    public Object documentData(){
        HashMap<String,Object> data = new HashMap<>();
        Documents documentsObj = documentsService.findOneByUid(HwTools.getCurrentUser().getId());
        data.put("documents",documentsObj);
        data.put("status",1);
        return data;
    }

    @RequestMapping(value = "/user/upload/info")
    public Object uploadInfo(Documents documents){
        Documents documentsObj = documentsService.findOneByUid(HwTools.getCurrentUser().getId());
        if (documentsObj!=null){
            if (documents.getCertifcation()!=null){
                documentsObj.setCertifcation(documents.getCertifcation());
            }

            if (documents.getGrantiepayRoll()!=null){
                documentsObj.setGrantiepayRoll(documents.getGrantiepayRoll());
            }

            if (documents.getGrantieVisa()!=null){
                documentsObj.setGrantieVisa(documents.getGrantieVisa());
            }

            if (documents.getPassport()!=null){
                documentsObj.setPassport(documents.getPassport());
            }

            if (documents.getPayRoll()!=null){
                documentsObj.setPayRoll(documents.getPayRoll());
            }

            if (documents.getVisa()!=null){
                documentsObj.setVisa(documents.getVisa());
            }

            if (documents.getTax()!=null){
                documentsObj.setTax(documents.getTax());
            }

            documentsService.update(documentsObj);
        }
        return new ResponseMessage(1,"success");
    }



}
