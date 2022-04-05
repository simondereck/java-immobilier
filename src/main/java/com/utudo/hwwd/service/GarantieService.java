package com.utudo.hwwd.service;


import com.utudo.hwwd.models.Garantie;
import java.util.Optional;

public interface GarantieService {

    public Optional<Garantie> findGarantieById(long id);

    public void save(Garantie garantie);

    public void update(Garantie garantie);

    public void delete(long id);

    public Garantie findGarantieByUid(long uid);

    public Optional<Garantie> findGarantieByNidAndUid(long nid, long uid);

    public Garantie findGarantieByIdAndUid(long id,long uid);
}
