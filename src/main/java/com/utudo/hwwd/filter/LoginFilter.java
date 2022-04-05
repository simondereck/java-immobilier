package com.utudo.hwwd.filter;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class LoginFilter implements Filter {

    private ArrayList<String> exculdeUrl = new ArrayList<>(){{
        add("/user/login");
        add("/user/test");
        add("/user/forgotPassword");
        add("/user/resetPassword");
        add("/user/checkUserExist");
        add("/user/location/keywords");
        add("/user/signup");
        add("/user/signup/email");
        add("/user/verify");
        add("/user/account/sendEmail");
        add("/user/profile");
    }};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = HwTools.request();
        String path = request.getRequestURI();	//当前请求相对url
        String rootPath = request.getContextPath();


        for (int i = 0; i < exculdeUrl.size(); i++) {
            if (path.equalsIgnoreCase(rootPath+exculdeUrl.get(i))){
                try {
                    filterChain.doFilter(servletRequest,servletResponse);
                } catch (IOException | ServletException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        Person person = HwTools.getCurrentUser();
        if (person!=null&&person.getType() == HwDatas.PERSON_TYPE_USER){
            if (person.getStatus() == HwDatas.PERSON_TYPE_USER_PROFILE){
                HwTools.response().sendRedirect("/user/profile");
            }else if (person.getStatus() == HwDatas.PERSON_TYPE_USER_BLOCK){
                HwTools.response().sendRedirect("/user/verify");
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else{
            HwTools.response().sendRedirect("/user/login");
        }
    }

    @Override
    public void destroy() {

    }
}
