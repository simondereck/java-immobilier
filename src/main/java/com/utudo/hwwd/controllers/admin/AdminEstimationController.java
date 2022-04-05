package com.utudo.hwwd.controllers.admin;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.EstimationUploadModel;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.Pays;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.service.impl.EstimationUploadModelServiceImpl;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import com.utudo.hwwd.service.impl.PaysServiceImpl;
import com.utudo.hwwd.service.impl.VilleServiceImpl;
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
public class AdminEstimationController extends MainController {

    @Resource
    EstimationUploadModelServiceImpl uploadModelService;


    @Resource
    PartnerServiceImpl partnerService;


    @Resource
    PaysServiceImpl paysService;


    @Resource
    VilleServiceImpl villeService;


    @RequestMapping("/admin/estimations")
    public ModelAndView adminEstimations(ModelAndView mv){
        mv.setViewName("adminEstimation/index");

        return mv;
    }


    @RequestMapping("/admin/estimations/data")
    public Object estimationData(EstimationUploadModel model){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(EstimationUploadModel.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select count(*)");

        if (model.getId()==0){
            sqlHelper.setOrder("id","desc");
        }else{
            sqlHelper.setOrder("id","asc");
        }

        if (model.getTelephone()!=null){
            sqlHelper.addAndLikeCondition("telephone",model.getTelephone());
        }

        if (model.getEmail()!=null){
            sqlHelper.addAndLikeCondition("email",model.getEmail());
        }

        if (model.getNom()!=null){
            sqlHelper.addAndLikeCondition("nom",model.getNom());
        }

        if (model.getPrenom()!=null){
            sqlHelper.addAndLikeCondition("prenom",model.getPrenom());
        }

        if (model.getSize()!=null){
            sqlHelper.addAndCondition("<=","size",model.getSize());
        }

        if (model.getIsSell()>0){
            sqlHelper.addAndCondition("is_sell",model.getIsSell());
        }

        BigInteger count = uploadModelService.getEstimationUploadCount(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setBaseUrl("/admin/estimations/data");
        String page = HwTools.request().getParameter("page");

        pageHelper.setCount(Long.parseLong(count+""));

        if (page != null ){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else {
            pageHelper.setCurrentPage(0);
        }
        pageHelper.setLimit(10);
        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);

        ArrayList<EstimationUploadModel> models = uploadModelService.getEstimationUploadBySql(sqlHelper.toSql());

        if (models!=null && models.size()>0){
            data.put("status",1);
            data.put("models",models);
            data.put("pageHelper",pageHelper);
        }else{
            data.put("status",0);
        }


        return data;
    }


    @RequestMapping(value = "/admin/estimate/detail/{id}")
    public ModelAndView estimateDetail(@PathVariable("id") long id, ModelAndView mv){
        mv.setViewName("adminEstimation/detail");
        mv.addObject("id",id);
        return mv;
    }

    @RequestMapping(value = "/admin/estimate/data/detail/{id}")
    public Object estimateDetailData(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        EstimationUploadModel model = uploadModelService.findEstimateById(id);

        if (model!=null){
            data.put("status",1);
            data.put("model",model);
            if (model.getUid()>0){
                Partner partner = partnerService.findPartnerById(model.getUid());
                if (partner!=null){
                    data.put("partner",partner);
                }
            }

            if (model.getPay()>0){
                Pays pays = paysService.getPayById(model.getPay());
                if (pays!=null){
                    data.put("pays",pays);
                }
            }

            return data;
        }

        data.put("status",0);
        return data;

    }

    @RequestMapping(value = "/admin/estimate/delete/{id}")
    public Object estimateDelete(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        if (!checkIsStageAdmin()){
            try{
                EstimationUploadModel model = uploadModelService.findEstimateById(id);
                if (model!=null){
                    uploadModelService.delete(id);
                    data.put("status",1);
                }
            }catch(Exception e){
                data.put("status",0);
            }

        }else {
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping(value = "/admin/bussiness/estimation/editor/data")
    public Object estimationEditorData(){
        HashMap<String,Object> data = new HashMap<>();
        String id = HwTools.request().getParameter("id");
        if (id!=null){
            EstimationUploadModel model = uploadModelService.findEstimateById(Long.parseLong(id));
            if (model!=null){
                Ville ville = villeService.getVilleById(model.getLocation());
                data.put("ville",ville);
                data.put("status",1);
                data.put("model",model);
                return data;
            }
        }

        data.put("status",0);
        return data;
    }
}
