package com.example.reportes.connection;

import com.example.reportes.connection.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioInterface {

    @GET("api/users")
    Call<List<Usuario>> getUsuario();

    @POST("api/users")
    Call<Usuario> addUser(@Body Usuario user);

    @GET("api/users/{id}")
    Call<Usuario> getByIdUser(@Path("id") int id);

    @GET("api/users/{name}")
    Call<Usuario> getByNameUser(@Path("name") String name);


    @PUT("api/users{id}")
    Call<Usuario> updateUser(@Path("id") int id, @Body Usuario user);

    @DELETE("api/users/{id}")
    Call<Usuario> deleteUser(@Path("id") int id);


}
