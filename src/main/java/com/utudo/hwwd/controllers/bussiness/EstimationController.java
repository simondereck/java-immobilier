package com.utudo.hwwd.controllers.bussiness;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwHttpClient;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.EstimationUploadModel;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.models.models.EstimateModel;
import com.utudo.hwwd.service.impl.EstimateModelServiceImpl;
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
public class EstimationController extends MainController {

    @Resource
    EstimateModelServiceImpl estimateModelService;

    @Resource
    EstimationUploadModelServiceImpl uploadModelService;


    @Resource
    VilleServiceImpl villeService;


    @RequestMapping(value = "/bussiness/estimation")
    public ModelAndView estimation(ModelAndView mv){
        mv.setViewName("bussiness/estimation");
        String address = HwTools.request().getParameter("address");
        if (address!=null){
            mv.addObject("address",address);
        }
        return mv;
    }


    @RequestMapping(value = "/bussiness/autoAddress")
    public Object autoAddress(){
        HashMap<String,Object> data = new HashMap<>();
        try{
            HwHttpClient.MapBoxAutoCompleteAddress();
            data.put("status",1);
        }catch (Exception e){
            data.put("status",0);
        }
        return data;
    }


    @RequestMapping(value = "/bussiness/getLocation")
    public Object getLocation(){
        HashMap<String,Object> data = new HashMap<>();
        try{
            String address = HwTools.request().getParameter("address");
            String postcode = HwTools.request().getParameter("code");
            MapBoxGeocodingResponse response = HwHttpClient.sendGetReqeust(address+","+postcode);
            data.put("response",response);
            data.put("status",1);
        }catch (Exception e){
            data.put("status",0);
        }
        return data;
    }




    @RequestMapping(value = "/bussiness/estimation/upload/data")
    public Object estimationUpload(EstimationUploadModel model){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);

        if (HwTools.getCurrentUser()!=null) {
            if (HwTools.getCurrentUser().getType() == HwDatas.PERSON_TYPE_PARTNER) {
                model.setUid(HwTools.getCurrentUser().getId());
                model.setuType(HwTools.getCurrentUser().getType());
            }

            data.put("status", 1);
        }
        uploadModelService.save(model);
        data.put("model",model);
        //do something
        SqlHelper sqlHelper = new SqlHelper(EstimateModel.class.getAnnotation(Table.class).name());
        sqlHelper.setOrder("id","desc");
        sqlHelper.setLimit(" limit 40");

        if (model.getHouseType()==0){
            //Masion
            sqlHelper.addAndCondition("code_type_local",1);
        }else if (model.getHouseType()==1){
            //appartment
            sqlHelper.addAndCondition("code_type_local",2);
        }

        if (model.getLat()!=null && model.getLng()!=null){
            double lat = Double.parseDouble(model.getLat());
            double lng = Double.parseDouble(model.getLng());
            sqlHelper = restSqlHelper(sqlHelper,lat,lng,0.0001);
        }else{
            Ville ville = villeService.findVilleById(model.getLocation());
            sqlHelper.addAndCondition("code_postal",ville.getPostcode());
        }

        ArrayList<EstimateModel> estimateModels = estimateModelService.getEstimateModelBySql(sqlHelper.toSql());

        if (estimateModels!=null && estimateModels.size()>0){
            data.put("estimates",estimateModels);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }



    public SqlHelper restSqlHelper(SqlHelper sqlHelper,double lat, double lng,double step){
        sqlHelper.setSelect("select count(*) ");
        sqlHelper.addAndBetteweenCondition("latitude",lat - step,lat + step);
        sqlHelper.addAndBetteweenCondition("longitude",lng - step,lng + step);
        BigInteger count = estimateModelService.getModelCountBySql(sqlHelper.toSql());
        if (count!=null){
            int num = Integer.parseInt(count+"");
            if (num > 10){
                sqlHelper.setSelect("select * ");
                return sqlHelper;
            }
        }
        sqlHelper.removeLastAndCondition();
        sqlHelper.removeLastAndCondition();
        return restSqlHelper(sqlHelper,lat,lng,2 * step);
    }



    @RequestMapping(value = "/bussiness/estimation/upload/price")
    public Object updatePrice(EstimationUploadModel model){
        HashMap<String,Object> data = new HashMap<>();
        if (model.getId()>0){
            EstimationUploadModel uploadModel = uploadModelService.findEstimateById(model.getId());
            if (uploadModel!=null){
                uploadModelService.save(model);
                data.put("status",1);
                return data;
            }
        }
        data.put("status",0);
        return data;
    }

}
