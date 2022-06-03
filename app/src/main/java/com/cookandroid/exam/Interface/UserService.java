package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Register;
import com.cookandroid.exam.DTO.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user")
    Call<Integer> register(@Body Register register);

    @POST("/login")
    Call<Integer> login(@Body UserLogin userLogin);
}
