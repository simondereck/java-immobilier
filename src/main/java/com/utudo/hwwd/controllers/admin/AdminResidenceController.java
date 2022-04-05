package com.utudo.hwwd.controllers.admin;

import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.AnnonceModel;
import com.utudo.hwwd.models.Partner;
import com.utudo.hwwd.service.impl.AnnonceModelServiceImpl;
import com.utudo.hwwd.service.impl.PartnerServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
public class AdminResidenceController extends MainController {

    @Resource
    PartnerServiceImpl partnerService;

    @Resource
    AnnonceModelServiceImpl annonceModelService;

    @RequestMapping("/admin/residence/create")
    public ModelAndView createResidence(ModelAndView mv, Partner partner){
        mv.setViewName("adminResidence/create");
        String method = HwTools.request().getMethod();

        if ("POST".equalsIgnoreCase(method)){
            Partner partner1 = partnerService.findUserByEmailOrTelephone(partner.getEmail());
            if (partner1 == null){
                Partner partner2 = partnerService.findUserByEmailOrTelephone(partner.getTelephone());
                if (partner2==null){
                    partnerService.save(partner);
                    Partner partner3 = partnerService.findUserByEmail(partner.getEmail());
                    AnnonceModel annonceModel = new AnnonceModel();
                    annonceModel.setUid(partner3.getId());
                    annonceModel.setType(3);
                    annonceModelService.save(annonceModel);
                    partner3.setStatus(HwDatas.PERSON_TYPE_USER_ACTIVE);
                    partnerService.update(partner3);
                    mv.setViewName("redirect:/admin/annonce/partners/lists");
                    return mv;
                }
            }
            mv.addObject("message","邮箱或者电话不可用");
        }

        return mv;
    }
}
