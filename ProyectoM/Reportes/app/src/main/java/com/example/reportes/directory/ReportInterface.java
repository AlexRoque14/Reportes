package com.example.reportes.directory;
import com.example.reportes.R;
import com.example.reportes.connection.Usuario;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReportInterface {

    @GET("api/report")
    Call<List<Report>> getReport();

    @POST("api/report")
    Call<Report> addReport(@Body Report report);

    @GET("api/report/{user_id}")
    Call<List<Report>> getById(@Path("user_id") int user_id);

    @PUT("api/report/{id}")
    Call<Report> updateReport(@Path("id") int id, @Body Report report);

    @DELETE("api/report/{id}")
    Call<Report> deleteReport(@Path("id") int id);

    @GET("api/report/{id}")
    Call<ResponseBody> getByIdReport(
            @Path("id") String id
    );

}

