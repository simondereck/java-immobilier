package com.utudo.hwwd.service;

import com.utudo.hwwd.models.LocationRegion;
import com.utudo.hwwd.models.PartnerRdv;

public interface LocationRegionService{

    public void save(LocationRegion region);

    public void update(LocationRegion region);

    public LocationRegion findOneByCode(long code);
}
