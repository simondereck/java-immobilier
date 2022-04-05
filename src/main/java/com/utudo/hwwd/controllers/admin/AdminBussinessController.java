package com.utudo.hwwd.controllers.admin;

import com.tencentcloudapi.tcaplusdb.v20190823.models.IdlFileInfo;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.searchModel.PartnerSearchModel;
import com.utudo.hwwd.service.impl.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class AdminBussinessController extends MainController {

    @Resource
    ApplyModelServiceImpl applyModelService;

    @Resource
    AnnonceBussinessServiceImpl annonceBussinessService;

    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    PaysServiceImpl paysService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    BussinessModelServiceImpl bussinessModelService;


    @RequestMapping(value = "/admin/apply/lists")
    public ModelAndView applyModels(ModelAndView mv){

        mv.setViewName("adminBussiness/apply");


        SqlHelper sqlHelper = new SqlHelper("hw_apply_info");
        sqlHelper.setSelect("select count(*) ");
        BigInteger count = applyModelService.getApplyCountBySql(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setBaseUrl("/admin/apply/lists");
        pageHelper.setLimit(15);


        sqlHelper.setSelect("select * ");
        sqlHelper.setOrder("id","desc");
        ArrayList<ApplyModel> data = applyModelService.getApplyBySql(sqlHelper.toSql());

        mv.addObject("applys",data);
        mv.addObject("pageHelper",pageHelper);

        return mv;
    }


    @RequestMapping(value = "/admin/apply/detail/data")
    public Object applyDetailData(){
        String id = HwTools.request().getParameter("id");
        HashMap<String,Object> data = new HashMap<>();
        if (id!=null){
            ApplyModel applyModel = applyModelService.findApplyById(Long.parseLong(id));
            if (applyModel!=null){
                data.put("status",1);
                data.put("apply",applyModel);
                return data;
            }
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/apply/update/{id}")
    public ModelAndView updateApply(@PathVariable("id") long id,ModelAndView mv,ApplyModel applyModel){
        mv.addObject("id",id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            ApplyModel model = applyModelService.findApplyById(id);
            applyModel.setCtime(model.getCtime());
            applyModel.setId(model.getId());
            applyModel.setStatus(applyModel.getiStatus());
            applyModelService.update(applyModel);
            mv.setViewName("redirect:/admin/apply/lists");
            return mv;
        }
        mv.setViewName("adminBussiness/updateApply");
        return mv;
    }


    @RequestMapping(value = "/admin/bussiness/sell/data")
    public Object bussinessSellData(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper("hw_annonce_bussiness");
        sqlHelper.setSelect("select count(*)");

        BigInteger count = annonceBussinessService.getAnnonceCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        String page = HwTools.request().getParameter("page");
        if (page!=null){
             pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setLimit(15);
        pageHelper.setBaseUrl("/admin/bussiness/sell/data");
        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);

        ArrayList<AnnonceBussiness> annonces = annonceBussinessService.getAnnonceBySql(sqlHelper.toSql());

        if (annonces!=null&&annonces.size()>0){
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
            data.put("pageHelper",pageHelper);
        }else{
            data.put("status",0);
        }

        return data;
    }

    @RequestMapping(value = "/admin/bussiness/sell/detail/data")
    public Object detailSell(){
        HashMap<String,Object> data = new HashMap<>();
        String id = HwTools.request().getParameter("id");
        if (id!=null){
            AnnonceBussiness annonce = annonceBussinessService.findAnnonceById(Long.parseLong(id));
            if (annonce!=null){
                long pid = annonce.getPay();
                Pays pays = paysService.getPayById(pid);
                Ville ville = villeService.getVilleById(annonce.getLocation());
                data.put("ville",ville);
                data.put("types", HwDatas.dataTypes);
                if (annonce.getTypes()==0){
                    data.put("houseType",HwDatas.dataHoseTypeOne);
                }else if (annonce.getTypes()==1){
                    data.put("houseType",HwDatas.dataHouseTypeTwo);
                }else if (annonce.getTypes()==2){
                    data.put("houseType",HwDatas.dataHouseTypeThree);
                }
                data.put("roomType",HwDatas.dataBussinessHouse);

                data.put("pays",pays);
                data.put("annonce",annonce);
                data.put("imgUrl","/partners/"+annonce.getPid()+"/");
                data.put("pays",pays);
                data.put("annonce",annonce);
                data.put("status",1);
                return data;
            }
        }

        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/bussiness/houses")
    public ModelAndView bussinessSell(ModelAndView mv){
        mv.setViewName("adminBussiness/houses");
        return mv;
    }


    @RequestMapping(value = "/admin/bussiness/sell/detail/{id}")
    public ModelAndView sellDetail(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("adminBussiness/sellDetail");
        mv.addObject("id",id);
        return mv;
    }


    @RequestMapping(value = "/admin/search/bussiness/partner/data")
    public Object partnerBussinesSearchData(PartnerSearchModel model){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<Partner> partners = partnerService.getPartnersBySql(model.toSql());
        if (partners!=null && partners.size()>0){
            ArrayList<Object> ids = new ArrayList<>();
            partners.forEach(p->{
                ids.add(p.getId());
            });
            SqlHelper sqlHelper = new SqlHelper("hw_bussiness_model");
            sqlHelper.addInCondition("uid",ids);
            ArrayList<BussinessModel> bussinessModels = bussinessModelService.getBussinessModelListsBySql(sqlHelper.toSql());
            data.put("partners",partners);
            data.put("models",bussinessModels);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/bussiness/partner/lists/data")
    public Object partnerBussinessData(PartnerSearchModel searchModel){
        HashMap<String,Object> data = new HashMap<>();


        if (searchModel.getId()>0 || searchModel.getNom()!=null
                || searchModel.getPrenom()!=null
                || searchModel.getEmail()!=null
                || searchModel.getTelephone()!=null){
            SqlHelper sqlHelper = new SqlHelper(Partner.class.getAnnotation(Table.class).name());

            if (searchModel.getId()>0){
                sqlHelper.addAndCondition("id",searchModel.getId());
            }
            if (searchModel.getNom()!=null){
                sqlHelper.addAndLikeCondition("nom",searchModel.getNom());
            }

            if (searchModel.getTelephone()!=null){
                sqlHelper.addAndLikeCondition("telephone",searchModel.getTelephone());
            }

            if (searchModel.getPrenom()!=null){
                sqlHelper.addAndLikeCondition("prenom",searchModel.getPrenom());
            }

//            if (searchModel.getType()>=0){
//                sqlHelper.addAndCondition("type",searchModel.getType());
//            }

            ArrayList<Partner> partners = partnerService.getPartnersBySql(sqlHelper.toSql());

            if (partners!=null && partners.size()>0){
                ArrayList<Object> pids = new ArrayList<>();
                partners.forEach(p->{
                    pids.add(p.getId());
                });

                ArrayList<BussinessModel> bussinessModels = bussinessModelService.getBussinessByPids(pids);

                HashMap<Long,Object> partnersObj = new HashMap<>();
                partners.forEach(p->{
                    partnersObj.put(p.getId(),p);
                });

                data.put("partners",partnersObj);
                data.put("models",bussinessModels);
                data.put("status",1);

            }

        }else{
            SqlHelper sqlHelper = new SqlHelper(BussinessModel.class.getAnnotation(Table.class).name());
            sqlHelper.setSelect("select count(*)");
            sqlHelper.setOrder("id","desc");
            BigInteger count = bussinessModelService.getBussinessCount(sqlHelper.toSql());
            if (searchModel.getType()>=0){
                sqlHelper.addAndCondition("type",searchModel.getType());
            }
            PageHelper pageHelper = new PageHelper();
            pageHelper.setBaseUrl("/admin/bussiness/partner/lists/data");
            String page =  HwTools.request().getParameter("page");
            pageHelper.setCount(Long.parseLong(count+""));
            if (page!=null){
                pageHelper.setCurrentPage(Long.parseLong(page));
            }else{
                pageHelper.setCurrentPage(0);
            }
            sqlHelper.setSelect("select * ");
            sqlHelper.setPageHelper(pageHelper);
            ArrayList<BussinessModel> bussinessModels = bussinessModelService.getBussinessModelListsBySql(sqlHelper.toSql());

            if (bussinessModels!=null&&bussinessModels.size()>0){
                data.put("status",1);
                ArrayList<Object> ids = new ArrayList<>();
                bussinessModels.forEach(b->{
                    ids.add(b.getUid());
                });
                ArrayList<Partner> partners = partnerService.getPartnersByIds(ids);
                HashMap<Long,Object> partnersObj = new HashMap<>();
                partners.forEach(p->{
                    partnersObj.put(p.getId(),p);
                });
                data.put("partners",partnersObj);
                data.put("pageHelper",pageHelper);
                data.put("models",bussinessModels);
                return data;
            }

        }



        data.put("status",0);
        return data;
    }


    @RequestMapping(value = "/admin/bussiness/partner/lists")
    public ModelAndView partnerBussinessLists(ModelAndView mv){
        mv.setViewName("adminBussiness/partnerLists");
        return mv;
    }


    @RequestMapping(value = "/admin/partner/bussiness/set/{id}")
    public ModelAndView partnerBussinessSet(@PathVariable("id") long id,ModelAndView mv,BussinessModel bussinessModel){
        mv.setViewName("adminBussiness/setPartner");
        BussinessModel model = bussinessModelService.findModelById(id);
        mv.addObject("model",model);
        mv.addObject("id",id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            model.setType(bussinessModel.getType());
            bussinessModelService.update(model);
            mv.setViewName("redirect:/admin/bussiness/partner/lists");
            return mv;
        }
        return mv;
    }

}
