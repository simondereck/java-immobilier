package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.PageEditor;
import com.utudo.hwwd.repository.PageEditorRepository;
import com.utudo.hwwd.service.PageEditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageEditorServiceImpl implements PageEditorService {

    @Autowired
    PageEditorRepository pageEditorRepository ;



    @Override
    public List<PageEditor> getPageEditorLists() {
        return pageEditorRepository.findAll();
    }

    @Override
    public PageEditor findPageEditorById(long id) {
        Optional<PageEditor> pageEditor = pageEditorRepository.findById(id);
        if (pageEditor.isPresent()){
            return pageEditor.get();
        }
        return null;
    }

    @Override
    public void save(PageEditor page) {
        String time = HwTools.getTime();
        page.setCtime(time);
        page.setUtime(time);
        pageEditorRepository.save(page);
    }

    @Override
    public void update(PageEditor page) {
        String time = HwTools.getTime();
        page.setUtime(time);
        pageEditorRepository.save(page);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public PageEditor findByType(int type) {
        return pageEditorRepository.findByTypeParamsNative(type);
    }

    @Override
    public PageEditor findByTypeAndStatus(int type, int status) {
        return pageEditorRepository.findByTypeAndStatusParamsNative(type,status);
    }


}
