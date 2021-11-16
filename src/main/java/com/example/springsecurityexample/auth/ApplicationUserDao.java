package com.example.springsecurityexample.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectUserFromDbByUserName(String username);
}