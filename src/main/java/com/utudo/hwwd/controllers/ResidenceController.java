package com.utudo.hwwd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.PageHelper;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.Documents;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.residenceModel.Residence;
import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import com.utudo.hwwd.models.residenceModel.ResidenceBookingRoom;
import com.utudo.hwwd.models.residenceModel.ResidenceType;
import com.utudo.hwwd.service.impl.DocumentsServiceImpl;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import com.utudo.hwwd.service.impl.UserServiceImpl;
import com.utudo.hwwd.service.impl.residence.ResidenceBookingRoomServiceImpl;
import com.utudo.hwwd.service.impl.residence.ResidenceBookingServiceImpl;
import com.utudo.hwwd.service.impl.residence.ResidenceServiceImpl;
import com.utudo.hwwd.service.impl.residence.ResidenceTypeServiceImpl;
import com.utudo.hwwd.service.impl.VilleServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.math.BigInteger;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class ResidenceController extends MainController {


    @Resource
    ResidenceTypeServiceImpl typeService;

    @Resource
    ResidenceServiceImpl residenceService;


    @Resource
    VilleServiceImpl villeService;


    @Resource
    PartnerServiceImpl partnerService;


    @Resource
    ResidenceBookingServiceImpl bookingService;

    @Resource
    UserServiceImpl userService;


    @Resource
    ResidenceBookingRoomServiceImpl bookingRoomService;

    @Resource
    DocumentsServiceImpl documentsService;

    @RequestMapping("/site/data/residences")
    public Object residenceListsData(){
        HashMap<String,Object> data = new HashMap<>();
        SqlHelper sqlHelper = new SqlHelper(Residence.class.getAnnotation(Table.class).name());
        sqlHelper.setSelect("select cout(*)");
        BigInteger count = residenceService.getCountBySql(sqlHelper.toSql());

        PageHelper pageHelper = new PageHelper();
        pageHelper.setLimit(20);
        pageHelper.setCount(Long.parseLong(count+""));
        pageHelper.setBaseUrl("/site/data/residences");

        String page = HwTools.request().getParameter("page");

        if (page!=null){
            pageHelper.setCurrentPage(Long.parseLong(page));
        }else{
            pageHelper.setCurrentPage(0);
        }

        sqlHelper.setSelect("select *");
        sqlHelper.setPageHelper(pageHelper);
        sqlHelper.setOrder("id","desc");

        ArrayList<Residence> residences = residenceService.getResidenceBySql(sqlHelper.toSql());
        if (residences!=null && !residences.isEmpty()){
            data.put("residences",residences);
            data.put("status",1);

        }else{
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping("/site/residence/{id}.html")
    public ModelAndView residenceDetail(@PathVariable("id") long id,ModelAndView mv){
        mv.setViewName("residence/detail");
        mv.addObject("id",id);
        return mv;
    }


    @RequestMapping("/site/residence/data/detail/{id}")
    public Object residenceDetailData(@PathVariable("id") long id){

        HashMap<String,Object> data = new HashMap<>();
        Residence residence = residenceService.findOneById(id);
        if (residence!=null){
            Ville ville = villeService.findVilleById(Long.parseLong(residence.getVille()));
            data.put("ville",ville);
            data.put("services", HwDatas.residenceServices);
            data.put("iconns",HwDatas.residenceServicesIconns);
            data.put("residence",residence);
            SqlHelper sqlHelper = new SqlHelper(ResidenceType.class.getAnnotation(Table.class).name());
            sqlHelper.addAndCondition("rid",residence.getId());
            sqlHelper.addAndCondition("uid",residence.getUid());
            sqlHelper.setLimit(" ");
            ArrayList<ResidenceType> types = typeService.getResidenceTypeBySql(sqlHelper.toSql());
            data.put("types",types);
            data.put("resTypes",HwDatas.dataResidenceTypes);
            data.put("charges",HwDatas.dataResidenceCharges);
            data.put("status",1);
            data.put("user", HwTools.getCurrentUser());
        }else{
            data.put("status",0);
        }
        return data;
    }



    @RequestMapping("/site/residence/booking/{id}/{rid}")
    public ModelAndView residenceBooking(@PathVariable("id") long id,@PathVariable("rid") long rid, ModelAndView mv){
        mv.setViewName("residence/booking");
        mv.addObject("id",id);
        mv.addObject("rid",rid);
        return mv;
    }

    @RequestMapping("/site/residence/booking/data/room/{rid}")
    public Object residenceBookingRoomData(@PathVariable("rid") long rid){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findeResidenceBookingRoomByUid(HwTools.getCurrentUser().getId());
        if (bookingRoom!=null){
            data.put("status",2);
        }else{
            ResidenceType room = typeService.findOneById(rid);
            if (room!=null){
                Residence residence = residenceService.findOneById(room.getRid());
                Partner partner = partnerService.findPartnerById(room.getUid());
                Ville ville = villeService.findVilleById(Long.parseLong(residence.getVille()));
                data.put("user",HwTools.getCurrentUser());
                data.put("ville",ville);
                data.put("types",HwDatas.dataResidenceTypes);
                data.put("residence",residence);
                data.put("partner",partner);
                data.put("room",room);
                data.put("status",1);
            }else{
                data.put("status",0);
            }
        }

        return data;
    }

    @RequestMapping("/user/residence/add/roomate")
    public Object residenceAddRoomate(){
        HashMap<String,Object> data = new HashMap<>();
        String telephone = HwTools.request().getParameter("telephone");
        String email = HwTools.request().getParameter("email");

        try{
            User user = userService.getUserByEmailAndTelephone(email,telephone);
            if (user!=null){
                data.put("user",user);
                data.put("status",1);
            }else{
                data.put("status",0);
            }
        }catch (Exception e){
            data.put("status",0);
        }


        return data;
    }


    @RequestMapping("/user/residence/booking/room")
    public Object residenceBookingRoom(ResidenceBookingRoom bookingRoom){
        HashMap<String,Object> data = new HashMap<>();
        try{
            bookingRoom.setUid(HwTools.getCurrentUser().getId());
            ArrayList<Integer> users = new ArrayList<>();
            if (bookingRoom.getUsers()!=null){
                bookingRoom.getUsers().forEach(u->{
                    if (u!=null){
                        users.add(u);
                    }
                });
            }
            bookingRoom.setUserJson(HwTools.toJson(users));
            bookingRoomService.save(bookingRoom);
            data.put("status",1);

        }catch (Exception e){
            e.printStackTrace();
            data.put("status",0);
        }


        data.put("bookingRoom",bookingRoom);
        return data;
    }



    @RequestMapping("/site/residence/booking/message")
    public Object residenceBookingMessage(ResidenceBooking residenceBooking){
        HashMap<String,Object> data = new HashMap<>();

        if (HwTools.getCurrentUser()!=null){
            Person person = HwTools.getCurrentUser();
            residenceBooking.setUid(person.getId());
            residenceBooking.setEmail(person.getEmail());
            residenceBooking.setTelephone(person.getTelephone());
            residenceBooking.setNom(person.getName());
            residenceBooking.setPrenom(person.getPrenom());
            residenceBooking.setSex(person.getSex());
        }else{
            //注册用户

        }

        try{
            bookingService.save(residenceBooking);
            data.put("status",1);
        }catch (Exception e){
            data.put("status",0);
        }

        return data;
    }


    @RequestMapping("/user/residence/data/apply")
    public Object resienceApplyData(){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findeResidenceBookingRoomByUid(HwTools.getCurrentUser().getId());
        if (bookingRoom!=null){
            Residence residence = residenceService.findOneById(bookingRoom.getRid());
            ResidenceType residenceType = typeService.findOneById(bookingRoom.getHid());
            Ville ville = villeService.findVilleById(Long.parseLong(residence.getVille()));
            if (bookingRoom.getUserJson()!=null){
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    ArrayList uids = objectMapper.readValue(bookingRoom.getUserJson(),ArrayList.class);
                    ArrayList users = userService.getUsersByIds(uids);
                    data.put("users",users);
                }catch (Exception e){
                    data.put("status",0);
                }
            }

            Documents documents = documentsService.findOneByUid(HwTools.getCurrentUser().getId());
            User myself = userService.findUserById(HwTools.getCurrentUser().getId());
            data.put("myself",myself);
            data.put("documents",documents);
            data.put("status",1);
            data.put("ville",ville);
            data.put("bookingRoom",bookingRoom);
            data.put("residence",residence);
            data.put("room",residenceType);
            data.put("resTypes",HwDatas.dataResidenceTypes);

        }else{
            data.put("status",0);
        }
        return data;
    }

    @RequestMapping("/user/residence/apply")
    public ModelAndView residenceApply(ModelAndView mv){
        mv.setViewName("user/residenceApply");

        return mv;
    }


    @RequestMapping("/user/residence/update/apply/month")
    public Object residenceUpdateMonth(ResidenceBookingRoom room){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findResidenceBookingRoomByUidAndId(HwTools.getCurrentUser().getId(),room.getId());
        if (bookingRoom!=null){
            bookingRoom.setMonth(room.getMonth());
            bookingRoomService.update(bookingRoom);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @RequestMapping("/user/residence/update/apply/entree")
    public Object residenceUpdateEntree(ResidenceBookingRoom room){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findResidenceBookingRoomByUidAndId(HwTools.getCurrentUser().getId(),room.getId());
        if (bookingRoom!=null){
            bookingRoom.setEntree(room.getEntree());
            bookingRoomService.update(bookingRoom);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }

    @RequestMapping("/user/residence/update/apply/message")
    public Object residenceUpdateMessage(ResidenceBookingRoom room){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findResidenceBookingRoomByUidAndId(HwTools.getCurrentUser().getId(),room.getId());
        if (bookingRoom!=null){
            bookingRoom.setMessage(room.getMessage());
            bookingRoomService.update(bookingRoom);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @RequestMapping("/user/residence/update/apply/cover")
    public Object residenceUpdateCover(ResidenceBookingRoom room){
        HashMap<String,Object> data = new HashMap<>();
        ResidenceBookingRoom bookingRoom = bookingRoomService.findResidenceBookingRoomByUidAndId(HwTools.getCurrentUser().getId(),room.getId());
        if (bookingRoom!=null){
            bookingRoom.setCover(room.getCover());
            bookingRoomService.update(bookingRoom);
            data.put("status",1);
            return data;
        }
        data.put("status",0);
        return data;
    }


    @RequestMapping("/site/residence/more")
    public ModelAndView residences(ModelAndView mv){
        mv.setViewName("residence/more");

        return mv;
    }


}
