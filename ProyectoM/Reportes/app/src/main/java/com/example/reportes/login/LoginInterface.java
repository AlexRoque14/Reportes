package com.example.reportes.login;

import android.database.Observable;

import com.example.reportes.connection.Usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    @POST("api/users/login")
    @FormUrlEncoded
    Call<ResponseBody>login(@Field("name") String email,
                            @Field("password") String password);

    @POST("api")
    Call<Usuario> nada ();


}
