package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.SMModel;
import com.utudo.hwwd.repository.SMModelRepository;
import com.utudo.hwwd.service.SMModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMModelServiceImpl implements SMModelService {

    @Autowired
    SMModelRepository smModelRepository;

    @Override
    public void save(SMModel smModel) {

        String time = HwTools.getTime();
        smModel.setCtime(time);
        smModel.setUtime(time);
        smModelRepository.save(smModel);
    }

    @Override
    public void update(SMModel smModel) {
        String time = HwTools.getTime();
        smModel.setUtime(time);
        smModelRepository.save(smModel);
    }

    @Override
    public void delete(SMModel smModel) {
        smModelRepository.delete(smModel);
    }
}
