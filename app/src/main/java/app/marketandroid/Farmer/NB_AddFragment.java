package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.marketandroid.R;
import app.marketandroid.Retrofit.DemandItem;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.PriceNLimitItem;
import app.marketandroid.Retrofit.ProductItem;
import app.marketandroid.Retrofit.SellItem;
import app.marketandroid.SharedPreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NB_AddFragment extends Fragment {

    ViewGroup viewGroup;
    TextView main_title;
    ArrayList<String> list_count = new ArrayList<>();
    ArrayList<String> list_weight = new ArrayList<>();
    ArrayAdapter<String> adapter_count;
    ArrayAdapter<String> adapter_weight;
    MyAPI mMyAPI;
    int text_position = 0;
    int count = 0;
    int product_id = 1;
    String[] product;
    int[] demand_id;
    int asd;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final RelativeLayout relativeLayout = getActivity().findViewById(R.id.add_main_layout);

        initMyAPI();

        final Call<List<ProductItem>> get_product = mMyAPI.get_product(SharedPreferenceManager.getString(getContext(), "token"));
        get_product.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                if (response.isSuccessful()) {
                    List<ProductItem> mList = response.body();
                    assert mList != null;
                    product = new String[mList.size()];
                    for (final ProductItem item1 : mList) {
                        for (int i = 1; count <= mList.size(); i++) {
                            if (item1.getId() == i) {
                                product[count] = item1.getName();
                                count++;
                                break;
                            }
                        }
                    }

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


                    MyAdapter adapter = new MyAdapter(
                            getContext(),
                            R.layout.gridview_row,       // GridView 항목의 레이아웃 row.xml
                            img,
                            product);    // 데이터

                    GridView gv = (GridView) getActivity().findViewById(R.id.gridView1);
                    gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용


                    // GridView 아이템을 클릭하면 상단 텍스트뷰에 position 출력
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {
                            text_position = position;
                            list_weight.add(0, "눌러서 선택");
                            Call<List<DemandItem>> get_demand = mMyAPI.get_demands(SharedPreferenceManager.getString(getContext(), "token"));
                            get_demand.enqueue(new Callback<List<DemandItem>>() {
                                @Override
                                public void onResponse(Call<List<DemandItem>> call, Response<List<DemandItem>> response) {
                                    List<DemandItem> mList = response.body();
                                    assert mList != null;
                                    demand_id = new int[mList.size()];
                                    for (DemandItem item2 : mList) {
                                        if (item2.getProduct() == position + 1) {
                                            list_weight.add(product_id, item2.getWeight());
                                            demand_id[product_id] = item2.getId();
                                            product_id++;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<DemandItem>> call, Throwable t) {
                                }
                            });
                            alertDialog();
                        }
                    });
                } else {
                    Log.d("retrofit", "Status Code : " + response.code());
                    TextView error = new TextView(getContext());
                    error.setText("세션이 만료되었습니다.\n다시 접속해주세요.");
                    error.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    error.setTextColor(Color.parseColor("#000000"));
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.addView(error);
                    relativeLayout.addView(linearLayout);
                }
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.d("retrofit", "Status Code : " + t.getMessage());

            }
        });

        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("출하물품 등록");
        list_count.add(0, "눌러서 선택");


        adapter_count = new ArrayAdapter<>(getContext(), R.layout.spinneritem, list_count);
        adapter_weight = new ArrayAdapter<>(getContext(), R.layout.spinneritem, list_weight);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.nb_add_fragment, container, false);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter_count.clear();
        adapter_weight.clear();
        count = 0;
        text_position = 0;
    }

    public void alertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.nb_add_dialog, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        final Button add_btn = view.findViewById(R.id.add_dial_add_btn);
        final Button cancel_btn = view.findViewById(R.id.add_dial_cancel_btn);
        final Spinner spinner_count = view.findViewById(R.id.add_dial_sell_spinner);
        final TextView add_dial_title = view.findViewById(R.id.add_dial_title);
        final Spinner spinner_weight = view.findViewById(R.id.add_dial_weight_spinner);
        final RelativeLayout relativeLayout = view.findViewById(R.id.add_dial_layout);
        final TextView price = view.findViewById(R.id.add_dial_price);
        final TextView weight = view.findViewById(R.id.add_dial_limit);
        add_dial_title.setText(product[text_position] + " 판매 신청");

        spinner_weight.setAdapter(adapter_weight);
        spinner_weight.setMinimumHeight(110);
        spinner_weight.setSelection(0);
        spinner_weight.setDropDownVerticalOffset(spinner_weight.getMinimumHeight());
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_weight.getSelectedItemPosition() != 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    list_count.clear();
                    list_count.add(0, "눌러서 선택");

                    Call<List<PriceNLimitItem>> get_priceNlimit = mMyAPI.get_priceNlimits(SharedPreferenceManager.getString(getContext(), "token"));
                    get_priceNlimit.enqueue(new Callback<List<PriceNLimitItem>>() {
                        @Override
                        public void onResponse(Call<List<PriceNLimitItem>> call, Response<List<PriceNLimitItem>> response) {
                            List<PriceNLimitItem> mList = response.body();
                            assert mList != null;
                            for (PriceNLimitItem item3 : mList) {
                                for (int i = spinner_weight.getSelectedItemPosition(); i <= spinner_weight.getSelectedItemPosition(); i++) {
                                    if (demand_id[i] == item3.getDemand()) {
                                        asd = item3.getId();
                                        price.setText(String.valueOf(item3.getPrice()));
                                        weight.setText(String.valueOf(item3.getLimit()));
                                        for (int j = 1; j <= item3.getLimit(); j++) {
                                            list_count.add(String.valueOf(j));
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PriceNLimitItem>> call, Throwable t) {
                        }
                    });
                } else {
                    relativeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                relativeLayout.setVisibility(View.GONE);
            }
        });

        spinner_count.setAdapter(adapter_count);
        spinner_count.setMinimumHeight(110);
        spinner_count.setSelection(0);
        spinner_count.setDropDownVerticalOffset(spinner_count.getMinimumHeight());

        alertDialog.setCanceledOnTouchOutside(false);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                final SellItem item = new SellItem();
                Call<SellItem> post_regist = mMyAPI.post_regists(SharedPreferenceManager.getString(getContext(), "token"), item);
                item.setUser(SharedPreferenceManager.getInt(getContext(), "hp"));
                item.setPriceNlimit(asd);
                item.setCount(spinner_count.getSelectedItemPosition());
                post_regist.enqueue(new Callback<SellItem>() {
                    @Override
                    public void onResponse(Call<SellItem> call, Response<SellItem> response) {
                        toastMsg("신청 완료");
                    }

                    @Override
                    public void onFailure(Call<SellItem> call, Throwable t) {
                    }
                });
                list_weight.clear();
                product_id = 1;
                alertDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_weight.clear();
                product_id = 1;
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    static class MyAdapter extends BaseAdapter {
        Context context;
        int layout;
        int[] img;
        String[] text;
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, int[] img, String[] text) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            this.text = text;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return text[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inf.inflate(layout, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
            TextView tv = (TextView) convertView.findViewById(R.id.textView1);
            iv.setImageResource(img[position]);
            tv.setText(text[position]);
            return convertView;
        }
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

    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) getActivity().findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(getContext());
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }

}