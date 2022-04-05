package com.utudo.hwwd.controllers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.searchModel.AnnonceBussinessSearchModel;
import com.utudo.hwwd.models.searchModel.AnnonceSearchModel;
import com.utudo.hwwd.models.searchModel.MapSearchModel;
import com.utudo.hwwd.service.impl.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class AdminSearchController extends MainController {

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    AnnonceBussinessServiceImpl annonceBussinessService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    PaysServiceImpl paysService;

    @Resource
    AnnonceLatLngServiceImpl annonceLatLngService;


    @RequestMapping(value = "/admin/search")
    public Object adminSearch(){
        String keyword = HwTools.request().getParameter("keyword");
        //select telephone/email/username from user -> needs
        //select telephone/email/usernmae from admin -> needs et annnonces et admin
        //select telephone/email/username from partner -> annonces
        //select ville from ville -> area -> annonces et needs
        //select postcode from ville -> area -> annonces et needs
        //select ecole from ecole -> annonces et needs
        return keyword;
    }


    @RequestMapping(value = "/admin/search/annonces")
    public Object adminSearchAnnonces(Annonce annonce){
        HashMap<String,Object> data = new HashMap<>();
        AnnonceSearchModel model = new AnnonceSearchModel(annonce);
        ArrayList<Annonce> annonces = annonceService.getAnnonceByPage(model.getSql());
        if (annonces.size()>0){
            ArrayList<Object> vids = new ArrayList<>();
            ArrayList<Object> pids = new ArrayList<>();
            annonces.forEach(a->{
                if (a.getLocation()!=null){
                    vids.add(a.getLocation());
                    pids.add(a.getPid());
                }
            });
            HashMap<Long, Ville> dataVille = new HashMap<Long, Ville>();
            villeService.getVillesByIds(vids).forEach(v->{
                dataVille.put(v.getId(),v);
            });
            HashMap<Long, Partner> dataPartner = new HashMap<>();
            partnerService.getPartnersByIds(pids).forEach(p->{
                dataPartner.put(p.getId(),p);
            });
            data.put("annonces",annonces);
            data.put("villes",dataVille);
            data.put("partners",dataPartner);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }


    @RequestMapping(value = "/admin/search/bussiness/annonces")
    public Object adminSearchBussinessAnnonces(AnnonceBussiness annonce){
        HashMap<String,Object> data = new HashMap<>();
        AnnonceBussinessSearchModel model = new AnnonceBussinessSearchModel(annonce);
        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getAnnonceBySql(model.getSql());
        if (annonces!=null && annonces.size()>0){

            ArrayList<Object> pids = new ArrayList<>();
            ArrayList<Object> countryIds = new ArrayList<>();
            HashMap<Long,Object> partners = new HashMap<>();
            HashMap<Long,Object> pays = new HashMap<>();
            annonces.forEach(a->{
                pids.add(a.getPid());
                countryIds.add(a.getPay());
            });
            partnerService.getPartnersByIds(pids).forEach(p->{
                partners.put(p.getId(),p);
            });

            paysService.getPaysByIds(countryIds).forEach(p->{
                pays.put(p.getId(),p);
            });

            data.put("pays",pays);
            data.put("partners",partners);
            data.put("status",1);
            data.put("annonces",annonces);

        }else{
            data.put("status",0);
        }
        return data;
    }



    @RequestMapping(value = "/site/map/search")
    public Object mapSearch(MapSearchModel mapSearchModel) throws JsonProcessingException {
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<Annonce> annonces = annonceService.getAnnonceByPage(mapSearchModel.toSql());
        if (annonces!=null && annonces.size()>0){
            ArrayList<Object> ids = new ArrayList<>();
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

            data.put("status",1);
            data.put("latlngs",annonceLatLngs);
            return data;
        }

        data.put("status",0);
        return data;
    }

}
