package com.utudo.hwwd.helpers;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.models.Admin;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.SMModel;
import com.utudo.hwwd.service.impl.AdminServiceImpl;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import com.utudo.hwwd.service.impl.SMModelServiceImpl;
import com.utudo.hwwd.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint("/websocket")   // 访问路径: ws://localhost:8080/websocket
@Component
public class WebSocketServer extends MainController {


    public Session session;

    public String token;

    public Person person;


    private AdminServiceImpl adminService;

    private UserServiceImpl userService;

    private PartnerServiceImpl partnerService;

    private SMModelServiceImpl smModelService;

    public static WebSocketServer componentClass;

    //此处是解决无法注入的关键
    private static ApplicationContext applicationContext;

    //你要注入的service或者dao
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    public static CopyOnWriteArraySet<WebSocketServer> webSocketSets =  new CopyOnWriteArraySet<WebSocketServer>();
    // 第一次连接调用

    public static CopyOnWriteArraySet<WebSocketServer> adminSocketSets = new CopyOnWriteArraySet<WebSocketServer>();

    public static HashMap<String,String> matchMaps = new HashMap<>();

    @OnOpen
    public void open(Session session) throws IOException {
        System.out.println("connect..");
        this.session = session;
        List<String> tokens = session.getRequestParameterMap().get("token");
        List<String> users = session.getRequestParameterMap().get("user");
        List<String> partners = session.getRequestParameterMap().get("partner");
        List<String> visitors = session.getRequestParameterMap().get("visitor");

        try {
            //此处是解决无法注入的关键
            if (tokens!=null&&tokens.size()>0){
                adminService = applicationContext.getBean(AdminServiceImpl.class);
                String token = tokens.get(0);
                Admin admin = adminService.findAdminByToken(token);
                if (admin!=null){
                    logger.error("admin"+admin.toString());
                    person = new Person(admin);
                    logger.error("person"+person.toString());
                    adminSocketSets.add(this);
                }
            }else{
                webSocketSets.add(this);
            }

            if (users!=null&&users.size()>0){
                userService = applicationContext.getBean(UserServiceImpl.class);
                String id = users.get(0);
                User user = userService.findUserById(Long.parseLong(id));
                if (user!=null){
                    person = new Person(user);
                    logger.error("person"+person.toString());
                }
            }

            if (partners!=null&&partners.size()>0){
                partnerService = applicationContext.getBean(PartnerServiceImpl.class);
                String id = partners.get(0);
                Partner partner = partnerService.findPartnerById(Long.parseLong(id));
                if (partner!=null){
                    person = new Person(partner);
                    logger.error("person"+person.toString());
                }
            }

            if (visitors!=null&&visitors.size()>0){
                String token = visitors.get(0);
                person = new Person();
                person.setType(-1);
                person.setName("游客"+session.getId());
                person.setToken(token);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        updateUserInfo();
        session.getBasicRemote().sendText("{\"server\": \"登陆成功!\"}");
    }


    // 关闭连接调用
    @OnClose
    public void close() {
        if (person.getType()!=3){
            webSocketSets.remove(this);
        }else{
            adminSocketSets.remove(this);
        }
        updateUserInfo();
        System.out.println("disconnect..");
    }

    // 接收消息
    @OnMessage
    public void message(String message, Session session) {
        System.out.println("client send: " + message);
        System.out.println("client info " + session.getId());
        smModelService = applicationContext.getBean(SMModelServiceImpl.class);
        SMModel smModel = HwTools.getSMessage(message);
        //todo
        if (person.getType()!=3){
            if (matchMaps.size()>0 && matchMaps.get(session.getId())!=null){
                String adminSessionId = matchMaps.get(session.getId());
                adminSocketSets.forEach(a->{
                    if (Integer.parseInt(adminSessionId)==Integer.parseInt(a.session.getId())){
                        try {
                            a.session.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else{
                if (adminSocketSets.size()>0){
                    adminSocketSets.forEach(a->{
                        try {
                            a.session.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }else{
                    //contact with us
                    try {
                        session.getBasicRemote().sendText("{\"message\":\"我们的客服暂时不在线,请留下您的联系方式，我们会第一时间和您联系！\",\"status\":0}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            //admin send message
            //do
            webSocketSets.forEach(w->{
                if (Integer.parseInt(w.session.getId()) == Integer.parseInt(smModel.getSendTo())){
                    try {
                        w.session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        //save data
        assert smModel != null;
        smModelService.save(smModel);

    }


    public void updateUserInfo(){
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String,Object> users = new HashMap<>();
        result.put("type","users");
        webSocketSets.forEach(w->{
            users.put(w.session.getId(),w.person);
        });
        result.put("users",users);
        adminSocketSets.forEach(a->{
//            if (a.person!=null && a.person.getType()==HwDatas.PERSON_TYPE_ADMIN){
            try {
                a.session.getBasicRemote().sendText(HwTools.toJson(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
//            }
        });
    }
}

