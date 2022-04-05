package com.utudo.hwwd.controllers.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.models.ResponseMessage;
import com.utudo.hwwd.service.impl.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class AdminController extends MainController {


    @Resource
    AdminServiceImpl adminService;

    @Resource
    AdminLocationServiceImpl locationServer;

    @Resource
    AlarmServiceImpl alarmService;

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    VilleServiceImpl villeService;

    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    UserServiceImpl userService;


    @Resource
    AdminLoginServiceImpl adminLoginService;


    @Resource
    AnnonceBussinessServiceImpl bussinessService;


    @Resource
    EmployeeModelServiceImpl employeeModelService;


    @RequestMapping("/admin")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("admin/index");
        return mv;
    }



    @RequestMapping("/admin/login")
    public ModelAndView login(ModelAndView mv,Admin admin){
        mv.setViewName("admin/login");
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            Admin adminObj = adminService.findAdminByEmailOrTelephone(admin.getEmail());
            if (adminObj!=null){
                if(adminObj.login(admin)){
                    //set cookie
                    HwTools.setPersonInCookie(HwTools.getCurrentUser());
                    String ip= HwTools.request().getRemoteAddr();
                    adminObj.setIp(ip);
                    adminService.update(adminObj);
                    AdminLocation location = locationServer.findLocationByAidAndDate(adminObj.getId(),HwTools.getDay());
                    if (location!=null){
                        locationServer.update(location);
                    }else{
                        AdminLocation adminLocation = new AdminLocation();
                        adminLocation.setAid(adminObj.getId());
                        adminLocation.setIp(ip);
                        locationServer.save(adminLocation);
                    }
                    mv.setViewName("redirect:/admin");
                    return mv;
                }
            }
        }
        mv.addObject("admin",admin);
        return mv;
    }


    @RequestMapping("/admin/location")
    public Object location(){
        HashMap<String,Object> data = new HashMap<>();
        HttpServletRequest request = HwTools.request();
        Person person = (Person) request.getSession().getAttribute("user");
        if (person!=null&&person.getType() == HwDatas.PERSON_TYPE_ADMIN){
            String lat = HwTools.request().getParameter("lat");
            String lng = HwTools.request().getParameter("lng");
            long id = person.getId();
            AdminLocation location = locationServer.findLocationByAidAndDate(id,HwTools.getDay());
            if (location!=null){
                if (person.getPermission()<9){
                    Admin admin = adminService.findAdminById(id);
                    Double lat1 = admin.getLoginLat();
                    Double lng1 = admin.getLoginLng();
                    if (lat1!=null && lng1!=null){
                        data.put("lat1",lat1);
                        data.put("lng1",lng1);
                        data.put("lat",lat);
                        data.put("lng",lng);
                        if(!checkLoginArea(lat1,lng1,Double.parseDouble(lat),Double.parseDouble(lng))){
                            Double lat2 = admin.getBackupLat();
                            Double lng2 = admin.getBackupLng();
                            if (lat2!=null && lng2!=null){
                                if (!checkLoginArea(lat2,lng2,Double.parseDouble(lat),Double.parseDouble(lng))){
                                    data.put("status",-1);
                                    data.put("lat2",lat2);
                                    data.put("lng2",lng2);
                                    return data;
                                }
                            }else{
                                data.put("status",-1);
                                return data;
                            }
                        }
                    }else{
                        data.put("status",-1);
                        return data;
                    }
                }
                location.setLat(Float.parseFloat(lat));
                location.setLng(Float.parseFloat(lng));
                locationServer.update(location);
                data.put("status",1);
                data.put("message","update location success");
                return data;
            }

        }
        data.put("status",0);
        data.put("message","please make sure your are admin or login");
        return data;
    }

    public boolean checkLoginArea(Double lat,Double lng,Double latCompare,Double lngCompare){

        if((lat >latCompare-0.0002 && lat < latCompare+0.0002) && (lng > lngCompare-0.0002 && lng <lngCompare + 0.0002)){
            return true;
        }
        return false;
    }

    @RequestMapping("/admin/test")
    public Object test(){

        String email = "513881204@163.com";
        Admin admin = adminService.findAdminByEmailOrTelephone(email);
        admin.signupLogin();
        HwTools.setPersonInCookie(HwTools.getCurrentUser());

        return HwTools.toJson(HwTools.getCurrentUser());
    }


    @RequestMapping("/admin/create_admin")
    public ModelAndView createAdmin(ModelAndView mv, Admin admin){
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            adminService.save(admin);
            mv.setViewName("redirect:/admin/lists");
            return mv;
        }
        mv.setViewName("admin/create_admin");
        mv.addObject("adminStatus",HwDatas.dataAdminStatus);
        mv.addObject("roles", HwDatas.dataAdmins);
        return mv;
    }


    @RequestMapping("/admin/delete/{id}")
    public Object deleteAdmin(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        if (checkIsSuperAdmin()){
            Admin admin = adminService.findAdminById(id);
            if (admin.getPermission()<HwDatas.PERSON_TYPE_ADMIN_SUPER){
                adminService.delete(id);
                data.put("status",1);
                return data;
            }
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping("/admin/lists")
    public ModelAndView adminLists(ModelAndView mv){
        if (!checkIsStageAdmin()){
            mv.setViewName("admin/lists");
            ArrayList<Admin> admins = adminService.getAdminLists();
            mv.addObject("admins",admins);
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }

    @RequestMapping("/admin/update/{id}")
    public ModelAndView updateAdmin(@PathVariable("id") long id,ModelAndView mv,Admin adminObj){
        if (!checkIsStageAdmin()) {
            Admin admin = adminService.findAdminById(id);
            String method = HwTools.request().getMethod();
            if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)) {
                if (!adminObj.getPassword().equalsIgnoreCase(admin.getPassword())) {
                    admin.setPassword(HwTools.hashPassword(adminObj.getPassword()));
                }
                admin.setLoginAddress(adminObj.getLoginAddress());
                admin.setBackupAddress(adminObj.getBackupAddress());
                admin.setLoginLat(adminObj.getLoginLat());
                admin.setLoginLng(adminObj.getLoginLng());
                admin.setBackupLat(adminObj.getBackupLat());
                admin.setBackupLng(adminObj.getBackupLng());
                admin.setBackupPostcode(adminObj.getBackupPostcode());
                admin.setLoginPostcode(adminObj.getLoginPostcode());
                admin.setPermission(adminObj.getPermission());
                admin.setUsername(adminObj.getUsername());
                admin.setAstatus(adminObj.getAstatus());
                admin.setEmail(adminObj.getEmail());
                admin.setTelephone(adminObj.getTelephone());
                adminService.update(admin);
                mv.setViewName("redirect:/admin/lists");
                return mv;
            }
            mv.setViewName("admin/updateAdmin");
            mv.addObject("admin", admin);
            mv.addObject("roles", HwDatas.dataAdmins);
            mv.addObject("adminStatus", HwDatas.dataAdminStatus);
        }else{
            mv.setViewName("redirect:/admin");
        }
        return mv;
    }

    @RequestMapping("/admin/detail/annonce/{id}")
    public Object getRowDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<String,Object>();
        Optional<Annonce> annonceOptional = annonceService.findAnnonceById(id);
        data.put("status",1);
        if (annonceOptional.isPresent()){
            Annonce annonce = annonceOptional.get();
            Partner partner = partnerService.findPartnerById(annonce.getPid());
            data.put("partner",partner);
        }

        return data;
    }


    @RequestMapping("/admin/partner/detail/{id}")
    public ModelAndView getPartner(@PathVariable("id") long id,ModelAndView mv){
        Partner partner = partnerService.findPartnerById(id);
        ArrayList<Annonce> annonces = annonceService.getAnnonceListsByPid(partner.getId());
        mv.setViewName("admin/partnerDetail");
        mv.addObject("partner",partner);
        mv.addObject("annonces",annonces);
        return mv;
    }


    @RequestMapping("/admin/logout")
    public ModelAndView logout(ModelAndView mv){
        mv.setViewName("redirect:/admin/login");

        long id = HwTools.getCurrentUser().getId();
        AdminLocation location = locationServer.findLocationByAidAndDate(id,HwTools.getDay());
        if (location!=null){
            location.setEtime(HwTools.getTime());
            locationServer.update(location);
        }

        HwTools.session().removeAttribute("user");
        return mv;
    }



    @RequestMapping("/admin/personnel")
    public ModelAndView personnel(ModelAndView mv){
        mv.setViewName("admin/personnel");
        return mv;
    }


    @RequestMapping("/admin/salaries")
    public ModelAndView salary(ModelAndView mv){
        mv.setViewName("admin/salary");

        return mv;
    }





    @RequestMapping(value = "/admin/set")
    public ModelAndView adminSet(ModelAndView mv,Admin adminObj){
        Admin admin = adminService.findAdminById(HwTools.getCurrentUser().getId());
        String method = HwTools.request().getMethod();
        if ("POST".equalsIgnoreCase(method)){
            admin.setEmail(adminObj.getEmail());
            admin.setTelephone(adminObj.getTelephone());
            adminService.update(admin);
            mv.setViewName("redirect:/admin");
            return mv;
        }
        mv.setViewName("admin/set");
        mv.addObject("admin",admin);
        return mv;
    }


    @RequestMapping(value = "/admin/data/partners")
    public Object dataPartners(Partner partner){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(Partner.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select count(*)");

        if (partner.getOrder()!=1){
            sqlHelper.setOrder("id","desc");
        }

        if (partner.getSex()>=0){
            sqlHelper.addAndCondition("sex",partner.getSex());
        }

        if (partner.getStatus()>=0){
            sqlHelper.addAndCondition("status",partner.getStatus());
        }

        if (partner.getNom()!=null){
            sqlHelper.addAndLikeCondition("nom",partner.getNom());
        }

        if (partner.getPrenom()!=null){
            sqlHelper.addAndLikeCondition("prenom",partner.getPrenom());
        }

        if (partner.getEmail()!=null){
            sqlHelper.addAndLikeCondition("email",partner.getEmail());
        }

        if (partner.getTelephone()!=null){
            sqlHelper.addAndLikeCondition("telephone",partner.getTelephone());
        }

        BigInteger count = partnerService.getPartnerCount(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(15);
        pageHelper.setBaseUrl("/admin/data/partners");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }

        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select *");

        ArrayList<Partner> partners = partnerService.getPartnersBySql(sqlHelper.toSql());

        if (partners!=null && partners.size()>0){
            data.put("partners",partners);
            data.put("pageHelper",pageHelper);
            data.put("status",1);
        }else{
            data.put("status",0);
        }

        return data;
    }

    @RequestMapping(value = "/admin/partner/lists")
    public ModelAndView partnerLists(ModelAndView mv){
        mv.setViewName("admin/partnerLists");
        return mv;
    }





    @RequestMapping(value = "/admin/user/lists")
    public ModelAndView userLists(ModelAndView mv){
        mv.setViewName("admin/userLists");

        SqlHelper sqlHelper = new SqlHelper("hw_user");
        sqlHelper.setSelect("select count(*)");
        sqlHelper.setOrder("id","desc");

        BigInteger count = userService.getUserCountBySql(sqlHelper.toSql());


        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setLimit(15);
        pageHelper.setBaseUrl("/admin/user/lists");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }

        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setSelect("select *");

        ArrayList<User> users = userService.getUsersBySql(sqlHelper.toSql());

        mv.addObject("users",users);
        mv.addObject("pageHelper",pageHelper);


        return mv;
    }

    @RequestMapping(value = "/admin/partner/update/{id}")
    public ModelAndView updateAdmin(@PathVariable("id") long id, ModelAndView mv,Partner partnerObj){
        Partner partner = partnerService.findPartnerById(id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin()&&"POST".equalsIgnoreCase(method)) {
            partner.setEmail(partnerObj.getEmail());
            partner.setTelephone(partnerObj.getTelephone());
            partner.setSex(partnerObj.getSex());
            partner.setNom(partnerObj.getNom());
            partner.setPrenom(partnerObj.getPrenom());
            partner.setStatus(partnerObj.getiStatus());
            if (!partner.getPassword().equalsIgnoreCase(HwTools.hashPassword(partnerObj.getPassword()))){
                partner.setPassword(partnerObj.getPassword());
            }
            partnerService.update(partner);
            mv.setViewName("redirect:/admin/partner/detail/"+id);
            return mv;
        }
        mv.addObject("partner",partner);
        mv.setViewName("admin/updatePartner");
        return mv;
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") long id,ModelAndView mv,User userObj){
        User user = userService.findUserById(id);
        String method = HwTools.request().getMethod();
        if (!checkIsStageAdmin() && "POST".equalsIgnoreCase(method)){
            user.setEmail(userObj.getEmail());
            user.setTelephone(userObj.getTelephone());
            user.setSex(userObj.getSex());
            user.setNom(userObj.getNom());
            user.setPrenom(userObj.getPrenom());
            user.setStatus(userObj.getiStatus());
            if (!user.getPassword().equalsIgnoreCase(HwTools.hashPassword(userObj.getPassword()))){
                user.setPassword(userObj.getPassword());
            }
            userService.update(user);
            mv.setViewName("redirect:/admin/user/lists");
            return mv;
        }
        mv.addObject("user",user);
        mv.setViewName("admin/updateUser");
        return mv;
    }



    @RequestMapping(value = "/admin/partner/login/{id}")
    public ModelAndView loginPartner(@PathVariable("id") long id,ModelAndView mv){
        Partner partner = partnerService.findPartnerById(id);
        if (!checkIsStageAdmin() && partner!=null){
            partner.adminLogin();
            mv.setViewName("redirect:/partner");
            return mv;
        }
        mv.setViewName("redirect:/");
        return mv;
    }

    @RequestMapping(value = "/admin/user/login/{id}")
    public ModelAndView loginUser(@PathVariable("id") long id,ModelAndView mv){
        User user = userService.findUserById(id);
        if (!checkIsStageAdmin() && user!=null){
            user.adminLogin();
            mv.setViewName("redirect:/user");
            return mv;
        }
        mv.setViewName("redirect:/");
        return mv;
    }


    @RequestMapping(value = "/admin/partner/delete/{id}")
    public Object deletePartner(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        if (!checkIsStageAdmin()){
            Partner partner =  partnerService.findPartnerById(id);
            //delete partner and delete annonces et bussiness
            try{

                ArrayList<Annonce> annonces = annonceService.getAnnonceListsByPid(partner.getId());
                if (annonces!=null && annonces.size()>0){
                    annonces.forEach(a->{
                        annonceService.delete(a.getId());
                    });
                }

                ArrayList<AnnonceBussiness> bussinesses = bussinessService.getAnnoncesByPid(partner.getId());
                if (bussinesses!=null && bussinesses.size()>0){
                    bussinesses.forEach(b->{
                        bussinessService.delete(b);
                    });
                }
                partnerService.delete(partner.getId());
                data.put("status",1);
            }catch (Exception e){
                data.put("status",0);
            }
        }else{
            data.put("status",0);
        }
        return data;
    }



    @RequestMapping(value = "/admin/setLoginInfo")
    public Object addLoginInfo(AdminLogin adminLogin){
        HashMap<String,Object> data = new HashMap<>();
        try{
            adminLogin.setUid(HwTools.getCurrentUser().getId());
            adminLoginService.save(adminLogin);
            data.put("status",1);
        }catch (Exception e){
            data.put("status",0);
        }

        return data;
    }

    @RequestMapping(value = "/admin/data/employees")
    public Object dataEmployess(Admin admin){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(Admin.class.getAnnotation(Table.class).name());

        if (admin.getEmail()!=null){
            sqlHelper.addAndLikeCondition("email",admin.getEmail());
        }

        if (admin.getTelephone()!=null){
            sqlHelper.addAndLikeCondition("telephone",admin.getTelephone());
        }

        if (admin.getUsername()!=null){
            sqlHelper.addAndLikeCondition("username",admin.getUsername());
        }

        if (admin.getPermission()!=null){
            if (admin.getPermission()==0){

            }else if (admin.getPermission()==1){
                sqlHelper.addAndCondition("permission",6);
            }else if (admin.getPermission()==2){
                sqlHelper.addAndCondition("permission",7);
            }else if (admin.getPermission()==3){
                sqlHelper.addAndCondition("permission",8);
            }else{
                sqlHelper.addAndCondition("permission",9);
            }
        }

        if (admin.getAstatus()!=null){
            sqlHelper.addAndCondition("astatus",admin.getAstatus());
        }


        if (admin.getId()>0){
            sqlHelper.setOrder("id","asc");
        }else{
            sqlHelper.setOrder("id","desc");
        }

        sqlHelper.setSelect("select count(*) ");

        BigInteger count = adminService.getCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/admin/data/employees");
        String page = HwTools.request().getParameter("page");
        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }
        sqlHelper.setSelect("select * ");
        sqlHelper.setPageHelper(pageHelper);

        ArrayList<Admin> admins = adminService.getAdminsBySql(sqlHelper.toSql());

        if (admins!=null && admins.size()>0){
            data.put("status",1);
            data.put("admins",admins);
            data.put("pageHelper",pageHelper);
        }else{
            data.put("status",0);
        }

        return data;
    }

    @RequestMapping(value = "/admin/employees")
    public ModelAndView employees(ModelAndView mv){
        mv.setViewName("adminEmploye/employeLists");
        return mv;
    }


    @RequestMapping(value = "/admin/employe/detail/{id}")
    public ModelAndView employeDetail(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("adminEmploye/detail");
        mv.addObject("id",id);
        return mv;
    }


    @RequestMapping(value = "/admin/employe/detail/data/{id}")
    public Object employeDetailData(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        Admin admin = adminService.findAdminById(id);
        if (admin!=null){
            data.put("admin",admin);
            data.put("status",1);
            data.put("date",HwTools.getDayLocal(1));
            ArrayList<AdminLogin> logins = adminLoginService.findLoginsByUidAndDate(id,HwTools.getDayLocal(1));
            data.put("logins",logins);
            AdminLocation location =  locationServer.findLastedLoginByAid(id);
            data.put("location",location);
        }else{
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping(value = "/admin/employe/info/{id}")
    public Object employeeDetail(@PathVariable("id") long id){
        HashMap<String,Object> data = new HashMap<>();
        Admin admin = adminService.findAdminById(id);
        if (admin!=null){
            data.put("admin",admin);
            data.put("status",1);
        }else{
            data.put("status",0);
        }
        return data;
    }


    @RequestMapping(value = "/admin/employe/date/search/{sdate}/{edate}/{id}")
    public Object employeeDateData(@PathVariable("id") long id,@PathVariable("sdate") String sdate,@PathVariable("edate") String edate){
        HashMap<String,Object> data = new HashMap<>();
        ArrayList<EmployeeModel> models = employeeModelService.findModelsByAidAndDates(id,sdate,edate);
        if (models!=null && models.size()>0){
            data.put("models",models);
            data.put("status",1);
        }else{
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping(value = "/admin/employe/date/search/{id}")
    public ModelAndView employeeDate(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("adminEmploye/date");
        mv.addObject("id",id);

        return mv;
    }
}
