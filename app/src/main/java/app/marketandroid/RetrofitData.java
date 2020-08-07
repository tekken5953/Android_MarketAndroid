package app.marketandroid;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitData {

    private MyAPI mMyAPI;

    public void initMyAPI(){

        Log.d("retrofit","initMyAPI : " + "서버주소");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("서버주소")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
    public void getCall(Call<List<PostItem>> call){
        call = mMyAPI.get_posts();
    }

    public void postCall(Call<PostItem> call){
        PostItem item = new PostItem();
        call = mMyAPI.post_posts(item);
    }
}
