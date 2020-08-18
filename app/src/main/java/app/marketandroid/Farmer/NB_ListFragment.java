package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.marketandroid.R;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.ProductItem;
import app.marketandroid.SharedPreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NB_ListFragment extends Fragment {

    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<NBRecyclerItem> mList = new ArrayList<>();
    NBRecyclerViewAdapter mAdapter;
    Drawable drawable_potato, drawable_apple;
    TextView main_title;
    ArrayList<NBRecyclerItem> mData = null;
    MyAPI mMyAPI;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.nbrecyclerView);

        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("금일 등록 현황");
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new NBRecyclerViewAdapter(mList);
        mData = mList;
        mRecyclerView.setAdapter(mAdapter);
        int[] img = {
                R.drawable.potato,
                R.drawable.sweetpotato,
                R.drawable.carrot,
                R.drawable.garlic,
                R.drawable.apple,
                R.drawable.sangchu,
                R.drawable.onion,
                R.drawable.cucomber,
                R.drawable.pumpkin
        };
        drawable_potato = ResourcesCompat.getDrawable(getResources(), img[0], null);
        drawable_apple = ResourcesCompat.getDrawable(getResources(), img[4], null);

        initMyAPI();

        addItem(drawable_potato, "감자", "20Kg", "3Box", "300,000원", "(1Box 당 100,000원)");
        addItem(drawable_apple, "사과", "10Kg", "5Box", "200,000원", "(1Box 당 40,000원)");
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nb_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mList.clear();
    }

    public void addItem(Drawable icon, String products, String weight, String count, String total_price, String personal_price) {
        NBRecyclerItem item = new NBRecyclerItem(icon, products, weight, count, total_price, personal_price);
        item.setIconDrawable(icon);
        item.setProductsStr(products);
        item.setWeightStr(weight);
        item.setCountStr(count);
        item.setTotal_priceStr(total_price);
        item.setPersonal_priceStr(personal_price);
        mList.add(item);
    }

    private void initMyAPI() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}