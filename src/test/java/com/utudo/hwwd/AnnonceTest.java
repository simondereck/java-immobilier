package com.utudo.hwwd;


import com.utudo.hwwd.helpers.HwHttpClient;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.Needs;
import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import com.utudo.hwwd.service.impl.AnnonceServiceImpl;
import com.utudo.hwwd.service.impl.NeedsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Optional;

public class AnnonceTest extends HwwdMainTest{

    @Resource
    AnnonceServiceImpl annonceService;

    @Resource
    NeedsServiceImpl needsService;
//    @Test
    void setAnnonces(){
        ArrayList<Annonce> annonces = annonceService.getAllAnnonces();
        annonces.forEach(a->{
//            a.getAddress();
//            a.getLocation();
            MapBoxGeocodingResponse response = HwHttpClient.sendGetReqeust(a.getAddress());
            logger.error("看看啊，这是啥："+response.toString());
        });
    }


//    @Test
//    @Rollback(value = false)
//    void DeleteNeeds(){
//        ArrayList<Needs>  needs = needsService.findAllNeddsByUid(1);
//        needs.forEach(n->{
//            needsService.delete(n.getId());
//        });
//    }

}
