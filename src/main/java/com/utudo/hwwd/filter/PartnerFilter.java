package com.utudo.hwwd.filter;


import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class PartnerFilter implements Filter {

    private ArrayList<String> exculdeUrl = new ArrayList<>(){{
        add("/partner/login");
        add("/partner/test");
        add("/partner/forgotPassword");
        add("/partner/resetPassword");
        add("/partner/checkUserExist");
        add("/partner/signup");
        add("/partner/verify");
        add("/partner/profile");
        add("/partner/account/sendEmail");
        add("/partner/signup/email");
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
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        Person person = HwTools.getCurrentUser();
        if (person!=null&&person.getType() == HwDatas.PERSON_TYPE_PARTNER){
            if (person.getStatus() == HwDatas.PERSON_TYPE_USER_PROFILE){
                HwTools.response().sendRedirect("/partner/profile");
            }else if (person.getStatus() == HwDatas.PERSON_TYPE_USER_BLOCK){
                HwTools.response().sendRedirect("/partner/verify");
            }else{
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else{
            HwTools.response().sendRedirect("/partner/login");
        }
    }

    @Override
    public void destroy() {

    }
}
