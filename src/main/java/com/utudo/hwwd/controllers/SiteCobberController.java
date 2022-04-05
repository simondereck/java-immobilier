package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.FileUtils;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.CobberShop;
import com.utudo.hwwd.models.cobber.PowerModel;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.service.OrderPowerService;
import com.utudo.hwwd.service.impl.CobberShopServiceImpl;
import com.utudo.hwwd.service.impl.OrderPowerServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class SiteCobberController extends MainController{


    @Resource
    CobberShopServiceImpl shopService;

    @Resource
    OrderPowerServiceImpl powerService;

    @RequestMapping(value = "/site/corporation/{id}.html")
    public ModelAndView corporation(ModelAndView mv, @PathVariable("id") long id){
        mv.setViewName("site/corporation");
        SqlHelper sqlHelper = new SqlHelper("hw_cobber_shop");
        sqlHelper.addAndCondition("status",1);
//        sqlHelper.addAndCondition("type",id);
        ArrayList<CobberShop> shops = shopService.getCobbersShopBySql(sqlHelper.toSql());
        mv.addObject("shops",shops);
        return mv;
    }

    @RequestMapping(value = "/site/corporation/detail/{id}.html")
    public ModelAndView cooDetail(ModelAndView mv,@PathVariable("id") long id){
        mv.setViewName("site/cooDetail");
        CobberShop shop = shopService.findCobberById(id);
        if (shop!=null){
            shop.setDescription(HtmlUtils.htmlUnescape(shop.getDescription()));
        }
        mv.addObject("types", HwDatas.dataRecommand);
        mv.addObject("shop",shop);
        return mv;
    }



    @RequestMapping(value = "/site/cobber/upload/file")
    public HashMap<String,Object> userUploadFile(@RequestParam("file") MultipartFile file){
        Person person = HwTools.getCurrentUser();

        HashMap<String,Object> result = new HashMap<>();
        String fileType = file.getContentType();
        String localPath = HwDatas.COBBER_FILE_PATH;
//        String localPath = HwDatas.USER_FILE_PATH + uid;
//        if (person!=null){
//             localPath = HwDatas.USER_FILE_PATH + person.getId();
//        }else{
//            localPath = HwDatas.COBBER_FILE_PATH;
//        }
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
        return result;
    }




    @RequestMapping(value = "/site/corporation/order/{id}.html")
    public ModelAndView orderCobber(ModelAndView mv, @PathVariable("id") long id, PowerModel powerModel){
        CobberShop shop = shopService.findCobberById(id);
        mv.addObject("shop",shop);
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            if (shop.getType()==3){
                Person person = HwTools.getCurrentUser();
                if (person!=null){
                    powerModel.setUid(person.getId());
                }
                powerModel.setCid(shop.getCid());
                powerService.save(powerModel);
                mv.setViewName("recommond/finish");
                return mv;
            }
        }
        mv.setViewName("site/orderCobber");
        return mv;
    }


}
