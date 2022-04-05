package com.utudo.hwwd.filter;


import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.WebSocketServer;
import com.utudo.hwwd.models.BussinessModel;
import com.utudo.hwwd.service.impl.AdminServiceImpl;
import com.utudo.hwwd.service.impl.BussinessModelServiceImpl;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class PartnerBussinessFilter implements Filter {


    BussinessModelServiceImpl bussinessModelService;


    private static ApplicationContext applicationContext;

    //你要注入的service或者dao
    public static void setApplicationContext(ApplicationContext applicationContext) {
        PartnerBussinessFilter.applicationContext = applicationContext;
    }


    private ArrayList<String> exculdeUrl = new ArrayList<>(){{
        add("/partner/bussiness");
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



        long id = HwTools.getCurrentUser().getId();

        bussinessModelService = applicationContext.getBean(BussinessModelServiceImpl.class);

        BussinessModel bussinessModel = bussinessModelService.findModelByUid(id);
        if (bussinessModel==null){
            HwTools.response().sendRedirect("/partner/bussiness");
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
