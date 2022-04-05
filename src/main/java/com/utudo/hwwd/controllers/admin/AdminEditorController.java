package com.utudo.hwwd.controllers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.FileUtils;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwEmail;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.models.SendEmailModel;
import com.utudo.hwwd.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class AdminEditorController extends MainController {

    @Resource
    PageEditorServiceImpl pageEditorService;

    public ArrayList<String> dataTypes = new ArrayList<>(){{
        add("---请选择---");
        add("投资我们");
        add("招贤纳才");
        add("关于我们");
    }};

    public ArrayList<String> dataArticleStatus = new ArrayList<>(){{
        add("---请选择---");
        add("保存");
        add("发布");
    }};

    @PostMapping(value = "/tmmedia/upload")
    public Object downloadFile(@RequestParam MultipartFile file) {
        HashMap<String, String> map = new HashMap<>();
        try {
            String fileName = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(fileName);
            long id = HwTools.getCurrentUser().getId();
            String name = id + "_" + HwTools.hashPassword(fileName) + "." + extension;
            if (FileUtils.upload(file, HwDatas.ADMIN_FILE_PATH, name)){
                map.put("location",  "/admins/" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("location", "");
        }
        return map;
    }

    @RequestMapping(value = "/admin/editor/lists")
    public ModelAndView lists(ModelAndView mv){
        mv.setViewName("adminEditor/lists");
        return mv;
    }




    @RequestMapping(value = "/admin/editor/add")
    public ModelAndView addNew(ModelAndView mv){
        mv.setViewName("adminEditor/addEditor");

        return mv;
    }

    @RequestMapping(value = "/admin/editor/update/{id}")
    public ModelAndView updateEditor(@PathVariable("id") long id, ModelAndView mv){
        mv.setViewName("adminEditor/updateEditor");
        mv.addObject("id",id);
        return mv;
    }

    @RequestMapping(value = "/admin/editor/data/{id}")
    public Object dataDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        PageEditor pageEditor = pageEditorService.findPageEditorById(id);
        if (pageEditor!=null){
            String temp = HtmlUtils.htmlUnescape(pageEditor.getDescription());
            pageEditor.setDescription(temp);
            data.put("pageEditor",pageEditor);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping(value = "/admin/editor/data/lists")
    public Object dataLists(){
        HashMap<String,Object> data = new HashMap<>();
        List<PageEditor> pages = pageEditorService.getPageEditorLists();
        if (pages!=null && pages.size()>0){
            data.put("pageEditors",pages);
            data.put("types",dataTypes);
            data.put("istatus",dataArticleStatus);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @RequestMapping(value = "/admin/editor/data")
    public Object dataEditor(PageEditor pageEditor){
        HashMap<String,Object> data = new HashMap<>();
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            PageEditor editor = pageEditorService.findByType(pageEditor.getPtype());
            if (editor!=null){
                String temp = HtmlUtils.htmlEscape(pageEditor.getDescription());
                editor.setDescription(temp);
                editor.setiStatus(pageEditor.getiStatus());
                pageEditorService.update(editor);
            }else{
                pageEditorService.save(pageEditor);
            }
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @Resource
    PartnerServiceImpl partnerService;




    @Resource
    UserServiceImpl userService;


    @Resource
    ApplyModelServiceImpl applyModelService;


    @Resource
    CobberServiceImpl cobberService;

    @Resource
    EstimationUploadModelServiceImpl uploadModelService;


    @Autowired
    private HwEmail mailUtils;

    @RequestMapping(value = "/admin/send/email")
    public ModelAndView adminEmail(ModelAndView mv){
        mv.setViewName("adminEmail/editor");
        return mv;
    }


    @RequestMapping(value = "/admin/send/email/data")
    public Object adminEmailSend(SendEmailModel sendEmailModel){
        HashMap<String,Object> data = new HashMap<>();
        data.put("result",sendEmailModel);
        data.put("status",1);

        HashMap<String,Object> dataParams = new HashMap<>();
        dataParams.put("description",sendEmailModel.getDescription());
        String templatePath =  "mail/admin.html";

        try{
            ArrayList<String> emails = HwTools.getStringArrayFromJson(sendEmailModel.getUsers());
            for (String email : emails) {
                logger.error("email:+++++"+email);
                mailUtils.sendThymeleafMail(sendEmailModel.getSubject(),email,dataParams,templatePath);
            }

        }catch (Exception exception){
            logger.error("到底哪里出错了:"+exception.toString());
        }
        return data;
    }



    @RequestMapping(value = "/admin/user/email")
    public Object getUserEmail(){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<User> users = userService.getUserEmail();
        if (users!=null && users.size()>0){
            data.put("users",users);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }


    @RequestMapping(value = "/admin/partner/email")
    public Object getPartnerEmail(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);
        ArrayList<Partner> partners = partnerService.getPartnerEmail();
        data.put("partners",partners);
        return data;
    }


    @RequestMapping(value = "/admin/apply/email")
    public Object getApplayEmail(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);
        ArrayList<ApplyModel> applys = applyModelService.getApplyEmail();
        data.put("applys",applys);
        return data;
    }


    @RequestMapping(value = "/admin/cobber/email")
    public Object getCobberEmail(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);
        ArrayList<Cobber> cobbers = cobberService.getCobberEmail();
        data.put("cobbers",cobbers);
        return data;
    }


    @RequestMapping(value = "/admin/estimation/email")
    public Object getEstimationEmail(){
        HashMap<String,Object> data = new HashMap<>();
        data.put("status",1);
        ArrayList<EstimationUploadModel> estimates = uploadModelService.getEstimationEmail();
        data.put("estimates",estimates);
        return data;
    }




}
