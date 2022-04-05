package com.utudo.hwwd.filter;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.models.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class AdminFilter implements Filter {

    private ArrayList<String> exculdeUrl = new ArrayList<>(){{
        add("/admin/login");
        add("/admin/test");
        add("/admin/location");
    }};

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
        if (person!=null){
            if (person.getType() == HwDatas.PERSON_TYPE_ADMIN){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                HwTools.response().sendRedirect("/");
            }
        }else{
            HwTools.response().sendRedirect("/admin/login");
        }
    }
}
