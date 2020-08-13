package app.marketandroid.Farmer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import app.marketandroid.R;
import app.marketandroid.Retrofit.MyAPI;
import app.marketandroid.Retrofit.PostItem;
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
    ArrayList<String> text = new ArrayList<>();
    final PostItem item = new PostItem();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMyAPI();

        main_title = getActivity().findViewById(R.id.main_title);
        main_title.setText("출하물품 등록");

        list_weight.add(0,"눌러서 선택");
        list_count.add(0,"눌러서 선택");
        for (int i = 1; i <= 9; i++) {
            list_count.add(String.valueOf(i));
        }

        for (int i = 1; i <= 5; i++){
            list_weight.add(String.valueOf(10 * i));
        }
        adapter_count = new ArrayAdapter<>(getContext(), R.layout.spinneritem, list_count);
        adapter_weight = new ArrayAdapter<>(getContext(), R.layout.spinneritem, list_weight);

        int img[] = {
                R.drawable.recycle_potato,
                R.drawable.recycle_apple,
                R.drawable.recycle_potato,
                R.drawable.recycle_apple,
                R.drawable.recycle_potato,
                R.drawable.recycle_apple,
                R.drawable.recycle_potato,
        };

        for (int i = 1; i<= 7; i++){
            if( i % 2 == 1){
                text.add("감자");
            }else{
                text.add("사과");
            }
        }

        MyAdapter adapter = new MyAdapter(
                getActivity().getApplicationContext(),
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
        add_dial_title.setText(text.get(text_position) +" 판매 신청");

        spinner_weight.setAdapter(adapter_weight);
        spinner_weight.setMinimumHeight(110);
        spinner_weight.setSelection(0);
        spinner_weight.setDropDownVerticalOffset(spinner_weight.getMinimumHeight());
        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_weight.getSelectedItemPosition() != 0){
                    relativeLayout.setVisibility(View.VISIBLE);
                }else{
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
                Toast.makeText(getContext(), "등록 완료", Toast.LENGTH_SHORT).show();
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
        ArrayList<String> text;
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, int[] img, ArrayList<String> text) {
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
            return text.get(position);
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
            tv.setText(text.get(position));
            return convertView;
        }
    }

    private void initMyAPI() {
        Log.d("retrofit", "initMyAPI : " + "https://e61c7e832bc9.ngrok.io/");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://e61c7e832bc9.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(MyAPI.class);
    }
}