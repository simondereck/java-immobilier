package com.utudo.hwwd.service;


import com.utudo.hwwd.models.PageEditor;
import com.utudo.hwwd.models.Pays;

import java.util.List;

public interface PageEditorService {

    public List<PageEditor> getPageEditorLists();

    public PageEditor findPageEditorById(long id);

    public void save(PageEditor page);

    public void update(PageEditor page);

    public void delete(long id);

    public PageEditor findByType(int type);

    public PageEditor findByTypeAndStatus(int type,int status);
}
