package com.utudo.hwwd.component;


import com.utudo.hwwd.models.*;
import com.utudo.hwwd.models.models.EstimateModel;
import com.utudo.hwwd.models.neuf.NeufModel;
import com.utudo.hwwd.models.residenceModel.Residence;
import com.utudo.hwwd.models.residenceModel.ResidenceBooking;
import com.utudo.hwwd.models.residenceModel.ResidenceBookingRoom;
import com.utudo.hwwd.models.residenceModel.ResidenceType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;

@Component
public class SqlRepository {

    @PersistenceContext
    EntityManager entityManager;

    public ArrayList<Admin> getAdminsBySql(String sql) {
        return (ArrayList<Admin>) entityManager.createNativeQuery(sql,Admin.class).getResultList();
    }

    public ArrayList<Ville> getVillesBySql(String sql) {
         return (ArrayList<Ville>) entityManager.createNativeQuery(sql,Ville.class).getResultList();
    }

    public ArrayList<User> getUsersBySql(String sql) {
        return (ArrayList<User>) entityManager.createNativeQuery(sql,User.class).getResultList();
    }


    public ArrayList<PartnerRdv> getRdvBySql(String sql){
        return (ArrayList<PartnerRdv>) entityManager.createNativeQuery(sql,PartnerRdv.class).getResultList();
    }

    public ArrayList<ApplyModel> getApplyBySql(String sql){
        return (ArrayList<ApplyModel>) entityManager.createNativeQuery(sql, ApplyModel.class).getResultList();
    }


    public BigInteger getCountBySql(String sql){
        return (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
    }


    public ArrayList<Partner> getPartnersBySql(String sql){
        return (ArrayList<Partner>) entityManager.createNativeQuery(sql,Partner.class).getResultList();
    }

    public ArrayList<Pays> getPaysBySql(String sql){
        return (ArrayList<Pays>) entityManager.createNativeQuery(sql,Pays.class).getResultList();
    }


    public ArrayList<Cobber> getCobbersBySql(String sql){
        return (ArrayList<Cobber>) entityManager.createNativeQuery(sql,Cobber.class).getResultList();
    }


    public ArrayList<CobberShop> getCobberShopsBySql(String sql){
        return (ArrayList<CobberShop>) entityManager.createNativeQuery(sql,CobberShop.class).getResultList();
    }



    public ArrayList<Universite> getUniversiteBySql(String sql){
        return (ArrayList<Universite>) entityManager.createNativeQuery(sql,Universite.class).getResultList();
    }


    public ArrayList<AnnonceLatLng> getAnnonceLatLngBySql(String sql){
        return (ArrayList<AnnonceLatLng>) entityManager.createNativeQuery(sql,AnnonceLatLng.class).getResultList();
    }


    public ArrayList<AnnonceBussiness> getAnnonceBussinessBySql(String sql){
        return (ArrayList<AnnonceBussiness>) entityManager.createNativeQuery(sql,AnnonceBussiness.class).getResultList();
    }

    public ArrayList<DemandeBussiness> getDemandeBussinessBySql(String sql){
        return (ArrayList<DemandeBussiness>) entityManager.createNativeQuery(sql,DemandeBussiness.class).getResultList();
    }

    public ArrayList<Annonce> getAnnonceBySql(String sql){
        return (ArrayList<Annonce>) entityManager.createNativeQuery(sql,Annonce.class).getResultList();
    }

    public ArrayList<BussinessModel> getBussinessModel(String sql){
        return (ArrayList<BussinessModel>) entityManager.createNativeQuery(sql,BussinessModel.class).getResultList();
    }

    public ArrayList<Needs> getNeedsBySql(String sql) {
        return (ArrayList<Needs>) entityManager.createNativeQuery(sql,Needs.class).getResultList();
    }



    public ArrayList<EstimationUploadModel> getEstimationUploadBySql(String sql) {
        return (ArrayList<EstimationUploadModel>) entityManager.createNativeQuery(sql,EstimationUploadModel.class).getResultList();
    }


    public ArrayList<EstimateModel> getEstimateModelBySql(String sql){
        return (ArrayList<EstimateModel>) entityManager.createNativeQuery(sql, EstimateModel.class).getResultList();
    }

    public ArrayList<Annonce> getAnnonces(String sql){
        return (ArrayList<Annonce>) entityManager.createNativeQuery(sql,Annonce.class).getResultList();
    }

    public ArrayList<EmployeeModel> getEmployeeModels(String sql){
        return (ArrayList<EmployeeModel>) entityManager.createNativeQuery(sql,EmployeeModel.class).getResultList();
    }


    public ArrayList<AnnonceModel> getAnnonceModelListsBySql(String sql){
        return (ArrayList<AnnonceModel>) entityManager.createNativeQuery(sql,AnnonceModel.class).getResultList();
    }


    public ArrayList<Residence> getResidencesBySql(String sql){
        return (ArrayList<Residence>) entityManager.createNativeQuery(sql,Residence.class).getResultList();
    }



    public ArrayList<ResidenceType> getResidencesTypeBySql(String sql){
        return (ArrayList<ResidenceType>) entityManager.createNativeQuery(sql,ResidenceType.class).getResultList();
    }


    public ArrayList<ResidenceBooking> getResidencesBookingBySql(String sql){
        return (ArrayList<ResidenceBooking>) entityManager.createNativeQuery(sql,ResidenceBooking.class).getResultList();
    }


    public ArrayList<ResidenceBookingRoom> getResidencesBookingRoomBySql(String sql){
        return (ArrayList<ResidenceBookingRoom>) entityManager.createNativeQuery(sql,ResidenceBookingRoom.class).getResultList();
    }


    public ArrayList<NeufModel> getNeufBySql(String sql){
        return (ArrayList<NeufModel>) entityManager.createNativeQuery(sql,NeufModel.class).getResultList();
    }
}
