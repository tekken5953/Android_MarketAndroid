package app.marketandroid.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI{

    //아이디, 비밀번호 입력 후 서버에 사용자인지 아닌지 확인
    //사용자인 경우 토큰 값 받아오기
    @POST("/api/v1/auth/")
    Call<LoginItem> post_users(@Body LoginItem post);

    //토큰 값 입력 후 사용자 정보 받아오기
    @GET("/api/v1/auth/info/")
    Call<LoginItem> get_my_info(@Header("Authorization") String token);

    //회원가입 정보 보내기
    @POST("/api/v1/auth/register/")
    Call<SignUpItem> post_signup_info(@Body SignUpItem post);

    //GridView 아이템 Setting
    @GET("/products/")
    Call<List<ProductItem>> get_product(@Header("Authorization") String token);

    @GET("/demands/")
    Call<List<DemandItem>> get_demands(@Header("Authorization") String token);

    @GET("/priceNlimits/")
    Call<List<PriceNLimitItem>> get_priceNlimits(@Header("Authorization") String token);

}