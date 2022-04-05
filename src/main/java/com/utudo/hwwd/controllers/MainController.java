package com.utudo.hwwd.controllers;

import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
    public Logger logger = LoggerFactory.getLogger(MainController.class);


    public boolean checkIsSuperAdmin(){
        return HwTools.getCurrentUser().getPermission() == HwDatas.PERSON_TYPE_ADMIN_SUPER;
    }

    public boolean checkIsManageAdmin(){
        return HwTools.getCurrentUser().getPermission() == HwDatas.PERSON_TYPE_ADMIN_MANAGE;
    }

    public boolean checkIsNormalAdmin(){
        return HwTools.getCurrentUser().getPermission() == HwDatas.PERSON_TYPE_ADMIN_NORMAL;
    }


    public boolean checkIsStageAdmin(){
        return HwTools.getCurrentUser().getPermission() == HwDatas.PERSON_TYPE_ADMIN_STAGE;
    }


//    public boolean checkIs

}
