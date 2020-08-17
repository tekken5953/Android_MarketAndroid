package app.marketandroid.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.marketandroid.R;
import app.marketandroid.Retrofit.DemandItem;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.PriceNLimitItem;
import app.marketandroid.Retrofit.ProductItem;
import app.marketandroid.SharedPreferenceManager;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMyAPI();

        Call<List<ProductItem>> get_product = mMyAPI.get_product(SharedPreferenceManager.getString(getContext(), "token"));
        get_product.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                List<ProductItem> mList = response.body();
                assert mList != null;
                for (final ProductItem item : mList) {
                    final myGroup temp = new myGroup(item.getName());

                    Call<List<DemandItem>> get_demand = mMyAPI.get_demands(SharedPreferenceManager.getString(getContext(),"token"));
                    get_demand.enqueue(new Callback<List<DemandItem>>() {
                        @Override
                        public void onResponse(Call<List<DemandItem>> call, Response<List<DemandItem>> response) {
                            List<DemandItem> dList = response.body();
                            int i = 1;
                            assert dList != null;
                            for (final DemandItem item2 : dList){
                                if (item2.getProduct() == item.getId()){
                                    temp.child__num.add(String.valueOf(i));
                                    temp.child_1.add(item2.getWeight());

                                    Call<List<PriceNLimitItem>> get_priceNlimits = mMyAPI.get_priceNlimits(SharedPreferenceManager.getString(getContext(),"token"));
                                    get_priceNlimits.enqueue(new Callback<List<PriceNLimitItem>>() {
                                        @Override
                                        public void onResponse(Call<List<PriceNLimitItem>> call, Response<List<PriceNLimitItem>> response) {
                                            List<PriceNLimitItem> pList = response.body();
                                            assert  pList != null;
                                            for (PriceNLimitItem item3 : pList){
                                                if (item3.getDemand() == item2.getId()){
                                                    temp.child_2.add(String.valueOf(item3.getPrice()));
                                                    temp.child_3.add(String.valueOf(item3.getLimit()));
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.manager_setting_fragment, container, false);
        return viewGroup;
    }

    private void initMyAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }

}