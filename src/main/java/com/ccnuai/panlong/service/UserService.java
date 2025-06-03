package com.ccnuai.panlong.service;

public interface UserService {
    void register(String username, String password);

    String login(String username, String password);

    void bind(String studentNumber, String userId, String name);
}
