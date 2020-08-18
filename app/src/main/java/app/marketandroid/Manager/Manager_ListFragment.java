package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Manager_ListFragment extends Fragment {
    ViewGroup viewGroup;
    ArrayList<MGRecyclerItem> mData = new ArrayList<>();
    RecyclerView mRecyclerView = null;
    ArrayList<MGRecyclerItem> mList = new ArrayList<>();
    MGRecyclerViewAdapter mAdapter;
    Spinner mg_products_spinner,mg_weight_spinner;
    ArrayList<String> plist = new ArrayList<>();
    ArrayList<String> wlist = new ArrayList<>();
    ArrayAdapter<String> padapter;
    ArrayAdapter<String> wadapter;
    MyAPI mMyAPI;
    EditText mg_fillter_edit;
    ImageView search_img;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        search_img = getActivity().findViewById(R.id.search_img);
        mg_products_spinner = getActivity().findViewById(R.id.mg_products_spinner);
        mg_weight_spinner = getActivity().findViewById(R.id.mg_weight_spinner);
        plist.add(0,"전체 보기");
        plist.add(1,"농부1");
        plist.add(2,"농부2");
        plist.add(3,"농부3");
        plist.add(4,"농부4");
        wlist.add(0,"전체 보기");
        wlist.add(1,"감자");
        wlist.add(2,"고구마");
        wlist.add(3,"사과");
        wlist.add(4,"당근");

        padapter = new ArrayAdapter<>(getContext(),R.layout.spinneritem,plist);
        wadapter = new ArrayAdapter<>(getContext(),R.layout.spinneritem,wlist);

        mg_weight_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mg_products_spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mg_products_spinner.setAdapter(padapter);
        mg_weight_spinner.setAdapter(wadapter);
        mg_products_spinner.setSelection(0);
        mg_weight_spinner.setSelection(0);
        mg_products_spinner.setDropDownVerticalOffset(180);
        mg_weight_spinner.setDropDownVerticalOffset(180);

        mg_products_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mg_products_spinner.getSelectedItemPosition() == 0){
                    if (mg_weight_spinner.getSelectedItemPosition() == 0){
                        fillter("");
                    }else{
                        fillter(mg_weight_spinner.getSelectedItem().toString());
                    }
                }else{
                    fillter(mg_products_spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mg_weight_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mg_weight_spinner.getSelectedItemPosition() == 0){
                    if (mg_products_spinner.getSelectedItemPosition() == 0){
                        fillter("");
                    }else{
                        fillter(mg_products_spinner.getSelectedItem().toString());
                    }
                }else{
                    fillter(mg_weight_spinner.getSelectedItem().toString());
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

        initMyAPI();
        Call<List<SellItem>> get_sell = mMyAPI.get_sell(SharedPreferenceManager.getString(getContext(),"token"));
        get_sell.enqueue(new Callback<List<SellItem>>() {
            @Override
            public void onResponse(Call<List<SellItem>> call, Response<List<SellItem>> response) {
                List<SellItem> mList = response.body();
                assert mList != null;
                for (SellItem item : mList){
                    addItem(item.getUser()+"",item.getId()+"", item.getPriceNlimit()+"Kg",item.getCount()+"Box","가격 x Box 원","(1Box 당 "+item.getPriceNlimit()+"원)");
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<SellItem>> call, Throwable t) {

            }
        });
        addItem("농부1","감자","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부2","고구마","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부3","당근","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부4","사과","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부5","감자","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부6","복숭아","20Kg","3Box","300,000원","(1Box 당 100,000원)");
        addItem("농부7","가지","20Kg","3Box","300,000원","(1Box 당 100,000원)");
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
                search_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String searchText = mg_fillter_edit.getText().toString();
                        fillter(searchText);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Toast.makeText(getContext(),  mg_fillter_edit.getText().toString()+"로 검색", Toast.LENGTH_SHORT).show();
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
    }

    public void addItem(String user_name, String products, String weight, String count, String total_price, String personal_price) {
        MGRecyclerItem item = new MGRecyclerItem(user_name,products,weight,count,total_price,personal_price);
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

    public void fillter(String searchText) {
        mList.clear();
        if(searchText.length() == 0)
        {
            mList.addAll(mData);
        }
        else
        {
            for( MGRecyclerItem item : mData)
            {
                if(item.getUser_name().contains(searchText) || item.getProductsStr().contains(searchText))
                {
                    mList.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

}