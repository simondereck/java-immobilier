package com.utudo.hwwd.controllers.partner;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.EstimationUploadModel;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.service.impl.EstimationUploadModelServiceImpl;
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
public class PartnerEstimationController extends MainController {

    @Resource
    EstimationUploadModelServiceImpl uploadModelService;

    @Resource
    VilleServiceImpl villeService;

    @RequestMapping(value = "/partner/estimation")
    public ModelAndView partnerEstimation(ModelAndView mv){
        mv.setViewName("partners/estimation");
        return mv;
    }


    @RequestMapping(value = "/partner/bussiness/data/estimation")
    public Object estimationData(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(EstimationUploadModel.class.getAnnotation(Table.class).name());
        sqlHelper.addAndCondition("uid", HwTools.getCurrentUser().getId());
        sqlHelper.setSelect("select count(*) ");

        BigInteger count = uploadModelService.getEstimationUploadCount(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(10);
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }

        pageHelper.setBaseUrl("/partner/bussiness/data/estimation");

        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select *");
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

    @RequestMapping(value = "/partner/bussiness/estimation/editor/{id}")
    public ModelAndView  estimationEditor(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("partners/estimationEditor");
        mv.addObject("id",id);

        return mv;
    }

    @RequestMapping(value = "/partner/bussiness/estimation/editor/data")
    public Object estimationEditorData(){
        HashMap<String,Object> data = new HashMap<>();
        String id = HwTools.request().getParameter("id");
        if (id!=null){
            EstimationUploadModel model = uploadModelService.getEstimationByUidAndId(HwTools.getCurrentUser().getId(),Long.parseLong(id));
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
