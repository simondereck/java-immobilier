package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Documents;
import com.utudo.hwwd.repository.DocumentsRepository;
import com.utudo.hwwd.service.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DocumentsServiceImpl implements DocumentsService {

    @Autowired
    DocumentsRepository documentsRepository;

    @Override
    public void save(Documents documents) {
        String time = HwTools.getTime();
        documents.setCtime(time);
        documents.setUtime(time);
        documentsRepository.save(documents);
    }

    @Override
    public void update(Documents documents) {
        documents.setUtime(HwTools.getTime());
        documentsRepository.save(documents);
    }

    @Override
    public Documents findOneByUid(long uid) {
        return documentsRepository.findDocumentsByUidParamsNative(uid);
    }
}
