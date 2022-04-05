package com.utudo.hwwd.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.models.models.Person;
import com.utudo.hwwd.models.SMModel;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HwTools {

    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDay(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDayLocal(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    public static String formatTimeToDay(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date formatDate(String dateString){
        //把字符串转化为时间
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = dateFormat.parse(dateString);
            return date;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public static long getTimeMillis(){
        return System.currentTimeMillis();
    }


    //md5 salt
    public static String hashPassword(String password){
        password = password + "_hwwd";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return new BigInteger(1, md.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }


    public static HttpServletRequest request(){
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();
        return request;
    }


    public static HttpServletResponse response(){
        HttpServletResponse response =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getResponse();
        return response;
    }

    public static String toJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


    public static Person getPersonFromJson(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,Person.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList getStringArrayFromJson(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,ArrayList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Socket message
     * @param json
     * @return
     */
    public static SMModel getSMessage(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json,SMModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Person getCurrentUser(){
        HttpSession session = HwTools.session();
        return (Person) session.getAttribute("user");
    }


    public static void setPersonInCookie(Person person){
        // create a cookie
        HttpServletResponse response = HwTools.response();
        Cookie cookie = new Cookie("user", person.getId()+"");
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        Cookie cookieType = new Cookie("type",person.getType()+"");
        cookieType.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookieType.setHttpOnly(true);
        cookieType.setPath("/");
        response.addCookie(cookieType);

    }




    public static Person getPersonByCookie(){
        Person person = new Person();
        HttpServletRequest request =  HwTools.request();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cook = cookies[i];
                if(cook.getName().equalsIgnoreCase("user")){
                    person.setId(Integer.parseInt(cook.getValue()));
                }
                if (cook.getName().equalsIgnoreCase("type")){
                    person.setType(Integer.parseInt(cook.getValue()));
                }
            }
        }
        return person;
    }


    public static String getResouceClassPath(){
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            return path.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getProjectPath(){
        return System.getProperty("user.dir");
    }


    public static String getResoucePathByName(String name){
        String path = Objects.requireNonNull(ClassUtils.getDefaultClassLoader().getResource(name)).getPath();
        try {
            return URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String getResoucePathBySystem(){
        Properties properties = System.getProperties();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>系统：" + properties.getProperty("os.name"));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>根路径" + properties.getProperty("user.dir"));

        String path = properties.getProperty("user.dir");
        if (properties.getProperty("os.name").toLowerCase().contains("win")) {
            path += "\\";
        }else {
            path += "/";
        }
        return path;
    }


    public static String readJsonFile(String fileName){
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
