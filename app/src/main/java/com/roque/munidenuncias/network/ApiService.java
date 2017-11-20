package com.roque.munidenuncias.network;

import com.roque.munidenuncias.model.Denuncia;
import com.roque.munidenuncias.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ROQUE on 22/10/2017.
 */

public interface ApiService {

    String API_BASE_URL = "https://findbank-api-cloned-roque363.c9users.io/";

    @GET("api/v1/usuarios")
    Call<List<User>> getUsuarios();

    @FormUrlEncoded
    @POST("api/v1/login")
    Call<ResponseMessage> loginUsuario(
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("api/v1/usuarios")
    Call<ResponseMessage> registrarUsuario(
            @Field("nombre") String nombre,
            @Field("email") String email,
            @Field("password") String password);

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();

    @FormUrlEncoded
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenuncia(@Field("titulo") String titulo,
                                         @Field("users_id") String users_id,
                                         @Field("descripcion") String descripcion,
                                         @Field("ubicacion") String ubicacion);
    @Multipart
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenunciaWithImage(
            @Part("titulo") RequestBody titulo,
            @Part("users_id") RequestBody users_id,
            @Part("descripcion") RequestBody descripcion,
            @Part("ubicacion") RequestBody ubicacion,
            @Part MultipartBody.Part imagen
    );


}
