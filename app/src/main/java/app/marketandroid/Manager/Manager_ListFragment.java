package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Manager_ListFragment extends Fragment {
    ViewGroup viewGroup;
    ArrayList<MGRecyclerItem> mData = new ArrayList<>();
    RecyclerView mRecyclerView = null;
    ArrayList<MGRecyclerItem> mList = new ArrayList<>();
    MGRecyclerViewAdapter mAdapter;
    Spinner mg_products_spinner, mg_weight_spinner;
    ArrayList<String> plist = new ArrayList<>();
    ArrayList<String> plist2 = new ArrayList<>();
    ArrayList<String> wlist2 = new ArrayList<>();
    ArrayList<String> wlist = new ArrayList<>();
    ArrayAdapter<String> padapter;
    ArrayAdapter<String> wadapter;
    MyAPI mMyAPI;
    EditText mg_fillter_edit;
    Button search;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMyAPI();

        search = getActivity().findViewById(R.id.search_btn);
        mg_products_spinner = getActivity().findViewById(R.id.mg_products_spinner);
        mg_weight_spinner = getActivity().findViewById(R.id.mg_weight_spinner);

        plist.add(0, "전체 보기");
        wlist2.add(0, "전체 보기");

        padapter = new ArrayAdapter<>(getContext(), R.layout.spinneritem, plist2);
        wadapter = new ArrayAdapter<>(getContext(), R.layout.spinneritem, wlist2);



        mg_weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mg_weight_spinner.getSelectedItemPosition() == 0) {
                    if (mg_products_spinner.getSelectedItemPosition() == 0) {
                        fillter_weight("", "");
                    } else {
                        fillter_product(mg_products_spinner.getSelectedItem().toString());
                    }
                } else {
                    fillter_weight(mg_products_spinner.getSelectedItem().toString(), mg_weight_spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mg_products_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                wlist2.clear();
                wlist.clear();
                wlist2.add(0, "전체 보기");
                mg_weight_spinner.setSelection(0);
                if (mg_products_spinner.getSelectedItemPosition() == 0) {
                    if (mg_weight_spinner.getSelectedItemPosition() == 0) {
                        fillter_product("");
                    }
                } else {
                    Call<List<SellItem>> get_sell = mMyAPI.get_sell(SharedPreferenceManager.getString(getContext(), "token"));
                    get_sell.enqueue(new Callback<List<SellItem>>() {
                        @Override
                        public void onResponse(Call<List<SellItem>> call, Response<List<SellItem>> response) {
                            List<SellItem> mList = response.body();
                            assert mList != null;
                            for (SellItem item : mList) {
                                if (mg_products_spinner.getSelectedItem().toString().equals(item.getPriceNlimit().getDemand().getProduct().getName())) {

                                    if (!wlist.contains(item.getPriceNlimit().getDemand().getWeight())) {
                                        wlist.add(item.getPriceNlimit().getDemand().getWeight() + "Kg");
                                        mAdapter.notifyDataSetChanged();
                                    }

                                }
                            }
                            Collections.sort(wlist); //오름차순
                            wlist2.addAll(wlist);

                            mAdapter.notifyDataSetChanged();
                            fillter_product(mg_products_spinner.getSelectedItem().toString());
                        }

                        @Override
                        public void onFailure(Call<List<SellItem>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mRecyclerView = getActivity().findViewById(R.id.mgrecyclerView);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new MGRecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);


        addItem(LocalDateTime.now().toString(), "농부1", "감자", "50Kg", "2Box","총 : 56,000￦", "박스당 : 28,000￦");
        addItem(LocalDateTime.now().toString(), "농부2", "옥수수", "70Kg", "4Box","총 : 126,000￦", "박스당 : 32,500￦");
        getActivity().findViewById(R.id.nothing).setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();

        Call<List<SellItem>> get_sell = mMyAPI.get_sell(SharedPreferenceManager.getString(getContext(), "token"));
        get_sell.enqueue(new Callback<List<SellItem>>() {
            @Override
            public void onResponse(Call<List<SellItem>> call, Response<List<SellItem>> response) {

                List<SellItem> nList = response.body();
                assert nList != null;
                for (final SellItem item : nList) {
                    String form_time = item.getCreated_at().substring(0, 10) + "  " + item.getCreated_at().substring(11, 16);
                    addItem(form_time, item.getUser().getName(), item.getPriceNlimit().getDemand().getProduct().getName(),
                            item.getPriceNlimit().getDemand().getWeight() + "Kg", item.getCount() + "Box", item.getCount() * item.getPriceNlimit().getPrice() + "원",
                            "(1Box 당 " + item.getPriceNlimit().getPrice() + "원)");
                    plist.add(item.getPriceNlimit().getDemand().getProduct().getName());

                    mAdapter.setOnItemClickListener(new MGRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            String tel = item.getUser().getPhone();
                            String tel_uri = tel.substring(0, 3) + "-" + tel.substring(3, 7) + "-" + tel.substring(7);
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel_uri));
                            getActivity().startActivity(intent);
                        }
                    });

                    if (mList.isEmpty()) {
                        getActivity().findViewById(R.id.nothing).setVisibility(View.VISIBLE);
                    } else {
                        getActivity().findViewById(R.id.nothing).setVisibility(View.GONE);
                    }

                    mAdapter.notifyDataSetChanged();

                }

                for (int i = 0; i < plist.size(); i++) {
                    if (!plist2.contains(plist.get(i))) {
                        plist2.add(plist.get(i));
                    }
                }

                mg_weight_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
                mg_products_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
                mg_products_spinner.setAdapter(padapter);
                mg_weight_spinner.setAdapter(wadapter);
                mg_products_spinner.setSelection(0);
                mg_weight_spinner.setSelection(0);
                mg_products_spinner.setDropDownVerticalOffset(180);
                mg_weight_spinner.setDropDownVerticalOffset(180);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SellItem>> call, Throwable t) {

            }
        });
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mg_fillter_edit = getActivity().findViewById(R.id.mg_fillter_edit);
        mg_fillter_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mg_weight_spinner.setSelection(0);
                        mg_products_spinner.setSelection(0);
                        String searchText = mg_fillter_edit.getText().toString();
                        fillter_edit(searchText);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Toast.makeText(getContext(), mg_fillter_edit.getText().toString() + "로 검색", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_list_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mData.clear();
        mList.clear();
        plist.clear();
        plist2.clear();
        wlist.clear();
        wlist2.clear();
    }

    public void addItem(String time, String user_name, String products, String weight, String count, String total_price, String personal_price) {
        MGRecyclerItem item = new MGRecyclerItem(time, user_name, products, weight, count, total_price, personal_price);
        item.setTime(time);
        item.setUser_name(user_name);
        item.setProductsStr(products);
        item.setWeightStr(weight);
        item.setCountStr(count);
        item.setTotal_priceStr(total_price);
        item.setPersonal_priceStr(personal_price);
        mList.add(item);
        mData.add(item);
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

    public void fillter_edit(String searchText) {
        mList.clear();
        if (searchText.length() == 0) {
            mList.addAll(mData);
        } else {
            for (MGRecyclerItem item : mData) {
                if (item.getUser_name().equals(searchText)) {
                    mList.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void fillter_product(String searchText) {
        mList.clear();
        if (searchText.length() == 0) {
            mList.addAll(mData);
        } else {
            for (MGRecyclerItem item : mData) {
                if (item.getProductsStr().equals(searchText)) {
                    mList.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void fillter_weight(String product, String searchText) {
        mList.clear();
        if (searchText.length() == 0) {
            mList.addAll(mData);
        } else {
            for (MGRecyclerItem item : mData) {
                if (item.getProductsStr().equals(product) && item.getWeightStr().equals(searchText)) {
                    mList.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}