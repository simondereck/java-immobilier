package com.utudo.hwwd.service;

import com.utudo.hwwd.models.Documents;


public interface DocumentsService {

    public void save(Documents documents);

    public void update(Documents documents);

    public Documents findOneByUid(long uid);


}
