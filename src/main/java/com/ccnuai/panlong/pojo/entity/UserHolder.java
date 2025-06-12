package com.ccnuai.panlong.pojo.entity;

import com.ccnuai.panlong.pojo.DTO.UserDTO;

public class UserHolder {

    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }

}
