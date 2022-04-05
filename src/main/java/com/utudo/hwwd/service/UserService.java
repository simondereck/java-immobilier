package com.utudo.hwwd.service;


import com.utudo.hwwd.models.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface UserService {
    public List<User> getUserList();

    public User findUserById(long id);

    public void save(User user);

    public void update(User user);

    public void delete(long id);

    public User checkUser(String email, String telephone);

    public User findUserByEmailOrTelephone(String email);

    public User findUserByEmail(String email);


    public User findUserByTelephone(String telephone);


    public User findUserByToken(String token);


    public ArrayList<User> getUsersByIds(ArrayList<Object> ids);


    public BigInteger getUserCountBySql(String sql);

    public ArrayList<User> getUsersBySql(String sql);

    public ArrayList<User> getLastedUser();

    public ArrayList<User> getUserEmail();

    public User getUserByEmailAndTelephone(String email,String telephone);
}
