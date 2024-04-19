package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.marketandroid.R;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.SellItem;
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
    Drawable drawable,noimg;
    TextView main_title, nothing;
    MyAPI mMyAPI;
    RelativeLayout relativeLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getActivity().findViewById(R.id.nbrecyclerView);
        relativeLayout = getActivity().findViewById(R.id.nb_list_relative);
        nothing = getActivity().findViewById(R.id.nothing2);
        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("금일 등록 현황");

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new NBRecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);


        final int[] img = {
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

        initMyAPI();

        Call<List<SellItem>> get_sell = mMyAPI.get_sell(SharedPreferenceManager.getString(getContext(), "token"));
        get_sell.enqueue(new Callback<List<SellItem>>() {
            @Override
            public void onResponse(Call<List<SellItem>> call, Response<List<SellItem>> response) {
                if (response.isSuccessful()){
                    List<SellItem> mList = response.body();
                    assert mList != null;
                    for (SellItem item : mList) {
                        String form_time = item.getCreated_at().substring(0, 10) + "  " + item.getCreated_at().substring(11, 16);

                        drawable = ResourcesCompat.getDrawable(getResources(), img[item.getPriceNlimit().getDemand().getProduct().getId() - 1], null);
                        noimg = ResourcesCompat.getDrawable(getResources(), R.drawable.noimg, null);
                        if (drawable != null){
                            addItem(drawable, form_time, item.getPriceNlimit().getDemand().getProduct().getName() + "", item.getPriceNlimit().getDemand().getWeight() + "Kg",
                                    item.getCount() + "개", (item.getCount() * item.getPriceNlimit().getPrice()) + "원", "(1Box 당 " + item.getPriceNlimit().getPrice() + "원)");
                        }else{
                            addItem(noimg, form_time, item.getPriceNlimit().getDemand().getProduct().getName() + "", item.getPriceNlimit().getDemand().getWeight() + "Kg",
                                    item.getCount() + "개", (item.getCount() * item.getPriceNlimit().getPrice()) + "원", "(1Box 당 " + item.getPriceNlimit().getPrice() + "원)");
                        }
                        mAdapter.notifyDataSetChanged();

                        if (mList.isEmpty()) {
                            nothing.setVisibility(View.VISIBLE);
                        } else {
                            nothing.setVisibility(View.GONE);
                        }
                    }
                }else{
                    TextView error = new TextView(getContext());
                    error.setText("세션이 만료되었습니다.\n다시 접속해주세요.");
                    error.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    error.setTextColor(Color.parseColor("#000000"));
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.addView(error);
                    relativeLayout.removeAllViews();
                    relativeLayout.addView(linearLayout);
                }
            }

            @Override
            public void onFailure(Call<List<SellItem>> call, Throwable t) {
            }
        });

        addItem(ResourcesCompat.getDrawable(getResources(), R.drawable.potato,null), LocalDateTime.now().toString(),
                "감자", "50" + "Kg",
                "2" + "Box",  "56,000" + "원", "(1Box 당 " + "28,000" + "원)");
        addItem(ResourcesCompat.getDrawable(getResources(), R.drawable.carrot,null),
                LocalDateTime.now().toString(), "옥수수", "70Kg", "4Box", "126,000원","32,500원");
        nothing.setVisibility(View.GONE);
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

    public void addItem(Drawable icon, String time, String products, String weight, String count, String total_price, String personal_price) {
        NBRecyclerItem item = new NBRecyclerItem(icon, time, products, weight, count, total_price, personal_price);

        item.setIconDrawable(icon);
        item.setTimeStr(time);
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