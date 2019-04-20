package com.diplom.electronicrecord.service;


import com.diplom.electronicrecord.model.User;

public interface UserService  {

    User authenticate(String login, String password);

}
