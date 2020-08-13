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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.marketandroid.R;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.ProductItem;
import app.marketandroid.SharedPreferenceManager;
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
    String[] text;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initMyAPI();

        final Call<List<ProductItem>> get_product = mMyAPI.get_product(SharedPreferenceManager.getString(getContext(), "token"));
        get_product.enqueue(new Callback<List<ProductItem>>() {
            @Override
            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
                if (response.isSuccessful()) {
                    List<ProductItem> mList = response.body();
                    assert mList != null;
                    text = new String[mList.size()];
                    for (ProductItem item : mList) {
                        for (int i = 9; i <= mList.size() + 15; i++) {
                            if (item.getId() == i) {
                                text[count] = item.getName();
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
                            R.drawable.cucomber,
                            R.drawable.pumpkin,
                    };

                    MyAdapter adapter = new MyAdapter(
                            getContext(),
                            R.layout.gridview_row,       // GridView 항목의 레이아웃 row.xml
                            img,
                            text);    // 데이터

                    GridView gv = (GridView) getActivity().findViewById(R.id.gridView1);
                    gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용

                    // GridView 아이템을 클릭하면 상단 텍스트뷰에 position 출력
                    gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            text_position = position;
                            alertDialog();
                        }
                    });
                } else {
                    Log.d("retrofit", "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
                Log.d("retrofit", "Status Code : " + t.getMessage());
            }
        });

        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("출하물품 등록");

        list_weight.add(0, "눌러서 선택");
        list_count.add(0, "눌러서 선택");
        for (int i = 1; i <= 9; i++) {
            list_count.add(String.valueOf(i));
        }

        for (int i = 1; i <= 5; i++) {
            list_weight.add(String.valueOf(10 * i));
        }
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
        add_dial_title.setText(text[text_position] + " 판매 신청");

        spinner_weight.setAdapter(adapter_weight);
        spinner_weight.setMinimumHeight(110);
        spinner_weight.setSelection(0);
        spinner_weight.setDropDownVerticalOffset(spinner_weight.getMinimumHeight());
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_weight.getSelectedItemPosition() != 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
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
                toastMsg("신청 완료");
                alertDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        Log.d("retrofit", "initMyAPI : " + "http://13.209.84.206/");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.209.84.206/")
                .addConverterFactory(GsonConverterFactory.create())
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