package app.marketandroid.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI{

    //로그인 정보 post
    @POST("/users/{pk}")
    Call<PostItem> post_users(@Path("pk") int pk, @Body PostItem post);

    //로그인 정보 전체 get
    @GET("/users/")
    Call<List<PostItem>> get_users();

    //로그인 정보 patch
    @PATCH("/users/{pk}/")
    Call<PostItem> patch_users(@Path("pk") int pk, @Body PostItem post);

    //로그인 정보 delete
    @DELETE("/users/{pk}/")
    Call<PostItem> delete_users(@Path("pk") int pk);

    //로그인 정보 특정 유저 get
    @GET("/users/{pk}/")
    Call<PostItem> get_users_pk(@Path("pk") int pk);

    //판매정보 post
    @POST("/sells/")
    Call<PostItem> post_sells(@Body PostItem post);

    //판매정보 전체 get
    @GET("/sells/")
    Call<List<PostItem>> get_sells();

    //판매정보 특정 유저 get
    @GET("/sells/{pk}/")
    Call<PostItem> get_sells_pk(@Path("pk") int pk);
}