package com.utudo.hwwd.controllers;


import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwMatch;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.service.impl.AnnonceServiceImpl;
import com.utudo.hwwd.service.impl.NeedsServiceImpl;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import com.utudo.hwwd.service.impl.PaysServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class DataController extends MainController{

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    NeedsServiceImpl needsService;

    @Resource
    PaysServiceImpl paysService;

    @Resource
    PartnerServiceImpl partnerService;

    @RequestMapping("/data/garanties/professions")
    public List<String> getDataProfessions(){
        return HwDatas.dataProfession;
    }


    @RequestMapping("/site/recommand")
    public Object recommand(){
        return HwDatas.dataRecommand;
    }


    @RequestMapping("/data/pays")
    public Object pays(){
        return paysService.getPaysLists();
    }

    @RequestMapping("/data/islogin")
    public Object islogin(){
        if (HwTools.getCurrentUser()!=null){
            return HwTools.getCurrentUser();
        }
        return HwTools.hashPassword(HwTools.getTimeMillis()+"_hwwd_admin");
    }


    @RequestMapping("/admin/data/match")
    public Object matchHouseData(){
        String id  = HwTools.request().getParameter("id");
        if (id!=null){
            Optional<Annonce> annonceOptional = annonceService.findAnnonceById(Long.parseLong(id));
            if (annonceOptional.isPresent()){
                HwMatch hwMatch = new HwMatch(annonceOptional.get());
                hwMatch.setNeedsService(needsService);
                hwMatch.matchData();
                return hwMatch.getNeeds();
            }
            return null;
        }
        return null;
    }

    @RequestMapping(value = "/data/partner/cover/{id}")
    public Object partnerCover(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        Partner partner = partnerService.findPartnerById(id);
        data.put("pid",id);
        if (partner!=null){
            if (partner.getCover()!=null){
                data.put("status",1);
                data.put("cover","/partners/"+id+"/"+partner.getCover());
                return data;
            }
        }
        data.put("status",0);
        return data;
    }
}
