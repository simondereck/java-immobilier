package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.component.SqlRepository;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.helpers.SqlHelper;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.repository.UserRepository;
import com.utudo.hwwd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SqlRepository sqlRepository;


    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        String time = HwTools.getTime();
        user.setUtime(time);
        user.setCtime(time);
        user.setStatus(HwDatas.PERSON_TYPE_USER_PROFILE);
        user.setPassword(HwTools.hashPassword(user.getPassword()));
        userRepository.save(user);
    }




    @Override
    public void update(User user) {
        user.setUtime(HwTools.getTime());
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User checkUser(String email, String telephone) {
        return new User();
    }

    @Override
    public User findUserByEmailOrTelephone(String email) {
        return userRepository.findByEmailOrTelephoneParamsNative(email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailParamsNative(email);
    }

    @Override
    public User findUserByTelephone(String telephone) {
        return userRepository.findUserByTelephoneParamsNative(telephone);
    }

    @Override
    public User findUserByToken(String token) {
        return userRepository.findUserByTokenParamsNative(token);
    }

    @Override
    public ArrayList<User> getUsersByIds(ArrayList<Object> ids) {
        SqlHelper sqlHelper = new SqlHelper("hw_user");
        sqlHelper.addInCondition("id",ids);
        return sqlRepository.getUsersBySql(sqlHelper.toSql());
    }

    @Override
    public BigInteger getUserCountBySql(String sql) {
        return sqlRepository.getCountBySql(sql);
    }

    @Override
    public ArrayList<User> getUsersBySql(String sql) {
        return sqlRepository.getUsersBySql(sql);
    }

    @Override
    public ArrayList<User> getLastedUser() {
        return userRepository.findLastedUser();
    }

    @Override
    public ArrayList<User> getUserEmail() {
        SqlHelper sqlHelper = new SqlHelper(User.class.getAnnotation(Table.class).name());
        sqlHelper.addCondition("email!=null");
        sqlHelper.addAndCondition("status",1);
        sqlHelper.setLimit(" ");
        return sqlRepository.getUsersBySql(sqlHelper.toSql());
    }

    @Override
    public User getUserByEmailAndTelephone(String email, String telephone) {
        return userRepository.findByEmailAndTelephoneParamsNative(email,telephone);
    }


}
