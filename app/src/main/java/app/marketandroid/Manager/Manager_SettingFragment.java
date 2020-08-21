package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.marketandroid.R;
import app.marketandroid.Retrofit.DemandItem;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.PriceNLimitItem;
import app.marketandroid.Retrofit.ProductItem;
import app.marketandroid.SharedPreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manager_SettingFragment extends Fragment {
    ViewGroup viewGroup;
    ArrayList<myGroup> list = new ArrayList<>();
    ExpandableListView listView;
    MyAPI mMyAPI;
    Button add_product;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMyAPI();

        add_product = getActivity().findViewById(R.id.mg_set_add_btn);

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View v = LayoutInflater.from(getContext()).inflate(R.layout.add_product_dialog,null,false);
                builder.setView(v);
                final AlertDialog alertDialog = builder.create();
                final EditText edit = v.findViewById(R.id.product_edit);
                final Button ok = v.findViewById(R.id.product_ok);
                final Button cancel = v.findViewById(R.id.product_cancel);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<List<ProductItem>> get_product = mMyAPI.get_product(SharedPreferenceManager.getString(getContext(),"token"));
                        get_product.enqueue(new Callback<List<ProductItem>>() {
                            @Override
                            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                                List<ProductItem> mList = response.body();
                                assert mList != null;
                                for (ProductItem item : mList){
                                    if (item.getName().equals(edit.getText().toString())){
                                        Toast.makeText(getContext(), "이미 존재하는 품목입니다.", Toast.LENGTH_SHORT).show();
                                        edit.setText("");
                                    }else if (edit.getText().toString().equals("")){
                                        Toast.makeText(getContext(), "품목명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        final ProductItem productItem = new ProductItem();
                                        Call<ProductItem> post_product = mMyAPI.post_product(SharedPreferenceManager.getString(getContext(),"token"),productItem);
                                        productItem.setName(edit.getText().toString());
                                        post_product.enqueue(new Callback<ProductItem>() {
                                            @Override
                                            public void onResponse(Call<ProductItem> call, Response<ProductItem> response) {
                                                Toast.makeText(getContext(), "추가 완료", Toast.LENGTH_SHORT).show();
                                                alertDialog.dismiss();
                                            }

                                            @Override
                                            public void onFailure(Call<ProductItem> call, Throwable t) {

                                            }
                                        });
                                    }
                                    break;
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ProductItem>> call, Throwable t) {

                            }
                        });
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });


        Call<List<ProductItem>> get_product = mMyAPI.get_product(SharedPreferenceManager.getString(getContext(), "token"));
        get_product.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                List<ProductItem> mList = response.body();
                assert mList != null;
                for (final ProductItem item : mList) {
                    final myGroup temp = new myGroup(item.getName());
                    temp.child__num.add("구분");
                    temp.child_1.add("중량");
                    temp.child_2.add("1Box 당 가격");
                    temp.child_3.add("수량");
                    temp.child_4.add("id");

                    Call<List<DemandItem>> get_demand = mMyAPI.get_demands(SharedPreferenceManager.getString(getContext(), "token"));
                    get_demand.enqueue(new Callback<List<DemandItem>>() {
                        @Override
                        public void onResponse(Call<List<DemandItem>> call, Response<List<DemandItem>> response) {
                            List<DemandItem> dList = response.body();
                            int i = 1;
                            assert dList != null;
                            for (final DemandItem item2 : dList) {
                                if (item2.getProduct() == item.getId()) {
                                    temp.child__num.add(String.valueOf(i));
                                    temp.child_1.add(item2.getWeight());
                                    temp.child_5.add(String.valueOf(item2.getProduct()));
                                    temp.child_5.add(0,String.valueOf(item2.getProduct()));
                                    i++;

                                    Call<List<PriceNLimitItem>> get_priceNlimits = mMyAPI.get_priceNlimits(SharedPreferenceManager.getString(getContext(), "token"));
                                    get_priceNlimits.enqueue(new Callback<List<PriceNLimitItem>>() {
                                        @Override
                                        public void onResponse(Call<List<PriceNLimitItem>> call, Response<List<PriceNLimitItem>> response) {
                                            List<PriceNLimitItem> pList = response.body();
                                            assert pList != null;
                                            for (PriceNLimitItem item3 : pList) {
                                                if (item3.getDemand() == item2.getId()) {
                                                    temp.child_2.add(String.valueOf(item3.getPrice()));
                                                    temp.child_3.add(String.valueOf(item3.getLimit()));
                                                    temp.child_4.add(String.valueOf(item3.getDemand()));
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<PriceNLimitItem>> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DemandItem>> call, Throwable t) {
                        }
                    });
                    list.add(temp);
                }
                ExpandAdapter adapter = new ExpandAdapter(getContext().getApplicationContext(), R.layout.group_row, R.layout.child_row, list);
                listView = getActivity().findViewById(R.id.manager_setting_list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list.clear();
        Log.d("cycle","DestroyFragment");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_setting_fragment, container, false);
        return viewGroup;
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